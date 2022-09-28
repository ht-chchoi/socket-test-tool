package com.ht.sockettesttool;

import com.ht.sockettesttool.view.frame.MainFrame;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class AppMain {
  private static final String APP_NAME = "Socket Test Tool";

  public static void main(String[] args) {
    MainFrame.getInstance().load(APP_NAME, getVersion());
  }

  private static String getVersion() {
    try {
      InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("version");
      if (is == null) {
        return "DEV";
      }
      return new BufferedReader(new InputStreamReader(is)).readLine();
    } catch (IOException e) {
      log.warn("Version File Not Exist, Check [gradle.build] File");
      return "No Version Info";
    }
  }
}
