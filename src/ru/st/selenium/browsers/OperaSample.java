package ru.st.selenium.browsers;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;

import com.opera.core.systems.OperaDriver;
import com.opera.core.systems.OperaProfile;

public class OperaSample {

  @Test
  public void simpleRun() {
    OperaDriver driver = new OperaDriver();
    driver.get("http://localhost/");
    driver.quit();
  }

  @Test
  public void runWithExistingProfile() throws MalformedURLException {
    OperaProfile profile = new OperaProfile(new File("C:\\Users\\alexei\\AppData\\Roaming\\Opera\\Opera"));
    OperaDriver driver = new OperaDriver(profile);
    driver.get("http://localhost/");
    driver.quit();
  } 

}
