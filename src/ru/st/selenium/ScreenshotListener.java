package ru.st.selenium;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.google.common.io.Files;

public class ScreenshotListener implements IInvokedMethodListener2 {

  public static final String SELENIUM_ATTR = "selenium";

  public ScreenshotListener() {
  }

  @Override
  public void beforeInvocation(IInvokedMethod mth, ITestResult result) {
  }

  @Override
  public void beforeInvocation(IInvokedMethod mth, ITestResult result,
      ITestContext context) {
  }

  @Override
  public void afterInvocation(IInvokedMethod mth, ITestResult result) {
  }

  @Override
  public void afterInvocation(IInvokedMethod mth, ITestResult result,
      ITestContext context) {
    if (!mth.isTestMethod())
      return;
    if (result.isSuccess())
      return;

    Reporter.setCurrentTestResult(result);

    File outputDirectory = new File(context.getOutputDirectory());
    WebDriver driver = (WebDriver) context.getAttribute(SELENIUM_ATTR);

    try {
      outputDirectory.mkdirs();
      File outFile = File.createTempFile("TEST-" + result.getName(), ".png",
          outputDirectory);
      outFile.delete();
      takeScreenshot(driver, outFile);
      Reporter.log("<a href='" + outFile.getName() + "'>"
          + result.getName() + " screenshot</a>");

    } catch (Exception e) {
      e.printStackTrace();
      Reporter.log("Couldn't create screenshot");
      Reporter.log(e.getMessage());
    }

    Reporter.setCurrentTestResult(null);
  }

  public void takeScreenshot(WebDriver driver, File screenshot) throws IOException {
      screenshot.delete();
      File screenshotTempFile = ((TakesScreenshot) driver)
        .getScreenshotAs(OutputType.FILE);
      Files.copy(screenshotTempFile, screenshot);
  }
}
