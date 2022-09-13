package com.qa.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class SearchedPage extends TestBase {

	@FindBy(xpath="//div[text()=\"Price -- Low to High\"]")
	WebElement lowToHigh;
	
	@FindBy(className="s1Q9rs")
	List<WebElement> allProductParticularPage;
	
	@FindBy(className="_30jeq3")
	List<WebElement> allProductPriceInSearchedPage;
	
	public SearchedPage() {
		PageFactory.initElements(getdriver(), this); 
	}
	
	public void clickPriceLowToHigh()
	{
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
}
