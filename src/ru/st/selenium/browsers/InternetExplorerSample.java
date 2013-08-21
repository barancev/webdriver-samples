package ru.st.selenium.browsers;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class InternetExplorerSample {

  @Test
  public void simpleRun() {
    InternetExplorerDriver driver = new InternetExplorerDriver();
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

  @Test
  public void runThroughProxy() throws MalformedURLException {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    capabilities.setCapability("proxy", proxy);

    InternetExplorerDriver driver = new InternetExplorerDriver(capabilities);
    driver.get("http://selenium2.ru/");
    driver.quit();
  } 

  @Test
  public void runCustomized() {
	InternetExplorerDriverService service = new InternetExplorerDriverService.Builder()
		.usingDriverExecutable(new File("C:/Tools/IEDriverServer.exe"))
		.build();

	DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    capabilities.setCapability(
    		InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

    InternetExplorerDriver driver = new InternetExplorerDriver(service, capabilities);

    driver.get("http://selenium2.ru/");
    driver.quit();
  }

}
