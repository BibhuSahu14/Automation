package com.qa.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qa.base.TestBase;

public class TestUtil extends TestBase {

	// Static public variable to use anywhere.
	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 20;
	public static long EXPLICIT_WAIT = 60;
	public static WebDriverWait wait = new WebDriverWait(getdriver(), Duration.ofSeconds(EXPLICIT_WAIT));

	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MMM_yyyy_HH_mm_ss");
	static LocalDateTime now = LocalDateTime.now();
	public static String CurrentTime = dtf.format(now);

	static JavascriptExecutor js;
	static Select dropdown;

	public void switchToFrame() {
		getdriver().switchTo().frame("mainpanel");
	}

	public static void threadMethod(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException ie) {
		}
	}

	public static String generateRandomGenerator() {

		String password = RandomStringUtils.randomAlphabetic(6);

		return password;

	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) getdriver()).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "//screenshots//" + System.currentTimeMillis() + ".png"));
	}

	// Creating a method getScreenshot and passing two parameters
	// driver and screenshotName
	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		// below line is just to append the date format with the screenshot name to
		// avoid duplicate names
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		// String destination = System.getProperty("user.dir") +
		// "//Screenshots//"+screenshotName+dateName+".png";
		String destination = System.getProperty("user.dir") + File.separator + "test-output" + File.separator
				+ "TestResults" + File.separator + "Report_" + CurrentTime + File.separator + screenshotName + dateName
				+ ".png";

		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);

		// This is required for Jenkins
		String ImagePathForRemote = "./" + screenshotName + dateName + ".png";

		// Returns the captured file path
		return ImagePathForRemote;
	}

	/*
	 * public static void runTimeInfo(String messageType, String message) throws
	 * InterruptedException { js = (JavascriptExecutor) getdriver(); // Check for
	 * jQuery on the page, add it if need be
	 * js.executeScript("if (!window.jQuery) {" +
	 * "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
	 * +
	 * "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
	 * + "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
	 * Thread.sleep(5000);
	 * 
	 * // Use jQuery to add jquery-growl to the page js.executeScript(
	 * "$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')"
	 * );
	 * 
	 * // Use jQuery to add jquery-growl styles to the page
	 * js.executeScript("$('head').append('<link rel=\"stylesheet\" " +
	 * "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " +
	 * "type=\"text/css\" />');"); Thread.sleep(5000);
	 * 
	 * // jquery-growl w/ no frills
	 * js.executeScript("$.growl({ title: 'GET', message: '/' });"); //'"+color+"'"
	 * if (messageType.equals("error")) {
	 * js.executeScript("$.growl.error({ title: 'ERROR', message: '"+message+"' });"
	 * ); }else if(messageType.equals("info")){ js.
	 * executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });"
	 * ); }else if(messageType.equals("warning")){ js.
	 * executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });"
	 * ); }else System.out.println("no error message"); // jquery-growl w/ colorized
	 * output // js.
	 * executeScript("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });"
	 * ); // js.
	 * executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });"
	 * ); // js.
	 * executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });"
	 * ); Thread.sleep(5000); }
	 */

	public static void scrollPageToElement(WebElement wb) {
		// Javascript command
		JavascriptExecutor js = (JavascriptExecutor) getdriver();
		js.executeScript("arguments[0].scrollIntoView();", wb);
	}

	// Get the current time(formatted)
	public static String getCurrentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MMM_yyyy_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();
		String currTime = dtf.format(now);

		return currTime;
	}

	public static WebDriverWait setExplicitWait(int seconds) {
		wait = new WebDriverWait(getdriver(), Duration.ofMillis(seconds));
		return wait;
	}

	/*
	 * 
	 * public static String input(WebElement element, String elemName, String Value)
	 * method specification :-
	 * 
	 * 1) Inputs/sends value 2) element -> Locator derived from FindBy in Page 3)
	 * elemName -> the name of the web element where we intend to input/send values
	 * 4) Value -> the string value which we intend to input/send 5)
	 * waitForElementToLoad(element) -> waits for web element to load 6)
	 * element.sendKeys(Value) -> inputs/sends the value to the intended web element
	 * 
	 * @param : WebElement for the input-box, name of the web element, value to be
	 * inputed *
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String input(WebElement element, String elemName, String Value) {

		log.debug("Sending Values in : " + elemName);
		System.out.println("Sending Values in : " + elemName);

		try {

			// Wait for the input box to appear on the page
			waitForElementToLoad(element);

			// Highlight the input box
			TestUtil.highlightElement(element);

			// Send values to the input box
			element.sendKeys(Value);

			// Log result
			System.out.println("Inputted '" + Value + "' text into : " + elemName);
			log.debug("Inputted '" + Value + "' text into : " + elemName);

			return "Pass : Inputted '" + Value + "' text into : " + elemName;

		}

		catch (Throwable inputException) {

			// Log error
			System.err.println("Error while inputting into - " + elemName + " : " + inputException.getMessage());
			log.debug("Error while inputting into - " + elemName + " : " + inputException.getMessage());

			return "Fail : Error while inputting into - " + elemName + " : " + inputException.getMessage();

		}

	}
	/*
	 * 
	 * public static String clearAndInput(WebElement element, String elemName,
	 * String Value) method specification :-
	 * 
	 * 1) Clears and Inputs/sends value 2) element -> Locator derived from FindBy in
	 * Page 3) elemName -> the name of the web element where we intend to input/send
	 * values 4) Value -> the string value which we intend to input/send 5)
	 * waitForElementToLoad(element) -> waits for web element to load
	 * 5)element.clear(); -> Clear the input field 6)element.sendKeys(Value) ->
	 * inputs/sends the value to the intended web element
	 * 
	 * @param : WebElement for the input-box, name of the web element, value to be
	 * inputed *
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String clearAndInput(WebElement element, String elemName, String Value) {
		log.debug("Sending Values in : " + elemName);
		System.out.println("Sending Values in : " + elemName);
		try {

			// Wait for the input box to appear on the page
			waitForElementToLoad(element);

			// Highlight the input box
			TestUtil.highlightElement(element);

			// Clear input field
			element.clear();
			log.debug("Cleared : " + elemName);
			System.out.println("Cleared : " + elemName);

			// Send values to the input box
			element.sendKeys(Value);

			// Log result
			System.out.println("Inputted '" + Value + "' text into : " + elemName);
			log.debug("Inputted '" + Value + "' text into : " + elemName);
			return "Pass : Inputted '" + Value + "' text into : " + elemName;

		}

		catch (Throwable inputException) {

			// Log error
			System.err.println(
					"Error while clearing and inputting into - " + elemName + " : " + inputException.getMessage());
			log.debug("Error while clearing and inputting into - " + elemName + " : " + inputException.getMessage());

			return "Fail : Error while clearing and inputting into - " + elemName + " : " + inputException.getMessage();

		}

	}
	/*
	 * public static void waitForElementToLoad(final WebElement element) method
	 * specification :- 1) Waits for the web element to appear on the page 2)
	 * Wait<WebDriver> wait = TestUtil.setExplicitWait(90);-> Waits for 90 seconds
	 * 3) wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition
	 * (All documents present on the page get ready) met
	 * 
	 * @param : WebElement to be waited *
	 * 
	 * @return : void
	 */

	public static void waitForElementToLoad(final WebElement element) {

		log.debug("Waiting for web element to load on the page");
		System.out.println("Waiting for web element to load on the page");

		try {

			// Wait until the element is located on the page
			@SuppressWarnings("unused")
			WebElement element1 = wait.until(visibilityOfElementLocated(element));

			// Log result
			log.debug("Waiting ends ... Web element loaded on the page");
			System.out.println("Waiting ends ... Web element loaded on the page");

		}

		catch (Throwable waitForElementException) {

			// Log error
			log.debug("Error came while waiting for element to appear : " + waitForElementException.getMessage());
			System.err.println(
					"Error came while waiting for element to appear : " + waitForElementException.getMessage());

		}

	}

	// highlight the element on which action will be performed
	public static void highlightElement(WebElement element) {

		try {

			for (int i = 0; i < 3; i++) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				// js.executeScript("arguments[0].setAttribute('style',
				// arguments[1]);",driver.findElement(Locator),
				// "color: red; border: 2px solid red;");
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"background-color: yellow; outline: 1px solid rgb(136, 255, 136);");

			}

		}

		catch (Throwable t) {
			System.out.println("Error came : " + t.getMessage());
			log.debug("Error came : " + t.getMessage());
		}

	}

	/*
	 * public static ExpectedCondition<WebElement> visibilityOfElementLocated(final
	 * WebElement element) method specification :-
	 * 
	 * 1) Waits for the web element to appear on the page 2) WebElement
	 * toReturn.isDisplayed() -> Returns true if displayed on the page, else returns
	 * false *
	 * 
	 * @param : Web element
	 * 
	 * @return : ExpectedCondition about the web element
	 */

	public static ExpectedCondition<WebElement> visibilityOfElementLocated(final WebElement element) {

		return new ExpectedCondition<WebElement>() {

			public WebElement apply(WebDriver driver) {

				// Store the web element
				WebElement toReturn = element;

				// Check whether the web element is displayed on the page
				if (toReturn.isDisplayed())
					return toReturn;

				return null;

			}

		};

	}

	/*
	 * public static String clickElement(WebElement element , String elemName)
	 * method specification :-
	 * 
	 * 1) Clicks on a web element 2) element -> element by id, x-path, name,etc. 3)
	 * elemName -> the name of the element which we intend to click 4)
	 * waitForElementToLoad(element) -> waits for element to load 5) element.click()
	 * -> clicks on the intended element
	 * 
	 * @param : Locator for the link, name of the web element
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String clickElement(WebElement element, String elemName) {

		log.debug("Clicking on : " + elemName);
		System.out.println("Clicking on : " + elemName);

		try {

			// Wait for link to appear on the page
			waitForElementToLoad(element);

			// Highlight link
			TestUtil.highlightElement(element);

			// Click on the link
			element.click();

			// Log result
			System.out.println("Clicked on : " + elemName);
			log.debug("Clicked on : " + elemName);

			return "Pass : Clicked on : " + elemName;

		}

		catch (Throwable clickElementException) {

			// Log error
			System.err.println("Error while clicking on - '" + elemName + "' : " + clickElementException.getMessage());
			log.debug("Error while clicking on - '" + elemName + "' : " + clickElementException.getMessage());

			return "Fail : Error while clicking on - '" + elemName + "' : " + clickElementException.getMessage();

		}

	}

	/*
	 * public static void waitForPageToLoad() method specification :-
	 * 
	 * 1) Waits for a new page to load completely 2)
	 * wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (All
	 * documents present on the page get ready) met
	 * 
	 * @param : no parameters passed
	 * 
	 * @return : void
	 */

	public static void waitForPageToLoad() throws InterruptedException {

		try {

			// Wait until expected condition (All documents present on the page
			// get ready) met
			wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {

					if (!(d instanceof JavascriptExecutor))
						return true;

					Object result = ((JavascriptExecutor) d)
							.executeScript("return document['readyState'] ? 'complete' == document.readyState : true");

					if (result != null && result instanceof Boolean && (Boolean) result)
						return true;

					return false;

				}

			});

		}

		catch (Throwable waitForPageToLoadException) {

			System.err
					.println("Error came while waiting for page to load : " + waitForPageToLoadException.getMessage());
			log.debug("Error came while waiting for page to load : " + waitForPageToLoadException.getMessage());

		}
	}

	/*
	 * public static void uncheckCheckBox(By locator, String elemName) method
	 * specification :-
	 * 
	 * 1) Checks a check-box if not checked already 2) locator -> to locate the
	 * element by id,x-path,name,etc. 3) elemName -> the name/type of the check-box
	 * which we intend to check 4) driver.findElement(locator).getAttribute("value")
	 * == "on" -> is used to verify whether the intended checkbox is earlier checked
	 * or not 5) driver.findElement(locator).click() -> checks the check-box
	 * 
	 * @param : Locator for the Check-box, name of the web element
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String uncheckCheckBox(WebElement element, String elemName) {

		log.debug("Unchecking the checkbox : " + elemName);
		System.out.println("Unchecking the checkbox : " + elemName);

		try {

			// Highlight check-box
			TestUtil.highlightElement(element);

			// Wait for check-box to appear on the page
			waitForElementToLoad(element);

			// UnCheck check-box if already checked
			if (element.isSelected()) {
				element.click();
			}

			// Log the result
			System.out.println("Unchecked '" + elemName + "'");
			log.debug("Unchecked '" + elemName + "'");

			return "Pass : Unchecked '" + elemName + "'";

		}

		catch (Throwable uncheckCheckBoxException) {

			// Log the exception
			System.err.println(
					"Error came while unchecking '" + elemName + "' : " + uncheckCheckBoxException.getMessage());
			log.debug("Error came while unchecking '" + elemName + "' : " + uncheckCheckBoxException.getMessage());

			return "Fail : Error came while unchecking '" + elemName + "' : " + uncheckCheckBoxException.getMessage();

		}

	}

	/*
	 * public static String verifyTextPresentInPageSource(String expText) method
	 * specification :-
	 * 
	 * 1) Verifies text present in the page source 2) expText -> Expected text to be
	 * verified from page source 3)
	 * Assert.assertTrue(driver.getPageSource().contains(expText)) -> verifies
	 * whether the expected text exist in the page source or not
	 * 
	 * @param : Expected text to verify
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String verifyTextPresentInPageSource(String expText) {

		log.debug("Verifying Text : '" + expText + "' " + "present in the Page Source");
		System.out.println("Verifying Text : '" + expText + "' " + "present in the Page Source");

		try {

			// Verify page source contains expected text
			Assert.assertTrue(driver.getPageSource().contains(expText));

			// Log result
			log.debug("'" + expText + "' present in the Page Source");
			System.out.println("'" + expText + "' present in the Page Source");

			return "Pass : '" + expText + "' present in the Page Source";

		}

		catch (Throwable verifyTextPresentError) {

			// report error
			System.err.println("Error while Verifying Text from Page Source : " + verifyTextPresentError.getMessage());
			log.debug("Error while Verifying Text from Page Source : " + verifyTextPresentError.getMessage());

			return "Fail : Error while Verifying Text from Page Source : " + verifyTextPresentError.getMessage();

		}

	}
	/*
	 * public static String clearField(WebElement element, String elemName) method
	 * specification :-
	 * 
	 * 1) Clears a text field 2) locator -> identify the text field by
	 * id,x-path,name,etc. 3) elemName -> the name of the text-field which we intend
	 * to clear 4) waitForElementToLoad(locator) -> waits for text-field to load 5)
	 * driver.findElement(locator).clear() -> clears the intended text-field
	 * 
	 * @param : Locator for the input-box, name of the web element
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String clearField(WebElement element, String elemName) {

		log.debug("Clearing field : " + elemName);
		System.out.println("Clearing field : " + elemName);

		try {

			// Wait for the input-box to load on the page
			waitForElementToLoad(element);

			// Highlight the input-box
			TestUtil.highlightElement(element);

			// Clear the input-box
			element.clear();

			// Log result
			System.out.println("Cleared : " + elemName);
			log.debug("Cleared : " + elemName);

			return "Pass : Cleared : " + elemName;

		}

		catch (Throwable clearFieldException) {

			// Log error
			System.err.println("Error while clearing - " + elemName + " : " + clearFieldException.getMessage());
			log.debug("Error while clearing - " + elemName + " : " + clearFieldException.getMessage());

			return "Fail : Error while clearing - " + elemName + " : " + clearFieldException.getMessage();

		}

	}
	/*
	 * public static String assertText(String elemName,String actValue, String
	 * expValue) method specification :-
	 * 
	 * 1) Verifies and returns TRUE if expected and actual text match 2) elemName ->
	 * the name/type of text we intend to compare 3) actValue -> the actual string
	 * value which is shown in the application 4) expValue -> the expected string
	 * value which should be shown in the application 5)
	 * Assert.assertEquals(expValue.trim(), actValue.trim())) -> trims and compares
	 * the actual and expected string value
	 * 
	 * @param : Name of the web element, Actual text and expected text
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String assertText(String elemName, String actValue, String expValue) {

		log.debug("Asserting  Text  where : ExpectedText = " + expValue + "  and ActualText = " + actValue);

		System.out.println("Asserting  Text  where : ExpectedText = " + expValue + "  and ActualText = " + actValue);

		try {

			// Assert that expected value matches with actual value
			Assert.assertEquals(expValue.trim(), actValue.trim());

			// Log result
			System.out.println("Successfully asserted text for : " + elemName + " where Expected text is '" + expValue
					+ "' and Actual text is '" + actValue + "'");
			log.debug("Successfully asserted text for : " + elemName + " where Expected text is '" + expValue
					+ "' and Actual text is '" + actValue + "'");

			return "Pass : Expected text matches with actual text";

		}

		catch (Throwable assertTextException) {

			// Log error
			System.err
					.println("Error while Asserting Text for - " + elemName + " : " + assertTextException.getMessage());
			log.debug("Error while Asserting Text for - " + elemName + " : " + assertTextException.getMessage());

			return "Fail : Error while Asserting Text for - " + elemName + " : " + assertTextException.getMessage();

		}

	}
	/*
	 * public static Boolean isElementPresent(By Locator,String elemName) method
	 * specification :-
	 * 
	 * 1) Check whether an element present or not on the webpage 2)
	 * driver.findElement(Locator).isDisplayed() -> Return true/false based on
	 * whether element is displayed or not on the page
	 * 
	 * @param : Locator for the web element, Name of the web element
	 * 
	 * @return : True/false based on whether element is displayed on the page or not
	 */

	public static Boolean isElementPresent(WebElement element, String elemName) {

		log.debug("Verifying whether Element : " + elemName + " is present");
		System.out.println("Verifying whether Element : " + elemName + " is present");

		Boolean present = null;

		try {

			// Wait for web element to load
			waitForElementToLoad(element);

			// Highlight the web element
			TestUtil.highlightElement(element);

			// Verify whether element is displayed on the page or not
			present = element.isDisplayed();

			// Log result
			if (present) {

				System.out.println("Element : " + elemName + " is present");
				log.debug("Element : " + elemName + " is present");

			}

			else {

				System.out.println("Element : " + elemName + " is not present");
				log.debug("Element : " + elemName + " is not present");

			}

			return present;

		}

		catch (Throwable isElementPresentException) {

			// Log error
			System.err.println("Error while verifying - " + elemName + " element Present : "
					+ isElementPresentException.getMessage());
			log.debug("Error while verifying - " + elemName + " element Present : "
					+ isElementPresentException.getMessage());

			return false;

		}

	}

	/*
	 * public static String retrieveText(WebElement element, String elemName) method
	 * specification :-
	 * 
	 * 1) Return retrieved text from webpage 2)
	 * driver.findElement(locator).getText() -> Retrieves text from the web element
	 * targeted by specified locator
	 * 
	 * @param : Locator for the web element, Name of the web element
	 * 
	 * @return : Text retrieved from the webpage
	 */

	public static String retrieveText(WebElement element, String elemName) {

		String retrievedText = null;

		log.debug("Retrieving Text from : " + elemName);
		System.out.println("Retrieving Text from : " + elemName);

		try {

			// Wait for web element to load on the page
			waitForElementToLoad(element);

			// Highlight the web element
			TestUtil.highlightElement(element);

			// Retrieve text from web element
			retrievedText = element.getText().trim();

			// Log result
			log.debug("Retrieved text : " + retrievedText);
			System.out.println("Retrieved text : " + retrievedText);

		}

		catch (Throwable retrieveTextException) {

			// Log error
			System.err.println(
					"Error while Getting Text from '" + elemName + "' : " + retrieveTextException.getMessage());
			log.debug("Error while Getting Text from '" + elemName + "' : " + retrieveTextException.getMessage());

			return "Fail : Error while Getting Text from '" + elemName + "' : " + retrieveTextException.getMessage();

		}

		return retrievedText;

	}

	/*
	 * public static String retrieveAttributeValue(By locator,String value,String
	 * elemName) method specification :-
	 * 
	 * 1) Return retrieved HTML attribute value from webpage 2)
	 * driver.findElement(locator).getAttribute(value) -> Retrieves attribute
	 * (present under a web element) value
	 * 
	 * @param : Locator for the web element, Attribute name, Name of the web element
	 * 
	 * @return : Attribute value retrieved
	 */

	public static String retrieveAttributeValue(WebElement element, String value, String elemName) {

		String attributeValue = null;

		log.debug("Getting Attribute '" + value + "'  Value from : " + elemName);
		System.out.println("Getting Attribute '" + value + "'  Value from : " + elemName);

		try {

			// Wait for web element to load
			waitForElementToLoad(element);

			// Highlight the web element
			TestUtil.highlightElement(element);

			// Get attribute value for the web element
			attributeValue = element.getAttribute(value);

			// Log result
			log.debug("Got Attribute '" + value + "'  Value from : " + elemName);
			System.out.println("Got Attribute '" + value + "'  Value from : " + elemName);

		}

		catch (Throwable retrieveAttributeValueException) {

			// report error
			System.err.println("Error while Getting Attribute '" + value + "' value from '" + elemName + "' : "
					+ retrieveAttributeValueException.getMessage());

			log.debug("Error while Getting Attribute '" + value + "' value from '" + elemName + "' : "
					+ retrieveAttributeValueException.getMessage());

			return "Fail : Error while Getting Attribute '" + value + "' value from '" + elemName + "' : "
					+ retrieveAttributeValueException.getMessage();

		}

		return attributeValue;

	}
	/*
	 * public static String acceptAlert(String elemName) method specification :-
	 * 
	 * 1) Accepts an alert 2) driver.switchTo().alert() -> Switch to the desired
	 * alert 3) alert.accept() -> Accepts the alert
	 * 
	 * @param : Name of the web element
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String acceptAlert(String elemName) {

		log.debug("Accepting alert : " + elemName);
		System.out.println("Accepting alert : " + elemName);

		try {

			// Create a new alert object
			Alert alert = driver.switchTo().alert();

			// Accept the alert
			alert.accept();

			// Log result
			System.out.println("Accepted alert : " + elemName);
			log.debug("Accepted alert : " + elemName);

			return "Pass : Accepted the alert '" + elemName + "'";

		}

		catch (Throwable acceptAlertException) {

			// Log error
			System.err.println("Error came while accepting alert : " + acceptAlertException.getMessage());
			log.debug("Error came while accepting alert : " + acceptAlertException.getMessage());

			return "Fail : Error came while accepting alert : " + acceptAlertException.getMessage();

		}

	}

	/*
	 * public static void clickAndWaitForPageLoad(WebElement element, String
	 * elemName) method specification :-
	 * 
	 * 1) Click and wait for next page to load 2)
	 * driver.findElement(locator).click() -> Clicks on the web element targeted by
	 * locator 3) driver.navigate().refresh() -> Refresh the page
	 * 
	 * @param : Locator to locate the web element, Name of the web element
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String clickAndWaitForPageLoad(WebElement element, String elemName) {

		try {

			// Highlight the web element
			TestUtil.highlightElement(element);

			// Click on the web element targeted by locator
			TestUtil.clickElement(element, elemName);

			// Wait for new page to load
			waitForPageToLoad();

			// Refresh the page
			// driver.get(driver.getCurrentUrl());

			// Log result
			System.out.println(
					"Clicked on the element : '" + elemName + " and new page loaded with title : " + driver.getTitle());

			log.debug(
					"Clicked on the element : '" + elemName + " and new page loaded with title : " + driver.getTitle());

			return "Pass : Clicked on the element : '" + elemName + " and new page loaded with title : "
					+ driver.getTitle();

		}

		catch (Throwable clickAndWaitException) {

			// Log error
			System.err.println("Error while clicking on link '" + elemName + "' and waiting for new page to load : "
					+ clickAndWaitException.getMessage());

			log.debug("Error while clicking on link '" + elemName + "' and waiting for new page to load : "
					+ clickAndWaitException.getMessage());

			return "Error while clicking on link '" + elemName + "' and waiting for new page to load : "
					+ clickAndWaitException.getMessage();

		}

	}

	/*
	 * public static void refreshPage() method specification : -
	 * 
	 * 1) Refresh the page 2) driver.navigate().refresh : This is used to refresh
	 * the current page
	 */
	public static void refreshPage() {

		log.debug("Executing : refreshPage() method");

		try {

			log.debug("Refreshing page");
			System.out.println("Refreshing page");
			driver.navigate().refresh();
			log.debug("Page successfully refreshed");
			System.out.println("Page successfully refreshed");
		} catch (Throwable pageRefreshException) {

			log.debug("Exception came while refreshing page : " + pageRefreshException.getMessage());
			System.err.println("Exception came while refreshing page : " + pageRefreshException.getMessage());
		}
	}

	/**
	 * public static String waitAlertToLoad(String alertName) :-
	 * 
	 * Waits for alert to load
	 * 
	 * @param alertName : Name of alert
	 * @return : Pass/Fail
	 */
	public static String waitAlertToLoad(String alertName) {

		log.debug("Waiting for Alert :" + alertName);
		System.out.println("Waiting for Alert :" + alertName);

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("Alert shown");
			return "Pass: Alert shown";

		}

		catch (Exception waitAlertToLoadException) {
			log.debug("Exception while waiting for alert: " + waitAlertToLoadException.getMessage());
			System.err.println("Exception while waiting for alert: " + waitAlertToLoadException.getMessage());

			return "Fail: Error occured while waiting for Alert dialog";
		}

	}

	/**
	 * public static String rightClick(WebElement element, String eleName) method
	 * specification:-
	 * 
	 * Right clicks on Element
	 * 
	 * @param locator : Takes locator as xpath,ID,Class etc.
	 * @param eleName : Name of element
	 * @return : Pass and fail
	 */

	public static String rightClick(WebElement element, String eleName) {

		log.debug(" Opening context menu of element " + eleName);
		System.out.println(" Opening context menu of element " + eleName);

		try {

			// Wait for locator to appear on the page
			waitForElementToLoad(element);

			// Highlight locator
			TestUtil.highlightElement(element);

			// Create a new action object
			Actions action = new Actions(driver);

			// Opening context menu
			action.contextClick(element).build().perform();

			log.debug("Right clicked on element : " + eleName);
			System.out.println("Right clicked on element : " + eleName);

			return "Pass: Right clicked on element : " + eleName;

		}

		catch (Exception rightClickExcetion) {

			log.debug("Error: while doing rightclick on element :" + eleName);
			System.out.println("Error: while doing rightclick on element :" + eleName);

			return "Fail: while doing rightclick on element : " + rightClickExcetion.getMessage();

		}
	}

	/*
	 * public static void isChecked(WebElement element, String elemName) method
	 * specification :-
	 * 
	 * 1) Verifies whether a Checkbox is checked or not 2) locator -> to locate the
	 * element by id,x-path,name,etc. 3) elemName -> the name/type of the check-box
	 * which we intend to check 4) driver.findElement(locator).isSelected() -> is to
	 * verify whether the intended checkbox is checked or not
	 * 
	 * @param : Locator for the Check-box, name of the web element
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String isChecked(WebElement element, String elemName) {

		log.debug("Verifying is the checkbox checked : " + elemName);
		System.out.println("Verifying is the checkbox checked : " + elemName);

		String result = null;

		try {

			// Highlight check-box
			TestUtil.highlightElement(element);

			// Wait for check-box to appear on the page
			waitForElementToLoad(element);

			// Verify whether check-box if already checked
			if (element.isSelected()) {

				// Log the result
				System.out.println("Is checked '" + elemName + "'");
				log.debug("Is checked '" + elemName + "'");
				result = "Pass : Is checked '" + elemName + "'";
			} else {

				// Log the result
				System.out.println("Is not checked '" + elemName + "'");
				log.debug("Is not checked '" + elemName + "'");
				result = "Fail : Is not checked '" + elemName + "'";
			}

		}

		catch (Throwable ischeckCheckBoxException) {

			// Log the exception
			System.err.println("Error while verifying checkbox is checked '" + elemName + "' : "
					+ ischeckCheckBoxException.getMessage());
			log.debug("Error while verifying checkbox is checked '" + elemName + "' : "
					+ ischeckCheckBoxException.getMessage());

			result = "Error while verifying checkbox is checked: '" + elemName + "' : "
					+ ischeckCheckBoxException.getMessage();

		}
		System.out.println("Result: " + result);
		return result;

	}

	/**
	 * String assertText(String expectedString, String actualString)
	 * 
	 * @param expectedString
	 * @param actualString
	 * @return
	 */
	public static String assertText(String expectedString, String actualString) {

		log.debug("Asserting  Text  where : ExpectedText = " + expectedString + "  and ActualText = " + actualString);
		System.out.println(
				"Asserting  Text  where : ExpectedText = " + expectedString + "  and ActualText = " + actualString);

		try {

			Assert.assertEquals(expectedString.trim(), actualString.trim());

			log.debug("Success : ExpectedText = " + expectedString + "  and ActualText = " + actualString
					+ " and both are same");
			System.out.println("Success : ExpectedText = " + expectedString + "  and ActualText = " + actualString
					+ " and both are same");

		}

		catch (Throwable t) {

			// report error
			System.out.println("Error while asserting text :- " + t.getMessage());
			log.debug("Error while asserting text :- " + t.getMessage());
			return "Fail : Error while asserting text :- " + t.getMessage();

		}

		return "Pass : " + expectedString + " matches with " + actualString;

	}

	/*
	 * 
	 * public static void maximizeWindow() method specification : -
	 * 
	 * 1) Maximize the currently opened browser window 2)
	 * driver.manage().window().maximize() : Maximize browser window
	 */
	public static void maximizeWindow() {

		log.debug("Executing : maximizeWindow() method");

		try {

			log.debug("Maximizing Browser window");
			System.out.println("Maximizing Browser window");
			driver.manage().window().maximize();

			log.debug("Browser window successfully maximized");
			System.out.println("Browser window successfully maximized");

		}

		catch (Throwable windowMaximizeException) {

			log.debug("Exception came while maximizing window : " + windowMaximizeException.getMessage());
			System.err.println("Exception came while maximizing window : " + windowMaximizeException.getMessage());

		}

	}

	/*
	 * 
	 * public static Boolean verifyPartialText(String elemName, String
	 * expValue,String actValue) method specification :
	 * 
	 * 1) This method is for verifying presence of a sub-string in between a larger
	 * string 2) String elemName : Passed as a parameter for naming the element 3)
	 * String expValue : Passed as a parameter for storing the expected value 4)
	 * String actValue : Passed as a parameter for storing the actual value 5)
	 * Boolean check = actValue.trim().contains(expValue.trim()) : Checks if actual
	 * text contains the expected text
	 */

	public static Boolean verifyPartialText(String elemName, String expValue, String actValue) {

		log.debug("Verifying Partial Text - '" + expValue + "' for : " + elemName);
		System.out.println("Verifying Partial Text - '" + expValue + "' for : " + elemName);

		try {

			Boolean check = actValue.trim().contains(expValue.trim());

			if (check) {

				log.debug(
						"Success : Partial text - '" + expValue + "' is present within Full text - '" + actValue + "'");
				System.out.println(
						"Success : Partial text - '" + expValue + "' is present within Full text - '" + actValue + "'");

				return true;

			}

			else {

				log.debug("Partial text - '" + expValue + "' is not present within Full text - '" + actValue + "'");
				System.err.println(
						"Partial text - '" + expValue + "' is not present within Full text - '" + actValue + "'");

				return false;

			}

		}

		catch (Throwable verifyPartialTextException) {

			System.err.println("Error while Verifying Partial Text for - " + elemName + " : "
					+ verifyPartialTextException.getMessage());
			log.debug("Error while Verifying Partial Text for : " + elemName + " : "
					+ verifyPartialTextException.getMessage());

			return false;

		}

	}

	/*
	 * public static void checkCheckBox(By locator, String elemName) method
	 * specification :
	 * 
	 * 1) Checks a check-box if it is not checked already 2) if
	 * (!driver.findElement(locator).isSelected()) {
	 * driver.findElement(locator).click() : Checks the checkbox if it is not
	 * checked already 3) String elemName : Passed as a parameter to name the
	 * element
	 */
	public static void checkCheckBox(WebElement element, String elemName) {

		log.debug("Clicking on: " + elemName);
		System.out.println("Clicking on: " + elemName);

		try {

			waitForElementToLoad(element);

			if (!element.isSelected()) {
				element.click();
			}

		}

		catch (Throwable t) {

			System.out.println("Error while Checking the checkbox -" + elemName);
			log.debug("Error while Checking the checkbox -" + elemName + t.getMessage());
		}

	}

	/**
	 * 
	 * @param element
	 * @param elemName
	 * @return
	 */
	public static Boolean isEnabled(WebElement element, String elemName) {
		log.debug("Is Element enabled : " + elemName);
		System.out.println("Is Element enabled : " + elemName);
		try {
			waitForElementToLoad(element);
			TestUtil.highlightElement(element);
			Boolean present = element.isEnabled();
			return present;
		} catch (Throwable e) {
			System.out.println("Error while veryfying Is Element Enabled :   -" + elemName);
			log.debug("Error while veryfying Is Element Enabled  :   -" + elemName + e.getMessage());
			return false;
		}

	}
	/*
	 * public static String assertTitle(String expectedTitle) method specification
	 * :-
	 * 
	 * 1) Asserts page title 2) driver.getTitle() -> Retrieves page title 3)
	 * Assert.assertEquals() -> Asserts for equality
	 * 
	 * @param : Expected title to assert
	 * 
	 * @return : Result of execution - Pass or fail (with cause)
	 */

	public static String assertTitle(String expectedTitle) {

		String actualTitle = null;

		log.debug("Asserting  title  where : Expected title = " + expectedTitle);
		System.out.println("Asserting  title  where : Expected title = " + expectedTitle);

		try {

			// Fetch actual title of the webpage
			actualTitle = driver.getTitle();

			// Asserts whether actual title matches with expected one
			Assert.assertEquals(expectedTitle.trim(), actualTitle.trim());

			// Log result
			log.debug("Actual title = " + actualTitle + " and matches with Expected title = " + expectedTitle);

			System.out.println("Actual title = " + actualTitle + " and matches with Expected title = " + expectedTitle);

			return "Pass : Actual title = " + actualTitle + " and matches with Expected title = " + expectedTitle;

		}

		catch (Throwable assertTitleException) {

			// Log error
			System.err.println("Error while asserting title : " + assertTitleException.getMessage());

			log.debug("Error while asserting title : " + assertTitleException.getMessage());

			return "Fail : Error while asserting title : " + assertTitleException.getMessage();

		}

	}
	/*
	 * public static void waitForNewWindow(int prevWndCount) method specification :-
	 * 
	 * 1) Waits for a new window to appear 2) new WebDriverWait(driver, 60) -> Waits
	 * for 60 seconds 3) wait.until((ExpectedCondition<Boolean>) -> Wait until
	 * expected condition (Window count increases) met 4)
	 * d.getWindowHandles().size() -> Returns number of window handles present
	 * 
	 * @param : Previous window count
	 * 
	 * @return : void
	 */

	public static void waitForNewWindow(int prevWndCount) {

		final int currWndCount = prevWndCount;

		try {

			// Wait until expected condition (Window count increases) met
			wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver d) {

					// Return true if window count increases, else return false
					return d.getWindowHandles().size() > currWndCount;

				}

			});

		}

		catch (Throwable waitForNewWindowException) {

			System.err.println(
					"Exception while waiting for new window to appear : " + waitForNewWindowException.getMessage());

			log.debug("Exception while waiting for new window to appear : " + waitForNewWindowException.getMessage());

		}

	}
	
	public static String getCurrentDateTime(String timezone) {
		
		return ZonedDateTime.now(ZoneId.of(timezone)).toString();
		
	}
	
	public static String convertTimeZone(String timestamp, String timezone) {
		
		ZonedDateTime zdt = ZonedDateTime.parse(timestamp);
		return zdt.withZoneSameInstant(ZoneId.of(timezone)).toString();
	}
	
	public static String formatTimestamp(String timestamp, String pattern) {
		
		ZonedDateTime zdt = ZonedDateTime.parse(timestamp);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		return dtf.format(zdt);
		
	}
		
	public static void selectByVisibleText(WebElement dropdownElement, String option) {
		
		dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(option);
		
	}
	
	//0 for first window, 1 for second and so on..
	public static void switchWindowByIndex(int windowIndex) {
		int index = -1;
		String windowHandle = "";
		Set<String> windowHandles = getdriver().getWindowHandles();
		Iterator<String> handle = windowHandles.iterator();
		while (handle.hasNext()) {
			index++;
			windowHandle = handle.next();
			if (index == windowIndex) {
				getdriver().switchTo().window(windowHandle);
			}
		}

	}
}
