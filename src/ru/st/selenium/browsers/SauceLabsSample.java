package ru.st.selenium.browsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SauceLabsSample {

  public static void main(String[] args) throws IOException {
    DesiredCapabilities firefox = DesiredCapabilities.firefox();
    simpleRun(firefox);
  }

  public static void simpleRun(Capabilities capabilities) throws MalformedURLException {
    RemoteWebDriver driver = new RemoteWebDriver(
        new URL("http://barancev:3167c93e-1a8f-47bd-8f92-7f4483d6f9d2@ondemand.saucelabs.com:80/wd/hub"),
        capabilities);
    driver.get("http://ya.ru/");
    driver.quit();
  }

}
