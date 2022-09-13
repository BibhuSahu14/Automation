package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import com.qa.util.TestUtil;
import com.qa.util.WebEventListener;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;

import java.time.Duration;

public class TestBase {

	// A static member is a member of a class that isn't associated with an instance
	// of a class. Instead, the member belongs to the class itself.

	public static WebDriver driver; // Initialize webdriver with static variable. This is set to private because I
									// am not using this anywhere outside this class
	public static DesiredCapabilities capabilities;
	public static Properties prop; // Public class variable for properties. This is a global variable(public
									// static).I can use inside TestBase.java class and all other child classes also
	@SuppressWarnings("deprecation")
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static String browserName1;
	public static String browserVersion1;
	private static String propertiesFileName;
	public static String env;
	public static JSONObject testDataFile;
	public static JSONObject testData;
	public static String browserName = null;

	// private static final String KEY = "environment";

	// This is the logger class. Generate the logs? : use Apache log4j API (log4j
	// jar)
	public static Logger log = Logger.getLogger(TestBase.class);

	// Method for adding logs passed from test cases. This is for extentTest HTML
	// report and log4j log files as well
	public static void reportLog(String message) {
		log.info(message);
		Reporter.log(message);
	}

	// Actions Class to take actions method.
	// Actions action = new Actions(getdriver());

	// Method to take screenshot at any step you want
	public static void takeScreenshotAtAnyStep(String screenshotPath) {
		Reporter.log(screenshotPath);
	}

	// To make driver thread safe. This will be required for parallel execution from
	// testNG
	private static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>(); // Initialize thread local variable.

	// Getter method ThreadLocal variable dr. This is the driver instance, it will
	// check which thread is calling this for parallel running.
	public static WebDriver getdriver() {
		return dr.get();
	}

	// Setter method. This will set the webdriver reference driverref
	public static void setDriver(WebDriver driverref) {
		dr.set(driverref);
	}

	// For cleanup operation. This will remove any values stored by threadLocal
	// variable. This will be used in afterclass method of each test.
	public static void unload() {
		dr.remove();
	}

	// Initialization method
	@SuppressWarnings("deprecation")
	public static void initialization() {
		// Read the properties file.Based on the parameter value provided for
		// environment, the properties file will load
		try {
			propertiesFileName = "config.properties";
			prop = new Properties(); // initializing the prop variable
			FileInputStream fs = new FileInputStream("src/main/java/com/qa/config/" + propertiesFileName);
			prop.load(fs);
			browserName = prop.getProperty("browser");
			System.out.println("browserName: " + browserName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (browserName.trim().equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			capabilities.setCapability("browserName", "chrome");
			capabilities.setPlatform(Platform.WINDOWS);
			ChromeOptions options = new ChromeOptions();
			options.setAcceptInsecureCerts(true);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		} else if (browserName.trim().equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("acceptSslCerts", "true");
			capabilities.setBrowserName("MicrosoftEdge");
			capabilities.setAcceptInsecureCerts(true);
			capabilities.setPlatform(Platform.WINDOWS);

		} else if (browserName.trim().equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("acceptInsecureCerts", true);
			capabilities.setCapability("marionette", true);
		}

		if (prop.getProperty("remote").equals("true")) {
			System.out.println("Running on remote");
			try {
				driver = new RemoteWebDriver(new URL(prop.getProperty("seleniumgrid_url")), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			
			if (browserName.trim().equalsIgnoreCase("chrome"))
				driver = new ChromeDriver(capabilities);
			else if (browserName.trim().equalsIgnoreCase("firefox"))
				driver = new FirefoxDriver(capabilities);
			else if (browserName.trim().equalsIgnoreCase("edge"))
				driver = new EdgeDriver(capabilities);
		}

		setDriver(driver);

		// To get Browser details:
		Capabilities browserCap = ((RemoteWebDriver) getdriver()).getCapabilities();
		browserName1 = browserCap.getBrowserName();
		browserVersion1 = browserCap.getBrowserVersion();
		reportLog(browserName1 + "   " + browserVersion1);

		// To get Application environment currently running

		// Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(KEY);
		env = prop.getProperty("environment");

		// Create EventFiringWebDriver class object
		e_driver = new EventFiringWebDriver(getdriver());
		// Now create object of EventListerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		// Register the eventListener class object with event firing webdriver object.
		e_driver.register(eventListener);
		// Assign this to the main driver
		// driver = e_driver;
		setDriver(e_driver);

		getdriver().manage().window().maximize();
		getdriver().manage().deleteAllCookies();
		getdriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		getdriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));


	}

}
