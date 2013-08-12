package ru.st.selenium;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.io.Files;

public class JUnitSample {
  
  private WebDriver driver;

  @Rule
  public TestRule screenshoter = new TestWatcher() {
    @Override
    protected void failed(Throwable e, Description description) {
      try {
        takeScreenshot(description.getMethodName());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      super.failed(e, description);
    }
  };
  
  public void takeScreenshot(String name) throws IOException {
    File screenshot = new File(name + ".png");
    screenshot.delete();
    File screenshotTempFile = ((TakesScreenshot) driver)
      .getScreenshotAs(OutputType.FILE);
    Files.copy(screenshotTempFile, screenshot);
  }
  
  @Test
  public void failingTestMethod() {
    driver.get("http://www.microsoft.com/");
    assertTrue(driver.findElement(By.tagName("body")).getText().contains("Linux"));
  }

  @Before
  public void initDriver() {
    driver = new FirefoxDriver();
  }
}
