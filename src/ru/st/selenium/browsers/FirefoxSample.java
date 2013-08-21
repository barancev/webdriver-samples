package ru.st.selenium.browsers;

import java.io.File;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.browsermob.proxy.ProxyServer;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxSample {
  
  @Test
  public void simpleRun() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runThroughProxy() throws Exception {
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

  @Test
  public void runThroughProxy2() {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    FirefoxProfile profile = new FirefoxProfile();
    profile.setProxyPreferences(proxy);

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runWithCustomProfile() {
    FirefoxProfile profile = new FirefoxProfile();
    profile.setPreference("intl.accept_languages", "en-us, en");

    profile.setPreference("browser.download.dir", "C:/TEMP");
    profile.setPreference("browser.download.folderList", 2);
    
    profile.setAssumeUntrustedCertificateIssuer(false);
    profile.setAcceptUntrustedCertificates(true);
    
    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://ci.seleniumhq.org:8080/");
    System.out.println(driver.getTitle());
    driver.quit();
  }

  @Test
  public void runWithSynthesizedEvents() throws Exception {
    FirefoxProfile profile = new FirefoxProfile();

    // profile.setEnableNativeEvents(true); // default for Windows
    profile.setEnableNativeEvents(false); // default for Linux
    
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("nativeEvents", true);

    FirefoxDriver driver = new FirefoxDriver(caps);
    driver.get("http://selenium2.ru");
    System.out.println(driver.getCapabilities());
    driver.quit();
  }

  @Test
  public void runWithExtensions() throws IOException {
    FirefoxProfile profile = new FirefoxProfile();

    profile.addExtension(new File("C:/Users/alexei/Downloads/firebug-1.11.4-fx.xpi"));
    profile.setPreference("extensions.firebug.currentVersion", "9.9.9");
    profile.setPreference("extensions.firebug.allPagesActivation", "on");
    profile.setPreference("extensions.firebug.defaultPanelName", "net");
    profile.setPreference("extensions.firebug.net.enableSites", true);

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://selenium2.ru/");
    //driver.quit();
  }

  @Test
  public void runWithExistingProfile() {
    FirefoxProfile profile = new FirefoxProfile(
        new File("C:/Users/alexei/AppData/Roaming/Mozilla/Firefox/Profiles/lzmkqeur.selenium"));

    FirefoxDriver driver = new FirefoxDriver(profile);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runFromNonstandardLocation() {
    FirefoxBinary binary = new FirefoxBinary(
        new File("C:/Program Files (x86)/Aurora/firefox.exe"));

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://selenium2.ru/");
    //driver.quit();
  }

  @Test
  public void runWithLongerTimeout() {
    FirefoxBinary binary = new FirefoxBinary();
    binary.setTimeout(90000); // default is 45000

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runWithCustomEnvironment() {
    FirefoxBinary binary = new FirefoxBinary();
    binary.setEnvironmentProperty("DISPLAY", ":1");

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runAndGetBrowserErrorLog() throws IOException {
    FirefoxBinary binary = new FirefoxBinary();

    FirefoxProfile profile = new FirefoxProfile();

    FirefoxDriver driver = new FirefoxDriver(binary, profile);
    driver.get("http://selenium2.ru/");
    driver.quit();

    System.out.println(binary.getConsoleOutput());
  }

}
