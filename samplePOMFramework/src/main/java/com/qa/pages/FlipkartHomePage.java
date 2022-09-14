package com.qa.pages;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.TestBase;

public class FlipkartHomePage extends TestBase {

	@FindBy(className="_3704LK")
	WebElement searchBar;
	
	WebDriverWait wait;
	
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
		wait=new WebDriverWait(getdriver(),Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(searchBar));
		searchBar.sendKeys(product,Keys.ENTER);
	}
	
	
}
