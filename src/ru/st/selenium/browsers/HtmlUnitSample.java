package ru.st.selenium.browsers;

import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HtmlUnitSample {

  @Test
  public void simpleRun() {
    HtmlUnitDriver driver = new HtmlUnitDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

}
