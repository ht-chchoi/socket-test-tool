package com.ht.sockettesttool.core.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class NettyClientManager {
  private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
  private String host;
  private Integer port;
  private ChannelPipeline channelPipeline;

  private NettyClientManager() {
  }

  private static class LazyHolder {
    public static final NettyClientManager INSTANCE = new NettyClientManager();
  }

  public static NettyClientManager getInstance() {
    return LazyHolder.INSTANCE;
  }

  public Result connect(final String host, final String port) {
    if (this.isChannelAvailable()) {
      log.warn("이미 채널이 열려있습니다. host: {}, port: {}", this.host, this.port);
      return Result.INVALID_STATE;
    }

    if (host == null|| host.isEmpty()) {
      log.error("invalid host, host: {}", host);
      return Result.INVALID_PARAMETER;
    }
    this.host = host;

    try {
      this.port = Integer.parseInt(port);
    } catch (NumberFormatException e) {
      log.error("invalid port, port: {}", port);
      return Result.INVALID_PARAMETER;
    }

    log.info("연결 시도 중 >>> host: {}, port: {}", this.host, this.port);

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap
          .group(this.workerGroup)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.SO_KEEPALIVE, true)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(final SocketChannel ch) {
              channelPipeline = ch.pipeline();
            }
          });
      bootstrap.connect(this.host, this.port).sync();
    } catch (Exception e) {
      log.error("fail to connect with server, host: {}, port: {}", this.host, this.port, e);
      return Result.CONNECTION_FAIL;
    }

    log.info("연결에 성공! host: {}, port: {}", this.host, this.port);
    return Result.CONNECTION_SUCCESS;
  }

  public void disConnect() {
    if (!this.isChannelAvailable()) {
      log.warn("열려있는 채널이 없습니다.");
      return;
    }

    try {
      this.channelPipeline.channel().close().sync();
    } catch (InterruptedException e) {
      log.error("fail to close channel: ", e);
    }
  }

  public Result sendMessage(final String message, final Charset charset) {
    if (!isChannelAvailable()) {
      log.error("channel not ready!");
      return Result.INVALID_STATE;
    }

    if (message == null || message.isEmpty() || charset == null) {
      log.error("invalid message or charset, message: {}\ncharset: {}", message, charset);
      return Result.INVALID_PARAMETER;
    }

    if (this.channelPipeline.get("reader") == null) {
      this.channelPipeline.addLast("reader", new ReadMessageHandler(charset));
    } else {
      this.channelPipeline.replace("reader", "reader", new ReadMessageHandler(charset));
    }

    try {
      log.info("[send] {}", message);
      this.channelPipeline.writeAndFlush(Unpooled.directBuffer()
              .writeBytes((String.format("%08d", message.getBytes(charset).length) + message).getBytes(charset)))
          .sync();
    } catch (InterruptedException e) {
      log.error("fail to send message, host: {}, port: {}, msg: {}", this.host, this.port, message);
      return Result.SEND_FAIL;
    }

    return Result.SEND_SUCCESS;
  }

  private boolean isChannelAvailable() {
    if (this.channelPipeline != null && this.channelPipeline.channel() != null) {
      return this.channelPipeline.channel().isActive();
    }
    return false;
  }

  public enum Result {
    INVALID_PARAMETER,
    INVALID_STATE,
    CONNECTION_SUCCESS,
    CONNECTION_FAIL,
    DISCONNECT_SUCCESS,
    DISCONNECT_FAIL,
    SEND_SUCCESS,
    SEND_FAIL;

    public static boolean isSuccessResult(final Result result) {
      if (result == null) {
        return false;
      }
      return result.name().endsWith("SUCCESS");
    }
  }
}
