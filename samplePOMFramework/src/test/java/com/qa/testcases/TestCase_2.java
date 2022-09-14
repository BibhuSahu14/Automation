package com.qa.testcases;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.FlipkartHomePage;
import com.qa.pages.FlipkartLoginPage;
import com.qa.pages.SearchedPage;
import com.qa.util.JsonReader;

public class TestCase_2 extends TestBase {

	FlipkartHomePage FlipkartHomePageobj;
	FlipkartLoginPage FlipkartLoginPageobj;
	SearchedPage SearchedPageobj;
	
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
	
	@Test
	public void addToCart()
	{
		reportLog("Test Started--Searching product and add first product to the cart");
		FlipkartHomePageobj=new FlipkartHomePage();
		FlipkartHomePageobj.navigateToFlipkartHomePage();
		FlipkartLoginPageobj=new FlipkartLoginPage();
		FlipkartLoginPageobj.login(testData.getString("userName"), testData.getString("password"));
		FlipkartHomePageobj.searchProduct(testData.getString("product"));
		SearchedPageobj=new SearchedPage();
		String actualCartPageTitle=SearchedPageobj.clickFirstSearchesproduct();
		String expectedCartPageTitle="Shopping Cart | Flipkart.com";
		assertEquals(actualCartPageTitle,expectedCartPageTitle);
	}
	@AfterMethod
	public void closeWindow()
	{
		getdriver().quit();
	}
	
	
}
