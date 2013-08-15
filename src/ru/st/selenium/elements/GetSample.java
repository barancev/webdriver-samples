  package ru.st.selenium.elements;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.opera.core.systems.OperaDriver;

public class GetSample {
  
  public static void main(String[] args) throws IOException {
    getInnerHtml();
  }

  public static void getTextSample() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/displayed.html");
    for (String id : new String[] {"displayed", "empty-div", "empty-span", "zero-size", "display-none", "hidden", "inside-hidden", "transparent", "white", "out-of-view", "hidden-behind"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.getText());
    }
    driver.quit();
  }
  
  public static void getFormElementsText() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/enabled.html");
    for (String id : new String[] {"checked", "unchecked", "radio-checked", "radio-unchecked", "select", "item-1", "item-2", "item-3"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.getText());
    }
    driver.quit();
  }

  public static void getAttributeId() {
    WebDriver driver = new InternetExplorerDriver();
    driver.get("http://localhost/test/displayed.html");
    for (String id : new String[] {"displayed", "empty-div", "empty-span", "zero-size", "hidden", "display-none", "inside-hidden", "transparent", "white", "out-of-view", "hidden-behind"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.getAttribute("ID"));
    }
    driver.quit();
  }
  
  public static void getAttributeValue() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/enabled.html");
    for (String id : new String[] {"checked", "unchecked", "radio-checked", "radio-unchecked", "select", "item-1", "item-2", "item-3"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.getAttribute("value"));
    }
    driver.quit();
  }
  
  public static void getAttributeHref() {
    WebDriver driver = new OperaDriver();
    driver.get("http://localhost/test/displayed.html");
    List<WebElement> elements = driver.findElements(By.tagName("img"));
    for (WebElement img : elements) {
      System.out.println(img.getAttribute("src"));
    }
    driver.quit();
  }
  
  public static void getAttributeStyle() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/displayed.html");
    for (String id : new String[] {"displayed", "empty-div", "empty-span", "zero-size", "hidden", "display-none", "inside-hidden", "transparent", "white", "out-of-view", "hidden-behind"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.getAttribute("style"));
    }
    driver.quit();
  }
  
  public static void getColor() {
    WebDriver driver = new OperaDriver();
    driver.get("http://localhost/test/displayed.html");
    for (String id : new String[] {"displayed", "transparent", "white"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.getCssValue("color") + " on " + element.getCssValue("background-color"));
    }
    driver.quit();
  }
  
  public static void getBackground() {
    WebDriver driver = new InternetExplorerDriver();
    driver.get("http://localhost/test/displayed.html");
    for (String id : new String[] {"displayed", "transparent", "white"}) {
      WebElement element = driver.findElement(By.id(id));
      System.out.println(id + ": " + element.getCssValue("background"));
    }
    driver.quit();
  }
  
  public static void getInnerHtml() {
    WebDriver driver = new InternetExplorerDriver();
    driver.get("http://localhost/test/displayed.html");
    WebElement element = driver.findElement(By.tagName("body"));
    System.out.println(((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML", element));
    driver.quit();
  }

  public static void getOuterHtml() {
    WebDriver driver = new OperaDriver();
    driver.get("http://localhost/test/displayed.html");
    WebElement element = driver.findElement(By.tagName("body"));
    System.out.println(((JavascriptExecutor) driver).executeScript("return arguments[0].outerHTML", element));
    driver.quit();
  }

}
