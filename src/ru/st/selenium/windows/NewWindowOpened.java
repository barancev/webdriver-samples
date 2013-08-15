package ru.st.selenium.windows;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewWindowOpened {
  
  public static void main(String[] args) throws IOException {
    new NewWindowOpened()
      .waitForTitle();
  }

  public void waitForTitle() {
    WebDriver driver = new InternetExplorerDriver();
    driver.get("http://localhost/3880.html");
    driver.findElement(By.tagName("a")).click();
    driver.switchTo().window(new WebDriverWait(driver, 10).until(windowWithTitle("Popup")));
    driver.close();
    driver.getWindowHandle();
    driver.quit();
  }

  protected ExpectedCondition<String> windowWithTitle(final String title) {
    return new ExpectedCondition<String>() {
      public String apply(WebDriver d) {
        String initialWindowHandle = d.getWindowHandle();
        String found = null;
        Set<String> windowHandles = d.getWindowHandles();
        for (String handle : windowHandles) {
          try {
            d.switchTo().window(handle);
            if (title.equals(d.getTitle())) {
              found = handle;
              break;
            }
          } catch (WebDriverException e) {
          }
        }
        d.switchTo().window(initialWindowHandle);
        return found;
      }
    };
  }

  public void waitForNewWindow() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/3880.html");
    Set<String> existingWindows = driver.getWindowHandles();
    System.out.println(existingWindows);
    driver.findElement(By.tagName("a")).click();
    System.out.println(new WebDriverWait(driver, 10).until(newWindow(existingWindows)));
    driver.quit();
  }

  protected ExpectedCondition<String> newWindow(final Set<String> existingWindows) {
    return new ExpectedCondition<String>(){
      public String apply(WebDriver d) {
        Set<String> windowHandles = d.getWindowHandles();
        windowHandles.removeAll(existingWindows);
        if (windowHandles.size() > 0) {
          return (String) windowHandles.toArray()[0];
        } else {
          return null;
        }
      }
    };
  }

  
  
}
