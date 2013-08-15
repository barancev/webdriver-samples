package ru.st.selenium.browsers;

import java.io.File;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.browsermob.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxSample {
  
  public static void simpleRun() {
    RemoteWebDriver driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    //driver.setLogLevel(Level.INFO);
    driver.get("https://support.atlassian.com/secure/Dashboard.jspa");
    driver.switchTo().frame(driver.findElement(By.id("gadget-0")));
    driver.findElement(By.name("os_username")).sendKeys("demo");
    driver.findElement(By.name("os_password")).sendKeys("demo");
    driver.findElement(By.name("login")).click();
    driver.quit();
  }

  public static void runThroughProxy() throws Exception {
    ProxyServer pserver = new ProxyServer(9999);
    pserver.start();
    pserver.addRequestInterceptor(new HttpRequestInterceptor() {
      @Override
      public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        System.out.println("" + request.getRequestLine());
        request.removeHeaders("User-Agent");
        request.addHeader("User-Agent", "Bananabot/1.0");
      }
    });
    pserver.addResponseInterceptor(new HttpResponseInterceptor() {
      @Override
      public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        System.out.println("" + response.getStatusLine());
      }
    });
    //pserver.autoBasicAuthorization("", "tester", "tester");
    FirefoxProfile profile = new FirefoxProfile();
    profile.setProxyPreferences(pserver.seleniumProxy());

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get("http://localhost/protected/index.html");
    driver.quit();
    pserver.stop();
  } 

  public static void runWithCustomProfile() {
    FirefoxProfile profile = new FirefoxProfile();
    profile.setPreference("intl.accept_languages", "ru, en-us, en");

    profile.setPreference("browser.download.dir", "C:/TEMP");
    profile.setPreference("browser.download.folderList", 2);
    
    profile.setAssumeUntrustedCertificateIssuer(false);
    profile.setAcceptUntrustedCertificates(true);
    //profile.setUnexpectedAlertBehaviour(UnexpectedAlertBehaviour.IGNORE);
    
    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://localhost/test/alerts.html");
    driver.findElement(By.id("prompt-with-default")).click();
    try {
      System.out.println(driver.findElement(By.id("text")).getText());
    } catch (UnhandledAlertException ex) {
      ex.printStackTrace();
    }
    System.out.println(driver.findElement(By.id("text")).getText());
    driver.quit();
  }

  public static void runThroughProxy2() {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    FirefoxProfile profile = new FirefoxProfile();
    profile.setProxyPreferences(proxy);

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runWithSynthesizedEvents() throws Exception {
    FirefoxProfile profile = new FirefoxProfile();

    // profile.setEnableNativeEvents(true); // default for Windows
    profile.setEnableNativeEvents(false); // default for Linux

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://jqueryui.com/demos/accordion/#mouseover");
    List<WebElement> elements = driver.findElements(By.className("ui-accordion-header"));
    System.out.println(elements);
    for (WebElement el : elements) {
      new Actions(driver).moveToElement(el).perform();
      Thread.sleep(1000);
    }
    driver.quit();
  }

  public static void runWithExtensions() throws IOException {
    FirefoxProfile profile = new FirefoxProfile();

    profile.addExtension(new File("C:/Users/alexei/Downloads/firebug-1.10.0a7.xpi"));
    profile.setPreference("extensions.firebug.currentVersion", "9.9.9");
    profile.setPreference("extensions.firebug.allPagesActivation", "on");
    profile.setPreference("extensions.firebug.defaultPanelName", "net");
    profile.setPreference("extensions.firebug.net.enableSites", true);

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runWithExistingProfile() {
    FirefoxProfile profile = new FirefoxProfile(
        new File("C:/Users/alexei/AppData/Roaming/Mozilla/Firefox/Profiles/lzmkqeur.selenium"));

    profile.setPreference("browser.download.dir", "C:/TEMP");
    profile.setPreference("browser.download.folderList", 2);

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runFromNonstandardLocation() {
    FirefoxBinary binary = new FirefoxBinary(
        new File("C:/Program Files/Mozilla Firefox/firefox.exe"));

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runWithLongerTimeout() {
    FirefoxBinary binary = new FirefoxBinary();
    binary.setTimeout(90000); // default is 45000

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runWithCustomEnvironment() {
    FirefoxBinary binary = new FirefoxBinary();
    binary.setEnvironmentProperty("DISPLAY", ":1");

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runAndGetBrowserErrorLog() throws IOException {
    FirefoxBinary binary = new FirefoxBinary();

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://localhost/");
    driver.quit();

    System.out.println(binary.getConsoleOutput());
  }

}
