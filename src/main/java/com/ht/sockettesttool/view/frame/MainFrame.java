package com.ht.sockettesttool.view.frame;

import com.ht.sockettesttool.view.page.MainPage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
@Getter
public class MainFrame extends JFrame {
  private String title;
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

  public void load(final String title, final String version) {
    log.info("load MainFrame");
    this.title = title;
    this.version = version;

    this.setTitle(this.title + " / " + this.version);
    this.mainPage = new MainPage(this);
    this.setContentPane(this.mainPage.getRootPanel());
    this.setVisible(true);

    isLoaded = true;
  }
}
