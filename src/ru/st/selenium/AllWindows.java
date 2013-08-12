package ru.st.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.collections.Lists;

public class AllWindows implements SearchContext {
  
  private final WebDriver driver;

  public AllWindows(WebDriver driver) {
    this.driver = driver;
  }

  @Override
  public WebElement findElement(By locator) {
    String initialWindowHandle = driver.getWindowHandle();
    WebElement found = null;
    for (String handle : driver.getWindowHandles()) {
      driver.switchTo().window(handle);
      try {
        found = driver.findElement(locator);
        break;
      } catch (WebDriverException e) {
      }
    }
    if (found == null) {
      driver.switchTo().window(initialWindowHandle);
    }
    return found;
  }

  @Override
  public List<WebElement> findElements(By locator) {
    String initialWindowHandle = driver.getWindowHandle();
    List<WebElement> found = Lists.newArrayList();
    for (String handle : driver.getWindowHandles()) {
      driver.switchTo().window(handle);
      try {
        found.addAll(driver.findElements(locator));
      } catch (WebDriverException e) {
      }
    }
    driver.switchTo().window(initialWindowHandle);
    return found;
  }

}
