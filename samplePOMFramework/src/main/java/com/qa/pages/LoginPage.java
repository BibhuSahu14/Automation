package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.qa.base.TestBase;
import com.qa.util.TestUtil;

public class LoginPage extends TestBase {

	// Page Factory - OR:
	@FindBy(id = "username")
	WebElement username;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "login")
	WebElement loginBtn;

	// Initializing the Page Factory/Objects:
	public LoginPage() {
		PageFactory.initElements(getdriver(), this); // "this" means current class object. All the above variables will
														// be initialized with this driver
	}
	
	//Navigate to login page
	public void navigateToLogin() {
		String loginUrl = prop.getProperty(env+"URL");
		reportLog("Login URL: "+loginUrl);
		getdriver().get(loginUrl);
	}
	

	/** Actions/Methods on Login page: 
	 * @throws InterruptedException **/

	public void login(String username, String password) {
		navigateToLogin();
		this.username.sendKeys(username);
		this.password.sendKeys(password);
		loginBtn.click();
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}// added sleep for now, will replace with wait later 
		reportLog("Logged In");
	
	}

	
	public void logout() {
		
	}

}
