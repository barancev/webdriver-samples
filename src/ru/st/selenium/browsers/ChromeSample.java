package ru.st.selenium.browsers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.Maps;

public class ChromeSample {

  @Test
  public void simpleRun() {
	ChromeDriver driver = new ChromeDriver();
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runThroughProxy() throws MalformedURLException {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setCapability("proxy", proxy);

    ChromeDriver driver = new ChromeDriver(capabilities);
    driver.get("http://selenium2.ru/");
    driver.quit();
  } 

  @Test
  public void runFromNonstandardLocation() {
    ChromeOptions options = new ChromeOptions();

    options.setBinary(new File("C:/Users/alexei/AppData/Local/Google/Chrome/Application/chrome.exe"));

    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }
  
  @Test
  public void runWithExtensions() {
    ChromeOptions options = new ChromeOptions();

    options.addExtensions(new File("C:/Users/alexei/Downloads/WebDriverIDE.crx"));

    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }
  
  @Test
  public void runWithExistingProfile() {
    ChromeOptions options = new ChromeOptions();

    options.addArguments("--user-data-dir=/home/user/.a5");
    // options.addArguments("--user-data-dir=/home/user/.a5", "--app=http://127.0.0.1");
  
    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runThroughProxy2() throws MalformedURLException {
    ChromeOptions options = new ChromeOptions();

    options.addArguments("--proxy-server=http://localhost:8888");

    ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runWithCustomEnvironment() {
    Map<String, String> env = Maps.newHashMap();
    env.put("DISPLAY", ":1");

    ChromeDriverService service = new ChromeDriverService.Builder()
      .usingDriverExecutable(new File("C:/Tools/chromedriver.exe"))
      .usingAnyFreePort()
      .withEnvironment(env)
      .build();    

    ChromeDriver driver = new ChromeDriver(service);

    driver.get("http://selenium2.ru/");
    driver.quit();
  }
}
