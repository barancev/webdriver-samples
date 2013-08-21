package ru.st.selenium.browsers;

import java.net.MalformedURLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class PhantomJSDriverDriverSample {

  @Test
  public void simpleRun() throws MalformedURLException, InterruptedException {
    WebDriver driver = new PhantomJSDriver();
    driver.get("http://selenium2.ru/");
    System.out.println(driver.findElement(By.cssSelector("h2")).getText());
    driver.quit();
  }

}
