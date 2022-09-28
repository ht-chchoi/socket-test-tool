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
}
