package ru.st.selenium.browsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteWebDriverSample {

  public static void main(String[] args) throws IOException {
    DesiredCapabilities firefox = DesiredCapabilities.firefox();
    FirefoxProfile profile = new FirefoxProfile();
    profile.setEnableNativeEvents(true);
    firefox.setCapability("firefox_profile", profile);
    simpleRun(firefox);
  }

  public static void simpleRun(Capabilities capabilities) throws MalformedURLException {
    RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runThroughProxy(DesiredCapabilities capabilities) throws MalformedURLException {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    capabilities.setCapability("proxy", proxy);

    RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    driver.get("http://localhost/");
    driver.quit();
  } 
  
  
}
