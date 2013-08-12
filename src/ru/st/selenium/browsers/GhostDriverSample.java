package ru.st.selenium.browsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class GhostDriverSample {

  public static void main(String[] args) throws Exception {
    simpleRun();
  }
  
  public static void simpleRun() throws MalformedURLException, InterruptedException {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName("phantomjs");
    WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/"), capabilities);
    driver.get("http://selenium2.ru/");
    System.out.println(driver.findElement(By.cssSelector("h2")).getText());
    driver.quit();
  }

}
