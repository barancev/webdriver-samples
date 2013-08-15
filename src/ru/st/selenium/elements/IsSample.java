  package ru.st.selenium.elements;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class IsSample {
  
  public static void main(String[] args) throws IOException {
    isDisplayedSample();
  }

  public static void isDisplayedSample() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/displayed.html");
    for (String id : new String[] {"displayed", "empty-div", "empty-span", "zero-size", "hidden", "display-none", "inside-hidden", "transparent", "white", "out-of-view", "hidden-behind"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.isDisplayed()
          + " " + element.getSize() + " " + element.getLocation());
    }
    driver.quit();
  }

  public static void isSelectedSample() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/enabled.html");
    for (String id : new String[] {"checked", "unchecked", "radio-checked", "radio-unchecked", "select", "item-1", "item-2", "item-3"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.isSelected());
    }
    driver.quit();
  }
}
