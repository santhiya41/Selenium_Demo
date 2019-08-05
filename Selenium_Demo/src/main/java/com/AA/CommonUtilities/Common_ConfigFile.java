package com.AA.CommonUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Common_ConfigFile {

	public Properties appPropertyFile,frameworkPropertyFile;
	
	public Common_ConfigFile(){
		
		/*Retrieve Properties variables*/
		appPropertyFile = new Properties();
		frameworkPropertyFile = new Properties();
		
		/*Declaration of the config file */
		File appSrc = new File("./Config/applicationConfig.properties");
		File frameworkSrc = new File("./Config/frameworkConfig.properties");
		
		try{
		
		/*Load the config file */
		FileInputStream appFis = new FileInputStream(appSrc);
		appPropertyFile.load(appFis);
		
		FileInputStream frameworkFis = new FileInputStream(frameworkSrc);
		frameworkPropertyFile.load(frameworkFis);
		
		}catch(Exception e){
			System.out.println("Exception is " + e.getMessage());
		}
	}
	
	public String getApplicationURL(){
		
		/*Retrieve the ApplicationURL*/
		String url = appPropertyFile.getProperty("URL");
		return url;
	}
	
	public String getBrowser(){
		
		/*Retrieve which browser to run the test*/
		String browser = appPropertyFile.getProperty("Browser");
		return browser;
	}
	
	public String getEnvironment(){
		
		/*Retrieve which browser to run the test*/
		String browser = appPropertyFile.getProperty("Environment");
		return browser;
	}
	
	public String getProjectFolderPath() {
		
		/*Retrieve the Project Folder path*/
		String path = frameworkPropertyFile.getProperty("projectFolderPath");
		return path;
	}
		
	public String getInputLocation() {
		
		/*Retrieve the Input location*/
		String path = frameworkPropertyFile.getProperty("inputLocation");
		return path;
	}
	
	public String getOutputLocation() {
		
		/*Retrieve the Output location*/
		String path = frameworkPropertyFile.getProperty("outputLocation");
		return path;
	}
	
}

