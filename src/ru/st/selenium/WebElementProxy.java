package ru.st.selenium;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

public abstract class WebElementProxy implements WebElement, WrapsElement,
    WrapsDriver, Locatable {

  private final WebElement element;
  private final WebElement underlyingElement;

  protected WebElementProxy(final WebElement element) {
    this.element = (WebElement) Proxy.newProxyInstance(
        WebElementProxy.class.getClassLoader(), extractInterfaces(element),
        new InvocationHandler() {
          public Object invoke(Object proxy, Method method, Object[] args)
              throws Throwable {
            String m = method.getName();
            if ("getWrappedElement".equals(m)) {
              return element;
            }

            doSomethogBeforeInvocation();
            try {
              Object result = method.invoke(element, args);
              doSomethogAfterInvocation();
              return result;
            } catch (InvocationTargetException e) {
              doSomethogOnError();
              throw e.getTargetException();
            }
          }

        });
    this.underlyingElement = element;
  }

  private Class<?>[] extractInterfaces(Object object) {
    Set<Class<?>> allInterfaces = new HashSet<Class<?>>();
    allInterfaces.add(WrapsDriver.class);
    if (object instanceof WebElement) {
      allInterfaces.add(WrapsElement.class);
    }
    extractInterfaces(allInterfaces, object.getClass());

    return allInterfaces.toArray(new Class<?>[allInterfaces.size()]);
  }

  private void extractInterfaces(Set<Class<?>> addTo, Class<?> clazz) {
    if (Object.class.equals(clazz)) {
      return; // Done
    }

    Class<?>[] classes = clazz.getInterfaces();
    addTo.addAll(Arrays.asList(classes));
    extractInterfaces(addTo, clazz.getSuperclass());
  }

  public void click() {
    element.click();
  }

  public void submit() {
    element.submit();
  }

  public void sendKeys(CharSequence... keysToSend) {
    element.sendKeys(keysToSend);
  }

  public void clear() {
    element.clear();
  }

  public String getTagName() {
    return element.getTagName();
  }

  public String getAttribute(String name) {
    return element.getAttribute(name);
  }

  public boolean isSelected() {
    return element.isSelected();
  }

  public boolean isEnabled() {
    return element.isEnabled();
  }

  public String getText() {
    return element.getText();
  }

  public boolean isDisplayed() {
    return element.isDisplayed();
  }

  public Point getLocation() {
    return element.getLocation();
  }

  public Dimension getSize() {
    return element.getSize();
  }

  public String getCssValue(String propertyName) {
    return element.getCssValue(propertyName);
  }

  public WebElement findElement(By by) {
    return createWebElement(element.findElement(by));
  }

  public List<WebElement> findElements(By by) {
    List<WebElement> temp = element.findElements(by);
    List<WebElement> result = new ArrayList<WebElement>(temp.size());
    for (WebElement element : temp) {
      result.add(createWebElement(element));
    }
    return result;
  }

  public WebElement getWrappedElement() {
    return underlyingElement;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof WebElement)) {
      return false;
    }

    WebElement other = (WebElement) obj;
    if (other instanceof WrapsElement) {
      other = ((WrapsElement) other).getWrappedElement();
    }

    return underlyingElement.equals(other);
  }

  @Override
  public int hashCode() {
    return underlyingElement.hashCode();
  }

  public WebDriver getWrappedDriver() {
    return ((WrapsDriver) underlyingElement).getWrappedDriver();
  }

  public Point getLocationOnScreenOnceScrolledIntoView() {
    Point locationOnScreenOnceScrolledIntoView =
    		((Locatable) element).getCoordinates().inViewPort();
    return locationOnScreenOnceScrolledIntoView;
  }

  public Coordinates getCoordinates() {
    Coordinates coordinates = ((Locatable) element).getCoordinates();
    return coordinates;
  }

  protected abstract WebElement createWebElement(WebElement from);

  protected void doSomethogOnError() {
  }

  protected void doSomethogAfterInvocation() {
  }

  protected void doSomethogBeforeInvocation() {
  }

}
