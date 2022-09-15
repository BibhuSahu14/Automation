package com.qa.testcases;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.FlipkartHomePage;
import com.qa.pages.SearchedPage;
import com.qa.util.JsonReader;

public class TestCase_1 extends TestBase{
	
	FlipkartHomePage FlipkartHomePageobj;
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
	public void verifySortedPrice()
	{
		reportLog("Test Started--verifySortedPrice");
		FlipkartHomePageobj=new FlipkartHomePage();
		SearchedPageobj=new SearchedPage();
		FlipkartHomePageobj.navigateToFlipkartHomePage();
		FlipkartHomePageobj.closeLoginPopup();
		FlipkartHomePageobj.searchProduct(testData.getString("product"));
		ArrayList al=(ArrayList) SearchedPageobj.getFirst5ProductNameandPrice();
		ArrayList sortedal=al;
		Collections.sort(sortedal);
		assertEquals(al, sortedal);
	}
	@AfterMethod
	public void closeWindow()
	{
		getdriver().close();
	}
}
