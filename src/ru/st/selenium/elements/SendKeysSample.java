  package ru.st.selenium.elements;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SendKeysSample {
  
  private static final String TESTDATADIR
    = "c:\\Users\\alexei\\workspace\\selenium-training";

  public static void main(String[] args) throws IOException {
    sendKeysFile();
  }
  
  public static void fillForm() {
    
  }
  
  public static void sendKeysFile() {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/enabled.html");
    driver.findElement(By.name("file")).sendKeys(
        TESTDATADIR + "\\chromedriver.log");
  }

  public static void benchmarkSendKeys(String fieldName) {
    WebDriver driver = new InternetExplorerDriver();
    driver.get("http://localhost/test/enabled.html");
    WebElement element = driver.findElement(By.name(fieldName));
    String letters = "qwertyuiopasdfghjklzxcvbnm".toUpperCase();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i++) {
      element.sendKeys(letters);
      element.clear();
    }
    long end = System.currentTimeMillis();
    System.out.println("" + (end - start));
    driver.quit();
  }

  public static void benchmarkClipboard(String fieldName) {
    WebDriver driver = new FirefoxDriver();
    driver.get("http://localhost/test/enabled.html");
    WebElement element = driver.findElement(By.name(fieldName));
    String letters = "qwertyuiopasdfghjklzxcvbnm".toUpperCase();
    setClipboardContents(letters);
    long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i++) {
      element.sendKeys(Keys.CONTROL + "v");
      element.clear();
    }
    long end = System.currentTimeMillis();
    System.out.println("" + (end - start));
    driver.quit();
  }

  public static void setClipboardContents(String text) {
    StringSelection stringSelection = new StringSelection( text );
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }
}
