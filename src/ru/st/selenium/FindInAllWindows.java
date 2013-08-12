package ru.st.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.collections.Lists;

public class FindInAllWindows {
  
  private WebDriver driver;

  public static void main(String[] args) {
    new FindInAllWindows().runSample();
  }
  
  public void runSample() {
    driver = new FirefoxDriver();
    driver.get("http://selenium2.ru/");
    ((JavascriptExecutor) driver).executeScript(
        "window.open(arguments[0])", "http://selenium2.ru/");
    
    List<WebElement> elements = findElementsInAllWindows(By.cssSelector("li.item-104 a"));
    
    for (WebElement element : elements) {
      element.click();
    }
  }

  public List<WebElement> findElementsInAllWindows(By locator) {
    String initialWindowHandle = driver.getWindowHandle();
    List<WebElement> found = Lists.newArrayList();
    for (String handle : driver.getWindowHandles()) {
      driver.switchTo().window(handle);
      try {
        for (WebElement element : driver.findElements(locator)) {
          found.add(new WebElementWithWindowInfo(element));
        }
      } catch (WebDriverException e) {
      }
    }
    driver.switchTo().window(initialWindowHandle);
    return found;
  }
}
