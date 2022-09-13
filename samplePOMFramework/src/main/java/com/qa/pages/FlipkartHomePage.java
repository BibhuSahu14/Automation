package com.qa.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class FlipkartHomePage extends TestBase {

	@FindBy(className="_3704LK")
	WebElement searchBar;
	
	public FlipkartHomePage() {
		PageFactory.initElements(getdriver(), this); 
	}
	
	public void navigateToFlipkartHomePage() {
		String loginUrl = prop.getProperty(env+"URL");
		reportLog("Login URL: "+loginUrl);
		getdriver().get(loginUrl);
	}
	
	public void searchProduct(String product) 
	{
		navigateToFlipkartHomePage();
		searchBar.sendKeys(product,Keys.ENTER);
	}
	
	
}
