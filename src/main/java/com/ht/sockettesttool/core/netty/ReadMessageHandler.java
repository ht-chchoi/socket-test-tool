package com.ht.sockettesttool.core.netty;

import com.ht.sockettesttool.core.mainpage.MainPageService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class ReadMessageHandler extends ChannelInboundHandlerAdapter {
  private final Charset charset;

  public ReadMessageHandler(final Charset charset) {
    this.charset = charset;
  }

  @Override
  public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
    String message = ((ByteBuf) msg).readCharSequence(((ByteBuf) msg).readableBytes(), this.charset).toString();
    MainPageService.getInstance().appendReceiveTextArea(message);
    log.info("receive done");
  }

  @Override
  public void channelInactive(final ChannelHandlerContext ctx) {
    log.warn("channel inactive");
    MainPageService.getInstance().manageConnectionState(true);
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
    log.error("channel exception: ", cause);
    MainPageService.getInstance().manageConnectionState(true);
  }
}
