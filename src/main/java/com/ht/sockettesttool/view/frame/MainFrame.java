package com.ht.sockettesttool.view.frame;

import com.ht.sockettesttool.view.page.MainPage;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class MainFrame extends JFrame {
  private final String title;
  private final String version;

  public MainFrame(final String title, final String version) {
    this.title = title;
    this.version = version;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1200, 1000);
  }

  public void load() {
    log.info("load MainFrame");

    this.setTitle(this.title + " / " + this.version);
    this.setContentPane(new MainPage(this).getRootPanel());
    this.setVisible(true);
  }
}
