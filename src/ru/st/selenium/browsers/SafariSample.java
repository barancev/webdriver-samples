package ru.st.selenium.browsers;

import java.io.IOException;

import org.openqa.selenium.safari.SafariDriver;

public class SafariSample {

  public static void main(String[] args) throws IOException {
    simpleRun();
  }
  
  public static void simpleRun() {
    SafariDriver driver = new SafariDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

}
