package ru.st.selenium.browsers;

import org.junit.Test;
import org.openqa.selenium.safari.SafariDriver;

public class SafariSample {

  @Test
  public void simpleRun() {
    SafariDriver driver = new SafariDriver();
    driver.get("http://selenium2.ru/");
    driver.quit();
  }

}
