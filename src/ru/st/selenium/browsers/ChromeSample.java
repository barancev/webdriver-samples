package ru.st.selenium.browsers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.Maps;

public class ChromeSample {

  public static void main(String[] args) throws IOException {
    simpleRun();
  }

  public static void simpleRun() {
    FirefoxDriver driver = new FirefoxDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runThroughProxy() throws MalformedURLException {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setCapability("proxy", proxy);

    ChromeDriver driver = new ChromeDriver(capabilities);
    driver.get("http://localhost/");
    driver.quit();
  } 

  public static void runFromNonstandardLocation() {
    ChromeOptions options = new ChromeOptions();

    options.setBinary(new File("C:/Users/alexei/AppData/Local/Google/Chrome/Application/chrome.exe"));

    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://localhost/");
    driver.quit();
  }
  
  public static void runWithExtensions() {
    ChromeOptions options = new ChromeOptions();

    options.addExtensions(new File("C:/Users/alexei/Downloads/WebDriverIDE.crx"));

    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://localhost/");
    driver.quit();
  }
  
  public static void runWithExistingProfile() {
    ChromeOptions options = new ChromeOptions();

    options.addArguments("--user-data-dir=/home/user/.a5");
    // options.addArguments("--user-data-dir=/home/user/.a5", "--app=http://127.0.0.1");
  
    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runThroughProxy2() throws MalformedURLException {
    ChromeOptions options = new ChromeOptions();

    options.addArguments("--proxy-server=http://localhost:8888");

    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runWithSpecificChromedriver() {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "C:/Tools/chromedriver19.exe");

    ChromeDriver driver = new ChromeDriver();
    driver.get("http://localhost/");
    driver.quit();
  }
  
  public static void runWithCustomEnvironment() {
    Map<String, String> env = Maps.newHashMap();
    env.put("DISPLAY", ":1");

    ChromeDriverService service = new ChromeDriverService.Builder()
      .usingDriverExecutable(new File("C:/Tools/chromedriver19.exe"))
      .usingAnyFreePort()
      .withEnvironment(env)
      .build();    

    ChromeDriver driver = new ChromeDriver(service);
    driver.get("http://localhost/");
    driver.quit();
  }
}
