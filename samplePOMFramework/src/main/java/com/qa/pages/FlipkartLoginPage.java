package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class FlipkartLoginPage extends TestBase {

	@FindBy(xpath="//input[@class='_2IX_2- VJZDxU']")
	WebElement username;
	
	@FindBy(xpath="//input[@class='_2IX_2- _3mctLh VJZDxU']")
	WebElement password;
	
	@FindBy(xpath="//button[@class='_2KpZ6l _2HKlqd _3AWRsL']//span")
	WebElement loginBtn;
	
	public FlipkartLoginPage() {
		PageFactory.initElements(getdriver(), this); 
	}
	
	public void login(String username, String password) 
	{
		
		this.username.sendKeys(username);
		this.password.sendKeys(password);
		loginBtn.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
