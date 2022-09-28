package com.ht.sockettesttool.core.mainpage;

import com.ht.sockettesttool.view.page.MainPage;

import java.nio.charset.StandardCharsets;

public class MainPagePostConstruct {
  private final MainPage mainPage;

  public MainPagePostConstruct(final MainPage mainPage) {
    this.mainPage = mainPage;
  }

  public void bootMainPageInitialSetting() {
    this.initBtnActions();
    this.setInitialValues();
  }

  private void setInitialValues() {
    this.mainPage.getTfIp().setText("127.0.0.1");
    this.mainPage.getTfPort().setText("19200");
  }

  private void initBtnActions() {
    this.setConnectDisConnectActions();
    this.setSendAction();
    this.setClearActions();
  }

  private void setConnectDisConnectActions() {
    this.mainPage.getBtnConnect().addActionListener(e ->
        MainPageService.getInstance()
            .connectToServer(this.mainPage.getTfIp().getText(), this.mainPage.getTfPort().getText()));
    this.mainPage.getBtnDisConnect().addActionListener(e -> MainPageService.getInstance().disConnectToServer());
  }

  private void setSendAction() {
    this.mainPage.getBtnSend().addActionListener(e -> MainPageService.getInstance()
        .sendToServer(this.mainPage.getTaSend().getText(), StandardCharsets.UTF_16LE));
  }

  private void setClearActions() {
    this.mainPage.getBtnClearSend().addActionListener(e -> this.mainPage.getTaSend().setText(""));
    this.mainPage.getBtnClearReceive().addActionListener(e -> this.mainPage.getTaReceive().setText(""));
    this.mainPage.getBtnClearLogConsole().addActionListener(e -> this.mainPage.getTaLogConsole().setText(""));
  }
}
