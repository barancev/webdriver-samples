package ru.st.selenium.elements;

import java.util.Arrays;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.google.common.base.Predicate;

public class Predicates {
  
  public static final Predicate<WebElement> displayed() {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return element.isDisplayed();
      }
    };
  }

  public static final Predicate<WebElement> invisible() {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return ! element.isDisplayed();
      }
    };
  }

  public static final Predicate<WebElement> enabled() {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return element.isEnabled();
      }
    };
  }

  public static final Predicate<WebElement> disabled() {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return ! element.isEnabled();
      }
    };
  }

  public static final Predicate<WebElement> hasClass(final String clss) {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return Arrays.asList(element.getAttribute("class").split(" ")).contains(clss);
      }
    };
  }

  public static final Predicate<WebElement> hasText(final String text) {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return element.getText().equals(text);
      }
    };
  }

  public static final Predicate<WebElement> contains(final String text) {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return element.getText().contains(text);
      }
    };
  }

  public static final Predicate<WebElement> fixed() {
    return new Predicate<WebElement>() {
      private Point prevLocation = null;
      private Dimension prevSize = null;
      @Override
      public boolean apply(WebElement element) {
        if (element.getLocation().equals(prevLocation) && element.getSize().equals(prevSize)) {
          return true;
        } else {
          prevLocation = element.getLocation();
          prevSize = element.getSize();
          return false;
        }
      }
    };
  }

  public static final Predicate<WebElement> not(final Predicate<WebElement> predicate) {
    return new Predicate<WebElement>() {
      @Override
      public boolean apply(WebElement element) {
        return ! predicate.apply(element);
      }
    };
  }
}
