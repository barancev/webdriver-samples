package ru.st.selenium.browsers;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InternetExplorerSample {

  public static void main(String[] args) throws IOException {
    simpleRun();
  }
  
  public static void simpleRun() {
    InternetExplorerDriver driver = new InternetExplorerDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runThroughProxy() throws MalformedURLException {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    capabilities.setCapability("proxy", proxy);

    InternetExplorerDriver driver = new InternetExplorerDriver(capabilities);
    driver.get("http://localhost/");
    driver.quit();
  } 

  public static void runWithIgnoredProtectedModeSettings() {
    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

    InternetExplorerDriver driver = new InternetExplorerDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

}
