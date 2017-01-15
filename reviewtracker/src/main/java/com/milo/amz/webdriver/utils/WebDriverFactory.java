package com.milo.amz.webdriver.utils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
  public static WebDriver getPhantomeJSDriver(){
	  WebDriver driver = null;
	  System.setProperty("phantomjs.binary.path","D:\\WebDriver\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		
		DesiredCapabilities capability = null;
		 capability = DesiredCapabilities.chrome();
       capability.setBrowserName("chrome");
       capability.setPlatform(Platform.WINDOWS);
       capability.setCapability("phantomjs.page.settings.userAgent",
               "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
       try {
           driver = new  PhantomJSDriver(capability);
           //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
       } catch (Throwable e) {
           e.printStackTrace();
       }
       return driver;
  }
  public static WebDriver getChromDriver(){
	  WebDriver driver = null;
	  System.setProperty("webdriver.chrome.driver","D:\\WebDriver\\chromedriver_win32\\chromedriver.exe");
		
		DesiredCapabilities capability = null;
		 capability = DesiredCapabilities.chrome();
       capability.setBrowserName("chrome");
       capability.setPlatform(Platform.WINDOWS);
       //            capability.setVersion("40");
      // capability.setCapability(FirefoxDriver.PROFILE, "default");
       try {
           driver = new  ChromeDriver(capability);
           //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
       } catch (Throwable e) {
           e.printStackTrace();
       }
       return driver;
  }
}
