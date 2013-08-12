package ru.st.selenium.browsers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.opera.core.systems.OperaDriver;
import com.opera.core.systems.OperaProfile;

public class OperaSample {

  public static void main(String[] args) throws IOException {
    runWithExistingProfile();
  }

  public static void simpleRun() {
    OperaDriver driver = new OperaDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

  public static void runWithExistingProfile() throws MalformedURLException {
    OperaProfile profile = new OperaProfile(new File("C:\\Users\\alexei\\AppData\\Roaming\\Opera\\Opera"));
    OperaDriver driver = new OperaDriver(profile);
    driver.get("http://localhost/");
    driver.quit();
  } 

}
