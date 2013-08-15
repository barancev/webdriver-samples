  package ru.st.selenium.alerts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AlertsSample {
  
  public static void main(String[] args) throws IOException {
    traceAndIgnoreAlert();
  }

  public static void stuckOnAlert() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/alerts.html");
    driver.findElement(By.tagName("a")).click();
    System.out.println(driver.findElement(By.tagName("a")).getText());
    driver.quit();
  }
  
  public static void traceAndIgnoreAlert() {
    WebDriver driver = new AlertTolerantWebDriver(new FirefoxDriver());
    driver.get("http://localhost/test/alerts.html");
    driver.findElement(By.tagName("a")).click();
    System.out.println(driver.findElement(By.tagName("a")).getText());
    driver.quit();
  }

}
