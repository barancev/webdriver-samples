package ru.st.selenium.browsers;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class HtmlUnitSample {

  public static void main(String[] args) throws IOException {
    runThroughProxy();
  }
  
  public static void simpleRun() {
    HtmlUnitDriver driver = new HtmlUnitDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runThroughProxy() throws MalformedURLException {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    capabilities.setCapability("proxy", proxy);

    HtmlUnitDriver driver = new HtmlUnitDriver(capabilities);
    driver.get("http://localhost/");
    System.out.println(driver.getCurrentUrl());
    driver.quit();
  } 

}
