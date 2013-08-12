package ru.st.selenium.elements;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class WebElementWaitSample {
  
  private WebDriver driver;
  
  public <T> T waitFor(ExpectedCondition<T> condition) {
    return new WebDriverWait(driver, 10).until(condition);
  }

  public void waitFor(WebElement element, Predicate<WebElement> condition) {
    new WebElementWait(element, 10).until(condition);
  }

  public static final Predicate<WebElement> invisible() {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        try {
          return ! element.isDisplayed();
        } catch (StaleElementReferenceException e) {
          return true;
        }
      }
    };
  }

  public void go() {
    driver = new FirefoxDriver();

    driver.get("http://jqueryui.com/demos/datepicker/");

    waitFor(presenceOfElementLocated(By.id("datepicker"))).click();
    WebElement datepicker = waitFor(presenceOfElementLocated(By.cssSelector("div#ui-datepicker-div")));
    datepicker.findElement(By.cssSelector("td.ui-datepicker-today a")).click();
    waitFor(datepicker, invisible());
    
    driver.quit();
  }
   
  public static void main(String[] args) throws IOException {
    new WebElementWaitSample().go();
  }

}
