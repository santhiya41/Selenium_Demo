package com.AA.Pages;

import java.util.logging.Logger;

import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;

import com.AA.CommonUtilities.*;

public class MasterSetup extends Common_Functions {
	
	static Common_ConfigFile config = new Common_ConfigFile(); 
	public static String ApplicationURL = config.getApplicationURL();
	public static String browser = config.getBrowser();
	public static String driverLocation = getDriverLocation(browser);
	//public static Logger logger;
	
	/*Steps required for the Initial setup */
	@BeforeClass
	public void initialSetup() 
	{

		//Set the driver location
		System.setProperty("webdriver.gecko.driver", driverLocation); //"GeckoDriver\\16.1\\32bit\\geckodriver.exe");
		
		//Initialize the logger file
		//logger = Logger.getLogger(Common_Functions.TestCaseName);
		
	}
	
	/*Steps required for the initial setup */
	@AfterClass
	public void tearDown() 
	{
		
		
		
	}
	
	
}
