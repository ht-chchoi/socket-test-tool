package com.ht.sockettesttool.common.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.AppenderBase;
import com.ht.sockettesttool.view.frame.MainFrame;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MainPageConsoleLogAppender extends AppenderBase<ILoggingEvent> {
  private final MainFrame mainFrame = MainFrame.getInstance();
  private final Highlighter.HighlightPainter yellowPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
  @Override
  protected void append(final ILoggingEvent eventObject) {
    if (!this.mainFrame.isLoaded()) {
      return;
    }
    if (eventObject.getLevel().equals(Level.ERROR)) {
      this.appendToMainConsoleWithHighLight(eventObject.toString());
      if (eventObject.getThrowableProxy() != null) {
        this.appendToMainConsoleWithHighLight("====================== StackTrace Start ===========================");
        this.appendToMainConsoleWithHighLight(
            Arrays.stream(eventObject.getThrowableProxy().getStackTraceElementProxyArray())
                .map(StackTraceElementProxy::getSTEAsString)
                .collect(Collectors.joining("\n")));
        this.appendToMainConsoleWithHighLight("====================== StackTrace End ===========================");
      }
    } else if (eventObject.getLevel().equals(Level.WARN)) {
      this.appendToMainConsoleWithHighLight(eventObject.toString());
    } else {
      this.appendToMainConsole(eventObject.toString());
    }
    this.mainFrame.getMainPage().getTaLogConsole().setCaretPosition(
        this.mainFrame.getMainPage().getTaLogConsole().getDocument().getLength());
  }

  private void appendToMainConsole(final String msg) {
    this.mainFrame.getMainPage().getTaLogConsole().append(msg + "\n");
  }

  private void appendToMainConsoleWithHighLight(final String msg) {
    int currentPosition = this.mainFrame.getMainPage().getTaLogConsole().getDocument().getLength();
    this.appendToMainConsole(msg);
    try {
      this.mainFrame.getMainPage().getTaLogConsole().getHighlighter()
          .addHighlight(currentPosition, currentPosition + msg.length(), this.yellowPainter);
    } catch (BadLocationException e) {
      throw new RuntimeException(e);
    }
  }
}
