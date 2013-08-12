package ru.st.selenium.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SizzleSample {

  /**
   * @param args
   */
  public static void main(String[] args) {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://seleniumhq.org/");
    WebElement link = driver.findElement(ExtBy.sizzle("a:contains(Documentation)"));
    link.click();
  }

}
