package com.qa.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import static org.testng.Assert.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.qa.base.TestBase;
import com.qa.pages.LoginPage;
import com.qa.util.JsonReader;
import com.qa.util.TestUtil;

public class sampleTestClass extends TestBase {

	LoginPage loginPagObj;

	@BeforeClass
	public void intializeTestData() {
		String className = this.getClass().getSimpleName();
		reportLog("Running Test class: " + className);
		testDataFile = JsonReader.getTestDataJSON(className);

	}

	@BeforeMethod
	public void setup() {
		initialization();
		testData = testDataFile.getJSONObject(env).getJSONObject(browserName);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws Exception {
		reportLog("Take sceenshot in case of failue and close browser");
		// Check if the test case failed or was skipped and take screenshot
		if (result.getStatus() == result.FAILURE || result.getStatus() == result.SKIP) {
			String screenshotPath = TestUtil.getScreenshot(getdriver(), result.getName());
			result.setAttribute("screenshotPath", screenshotPath); // sets the value the variable/attribute
																	// screenshotPath as the path of the screenshot
		}
		getdriver().quit();
		unload();
	}

	@Test
	public void verifyLoginAndDashboardLoad() {

		reportLog("Test Started -- verifyLoginAndDashboardLoad");
		loginPagObj.login(testData.getString("userName"),testData.getString("password"));
		assertEquals("actualText", "expectedText");
		
	}

}
