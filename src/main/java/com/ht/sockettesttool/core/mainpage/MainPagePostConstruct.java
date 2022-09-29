package com.ht.sockettesttool.core.mainpage;

import com.ht.sockettesttool.view.page.MainPage;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MainPagePostConstruct {
  private final MainPage mainPage;

  public MainPagePostConstruct(final MainPage mainPage) {
    this.mainPage = mainPage;
  }

  public void bootMainPageInitialSetting() {
    this.initBtnActions();
    this.setInitialValues();
    this.initLayoutList();
    this.initEncodingComboBox();
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

  private void initLayoutList() {
    this.mainPage.getLiLayout().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.mainPage.getLiLayoutModel().addElement("default");
    this.mainPage.getLiLayoutModel().addElement("공지사항요청");
    this.mainPage.getLiLayout().addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() > 1) {
          String selectedValue = mainPage.getLiLayout().getSelectedValue();
          if (selectedValue != null) {
            MainPageService.getInstance().setSendTextArea(LayoutService.getInstance().createXmlMessage(selectedValue));
          }
        }
      }
    });
    this.mainPage.getLiLayout().addListSelectionListener(e -> {
      if (e.getValueIsAdjusting()) {
        String selectedValue = this.mainPage.getLiLayout().getSelectedValue();
        if (selectedValue != null) {
          MainPageService.getInstance().setSendTextArea(LayoutService.getInstance().createXmlMessage(selectedValue));
        }
      }
    });
  }

  private void initEncodingComboBox() {
    this.mainPage.getComboEncoding().addItem(StandardCharsets.UTF_16LE);
    this.mainPage.getComboEncoding().addItem(StandardCharsets.UTF_8);
  }

  private void setConnectDisConnectActions() {
    this.mainPage.getBtnConnect().addActionListener(e ->
        MainPageService.getInstance()
            .connectToServer(this.mainPage.getTfIp().getText(), this.mainPage.getTfPort().getText()));
    this.mainPage.getBtnDisConnect().addActionListener(e -> MainPageService.getInstance().disConnectToServer());
  }

  private void setSendAction() {
    this.mainPage.getBtnSend().addActionListener(e -> MainPageService.getInstance()
        .sendToServer(
            this.mainPage.getTaSend().getText(),
            (Charset) this.mainPage.getComboEncoding().getSelectedItem()));
  }

  private void setClearActions() {
    this.mainPage.getBtnClearSend().addActionListener(e -> this.mainPage.getTaSend().setText(""));
    this.mainPage.getBtnClearReceive().addActionListener(e -> this.mainPage.getTaReceive().setText(""));
    this.mainPage.getBtnClearLogConsole().addActionListener(e -> this.mainPage.getTaLogConsole().setText(""));
  }
}
