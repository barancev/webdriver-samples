package ru.st.selenium;

import org.openqa.selenium.WebElement;

public class WebElementWithWindowInfo extends WebElementProxy {

  private String windowHandle;

  public WebElementWithWindowInfo(WebElement element) {
    super(element);
    windowHandle = getWrappedDriver().getWindowHandle();
  }

  @Override
  protected WebElement createWebElement(WebElement from) {
    return new WebElementWithWindowInfo(from);
  }
  
  public String getWindowHandle() {
    return windowHandle;
  }

  @Override
  protected void doSomethogBeforeInvocation() {
    getWrappedDriver().switchTo().window(getWindowHandle());
  }

  
}
