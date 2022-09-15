package com.qa.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.TestBase;

public class SearchedPage extends TestBase {

	@FindBy(xpath="//div[text()=\"Price -- Low to High\"]")
	WebElement lowToHigh;
	
	@FindBy(className="s1Q9rs")
	List<WebElement> allProductParticularPage;
	
	@FindBy(className="_30jeq3")
	List<WebElement> allProductPriceInSearchedPage;
	
	@FindBy(xpath="//div[@class='_4rR01T']")
	WebElement firstProduct;
	
	@FindBy(xpath="//button[@class='_2KpZ6l _2U9uOA _3v1-ww']")
	WebElement addToCartBtn;
	
	WebDriverWait wait;
	
	public SearchedPage() {
		PageFactory.initElements(getdriver(), this); 
	}
	
	public void clickPriceLowToHigh()
	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lowToHigh.click();
	}
	public ArrayList getFirst5ProductNameandPrice()
	{
		clickPriceLowToHigh();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("The sorted product according to price :");
		ArrayList ll=new ArrayList();
		for(int i=0;i<5;i++)
		{
			System.out.println(allProductParticularPage.get(i).getText()+"----"+allProductPriceInSearchedPage.get(i).getText());
			int amount=Integer.parseInt(allProductPriceInSearchedPage.get(i).getText().substring(1).replaceAll(",", ""));
			ll.add(amount);
		}
		return ll;
	
	}
	
	public String clickFirstSearchesproduct()
	{
		wait=new WebDriverWait(getdriver(),Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(firstProduct));
		firstProduct.click();
		switchWindow();
		wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
		addToCartBtn.click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cartPageTitle=getdriver().getTitle();
		return cartPageTitle;
		
		
	}
	
	public void switchWindow()
	{
		String parentWindow=getdriver().getWindowHandle();
		Set<String> allWindows=getdriver().getWindowHandles();
		Iterator<String> itr=allWindows.iterator();
		String childwindow;
		while(itr.hasNext())
		{
			childwindow=itr.next();
			if(!parentWindow.equalsIgnoreCase(childwindow))
			{
				driver.switchTo().window(childwindow);
				break;
			}
		}
	}
}
