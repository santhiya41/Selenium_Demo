package com.AA.Pages;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import com.AA.CommonUtilities.Common_Functions;

public class HomePage {
	
	WebDriver ldriver;
	
	public HomePage(WebDriver rdriver){
		ldriver = rdriver;
		PageFactory.initElements(rdriver,this);
	}
	
	@FindBy(xpath="/html/body/section/div/div[2]/div[1]/div/div/div/div[1]/form/div[1]/div[1]/ul/li[2]/label/span[2]")
	@CacheLookup
	WebElement we_HP_OneWay;
	
	@FindBy(id="reservationFlightSearchForm.originAirport")
	@CacheLookup
	WebElement wedt_HP_From;
	
	public String fn_SearchOneWay() throws UnsupportedOperationException, IOException, Throwable {
		
		//Click on One way Button
		if (Common_Functions.fn_ClickElement(we_HP_OneWay)) {
			Common_Functions.fn_Update_HTML(Common_Functions.HtmlFile, Common_Functions.TestCaseName, "PASS", "Host Name", "Clicked on One Way", ldriver, false);
		}else{
			Common_Functions.fn_Update_HTML(Common_Functions.HtmlFile, Common_Functions.TestCaseName, "PASS", "Host Name", "Clicked on One Way", ldriver, false);
		}
		return null;	
		
		
	}
	
	

}
