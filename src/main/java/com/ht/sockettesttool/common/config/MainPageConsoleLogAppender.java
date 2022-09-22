package com.ht.sockettesttool.common.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.ht.sockettesttool.view.frame.MainFrame;

public class MainPageConsoleLogAppender extends AppenderBase<ILoggingEvent> {
  private final MainFrame mainFrame = MainFrame.getInstance();
  @Override
  protected void append(final ILoggingEvent eventObject) {
    if (!this.mainFrame.isLoaded()) {
      return;
    }
    this.mainFrame.getMainPage().getTaLogConsole().append(eventObject.toString() + "\n");
  }
}
