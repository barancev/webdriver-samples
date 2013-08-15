package ru.st.selenium.alerts;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.logging.Logs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlertTolerantWebDriver implements WebDriver, JavascriptExecutor,
    TakesScreenshot, WrapsDriver, HasInputDevices, HasTouchScreen {

  private final WebDriver driver;

  private void logUnhandledAlert(UnhandledAlertException target) {
    System.err.println("Unhandled alert: " + target.getAlertText());
    target.printStackTrace();
  }

  public AlertTolerantWebDriver(final WebDriver driver) {
    Class<?>[] allInterfaces = extractInterfaces(driver);

    this.driver = (WebDriver) Proxy.newProxyInstance(
        AlertTolerantWebDriver.class.getClassLoader(),
        allInterfaces,
        new InvocationHandler() {
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String m = method.getName();
            if ("getWrappedDriver".equals(m)) {
              return driver;
            }

            try {
              Object result = method.invoke(driver, args);
              return result;
            } catch (InvocationTargetException e) {
              Throwable target = e.getTargetException();
              if (target instanceof UnhandledAlertException) {
                logUnhandledAlert((UnhandledAlertException) target);
                // try again
                try {
                  Object result = method.invoke(driver, args);
                  return result;
                } catch (InvocationTargetException e1) {
                  throw e1.getTargetException();
                }
              }
              throw target;
            }
          }
        });
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


  public WebDriver getWrappedDriver() {
    return driver;
  }

  public void get(String url) {
    driver.get(url);
  }

  public String getCurrentUrl() {
    return driver.getCurrentUrl();
  }

  public String getTitle() {
    return driver.getTitle();
  }

  public List<WebElement> findElements(By by) {
    List<WebElement> temp = driver.findElements(by);
    List<WebElement> result = new ArrayList<WebElement>(temp.size());
    for (WebElement element : temp) {
      result.add(createWebElement(element));
    }
    return result;
  }

  public WebElement findElement(By by) {
    return createWebElement(driver.findElement(by));
  }

  public String getPageSource() {
    return driver.getPageSource();
  }

  public void close() {
    driver.close();
  }

  public void quit() {
    driver.quit();
  }

  public Set<String> getWindowHandles() {
    return driver.getWindowHandles();
  }

  public String getWindowHandle() {
    return driver.getWindowHandle();
  }

  public Object executeScript(String script, Object... args) {
    if (driver instanceof JavascriptExecutor) {
      Object[] usedArgs = unpackWrappedArgs(args);
      return ((JavascriptExecutor) driver).executeScript(script, usedArgs);
    }

    throw new UnsupportedOperationException(
        "Underlying driver instance does not support executing javascript");
  }

  public Object executeAsyncScript(String script, Object... args) {
    if (driver instanceof JavascriptExecutor) {
      Object[] usedArgs = unpackWrappedArgs(args);
      return ((JavascriptExecutor) driver).executeAsyncScript(script, usedArgs);
    }

    throw new UnsupportedOperationException(
        "Underlying driver instance does not support executing javascript");
  }

  private Object[] unpackWrappedArgs(Object... args) {
    // Walk the args: the various drivers expect unpacked versions of the
    // elements
    Object[] usedArgs = new Object[args.length];
    for (int i = 0; i < args.length; i++) {
      usedArgs[i] = unpackWrappedElement(args[i]);
    }
    return usedArgs;
  }

  private Object unpackWrappedElement(Object arg) {
    if (arg instanceof List<?>) {
      List<Object> aList = (List<Object>) arg;
      List<Object> toReturn = new ArrayList<Object>();
      for (int j = 0; j < aList.size(); j++) {
        toReturn.add(unpackWrappedElement(aList.get(j)));
      }
      return toReturn;
    } else if (arg instanceof Map<?, ?>) {
      Map<Object, Object> aMap = (Map<Object, Object>) arg;
      Map<Object, Object> toReturn = new HashMap<Object, Object>();
      for (Object key : aMap.keySet()) {
        toReturn.put(key, unpackWrappedElement(aMap.get(key)));
      }
      return toReturn;
    } else if (arg instanceof AlertTolerantWebElement) {
      return ((AlertTolerantWebElement) arg).getWrappedElement();
    } else {
      return arg;
    }
  }

  public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
    if (driver instanceof TakesScreenshot) {
      return ((TakesScreenshot) driver).getScreenshotAs(target);
    }

    throw new UnsupportedOperationException(
        "Underlying driver instance does not support taking screenshots");
  }

  public TargetLocator switchTo() {
    return new AlertTolerantTargetLocator(driver.switchTo());
  }

  public Navigation navigate() {
    return new TracingNavigation(driver.navigate());
  }

  public Options manage() {
    return new TracingOptions(driver.manage());
  }

  private WebElement createWebElement(WebElement from) {
    return new AlertTolerantWebElement(from);
  }

  public Keyboard getKeyboard() {
    if (driver instanceof HasInputDevices) {
      return new AlertTolerantKeyboard(driver);
    } else {
      throw new UnsupportedOperationException(
          "Underlying driver does not implement advanced user interactions.");
    }
  }

  public Mouse getMouse() {
    if (driver instanceof HasInputDevices) {
      return new AlertTolerantMouse(driver);
    } else {
      throw new UnsupportedOperationException(
          "Underlying driver does not implement advanced user interactions.");
    }
  }

  public TouchScreen getTouch() {
    if (driver instanceof HasTouchScreen) {
      return new AlertTolerantTouch(driver);
    } else {
      throw new UnsupportedOperationException(
          "Underlying driver does not implement touch api.");
    }
  }

  private class AlertTolerantWebElement implements WebElement, WrapsElement, WrapsDriver, Locatable {

    private final WebElement element;
    private final WebElement underlyingElement;

    private AlertTolerantWebElement(final WebElement element) {
      this.element = (WebElement) Proxy.newProxyInstance(
          AlertTolerantWebDriver.class.getClassLoader(),
          extractInterfaces(element),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method,
                Object[] args) throws Throwable {
              String m = method.getName();
              if ("getWrappedElement".equals(m)) {
                return element;
              }

              try {
                Object result = method.invoke(element, args);
                return result;
              } catch (InvocationTargetException e) {
                Throwable target = e.getTargetException();
                if (target instanceof UnhandledAlertException) {
                  logUnhandledAlert((UnhandledAlertException) target);
                  // try again
                  try {
                    Object result = method.invoke(element, args);
                    return result;
                  } catch (InvocationTargetException e1) {
                    throw e1.getTargetException();
                  }
                }
                throw target;
              }
            }
          });
      this.underlyingElement = element;
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
      return driver;
    }

    public Coordinates getCoordinates() {
      Coordinates coordinates = ((Locatable) underlyingElement).getCoordinates();
      return coordinates;
    }
  }

  private class TracingNavigation implements Navigation {

    private final WebDriver.Navigation navigation;

    TracingNavigation(Navigation navigation) {
      this.navigation = navigation;
    }

    public void to(String url) {
      navigation.to(url);
    }

    public void to(URL url) {
      to(String.valueOf(url));
    }

    public void back() {
      navigation.back();
    }

    public void forward() {
      navigation.forward();
    }

    public void refresh() {
      navigation.refresh();
    }
  }

  private class TracingOptions implements Options {

    private Options options;

    private TracingOptions(Options options) {
      this.options = options;
    }

    public void addCookie(Cookie cookie) {
      options.addCookie(cookie);
    }

    public void deleteCookieNamed(String name) {
      options.deleteCookieNamed(name);
    }

    public void deleteCookie(Cookie cookie) {
      options.deleteCookie(cookie);
    }

    public void deleteAllCookies() {
      options.deleteAllCookies();
    }

    public Set<Cookie> getCookies() {
      return options.getCookies();
    }

    public Cookie getCookieNamed(String name) {
      return options.getCookieNamed(name);
    }

    public Timeouts timeouts() {
      return options.timeouts();
    }

    public ImeHandler ime() {
      return options.ime();
    }

    @Override
    public Logs logs() {
      return options.logs();
    }

    @Override
    public Window window() {
      return options.window();
    }
  }

  private class AlertTolerantTargetLocator implements TargetLocator {

    private final TargetLocator targetLocator;

    private AlertTolerantTargetLocator(final TargetLocator targetLocator) {
      this.targetLocator = (TargetLocator) Proxy.newProxyInstance(
          AlertTolerantWebDriver.class.getClassLoader(),
          extractInterfaces(targetLocator),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
              try {
                Object result = method.invoke(targetLocator, args);
                return result;
              } catch (InvocationTargetException e) {
                Throwable target = e.getTargetException();
                if (target instanceof UnhandledAlertException) {
                  logUnhandledAlert((UnhandledAlertException) target);
                  // try again
                  try {
                    Object result = method.invoke(targetLocator, args);
                    return result;
                  } catch (InvocationTargetException e1) {
                    throw e1.getTargetException();
                  }
                }
                throw target;
              }
            }

          });
    }

    public WebDriver frame(int frameIndex) {
      return targetLocator.frame(frameIndex);
    }

    public WebDriver frame(String frameName) {
      return targetLocator.frame(frameName);
    }

    public WebDriver frame(WebElement frameElement) {
      return targetLocator.frame(frameElement);
    }

    public WebDriver window(String windowName) {
      return targetLocator.window(windowName);
    }

    public WebDriver defaultContent() {
      return targetLocator.defaultContent();
    }

    public WebElement activeElement() {
      return targetLocator.activeElement();
    }

    public Alert alert() {
      return targetLocator.alert();
    }
  }

  public class AlertTolerantKeyboard implements Keyboard {

    private final WebDriver driver;
    private final Keyboard keyboard;

    public AlertTolerantKeyboard(WebDriver driver) {
      this.driver = driver;
      final Keyboard kb = ((HasInputDevices) this.driver).getKeyboard();
      this.keyboard = (Keyboard) Proxy.newProxyInstance(
          AlertTolerantWebDriver.class.getClassLoader(),
          extractInterfaces(kb),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
              try {
                Object result = method.invoke(kb, args);
                return result;
              } catch (InvocationTargetException e) {
                Throwable target = e.getTargetException();
                if (target instanceof UnhandledAlertException) {
                  logUnhandledAlert((UnhandledAlertException) target);
                  // try again
                  try {
                    Object result = method.invoke(kb, args);
                    return result;
                  } catch (InvocationTargetException e1) {
                    throw e1.getTargetException();
                  }
                }
                throw target;
              }
            }
          });
    }

    public void sendKeys(CharSequence... keysToSend) {
      keyboard.sendKeys(keysToSend);
    }

    public void pressKey(CharSequence keyToPress) {
      keyboard.pressKey(keyToPress);
    }

    public void releaseKey(CharSequence keyToRelease) {
      keyboard.releaseKey(keyToRelease);
    }

  }

  public class AlertTolerantMouse implements Mouse {
    private final WebDriver driver;
    private final Mouse mouse;

    public AlertTolerantMouse(WebDriver driver) {
      this.driver = driver;
      final Mouse ms = ((HasInputDevices) this.driver).getMouse();
      this.mouse = (Mouse) Proxy.newProxyInstance(
          AlertTolerantWebDriver.class.getClassLoader(),
          extractInterfaces(ms),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
              try {
                Object result = method.invoke(ms, args);
                return result;
              } catch (InvocationTargetException e) {
                Throwable target = e.getTargetException();
                if (target instanceof UnhandledAlertException) {
                  logUnhandledAlert((UnhandledAlertException) target);
                  // try again
                  try {
                    Object result = method.invoke(ms, args);
                    return result;
                  } catch (InvocationTargetException e1) {
                    throw e1.getTargetException();
                  }
                }
                throw target;
              }
            }
          });
    }

    public void click(Coordinates where) {
      mouse.click(where);
    }

    public void doubleClick(Coordinates where) {
      mouse.doubleClick(where);
    }

    public void mouseDown(Coordinates where) {
      mouse.mouseDown(where);
    }

    public void mouseUp(Coordinates where) {
      mouse.mouseUp(where);
    }

    public void mouseMove(Coordinates where) {
      mouse.mouseMove(where);
    }

    public void mouseMove(Coordinates where, long xOffset, long yOffset) {
      mouse.mouseMove(where, xOffset, yOffset);
    }

    public void contextClick(Coordinates where) {
      mouse.contextClick(where);
    }
  }

  public class AlertTolerantTouch implements TouchScreen {

    private final WebDriver driver;
    private final TouchScreen touchScreen;

    public AlertTolerantTouch(WebDriver driver) {
      this.driver = driver;
      final TouchScreen ts = ((HasTouchScreen) this.driver).getTouch();
      this.touchScreen = (TouchScreen) Proxy.newProxyInstance(
          AlertTolerantWebDriver.class.getClassLoader(),
          extractInterfaces(ts),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
              try {
                Object result = method.invoke(ts, args);
                return result;
              } catch (InvocationTargetException e) {
                Throwable target = e.getTargetException();
                if (target instanceof UnhandledAlertException) {
                  logUnhandledAlert((UnhandledAlertException) target);
                  // try again
                  try {
                    Object result = method.invoke(ts, args);
                    return result;
                  } catch (InvocationTargetException e1) {
                    throw e1.getTargetException();
                  }
                }
                throw target;
              }
            }
          });
    }

    public void singleTap(Coordinates where) {
      touchScreen.singleTap(where);
    }

    public void down(int x, int y) {
      touchScreen.down(x, y);
    }

    public void up(int x, int y) {
      touchScreen.up(x, y);
    }

    public void move(int x, int y) {
      touchScreen.move(x, y);
    }

    public void scroll(Coordinates where, int xOffset, int yOffset) {
      touchScreen.scroll(where, xOffset, yOffset);
    }

    public void doubleTap(Coordinates where) {
      touchScreen.doubleTap(where);
    }

    public void longPress(Coordinates where) {
      touchScreen.longPress(where);
    }

    public void scroll(int xOffset, int yOffset) {
      touchScreen.scroll(xOffset, yOffset);
    }

    public void flick(int xSpeed, int ySpeed) {
      touchScreen.flick(xSpeed, ySpeed);
    }

    public void flick(Coordinates where, int xOffset, int yOffset, int speed) {
      touchScreen.flick(where, xOffset, yOffset, speed);
    }
  }
}
