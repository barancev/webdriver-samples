package ru.st.selenium.browsers;

import java.net.MalformedURLException;
import java.net.URL;

public class ReusableRemoteWebDriverSample {

  public static void main(String[] args) throws MalformedURLException {
    ReusableRemoteWebDriver driver = new ReusableRemoteWebDriver(new URL("http://127.0.0.1:7055/hub"));
    driver.get("http://localhost/webmail");
  }

}
