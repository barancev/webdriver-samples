package ru.st.selenium.browsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ReusableRemoteWebDriver extends RemoteWebDriver {
  
  private static final String SID_FILE = "sid.dat";

  public ReusableRemoteWebDriver(URL remoteAddress) {
    super(remoteAddress, DesiredCapabilities.firefox());
  }

  @Override
  protected void startSession(Capabilities desiredCapabilities) {
    String sid = readPreviousSessionId();
    if (sid != null) {
      setSessionId(sid);
      try {
        getCurrentUrl();
      } catch (WebDriverException e) {
        // session is not valid
        //e.printStackTrace();
        sid = null;
      }
    }
    if (sid == null) {
      super.startSession(desiredCapabilities);
      saveSessionId(getSessionId().toString());
    }
  }

  private String readPreviousSessionId() {
    String sid = null;
    File sidFile = new File(SID_FILE);
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(sidFile));
      sid = reader.readLine();
    } catch (IOException e) {
      // noop
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return sid;
  }

  private void saveSessionId(String sid) {
    File sidFile = new File(SID_FILE);
    FileWriter writer = null;
    try {
      writer = new FileWriter(sidFile);
      writer.write(sid);
    } catch (IOException e) {
      // noop
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
