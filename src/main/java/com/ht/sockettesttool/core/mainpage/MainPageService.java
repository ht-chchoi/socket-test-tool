package com.ht.sockettesttool.core.mainpage;

import com.ht.sockettesttool.core.netty.NettyClientManager;
import com.ht.sockettesttool.view.frame.MainFrame;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class MainPageService {
  private MainPageService() {
  }

  private static class LazyHolder {
    public static final MainPageService INSTANCE = new MainPageService();
  }

  public static MainPageService getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void connectToServer(final String host, final String port) {
    NettyClientManager.Result result = NettyClientManager.getInstance().connect(host, port);
    if (!NettyClientManager.Result.isSuccessResult(result)) {
      this.manageConnectionState(true);
      return;
    }
    this.manageConnectionState(false);
  }

  public void disConnectToServer() {
    NettyClientManager.getInstance().disConnect();
    this.manageConnectionState(true);
  }

  public void sendToServer(final String message, final Charset charset) {
    NettyClientManager.Result result = NettyClientManager.getInstance().sendMessage(message, charset);
    if (NettyClientManager.Result.isSuccessResult(result)) {
      return;
    }
    switch (result) {
      case INVALID_STATE:
        this.manageConnectionState(true);
        break;
      case SEND_FAIL:
        this.manageConnectionState(true);
        this.disConnectToServer();
    }
  }

  public void setSendTextArea(final String text) {
    MainFrame.getInstance().getMainPage().getTaSend().setText(text);
  }

  public void appendReceiveTextArea(final String text) {
    MainFrame.getInstance().getMainPage().getTaReceive().append(text + "\n");
  }

  public void manageConnectionState(final boolean isConnectionEnable) {
    MainFrame.getInstance().getMainPage().getBtnConnect().setEnabled(isConnectionEnable);
    MainFrame.getInstance().getMainPage().getBtnDisConnect().setEnabled(!isConnectionEnable);
    MainFrame.getInstance().getMainPage().getTaReceive().setEnabled(!isConnectionEnable);
    MainFrame.getInstance().getMainPage().getBtnSend().setEnabled(!isConnectionEnable);
  }
}
