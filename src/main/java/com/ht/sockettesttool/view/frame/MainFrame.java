package com.ht.sockettesttool.view.frame;

import com.ht.sockettesttool.core.mainpage.MainPagePostConstruct;
import com.ht.sockettesttool.view.page.MainPage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
@Getter
public class MainFrame extends JFrame {
  private String appName;
  private String version;
  private MainPage mainPage;
  private boolean isLoaded = false;

  private MainFrame() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1200, 1000);
  }

  private static class LazyHolder {
    public static final MainFrame INSTANCE = new MainFrame();
  }

  public static MainFrame getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void load(final String appName, final String version) {
    log.info("load MainFrame");
    this.appName = appName;
    this.version = version;

    this.setTitle(appName + " [" + version + "]");
    this.mainPage = new MainPage(this);
    new MainPagePostConstruct(this.mainPage).bootMainPageInitialSetting();
    this.setContentPane(this.mainPage.getRootPanel());
    this.setVisible(true);

    isLoaded = true;
  }
}
