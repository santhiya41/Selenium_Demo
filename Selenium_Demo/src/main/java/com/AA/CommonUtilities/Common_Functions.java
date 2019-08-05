package com.AA.CommonUtilities;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.google.common.io.Files;
import org.apache.poi.ss.*;

public class Common_Functions {

	 /* Read the config file */
	public static Common_ConfigFile configCF= new Common_ConfigFile();
	public static String FolderPath = configCF.getProjectFolderPath();
	
	//Variable
	public static String HtmlFile, TestCaseName;
	public static String InputMasterlocation = FolderPath+configCF.getInputLocation();
	public static String OutputMasterlocation = FolderPath+configCF.getOutputLocation();
	public static String Resultfolderlocation = OutputMasterlocation+"Results\\";
	public static String OuputDatalocation = OutputMasterlocation+"DataFiles\\";
	public static String HTMLlocation = InputMasterlocation+"Settings\\ResultTemplate.html";
	public static String InputDatalocation = InputMasterlocation+"Test Data\\";
	public static String MasterInputExcel = InputDatalocation+"JPMC_SELENIUM_MASTERDATA.xls";
	public static String Propertyfilelocation = InputMasterlocation+"Properties File\\";
	public static String BrowserPath = InputMasterlocation+"Browser\\";
	public static String PropertiesFilePath = InputMasterlocation+"Properties File\\";
	public static String DB_PropertiesFilePath = PropertiesFilePath+"DB.properties";
	public static String AsOfDate_PropertiesFilePath = PropertiesFilePath+"AsOfDate.properties";
	public static String Browser_PropertiesFilePath = PropertiesFilePath+"Browser_Path.properties";
	public static String VBS_FilePath = InputMasterlocation+"VBS-File\\";
	public static String VBS_RESULT_FilePath = VBS_FilePath+"ETF_RESULT_MAIL.vbs";
	public static String VBS_NOT_TRIGGERED_FilePath = VBS_FilePath+"ETF_NOT-TRIGGERED_MAIL.vbs";
	public static String VBS_TRIGGER_INTIMATION = VBS_FilePath+"ETF_TRIGGER_INTIMATION.vbs";
	public static String VBS_EXECUTION_COMPLETED_INTIMATION = VBS_FilePath+"ETF_COMPLETED_INTIMATION.vbs";
	public static String VBS_GET_FAILED_SCRIPT_DETAILS = VBS_FilePath+"ETF_GET_FAILED_SCRIPTS_DATA.vbs";
	public static String BatchReportTemplatePath = OutputMasterlocation+"BatchExecutionReport\\Settings\\AllETFBatchReportTemplate.xls";
	public static String ETF_NotTriggeredTemplatePath = OutputMasterlocation+"BatchExecutionReport\\Settings\\ETF_Scripts_Not_Triggered_Template.xls";
	public static String ETF_Result_Schema = "PRISM_OWN";
	public static String BlueNormal_Start = "<font color=\"Blue\">";
	public static String BlueNormal_End = "</font>";
	public static String BlueBold_Start = "<B><font color=\"Blue\">";
	public static String BlueBold_End = "</font></B>";
	public static String RedNormal_Start = "<font color=\"Red\">";
	public static String RedNormal_End = "</font>";
	public static String RedBold_Start = "<B><font color=\"Red\">";
	public static String RedBold_End = "</font></B>";
	public static String NextLine = "<br>";
	public static String TestNGXMLFile = InputMasterlocation+"Test Script\\Selenium_3.4\\TestNG.xml";
		
	//Main Method
	public static void main(String args[]) {
				
	}
	
	//Start the HTML creation
	public static String fn_Pre_Requiste_Execution(String ExecutionFundName, String ExecutionScriptName) throws Exception {
		
		//Create the result folder
		File ResultFoldername = fn_CreateResultFolder();
		
		//Create the folder with Fund Name
		File FundNameFolder = fn_CreateFundNameFolder(ResultFoldername,ExecutionFundName);

		//Create the folder with the test script name
		File TestScriptNameFolder = fn_CreateTestScriptNameFolder(FundNameFolder,ExecutionScriptName);
		
		//Create HTML result folder
		File RuntimeHTMLFile = fn_CreateHTML(TestScriptNameFolder);
		
		String HTML_FILE =  RuntimeHTMLFile.getPath();
		
		fn_Start_HTML(HTML_FILE, ExecutionScriptName, ExecutionFundName);
		
		return HTML_FILE;
	}
	
	//Create Folder with current date
	public static File fn_CreateResultFolder() throws Exception{

		//Get the date
		String current_Date = fn_GetDate();

		String Resultpath = Resultfolderlocation+current_Date;
		File file = new File(Resultfolderlocation);
		File file1 = new File(Resultpath);
		String[] Filenames = file.list();
		boolean Folderexist = false;
		
		//Verify the result folder exists 
		for(String name : Filenames)
		{
			if (name.toString().equals(current_Date.toString())) {
				Folderexist = true;
				break;
			}
		}
		
		//Create the result folder if it doesn't exists
		if (!Folderexist) {
			file1.mkdir();
			Folderexist = true;
		}
		
		return file1;
	}
	
	//Creates fund name folder
	public static File fn_CreateFundNameFolder(File resultFoldername, String FundName) {
		
		String FundPath = resultFoldername+"\\"+FundName;
		File FundNameFolderpath = new File(FundPath);
		String[] FundFoldNames = resultFoldername.list();
		boolean FundFolderexist = false;
		
		//Verify the result folder exists 
		for(String fundfoldname : FundFoldNames)
		{
			if (fundfoldname.toString().equals(FundName)) {
				FundFolderexist = true;
				break;
			}
		}
		
		//Create the folder with the fund Name
		if (!FundFolderexist) {
			FundNameFolderpath.mkdir();
		}
		return FundNameFolderpath;
	}
	
	//Creates the HTML file
	public static File fn_CreateHTML(File RuntimeResultFolderlocation) throws Exception {
		
		String ResFold = RuntimeResultFolderlocation+"\\ResultTemplate.html";
		File ToHTMLfolder = new File(ResFold);
		File FromHTMLfolder = new File(HTMLlocation);
		Files.copy(FromHTMLfolder, ToHTMLfolder);	
		return ToHTMLfolder;
		
	}
	
	//Get the current date
	public static String fn_GetDate(){
		//Get the current system date
		String Current_Date = new SimpleDateFormat("M-d-yyyy").format(Calendar.getInstance().getTime());		
		return Current_Date;
	}
	
	//Get the time
	public static String fn_GetTime(){
		//Get the current system date
		String Current_Time = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());		
		return Current_Time;
	
	}
	
	//Create the test script name folder
	public static File fn_CreateTestScriptNameFolder(File fundNameFolder,String testscirptname) {
		
		String TestscriptfolderName = fundNameFolder+"\\"+testscirptname+"_"+fn_GetDate()+"_"+fn_GetTime();
		String SnapshotFoldername =  TestscriptfolderName+"\\Snapshot";
		File TestScriptFolderpath = new File(TestscriptfolderName);
		File SnapshotFolderpath = new File(SnapshotFoldername);
		
		//String[] TestScriptFolder = fundNameFolder.list();
		//boolean FundFolderexist = false;
		
		////Verify the result folder exists 
		//for(String TestFolder : TestScriptFolder)
		//{
			//if (TestFolder.toString().equals(testscirptname)) {
				//FundFolderexist = true;
				//break;
			//}
		//}
		
		//Create the folder with the test name
		//if (!FundFolderexist) {
			TestScriptFolderpath.mkdir();
			SnapshotFolderpath.mkdir();
		//}
			
		return TestScriptFolderpath;
	}
	
	//Calculate As Of Date
	public static String fn_Final_AsOfDate(String Frequency) {
		
		String ReturnDate = null;
		int BusinessDay = 0;
		
		//Get the Business Day
		BusinessDay = fn_Calculate_No_Of_Business_Days();
		
		switch (Frequency) {
		
		case "Daily":
			
			ReturnDate = fn_Daily_AsOfDate();
			break;
			
		case "Monthly":
			
			if (BusinessDay < 7) {
				ReturnDate = fn_Previous_Monthly_AsOfDate();
				break;
			} else {
				ReturnDate = fn_Monthly_AsOfDate();
				break;
			}
			
		case "Quarterly":
			
			if (BusinessDay < 7) {
				ReturnDate = fn_Previous_QuarterEndAsOfDate();
				break;
			} else {
				ReturnDate = fn_QuarterEndAsOfDate();
				break;
			}
			
		case "Yearly":
			
			ReturnDate = fn_YearEndAsOfDate();
			break;
			
		}
		
		return ReturnDate;
		
	}
	
	//Calculates Daily As Of Date
	public static String fn_Daily_AsOfDate() {
		
		String DailyAsOfDate = null;
		
		//Get the Current Day with number
		String DateNumber = new SimpleDateFormat("u").format(Calendar.getInstance().getTime());
		
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
        Date myDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        
        //If the Date Number is 1 ie., Monday get the As of Date as Friday
        if (DateNumber.equalsIgnoreCase("1")) {
        	cal.add(Calendar.DATE, -3);
		} else {
			cal.add(Calendar.DATE, -1);
		}
        
		DailyAsOfDate = dateFormat.format(cal.getTime());		
		return DailyAsOfDate;
	}
	
	//Calculate the Previous Monthly As Of Date
	public static String fn_Previous_Monthly_AsOfDate() {
		
		//Get the Current Day with number
		String MonthNumber = new SimpleDateFormat("M").format(Calendar.getInstance().getTime());
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		int year = Integer.parseInt(Year);
		int previousyear = year - 1;
		boolean leapyear = false;
		String Previous_MonthEndAsOfDate = null;
		
		//verify the year is leap year	
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			leapyear = true;
		} else {
			leapyear = false;
		}
		
		//Get the Month End As Of Date
		switch (MonthNumber) {
		
		case "1":
			Previous_MonthEndAsOfDate =  "11/30/"+previousyear;
			break;
		
		case "2":
			Previous_MonthEndAsOfDate =  "12/31/"+previousyear;
			break;
			
		case "3":
			Previous_MonthEndAsOfDate =  "1/31/"+Year;
			break;
			
		case "4":
			//Verify the year is leap year
			if (leapyear) {
				Previous_MonthEndAsOfDate =  "2/29/"+Year;
				break;
			} else {
				Previous_MonthEndAsOfDate =  "2/28/"+Year;
				break;
			}
			
		case "5":
			Previous_MonthEndAsOfDate =  "3/31/"+Year;
			break;
			
		case "6":
			Previous_MonthEndAsOfDate =  "4/30/"+Year;
			break;
			
		case "7":
			Previous_MonthEndAsOfDate =  "5/31/"+Year;
			break;
			
		case "8":
			Previous_MonthEndAsOfDate =  "6/30/"+Year;
			break;
			
		case "9":
			Previous_MonthEndAsOfDate =  "7/31/"+Year;
			break;
			
		case "10":
			Previous_MonthEndAsOfDate =  "8/31/"+Year;
			break;
			
		case "11":
			Previous_MonthEndAsOfDate =  "9/30/"+Year;
			break;
			
		case "12":
			Previous_MonthEndAsOfDate =  "10/31/"+Year;
			break;
			
		}
		
		return Previous_MonthEndAsOfDate;
		
	}
	
	//Calculates Monthly As Of Date
	public static String fn_Monthly_AsOfDate() {
		
		//Get the Current Day with number
		String MonthNumber = new SimpleDateFormat("M").format(Calendar.getInstance().getTime());
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		int year = Integer.parseInt(Year);
		int previousyear = year - 1;
		boolean leapyear = false;
		String MonthEndAsOfDate = null;
		
		//verify the year is leap year	
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			leapyear = true;
		} else {
			leapyear = false;
		}
		
		//Get the Month End As Of Date
		switch (MonthNumber) {
		
		case "1":
			MonthEndAsOfDate =  "12/31/"+previousyear;
			break;
			
		case "2":
			MonthEndAsOfDate =  "1/31/"+Year;
			break;
			
		case "3":
			//Verify the year is leap year
			if (leapyear) {
				MonthEndAsOfDate =  "2/29/"+Year;
				break;
			} else {
				MonthEndAsOfDate =  "2/28/"+Year;
				break;
			}
			
		case "4":
			MonthEndAsOfDate =  "3/31/"+Year;
			break;
			
		case "5":
			MonthEndAsOfDate =  "4/30/"+Year;
			break;
			
		case "6":
			MonthEndAsOfDate =  "5/31/"+Year;
			break;
			
		case "7":
			MonthEndAsOfDate =  "6/30/"+Year;
			break;
			
		case "8":
			MonthEndAsOfDate =  "7/31/"+Year;
			break;
			
		case "9":
			MonthEndAsOfDate =  "8/31/"+Year;
			break;
			
		case "10":
			MonthEndAsOfDate =  "9/30/"+Year;
			break;
			
		case "11":
			MonthEndAsOfDate =  "10/31/"+Year;
			break;
			
		case "12":
			MonthEndAsOfDate =  "11/30/"+Year;
			break;
			
		}
		return MonthEndAsOfDate;		
	}
	
	//Calculates Year End As Of Date
	public static String fn_YearEndAsOfDate() {
		String YearEndAsOfDate = null;
		//Get the Current Day with number
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		int year = Integer.parseInt(Year);
		int previousyear = year - 1;
		YearEndAsOfDate = "12/31/"+previousyear;
		return YearEndAsOfDate;
	}

	//Calculates Previous Quarter End As Of Date
	public static String fn_Previous_QuarterEndAsOfDate() {
		
		String Previous_QuarterEndAsOfDate = null;
		
		//Get the Current Day with number
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		int year = Integer.parseInt(Year);
		String Month = new SimpleDateFormat("M").format(Calendar.getInstance().getTime());
		int month = Integer.parseInt(Month);
		int previousyear = year - 1;
		
		//Calculate the QuarterEndAsOfDate
		if ((month >= 4) && (month <= 6)) {
			Previous_QuarterEndAsOfDate = "12/31/"+previousyear;
		} else if ((month >= 7) && (month <= 9)) {
			Previous_QuarterEndAsOfDate = "3/31/"+year;
		} else if ((month >= 10) && (month <= 12)) {
			Previous_QuarterEndAsOfDate = "6/30/"+year;
		} else if ((month >= 1) && (month <= 3)) {
			Previous_QuarterEndAsOfDate = "9/30/"+previousyear;
		}
		
		return Previous_QuarterEndAsOfDate;
	}
	
	//Calculates Quarter End As Of Date
	public static String fn_QuarterEndAsOfDate() {
		
		String QuarterEndAsOfDate = null;
		
		//Get the Current Day with number
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		int year = Integer.parseInt(Year);
		String Month = new SimpleDateFormat("M").format(Calendar.getInstance().getTime());
		int month = Integer.parseInt(Month);
		int previousyear = year - 1;
		
		//Calculate the QuarterEndAsOfDate
		if ((month >= 1) && (month <= 3)) {
			QuarterEndAsOfDate = "12/31/"+previousyear;
		} else if ((month >= 4) && (month <= 6)) {
			QuarterEndAsOfDate = "3/31/"+year;
		} else if ((month >= 7) && (month <= 9)) {
			QuarterEndAsOfDate = "6/30/"+year;
		} else if ((month >= 10) && (month <= 12)) {
			QuarterEndAsOfDate = "9/30/"+year;
		}
		
		return QuarterEndAsOfDate;
	}
	
	//Capture the screenshot
	public static String fn_Capture_Screenshot(String HTMLFilePath, WebDriver Driver) {
		
		String Screenshotname = "Snapshots_"+fn_GetDate()+"_"+fn_GetTime()+".PNG";
		String Snapshotpath = HTMLFilePath.replace("ResultTemplate.html", "")+"Snapshot\\"+Screenshotname;
		File screen = ((TakesScreenshot)Driver).getScreenshotAs(OutputType.FILE);
		File ScreeenshotLocation = new File(Snapshotpath);
		
		try {
			org.apache.commons.io.FileUtils.copyFile(screen,ScreeenshotLocation);
		} catch (IOException e) {
			
		}
		
		return ScreeenshotLocation.getPath();
	}
	
	//Writes a new entry in the HTML reporter by using the existing screenshot path
	public static String fn_Update_HTML(String HTML_FilePath, String TestCase,String Status, String Step, String Description, WebDriver Driver ,String Snapshot_Path) throws IOException, UnsupportedOperationException, Throwable {
		
		//Open TR Tag
		fn_Open_TR_Tag(HTML_FilePath);
	    
		FileWriter filewrite = new FileWriter(HTML_FilePath,true);
	    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
	    PrintWriter write = new PrintWriter(BufferWrite);
	    
	    String Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	    String Snapshotvalue = null;
	    String DataToAppend = null;
	    
	    //Capture the snapshot
		Snapshotvalue = "<a href = "+Snapshot_Path+">Snap Shot</a>";
	    
	    //populate the appending line
	    if (Status.equalsIgnoreCase("PASS")) {
	    	DataToAppend = "<td>"+TestCase+"</td><td><font color=\"limegreen\">PASS</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
		} else if (Status.equalsIgnoreCase("FAIL")) {
			DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Red\">FAIL</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
		} else if (Status.equalsIgnoreCase("WARN")) {
			DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Yellow\">WARN</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
		}	

	    write.println(DataToAppend);
	    write.close();
	    BufferWrite.close();
	    filewrite.close();
		
	    //Close TR Tag
	    fn_Close_TR_Tag(HTML_FilePath);
	    
    	return Snapshot_Path;
		
	}
	
	//Writes a new entry in the HTML reporter by taking the screenshot
	public static String fn_Update_HTML(String HTML_FilePath, String TestCase,String Status, String Step, String Description, WebDriver Driver ,boolean snapshot) throws IOException, UnsupportedOperationException, Throwable {
		
		//Open TR Tag
		fn_Open_TR_Tag(HTML_FilePath);
	    
		FileWriter filewrite = new FileWriter(HTML_FilePath,true);
	    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
	    PrintWriter write = new PrintWriter(BufferWrite);
	    
	    String Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	    String Snapshotvalue = null;
	    String DataToAppend = null;
	    String Screenshotname = "Snapshots_"+fn_GetDate()+"_"+fn_GetTime()+".PNG";
	    String Snapshotpath = HTML_FilePath.replace("ResultTemplate.html", "")+"Snapshot\\"+Screenshotname;
	    
	    //Capture the snapshot
	    if (snapshot) {
	    	//BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	    	//File file = new File(Snapshotpath);
	    	//Save as JPEG
	    	//ImageIO.write(screencapture,"jpg",file);
	    	
			File screen = ((TakesScreenshot)Driver).getScreenshotAs(OutputType.FILE);
			File ScreeenshotLocation = new File(Snapshotpath);
			org.apache.commons.io.FileUtils.copyFile(screen,ScreeenshotLocation);
			Snapshotvalue = "<a href = "+Snapshotpath+">Snap Shot</a>";
	    	
		} else {
			Snapshotvalue = "NA";
		}
	    
	    //populate the appending line
	    if (Status.equalsIgnoreCase("PASS")) {
	    	DataToAppend = "<td>"+TestCase+"</td><td><font color=\"limegreen\">PASS</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
		} else if (Status.equalsIgnoreCase("FAIL")) {
			DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Red\">FAIL</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
		} else if (Status.equalsIgnoreCase("WARN")) {
			DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Yellow\">WARN</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
		}	

	    write.println(DataToAppend);
	    write.close();
	    BufferWrite.close();
	    filewrite.close();
		
	    //Close TR Tag
	    fn_Close_TR_Tag(HTML_FilePath);
	    
	    //Return the snapshot path
	    if (snapshot) {
	    	return Snapshotpath;
		} else {
			return "";
		}	
		
	}

	//Add the filter to the HTML reporter
	public static void fn_addFilter(String filePath)
	{
		try
		{
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		    out.println("<script language=\"javascript\" type=\"text/javascript\">");
		    out.println("//<![CDATA[");
		    out.println("setFilterGrid(\"table1\");");
		    out.println("//]]>");
		    out.println("</script>");
		    out.close();
		}
		catch(IOException e)
		{
			
		}
	}
	
	//Open the TR tag in the HTML
	public static void fn_Open_TR_Tag(String HTML_FilePath) throws IOException {
		
		FileWriter filewrite = new FileWriter(HTML_FilePath,true);
	    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
	    PrintWriter write = new PrintWriter(BufferWrite);
	    
	    write.println("<tr>");
	    write.close();
	    BufferWrite.close();
	    filewrite.close();
	}
	
	//Close the TR tag in the HTML
	public static void fn_Close_TR_Tag(String HTML_FilePath) throws IOException {
		
		FileWriter filewrite = new FileWriter(HTML_FilePath,true);
	    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
	    PrintWriter write = new PrintWriter(BufferWrite);
	    
	    write.println("</tr>");
	    write.close();
	    BufferWrite.close();
	    filewrite.close();
	}
	
	//End the HTML reporter by calculating the number of PASS/FAIL/WARNING
	public static void fn_End_HTML (String HTMLFilePath) {
		
		int beginIndex = 0;
		int endIndex = 0;
		String Executionstart = "EXECUTION STARTED ON </td><td>";
		int Extstart = Executionstart.trim().length();
		
		try {
			
			FileInputStream fileinput = new FileInputStream(HTMLFilePath);
			String content = IOUtils.toString(fileinput, "UTF-8");
			beginIndex = content.indexOf(Executionstart);
			endIndex = beginIndex+Extstart+8;
			
			int passcount = 0;
			int failcount = 0;
			int warncount = 0;
			
			//Calculate the number of Pass, Fail, Warnings		
			if (content.contains(">PASS<")) {
				String[] PassRepetition = content.split(">PASS<");
				passcount = PassRepetition.length;
				passcount = passcount - 1;
			} if (content.contains(">FAIL<")) {
				String[] FailRepetition = content.split(">FAIL<");
				failcount = FailRepetition.length;
				failcount = failcount - 1;
			} if (content.contains(">WARN<")) {
				String[] WarnRepetition = content.split(">WARN<");
				warncount = WarnRepetition.length;
				warncount = warncount - 1;
			}
			
			String passcnt = String.valueOf(passcount);
			String failcnt = String.valueOf(failcount);
			String warncnt = String.valueOf(warncount);
			
			//Convert the start time to the milliseconds
			String StartTime = content.substring(beginIndex+Extstart, endIndex);
			String[] SplitTime = StartTime.split(":");
			long longstarthour = Long.parseLong(SplitTime[0])*60*60*1000;
			long longstartminute = Long.parseLong(SplitTime[1])*60*1000;
			long longstartsecond = Long.parseLong(SplitTime[2])*1000;
			long StartTimemillisecond = longstarthour+longstartminute+longstartsecond;
			
			//Convert the end time to the milliseconds
			String Current_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
			String[] SplitCurrentime = Current_Time.split(":");
			long longendhour = Long.parseLong(SplitCurrentime[0])*60*60*1000;
			long longendminute = Long.parseLong(SplitCurrentime[1])*60*1000;
			long longendsecond = Long.parseLong(SplitCurrentime[2])*1000;
			long EndTimemillisecond = longendhour+longendminute+longendsecond;
			long timedifference = EndTimemillisecond - StartTimemillisecond;
			
			//Convert the time difference to hh:mm:ss format
			String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timedifference),TimeUnit.MILLISECONDS.toMinutes(timedifference) % TimeUnit.HOURS.toMinutes(1),TimeUnit.MILLISECONDS.toSeconds(timedifference) % TimeUnit.MINUTES.toSeconds(1));
			String[] splittimeduration = hms.split(":");
			String Timeduration = splittimeduration[0]+" hr:"+splittimeduration[1]+" min:"+splittimeduration[2]+" sec";
			
			//Update the html reporter
			content = content.replaceAll("KEY_END_TIME", Current_Time);
			content = content.replaceAll("KEY_DURATION_TIME", Timeduration);
			content = content.replaceAll("KEY_PASS", passcnt);
			content = content.replaceAll("KEY_FAIL", failcnt);
			content = content.replaceAll("KEY_WARNING", warncnt);
			
			FileOutputStream fileoutput = new FileOutputStream(HTMLFilePath);
			IOUtils.write(content,fileoutput , "UTF-8");
			fileinput.close();
			fileoutput.close();
			
			//Add the filter
			fn_addFilter(HTMLFilePath);
			
		} catch (Exception e) {
		
		}
				
	}
		
	//Start the HTML by updating the table
	public static void fn_Start_HTML(String HTMLFilePath, String test_name, String fund_name) throws IOException {
		
		String MonthNumber = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
		String Date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		String Current_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		String ExecutionStarte_date = Date+"/"+MonthNumber+"/"+Year;
		String ExecutionStart_Time = Current_Time;
		
		FileInputStream fileinput = new FileInputStream(HTMLFilePath);
		String content = IOUtils.toString(fileinput, "UTF-8");
		content = content.replaceAll("KEY_WORKFLOW_NAME", test_name);
		content = content.replaceAll("KEY_START_TIME", ExecutionStart_Time);
		content = content.replaceAll("KEY_EXECUTIONDATE", ExecutionStarte_date);
		content = content.replaceAll("KEY_FUND_NAME", fund_name);
		FileOutputStream fileoutput = new FileOutputStream(HTMLFilePath);
		IOUtils.write(content,fileoutput , "UTF-8");
		fileinput.close();
		fileoutput.close();
	}
	
	//Establish the DB connection
	public static Connection fn_Open_DB_Connection() throws SQLException, IOException {

		Connection Con = null;
		
		try {
			
			FileInputStream InputFile = new FileInputStream(MasterInputExcel);
			HSSFWorkbook Wrkbk = new HSSFWorkbook(InputFile);
			HSSFSheet Sheet = Wrkbk.getSheet("DB_Details");
			
			HSSFRow Row = Sheet.getRow(1);
			HSSFCell cell = Row.getCell(0);
			
			//Get the DB Name
			String DBName = cell.getStringCellValue();
			
			//Close the file
			Wrkbk.close();
			InputFile.close();
					
			String URL = null;
			String UID = null;
			String PWD = null;
			//System.out.println("Entered into DB Function");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			
			//Load the Properties File
			File propfile = new File(Common_Functions.DB_PropertiesFilePath);
			FileInputStream Fip;
			Fip = new FileInputStream(propfile);
			Properties prop = new Properties();
			prop.load(Fip);
			
			//Get the URL
			URL = prop.getProperty(DBName);
			
			//Get the UID and PWD from the property file
			if (DBName.equalsIgnoreCase("ORAPRDPIM")) {
				UID = prop.getProperty("PRDPIM_DB_UID");
				PWD = prop.getProperty("PRDPIM_DB_PWD");
			} else if (DBName.equalsIgnoreCase("ORARPTPIM_CA")) {
				UID = prop.getProperty("RPTPIM_CA_DB_UID");
				PWD = prop.getProperty("RPTPIM_CA_DB_PWD");
			} else if (DBName.equalsIgnoreCase("ORABTAPIM")) {
				UID = prop.getProperty("BTAPIM_DB_UID");
				PWD = prop.getProperty("BTAPIM_DB_PWD");
			}
			
			if (UID == null || UID == "") {
				UID = "";
			}
			
			if (PWD == null || PWD == "") {
				PWD = "";
			}
			
			//System.out.println("Establishing the connection");
			//System.out.println("URL is"+URL);
			//System.out.println("User id is"+UID);
			//System.out.println("Password is"+PWD);
			
			//Establish the connection
			Con = DriverManager.getConnection(URL,UID,PWD);
			//System.out.println("Established the connection"+Con);
		} catch (Exception e) {
			e.printStackTrace();
			Con = null;
			
		}
				
		return Con;
		
	}
	
	//Get the Execution Flag for the Fund and test script
	public static String fn_Get_Execution_Flag(String ExcelName, String SheetName, String FundName, String TestScriptName) throws IOException {
			
		String ExecutionFlag = "n";
		int FundNamerix = -1;
		int TestNamecix = -1;		
		
		FundNamerix = fn_Get_RowIndex(ExcelName, SheetName, FundName);
		TestNamecix = fn_Get_ColumnIndex(ExcelName, SheetName, TestScriptName);
		
		FileInputStream Fip = new FileInputStream(ExcelName);
		HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
		HSSFSheet sheet = wrkbk.getSheet(SheetName);
		
		HSSFRow row = (HSSFRow) sheet.getRow(FundNamerix);
		HSSFCell cell = (HSSFCell) row.getCell(TestNamecix);
		
		ExecutionFlag = cell.getStringCellValue().toString();
		
		wrkbk.close();
		Fip.close();
		return ExecutionFlag;
			
	}
	
	//Get the Row Index
	public static int fn_Get_RowIndex(String ExcelName, String SheetName, String RowName) throws IOException {
		
		int rowindex = -1;
		int rowincrement = -1;
		
		FileInputStream Fip = new FileInputStream(ExcelName);
		HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
		HSSFSheet sheet = wrkbk.getSheet(SheetName);
		
		Iterator<Row> Row = sheet.rowIterator();
		
		while (Row.hasNext()) {
			rowincrement ++;
			HSSFRow row = (HSSFRow) Row.next();
			HSSFCell cell = row.getCell(0);
			if (cell.getStringCellValue().equalsIgnoreCase(RowName)) {
				rowindex = rowincrement;
				break;
			}
		}
		
		wrkbk.close();
		Fip.close();
		return rowindex;
	}
	
	//Get the column index
	public static int fn_Get_ColumnIndex(String ExcelName, String SheetName, String ColumnName) throws IOException {
		
		int columnindex = -1;
		
		FileInputStream Fip = new FileInputStream(ExcelName);
		HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
		HSSFSheet sheet = wrkbk.getSheet(SheetName);
		
		HSSFRow row = sheet.getRow(0);
			
		Iterator<Cell> CellItr = row.cellIterator();
		
		while (CellItr.hasNext()) {
			
			HSSFCell cell = (HSSFCell) CellItr.next();
			
			String Column_Name = cell.getStringCellValue();
			
			if (Column_Name.equalsIgnoreCase(ColumnName)) {
				columnindex = cell.getColumnIndex();
				break;
			}
			
		}
		
		wrkbk.close();
		Fip.close();
		return columnindex;
	}
	
	//Get the DB Data
	public static String fn_GetDBData(Connection Connect, String SQLQuery) {
		
		String DBOutput = null;
		String DB_OP = null;
		boolean hasData = false;
		
		try {
			
			//Execute the Query
			Statement stmt = Connect.createStatement();
			ResultSet rs = stmt.executeQuery(SQLQuery);
			
			//Get the DB record
			while (rs.next()){
				hasData=true;
				if ((rs.getString(1) == null) || (rs.getString(1).isEmpty())) {
					DB_OP = "0";
				} else {
					DB_OP = rs.getString(1).trim();
				}
			}
			
			if(!hasData){
				DBOutput = null;
			} else {
				DBOutput = DB_OP;
			}
			
			//If the result contains the value as 'N/A' them make the output as '0'
			if (DBOutput.equalsIgnoreCase("N/A")) {
				DBOutput = "0";
			}
			
		} catch (Exception e) {
			DBOutput = null;
		}
		
		return DBOutput;

	}
	
	//Verify SQL Exception
	public static boolean fn_Verify_SQL_Exception(Connection Connect, String SQLQuery) {
		
		String DBOutput = null;
		String DB_OP = null;
		boolean hasData = false;
		boolean SQL_Exeption = false;
		
		try {
			
			//Execute the Query
			Statement stmt = Connect.createStatement();
			ResultSet rs = stmt.executeQuery(SQLQuery);
			
			//Get the DB record
			while (rs.next()){
				hasData=true;
				if ((rs.getString(1) == null) || (rs.getString(1).isEmpty())) {
					DB_OP = "0";
				} else {
					DB_OP = rs.getString(1).trim();
				}
			}
			
			if(!hasData){
				DBOutput = null;
			} else {
				DBOutput = DB_OP;
			}
			
			//If the result contains the value as 'N/A' them make the output as '0'
			if (DBOutput.equalsIgnoreCase("N/A")) {
				DBOutput = "0";
			}
			
		} catch (Exception e) {
			SQL_Exeption = true;
		}
		
		return SQL_Exeption;

	}
	
	//Get the URL link for the fund name
	public static String fn_Get_Link(String FundName) {
		
		try {
			
			String Link = null;
			
			int FundNamerix = fn_Get_RowIndex(MasterInputExcel, "Fund_Execution", FundName);
			int TestNamecix = fn_Get_ColumnIndex(MasterInputExcel, "Fund_Execution","Link");
			
			FileInputStream Fip = new FileInputStream(MasterInputExcel);
			HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
			HSSFSheet sheet = wrkbk.getSheet("Fund_Execution");
			
			HSSFRow row = (HSSFRow) sheet.getRow(FundNamerix);
			HSSFCell cell = (HSSFCell) row.getCell(TestNamecix);
			
			Link = cell.getStringCellValue().toString();
		
			wrkbk.close();
			Fip.close();
			
			return Link;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage()); }
			return null;
	}
	
	//Get all the excel rows with 'Y' in a string
	public static ArrayList<String> fn_GetDataPoint_DBQuery_CSSLocator(String FilePath, String SheetName) throws IOException {
		
		ArrayList<String> Lst = new ArrayList<String>();		
		String FinalValue = null;
		
		//Get the column index of 'Table', 'Data Point', 'Priority', 'Frequency','Tolerance Range', DB Query', 'CSS Locator'
		
		int Executecix = -1;
		int Tablecix =  -1;
		int DataPointcix =  -1;
		int Prioritycix =  -1;
		int Frequencycix =  -1;
		int ToleranceRangecix = -1;
		int DBQuerycix = -1;
		int Locatorcix = -1;
		int AsOfDateLocatorcix = -1;
		
		FileInputStream fip = new FileInputStream(FilePath);
		HSSFWorkbook wrkbk = new HSSFWorkbook(fip);
		HSSFSheet sheet = wrkbk.getSheet(SheetName);
		FormulaEvaluator FE = new HSSFFormulaEvaluator((HSSFWorkbook)wrkbk);
		DataFormatter DF = new DataFormatter();
		
		HSSFRow Row1 = sheet.getRow(0);
			
		Iterator<Cell> CellItr = Row1.cellIterator();
		
		while (CellItr.hasNext()) {
			
			HSSFCell cell = (HSSFCell) CellItr.next();
			
			String Column_Name = cell.getStringCellValue();
			
			switch (Column_Name) {

			case "Execute":
				Executecix = cell.getColumnIndex();
				break;
			
			case "Table":
				Tablecix = cell.getColumnIndex();
				break;

			case "DataPoint":
				DataPointcix = cell.getColumnIndex();
				break;
				
			case "Priority":
				Prioritycix = cell.getColumnIndex();
				break;
			
			case "Frequency":
				Frequencycix = cell.getColumnIndex();
				break;
			
			case "ToleranceRange":
				ToleranceRangecix = cell.getColumnIndex();
				break;
				
			case "DBQuery":
				DBQuerycix =  cell.getColumnIndex();
				break;
	
			case "UI_Locator":
				Locatorcix =  cell.getColumnIndex();
				break;	

			case "AsOfDate_Locator":
				AsOfDateLocatorcix =  cell.getColumnIndex();
				break;	
				
			default :
				break;
			}
			
		}
		
		//Row Iterator
		Iterator<Row> Row = sheet.rowIterator();
		
		Row.next();
		
		while (Row.hasNext()) {
			
			HSSFRow row = (HSSFRow) Row.next();
			FinalValue = "";
			
			HSSFCell cellExecute = row.getCell(Executecix);
			String Execute = cellExecute.getStringCellValue();
			
			if (Execute.equalsIgnoreCase("Y")) {
								
				HSSFCell cellTable = row.getCell(Tablecix);
				HSSFCell cellDatapoint = row.getCell(DataPointcix);
				HSSFCell cellPriority = row.getCell(Prioritycix);
				HSSFCell cellFrequency = row.getCell(Frequencycix);
				HSSFCell cellToleranceRange = row.getCell(ToleranceRangecix);
				HSSFCell cellDBQuery = row.getCell(DBQuerycix);
				HSSFCell cellCSSLocator = row.getCell(Locatorcix);
				HSSFCell cellAsOfDateCSSLocator = row.getCell(AsOfDateLocatorcix);
				
				FE.evaluate(cellTable);
				FE.evaluate(cellDatapoint);
				FE.evaluate(cellPriority);
				FE.evaluate(cellFrequency);
				FE.evaluate(cellToleranceRange);
				FE.evaluate(cellDBQuery);
				FE.evaluate(cellCSSLocator);
				FE.evaluate(cellAsOfDateCSSLocator);
				
				DF.formatCellValue(cellTable,FE);
				DF.formatCellValue(cellDatapoint,FE);
				DF.formatCellValue(cellPriority,FE);
				DF.formatCellValue(cellFrequency,FE);
				DF.formatCellValue(cellToleranceRange,FE);
				DF.formatCellValue(cellDBQuery,FE);
				DF.formatCellValue(cellCSSLocator,FE);
				DF.formatCellValue(cellAsOfDateCSSLocator,FE);
				
				String Table = cellTable.getStringCellValue();
				String DataPoint = cellDatapoint.getStringCellValue();
				String Priority = cellPriority.getStringCellValue();
				String Frequency = cellFrequency.getStringCellValue();
				double ToleranceRange = cellToleranceRange.getNumericCellValue();
				String DBQuery = cellDBQuery.getStringCellValue();
				String UILocator = cellCSSLocator.getStringCellValue();
				String AsOfDateLocator = cellAsOfDateCSSLocator.getStringCellValue();
				
				if (DBQuery == null || DBQuery.isEmpty()) {
					DBQuery = " ";
				}
				
				if (UILocator == null || UILocator.isEmpty()) {
					UILocator = " ";
				}
				
				if (AsOfDateLocator == null || AsOfDateLocator.isEmpty()) {
					AsOfDateLocator = " ";
				}
				
				FinalValue = Table+"|"+DataPoint+"|"+Priority+"|"+Frequency+"|"+ToleranceRange+"|"+DBQuery+"|"+UILocator+"|"+AsOfDateLocator;
				Lst.add(FinalValue);
			}
			
		}
		
		fip.close();		
		return Lst;		
	}
	
	//Open the browser
	public static WebDriver fn_Open_Browser(String ETF_URL) throws IOException {
		
		WebDriver driver = null;
		String Hostname = null;
		String DriverLocation = null;
		try {
			
			//Load the Properties File
			File propfile = new File(Common_Functions.Browser_PropertiesFilePath);
			FileInputStream Fip;
			Fip = new FileInputStream(propfile);
			Properties prop = new Properties();
			prop.load(Fip);
						
			//Get the Host Name
			Hostname = InetAddress.getLocalHost().getHostName();
			
			//IE Explorer Driver----------
			//String ChromeDriverPath=BrowserPath+"IEDriverServer.exe";
			//System.setProperty("webdriver.ie.driver",ChromeDriverPath);
			//WebDriver driver = new InternetExplorerDriver();
			//------------
			
			//------------
				
			if (Hostname.equalsIgnoreCase("NB-15-CFT-01") || Hostname.equalsIgnoreCase("NB-15-CFT-02")) {
				
				System.out.println("Entering into the function for opening the Browser");
				//Get the Driver location
				DriverLocation = prop.getProperty("FF_Path");
				System.out.println(DriverLocation);
				//FireFox driver----------		
				System.setProperty("webdriver.gecko.driver", BrowserPath+DriverLocation); //"GeckoDriver\\16.1\\32bit\\geckodriver.exe");
				DesiredCapabilities capablities = DesiredCapabilities.firefox();
				capablities.setCapability("marionette", true);
				System.out.println(capablities);
				driver = new FirefoxDriver(capablities);				
		        //-------------
				System.out.println(driver);
				
			} else {
				
				//Get the Driver location
				DriverLocation = prop.getProperty("GC_Path");
				
				//Chrome Driver----------
				String ChromeDriverPath=BrowserPath+DriverLocation; //"chromedriver.exe";
				System.out.println("About to set the property");
				System.setProperty("webdriver.chrome.driver",ChromeDriverPath);
				System.out.println("Completed setting the property");
				ChromeOptions options  =  new ChromeOptions();
				System.out.println("Completed setting the Options"+options);
				options.addArguments("--disable-extensions");
				options.addArguments("--start-maximized");
				options.setExperimentalOption("useAutomationExtension", false);
				driver= new ChromeDriver(options);
				System.out.println(driver);
				
			}

			
			driver.get(ETF_URL);
			Common_Functions.fn_WaitForPageLoaded(driver);
			
		} catch (Exception e) {
			e.printStackTrace();
			driver = null;
		}
				
		//driver.manage().window().maximize();
		return driver;
	}
		
	//Verify the Periodic check
	public static String fn_Compare_AsOfDate(String Actual_AsOfDate, String Frequency) throws ParseException {
		
		String PeriodicCheck = "Failed";
		String DailyAsOfDate = null;
		String Old_MonthEndDate = null;
		String Current_MonthEndDate = null;
		String Old_QuarterEndDate = null;
		String Current_QuarterEndDate = null;
		String YearlyAsOfDate = null;
		
		int BusinessDay = 0;
		
		try {
			
			//Get the Business Day
			BusinessDay = fn_Calculate_No_Of_Business_Days();
			
			switch (Frequency) {
			
			case "Daily":
				
				DailyAsOfDate = fn_Daily_AsOfDate();
				break;
				
			case "Monthly":
				
				Old_MonthEndDate = fn_Previous_Monthly_AsOfDate();
				Current_MonthEndDate = fn_Monthly_AsOfDate();
				break;
				
			case "Quarterly":
				
				Old_QuarterEndDate = fn_Previous_QuarterEndAsOfDate();
				Current_QuarterEndDate = fn_QuarterEndAsOfDate();
				break;
				
			case "Yearly":
				
				YearlyAsOfDate = fn_YearEndAsOfDate();
				break;
				
			}
			
			//Verify whether the UI As Of Date is null
			if (Actual_AsOfDate == null || Actual_AsOfDate.isEmpty()) {
				
				PeriodicCheck = "Failed";
				
				
			} else {
				
				//The As of date logic for the Month End and Quarter End dates
				if (Frequency.equalsIgnoreCase("Monthly") || Frequency.equalsIgnoreCase("Quarterly")) {
					
					String OldEndDate = null;
					String CurrentEndDate = null;
					
					if (Frequency.equalsIgnoreCase("Monthly")) {
						OldEndDate = Old_MonthEndDate;
						CurrentEndDate = Current_MonthEndDate;
					} else if (Frequency.equalsIgnoreCase("Quarterly")) {
						OldEndDate = Old_QuarterEndDate;
						CurrentEndDate = Current_QuarterEndDate;
					}
					
					//If the Business date is less than 7 days
					if (BusinessDay < 7) {
						
						Date UIdate;
						Date olddate;
						Date currentdate;
						//String Pass_AsOfdate = null;
						
						String AsOfDate_UI = fn_Trim_AsOfDate(Actual_AsOfDate);
						SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
						
						UIdate = (Date) formatter.parse(AsOfDate_UI);
						olddate = (Date) formatter.parse(OldEndDate);
						currentdate = (Date) formatter.parse(CurrentEndDate);
						
						//if ((UIdate.compareTo(olddate) == 0)) {
							//Pass_AsOfdate = OldEndDate;
						//} else if ((UIdate.compareTo(currentdate) == 0)) {
							//Pass_AsOfdate = CurrentEndDate;
						//}
						
						//Compare the dates
						if ((UIdate.compareTo(olddate) == 0) || (UIdate.compareTo(currentdate) == 0)) {
							PeriodicCheck = "Passed";
						} else {
							PeriodicCheck = "Failed";
						}
						
					} else if (BusinessDay >= 7) {
						
						Date UIdate;
						Date currentdate;
						
						String AsOfDate_UI = fn_Trim_AsOfDate(Actual_AsOfDate);
						SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
						
						UIdate = (Date) formatter.parse(AsOfDate_UI);
						currentdate = (Date) formatter.parse(CurrentEndDate);
						
						//Compare the dates
						if ((UIdate.compareTo(currentdate) == 0)) {
							PeriodicCheck = "Passed";
						} else {
							PeriodicCheck = "Failed";
						}
						
					}			
						
				} else if (Frequency.equalsIgnoreCase("Daily") || Frequency.equalsIgnoreCase("Yearly")) {
					
					Date UIdate;
					Date ExpectedAsOfdate;
					String CurrentEndDate = null;
					
					if (Frequency.equalsIgnoreCase("Daily")) {
						CurrentEndDate = DailyAsOfDate;
					} else if (Frequency.equalsIgnoreCase("Yearly")) {
						CurrentEndDate = YearlyAsOfDate;
					}
					
					String AsOfDate_UI = fn_Trim_AsOfDate(Actual_AsOfDate);
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					
					UIdate = (Date) formatter.parse(AsOfDate_UI);
					ExpectedAsOfdate = (Date) formatter.parse(CurrentEndDate);
					
					//Compare the dates
					if ((UIdate.compareTo(ExpectedAsOfdate) == 0)) {
						PeriodicCheck = "Passed";
					} else {
						PeriodicCheck = "Failed";
					}
					
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			PeriodicCheck = "Failed";
			
		}
				
		return PeriodicCheck;
		
	}
	
	public static String fn_Calculate_UIElement_UIvsDB_PeriodicCheck_ToleranceApplied_ToleranceThreshold_ActualSwing(String Website, String Table, String DataPoint, String Priority, String Frequency, String Tolerance, String SQL_Query, String DistributionTableDateValue, String App_UI_Value, String Act_AsOfDate, Connection DB_Connection, String Snapshot) throws SQLException, ParseException, Throwable {
				
		String FinalValue = null;
		String UIVal = null;
		String UIElement = null;
		String UIVsDB = null;
		String ToleranceApplied = null;
		String Tolerance_Threshold = null;
		String Actual_Swing = null;
		String Periodic_Check = null;
		String Modified_SQL = null;
		String DB_Value = null;
		String Exp_AsOfDate = null;
		String PropertyAsOfDate = null;
		String PropertyAsOfDateKey = null;
		
		//Load the Properties File
		File propfile = new File(AsOfDate_PropertiesFilePath);
		FileInputStream Fip;
		Fip = new FileInputStream(propfile);
		Properties prop = new Properties();
		prop.load(Fip);
		
		//Get the As Of Date Key for the respective frequency
		if (Frequency.trim().equalsIgnoreCase("Daily")) {
			PropertyAsOfDateKey = "Daily_AsOfDate_M_D_YYYY_Seperated_With_Forward_Slash";
		} else if (Frequency.trim().equalsIgnoreCase("Monthly")) {
			PropertyAsOfDateKey = "Monthly_AsOfDate_M_D_YYYY_Seperated_With_Forward_Slash";
		} else if (Frequency.trim().equalsIgnoreCase("Quarterly")) {
			PropertyAsOfDateKey = "Quarterly_AsOfDate_M_D_YYYY_Seperated_With_Forward_Slash";
		} else if (Frequency.trim().equalsIgnoreCase("Yearly")) {
			PropertyAsOfDateKey = "Yearly_AsOfDate_M_D_YYYY_Seperated_With_Forward_Slash";
		}
		
		//Get the AsOf Date from the Property File
		if (prop.containsKey(PropertyAsOfDateKey)) {
			PropertyAsOfDate = prop.getProperty(PropertyAsOfDateKey);
		}
		
		//Verify if the AsOfDate from the property file is null
		if ((PropertyAsOfDate == null) || (PropertyAsOfDate.isEmpty())) {
			
			//Calculate the Periodic Check
			Periodic_Check = fn_Compare_AsOfDate(Act_AsOfDate, Frequency);
			
			//Verify the periodic check is passed
			if (Periodic_Check.equalsIgnoreCase("passed")) {
				Exp_AsOfDate = Act_AsOfDate;
			} else {
				Exp_AsOfDate = fn_Final_AsOfDate(Frequency);
			}
			
		} else {
			
			//Expected As Of Date will be the date from the property file
			Exp_AsOfDate = PropertyAsOfDate;
			
			try {
				
				//Calculate the Periodic Check
				Date ExpectedAsOfDate;
				Date ActualAsOfDate;
				
				String UI_AsOfDate = fn_Trim_AsOfDate(Act_AsOfDate);
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				
				ActualAsOfDate = (Date) formatter.parse(UI_AsOfDate);
				ExpectedAsOfDate = (Date) formatter.parse(Exp_AsOfDate);
				
				//Compare the dates
				if ((ActualAsOfDate.compareTo(ExpectedAsOfDate) == 0)) {
					Periodic_Check = "Passed";
				} else {
					Periodic_Check = "Failed";
				}
				
			} catch (Exception e) {
				
				Periodic_Check = "Failed";
				
			}
						
		}
		
		//Verify the SQL Query is null
		if (!SQL_Query.isEmpty()) {
						
			double A_S = 0.0 ;
			double Excel_Tolerance = 0.0;
			double UI = 0;
			double DB = 0;
			
			boolean dbcheck = false;
			boolean uicheck = false;
			boolean dberror = false;
			
			//Remove the special characters in the string
			if (App_UI_Value!=null) {
				UIVal = fn_Triming_UI_Values(App_UI_Value);
			} else {
				UIVal = null;
			}
			
			//Convert the Tolerance values to double
			if ((Tolerance == null) || (Tolerance.isEmpty())) {
				Excel_Tolerance = 0.0;
			} else {
				Excel_Tolerance = Double.parseDouble(Tolerance);
			}
			
			//Convert the UI values to double
			if ((UIVal == null) || (UIVal.isEmpty())) {
				uicheck = false;
			} else {
				UI = Double.parseDouble(UIVal); //Float.parseFloat(UIVal);
				uicheck = true;
			}
						
			//Modified SQL Query
			if (Website.equalsIgnoreCase("Distributions_Tabs")) {
				if (DistributionTableDateValue != null) {
					Modified_SQL = SQL_Query.trim().replaceAll("ReplaceAsOfDate",DistributionTableDateValue);
				}
			} else {
				if ((Act_AsOfDate != null)) {
					Modified_SQL = SQL_Query.trim().replaceAll("ReplaceAsOfDate",Act_AsOfDate);
				} else {
					Modified_SQL = SQL_Query.trim();
				}
			}
					
			//Get the DB Value
			DB_Value = fn_GetDBData(DB_Connection,Modified_SQL);
			
			//Verify while running the DB query the exception occurs
			dberror = fn_Verify_SQL_Exception(DB_Connection,Modified_SQL);
			
			//Convert the DB values to double
			if ((DB_Value == null) || (DB_Value.isEmpty())) {
				if (App_UI_Value!=null) {
					if (App_UI_Value.equalsIgnoreCase("--")) {
						DB = 0;
						dbcheck = true;
					} else {
						dbcheck = false;
					}
				}
			} else {
				DB = Double.parseDouble(DB_Value); //Float.parseFloat(DB_Value);
				dbcheck = true;
			}		
			
			//Verify the UI Element
			if (UIVal!=null) {
				UIElement = "Passed";
			} else {
				UIElement = "Failed";
			}
						
			//Calculate the UIVsDB and Tolerance Applied
			if (dbcheck && uicheck) {
				
				//If the UI Element is Passed, Then perform the UI Vs DB comparison
				if (UIElement.equalsIgnoreCase("Passed")) {
								
					//Verify whether the UI and DB values are same 
					if (UI == DB) {
						UIVsDB = "Passed";
						ToleranceApplied = "Not Applied";
					} 
					
					//If the UI and DB values are not matched then apply the tolerance to see whether it is matching
					if (UI != DB) {
						
						//Verify whether the tolerance is mentioned
						if (Excel_Tolerance != 0.0) {
							
							//Apply the tolerance to see whether it is matching
							if (((!(DB > UI+Excel_Tolerance)) || (!(DB < UI-Excel_Tolerance)))) {
								UIVsDB = "Passed";
								ToleranceApplied = "Applied";
							} else {
								UIVsDB = "Failed";
								ToleranceApplied = "Applied";
							}
							
						} else {
							UIVsDB = "Failed";
							ToleranceApplied = "NA";
						}
					}
					
				} else if (UIElement.equalsIgnoreCase("Failed")) {
					UIVsDB = "Failed";
					ToleranceApplied = "NA";
				}
			} else if ((!uicheck) && (!dbcheck) && (!dberror)) {
				if ((UIVal == null || UIVal.isEmpty()) && (DB_Value == null || DB_Value.isEmpty())) {
					UIVsDB = "Passed";
					ToleranceApplied = null;
				}
			} else {
				UIVsDB = "Failed";
				ToleranceApplied = null;
			}
			
			//Calculate the Tolerance Threshold & Actual Swing
			if (dbcheck) {
				if (ToleranceApplied != null) {
					if (ToleranceApplied.equalsIgnoreCase("Applied")) {
						if (DB != 0) {
							A_S = Math.round(((DB - UI)/DB)*100) ;
							Actual_Swing = Double.toString(A_S)+" %";
							Tolerance_Threshold = Double.toString(Excel_Tolerance);
						} else {
							Actual_Swing = null;
							Tolerance_Threshold = Double.toString(Excel_Tolerance);
						}
					}
				}

			} else {
				Actual_Swing = null;
				Tolerance_Threshold = Double.toString(Excel_Tolerance);
			}
			
			//Mark the DB value as Blank if it contains null record
			if ((DB_Value == null) || (DB_Value.isEmpty())){
				DB_Value = "BLANK";
			}
			
			//For the test script 'Composition' tab - [Mark the UI status as 'Failed' if it contains the value '0.00'] 
			if (Website.equalsIgnoreCase("Composition_Tabs")) {
				
                if (DataPoint.contains("Effective Maturity")) {
                	
                	//Calculate the UI element
                	if (uicheck) {
                		if (UI >= 0) {
                			UIElement = "Passed";
                		} else {
                			UIElement = "Failed";
                		}
                	}
                       
	               //Calculate the UIVSDB element for the data point 'Effective Maturity'.
	               if ((UI == 0)) {
	                  if (DB_Value.equalsIgnoreCase("BLANK")) {
	                         UIVsDB = "Passed";
	                  } else {
	                	  UIVsDB = "Failed";
	                  } 
	               } else if (UI != 0) {
	                  if ( UI == DB) {
	                         UIVsDB = "Passed";
	                  } else {
	                         UIVsDB = "Failed";
	                  }
	               }
                       
                }
          }
						
			//Get the Final Value
			//FinalValue = Website+"|"+Table+"|"+DataPoint+"|"+Priority+"|"+Frequency+"|"+Tolerance+"|"+Modified_SQL+"|"+UIElement+"|"+UIVsDB+"|"+UIVal+"|"+DB_Value+"|"+Periodic_Check+"|"+Exp_AsOfDate+"|"+Act_AsOfDate+"|"+ToleranceApplied+"|"+Tolerance_Threshold+"|"+Actual_Swing;
			FinalValue = Website+"|"+Table+"|"+DataPoint+"|"+Priority+"|"+Frequency+"|"+Tolerance+"|"+Modified_SQL+"|"+UIElement+"|"+UIVsDB+"|"+App_UI_Value+"|"+DB_Value+"|"+Periodic_Check+"|"+Exp_AsOfDate+"|"+Act_AsOfDate+"|"+ToleranceApplied+"|"+Tolerance_Threshold+"|"+Actual_Swing+"|"+Snapshot;
			
		} else if (SQL_Query.isEmpty()) {
			
			//Remove the special characters in the string
			if (App_UI_Value!=null) {
				UIVal = fn_Triming_UI_Values(App_UI_Value);
			} else {
				UIVal = null;
			}
			
			//Verify the UI Element
			if (UIVal!=null) {
				UIElement = "Passed";
			} else {
				UIElement = "Failed";
			}

			if ((UIVal == null) || (UIVal.isEmpty())){
				UIVal = "--";
			} else {
				UIVal = fn_Triming_UI_Values(UIVal);
			}
			
			//Get the Final Value
			//FinalValue = Website+"|"+Table+"|"+DataPoint+"|"+Priority+"|"+Frequency+"|"+Tolerance+"|"+Modified_SQL+"|"+UIElement+"|"+UIVsDB+"|"+UIVal+"|"+DB_Value+"|"+Periodic_Check+"|"+Exp_AsOfDate+"|"+Act_AsOfDate+"|"+ToleranceApplied+"|"+Tolerance_Threshold+"|"+Actual_Swing;
			FinalValue = Website+"|"+Table+"|"+DataPoint+"|"+Priority+"|"+Frequency+"|"+Tolerance+"|"+Modified_SQL+"|"+UIElement+"|"+UIVsDB+"|"+App_UI_Value+"|"+DB_Value+"|"+Periodic_Check+"|"+Exp_AsOfDate+"|"+Act_AsOfDate+"|"+ToleranceApplied+"|"+Tolerance_Threshold+"|"+Actual_Swing+"|"+Snapshot;
		}
		
		return FinalValue;

	}
	
	//Trimming the UI Values
	public static String fn_Triming_UI_Values(String UIVal) {
		
		String Final_UI_Val = null;
		String UI_Value = null;
		
		String UIVal_Rep_With_Dollar = UIVal.replace('$', ' ').trim();
		String UIVal_Rep_With_Percent = UIVal_Rep_With_Dollar.replace('%', ' ').trim();
		//String UIVal_Rep_With_Dash = UIVal_Rep_With_Percent.replaceAll("--", "");
		String UIVal_Rep_With_Comma_String = UIVal_Rep_With_Percent.replaceAll(",", "");
		String UIVal_Rep_With_Comma_Char = UIVal_Rep_With_Comma_String.replace(",", "");
		String UIVal_Rep_With_Year = UIVal_Rep_With_Comma_Char.replaceAll("Years", "");
		String UIVal_rep_with_Yr = UIVal_Rep_With_Year.replaceAll("yr", "");
		UI_Value = UIVal_rep_with_Yr.trim();
		
		if (UI_Value.trim().equalsIgnoreCase("--")) {
			Final_UI_Val = "0";
		} else {
			Final_UI_Val = UI_Value;
		}
		
		return Final_UI_Val;
	}
		
	//Trim the As Of Date
	public static String fn_Trim_AsOfDate(String UI_AsOfDate) {
		
		String FinalAsOfDate = null;
		String AsOfDate = null;
		
		AsOfDate = UI_AsOfDate.replaceAll("As of ","").trim();
		
		//Split the string with "/"
		if (AsOfDate.length() > 10) {
			String[] Finaldate = AsOfDate.split("/");
			String Month = Finaldate[0];
			String Date = Finaldate[1];
			String year = Finaldate[2];
			String Finalyear = null;
			
			if (year.length() > 4) {
				Finalyear = year.substring(0, 4);
			} else {
				Finalyear = year;
			}
			
			FinalAsOfDate = Month+"/"+Date+"/"+Finalyear;
			
		} else {
			FinalAsOfDate = AsOfDate;
		}
		return FinalAsOfDate;
	}
	
	//Update the output excel sheet
	public static void fn_Update_Output_Excel(String Outputexcelfile, String SheetName, ArrayList<String> Excel_WebsiteTab, ArrayList<String> Excel_Table, ArrayList<String> Excel_DataPoint, ArrayList<String> Excel_Priority, ArrayList<String> Excel_Frequency, ArrayList<String> Excel_Tolerance_Range, ArrayList<String> Excel_Modified_SQL, ArrayList<String> Excel_UI_Element, ArrayList<String> Excel_UI_Vs_DB, ArrayList<String> Excel_UI_Value, ArrayList<String> Excel_DB_Value, ArrayList<String> Excel_Periodic_Check, ArrayList<String> Excel_Expected_Date, ArrayList<String> Excel_Actual_Date, ArrayList<String> Excel_Tolerance_Applied, ArrayList<String> Excel_Tolerance_Treshold, ArrayList<String> Excel_Actual_Swing, ArrayList<String> Excel_Snapshot) throws Throwable {
				
		//Create the File Object
		File OutputExcelFile = new File(Outputexcelfile);
		
		//Delete the file if it exists
		if (OutputExcelFile.exists()) {
			OutputExcelFile.delete();
		}

		//Create the Output file in the output path
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet Sheet = workbook.createSheet(SheetName);
        
        //Input the Row Head
        HSSFRow Outputsheetrowheader = Sheet.createRow(0);
      
        Outputsheetrowheader.createCell(0).setCellValue("Execute");
        Outputsheetrowheader.createCell(1).setCellValue("WebsiteTab");
        Outputsheetrowheader.createCell(2).setCellValue("Table");
        Outputsheetrowheader.createCell(3).setCellValue("DataPoint");
        Outputsheetrowheader.createCell(4).setCellValue("Priority");
        Outputsheetrowheader.createCell(5).setCellValue("Frequency");
        Outputsheetrowheader.createCell(6).setCellValue("ToleranceRange");
        Outputsheetrowheader.createCell(7).setCellValue("DBQuery");
        Outputsheetrowheader.createCell(8).setCellValue("UI_Element");
        Outputsheetrowheader.createCell(9).setCellValue("UI_Vs_DB");
        Outputsheetrowheader.createCell(10).setCellValue("UI_Value");
        Outputsheetrowheader.createCell(11).setCellValue("DB_Value");
        Outputsheetrowheader.createCell(12).setCellValue("Periodic_Check");
        Outputsheetrowheader.createCell(13).setCellValue("Expected_Date");
        Outputsheetrowheader.createCell(14).setCellValue("Actual_Date");
        Outputsheetrowheader.createCell(15).setCellValue("Tolerance_Applied");
        Outputsheetrowheader.createCell(16).setCellValue("Tolerance_Treshold");
        Outputsheetrowheader.createCell(17).setCellValue("Actual_Swing");
        Outputsheetrowheader.createCell(18).setCellValue("Comments");
        Outputsheetrowheader.createCell(19).setCellValue("Snapshot");
        Outputsheetrowheader.createCell(20).setCellValue("Trend_Type");
        Outputsheetrowheader.createCell(21).setCellValue("Trend_Query");
        Outputsheetrowheader.createCell(22).setCellValue("Trend_Tolerance");
        Outputsheetrowheader.createCell(23).setCellValue("Trend_Tolerance_Type");
        Outputsheetrowheader.createCell(24).setCellValue("Trend_Check");
        Outputsheetrowheader.createCell(25).setCellValue("Trend_Actual_Swing");
        Outputsheetrowheader.createCell(26).setCellValue("Trend_Actual_Value");
        
		for (int i = 0; i < Excel_DataPoint.size(); i++) {
			
			//Creates the New Row in the Summary Sheet
			HSSFRow sheetrow = Sheet.createRow(i+1);
			
			//Create the Row
			HSSFCell Cell0 = sheetrow.createCell(0);
			HSSFCell Cell1 = sheetrow.createCell(1);
			HSSFCell Cell2 = sheetrow.createCell(2);
			HSSFCell Cell3 = sheetrow.createCell(3);
			HSSFCell Cell4 = sheetrow.createCell(4);
			HSSFCell Cell5 = sheetrow.createCell(5);
			HSSFCell Cell6 = sheetrow.createCell(6);
			HSSFCell Cell7 = sheetrow.createCell(7);
			HSSFCell Cell8 = sheetrow.createCell(8);
			HSSFCell Cell9 = sheetrow.createCell(9);
			HSSFCell Cell10 = sheetrow.createCell(10);
			HSSFCell Cell11 = sheetrow.createCell(11);
			HSSFCell Cell12 = sheetrow.createCell(12);
			HSSFCell Cell13 = sheetrow.createCell(13);
			HSSFCell Cell14 = sheetrow.createCell(14);
			HSSFCell Cell15 = sheetrow.createCell(15);
			HSSFCell Cell16 = sheetrow.createCell(16);
			HSSFCell Cell17 = sheetrow.createCell(17);
			HSSFCell Cell18 = sheetrow.createCell(19);
			
			Cell0.setCellValue("Y");
			Cell1.setCellValue(Excel_WebsiteTab.get(i));
			Cell2.setCellValue(Excel_Table.get(i));
			Cell3.setCellValue(Excel_DataPoint.get(i));
			Cell4.setCellValue(Excel_Priority.get(i));
			Cell5.setCellValue(Excel_Frequency.get(i));
			Cell6.setCellValue(Excel_Tolerance_Range.get(i));
			Cell7.setCellValue(Excel_Modified_SQL.get(i));	
			Cell8.setCellValue(Excel_UI_Element.get(i));
			Cell9.setCellValue(Excel_UI_Vs_DB.get(i));
			Cell10.setCellValue(Excel_UI_Value.get(i));
			Cell11.setCellValue(Excel_DB_Value.get(i));
			Cell12.setCellValue(Excel_Periodic_Check.get(i));
			Cell13.setCellValue(Excel_Expected_Date.get(i));
			Cell14.setCellValue(Excel_Actual_Date.get(i));
			Cell15.setCellValue(Excel_Tolerance_Applied.get(i));
			Cell16.setCellValue(Excel_Tolerance_Treshold.get(i));
			Cell17.setCellValue(Excel_Actual_Swing.get(i));
			Cell18.setCellValue(Excel_Snapshot.get(i));
		}
				
		//Create the Input Stream
		FileOutputStream Summary = new FileOutputStream(new File(Outputexcelfile));
		
		//Write the Data to the workbook
		workbook.write(Summary);
		
		//Close the Output File
		Summary.close();
		
	}

	//Get the As Of Dates
	public static String fn_Get_UI_AsOfDate(WebDriver Driver, String AsOfDateLocator) {
		
		String AsOfDate = null;
		String FinalAsOfDate = null;
		WebElement element = null;
		
		try {
			
			element = Driver.findElement(By.cssSelector(AsOfDateLocator));
			
			try {
				((JavascriptExecutor)Driver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(2000);
			} catch (Exception e) {
				//System.out.println(e);
			}            
			
			AsOfDate = element.getText().trim();
			FinalAsOfDate = fn_Trim_AsOfDate(AsOfDate);
			
		} catch (Exception e) {
			FinalAsOfDate = null;
		}

		return FinalAsOfDate;
		
	}
	
	//Batch update of the HTML reporter
	public static void fn_BatchUpdate_HTML(String HTML_FilePath, String TestcaseName, ArrayList<String> Table, ArrayList<String> DataPoint, ArrayList<String> UIValue, ArrayList<String> UIStatus, ArrayList<String> SQL_Query, ArrayList<String> DBValue, ArrayList<String> UIVsDBStatus, ArrayList<String> ExpectedAsOfDate, ArrayList<String> ActualAsOfDate, ArrayList<String> AsOfDateStatus, ArrayList<String> SnapshotPath) {
	    
		String FinalDataToAppend = null;
		String FinalUIDataToAppend = null;
		String FinalUIVsDBDataToAppend = null;
		String FinalAsOfDateDataToAppend = null;
		    
	    //Iterate the For Loop with the Data Points
	    for (int index = 0; index < DataPoint.size(); index++) {
			
	    	String Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	    	String UIDataToAppend = null;
	    	String UIVsDBDataToAppend = null;
	    	String AsOfDateDataToAppend = null;
	    	String UISnapshotvalue = null;
	    	String UIStep = null;
	    	String UIDescription = null;
	    	String UIVsDBSnapshotvalue = null;
	    	String UIVsDBStep = null;
	    	String UIVsDBDescription = null;
	    	String AsOfDateSnapshotvalue = null;
	    	String AsOfDateStep = null;
	    	String AsOfDateDescription = null;
	    	
	    	UIStep = "Verify the UI Value for the"+NextLine+"DataPoint : "+BlueNormal_Start+DataPoint.get(index)+BlueNormal_End+NextLine+"Table : "+BlueNormal_Start+Table.get(index)+BlueNormal_End;
			UISnapshotvalue = "<a href = "+SnapshotPath.get(index)+">Snap Shot</a>";
	    	
	    	//Update the UI Values
			if (UIStatus.get(index).toString().equalsIgnoreCase("Passed")) {
				UIDescription = "The UI Value is "+BlueNormal_Start+UIValue.get(index)+BlueNormal_End;
				UIDataToAppend = "<tr><td>"+TestcaseName+"</td><td><font color=\"limegreen\">PASS</font></td><td>"+UIStep+"</td><td>"+UIDescription+"</td><td>"+Time+"</td><td>"+"N/A"+"</td></tr>";
			} else if (UIStatus.get(index).toString().equalsIgnoreCase("Failed")) {
				UIDescription = "The UI Value is "+RedBold_Start+UIValue.get(index)+RedBold_End;
				UIDataToAppend = "<tr><td>"+TestcaseName+"</td><td><font color=\"Red\">FAIL</font></td><td>"+UIStep+"</td><td>"+UIDescription+"</td><td>"+Time+"</td><td>"+UISnapshotvalue+"</td></tr>";
			} else {
				UIDescription = "The UI Value is "+BlueNormal_Start+UIValue.get(index)+BlueNormal_End;
				UIDataToAppend = "<tr><td>"+TestcaseName+"</td><td>WARN</td><td>"+UIStep+"</td><td>"+UIDescription+"</td><td>"+Time+"</td><td>"+UISnapshotvalue+"</td></tr>";
			}
			
			if (FinalUIDataToAppend == null || FinalUIDataToAppend.isEmpty()) {
				FinalUIDataToAppend = UIDataToAppend;
			} else {
				FinalUIDataToAppend = FinalUIDataToAppend+UIDataToAppend;
			}
			
			UIVsDBStep = "Verify the 'UIVsDB' Value for the"+NextLine+"DataPoint : "+BlueNormal_Start+DataPoint.get(index)+BlueNormal_End+NextLine+"Table : "+BlueNormal_Start+Table.get(index)+BlueNormal_End;
			UIVsDBSnapshotvalue = "<a href = "+SnapshotPath.get(index)+">Snap Shot</a>";
			
			//Update the UIVsDB values
			if (!(SQL_Query.get(index).equalsIgnoreCase("null"))) {
				
				if (UIVsDBStatus.get(index).toString().equalsIgnoreCase("Passed")) {
					UIVsDBDescription = "UI Value is : "+BlueBold_Start+UIValue.get(index)+BlueBold_End+NextLine+"DB Value is : "+BlueBold_Start+DBValue.get(index)+BlueBold_End+NextLine+"SQL_QUERY : "+SQL_Query.get(index);
					UIVsDBDataToAppend = "<tr><td>"+TestcaseName+"</td><td><font color=\"limegreen\">PASS</font></td><td>"+UIVsDBStep+"</td><td>"+UIVsDBDescription+"</td><td>"+Time+"</td><td>"+"N/A"+"</td></tr>";
				} else if (UIVsDBStatus.get(index).toString().equalsIgnoreCase("Failed")) {
					UIVsDBDescription = "UI Value is : "+RedBold_Start+UIValue.get(index)+RedBold_End+NextLine+"DB Value is : "+RedBold_Start+DBValue.get(index)+RedBold_End+NextLine+"SQL_QUERY : "+SQL_Query.get(index);
					UIVsDBDataToAppend = "<tr><td>"+TestcaseName+"</td><td><font color=\"Red\">FAIL</font></td><td>"+UIVsDBStep+"</td><td>"+UIVsDBDescription+"</td><td>"+Time+"</td><td>"+UIVsDBSnapshotvalue+"</td></tr>";				
				} else {
					UIVsDBDescription = "UI Value is : "+BlueBold_Start+UIValue.get(index)+BlueBold_End+NextLine+"DB Value is : "+BlueBold_Start+DBValue.get(index)+BlueBold_End+NextLine+"SQL_QUERY : "+SQL_Query.get(index);
					UIVsDBDataToAppend = "<tr><td>"+TestcaseName+"</td><td>WARN</td><td>"+UIVsDBStep+"</td><td>"+UIVsDBDescription+"</td><td>"+Time+"</td><td>"+UIVsDBSnapshotvalue+"</td></tr>";
				}
	
				if (FinalUIVsDBDataToAppend == null || FinalUIVsDBDataToAppend.isEmpty()) {
					FinalUIVsDBDataToAppend = UIVsDBDataToAppend;
				} else {
					FinalUIVsDBDataToAppend = FinalUIVsDBDataToAppend+UIVsDBDataToAppend;
				}
				
			}

			
			AsOfDateStep = "Verify the 'As Of Date' Value for the"+NextLine+"DataPoint : "+BlueNormal_Start+DataPoint.get(index)+BlueNormal_End+NextLine+"Table : "+BlueNormal_Start+Table.get(index)+BlueNormal_End;;
			AsOfDateSnapshotvalue = "<a href = "+SnapshotPath.get(index)+">Snap Shot</a>";
			
			//Verify the AsOfDate
			if (AsOfDateStatus.get(index).toString().equalsIgnoreCase("Passed")) {
				AsOfDateDescription = "Expected 'As Of Date' Value is : "+BlueBold_Start+ExpectedAsOfDate.get(index)+BlueBold_End+NextLine+"Actual 'As Of Date' value is : "+BlueBold_Start+ActualAsOfDate.get(index)+BlueBold_End;
				AsOfDateDataToAppend = "<tr><td>"+TestcaseName+"</td><td><font color=\"limegreen\">PASS</font></td><td>"+AsOfDateStep+"</td><td>"+AsOfDateDescription+"</td><td>"+Time+"</td><td>"+"N/A"+"</td></tr>";
			} else if (AsOfDateStatus.get(index).toString().equalsIgnoreCase("Failed")) {
				AsOfDateDescription = "Expected 'As Of Date' Value is : "+RedBold_Start+ExpectedAsOfDate.get(index)+RedBold_End+NextLine+"Actual 'As Of Date' value is : "+RedBold_Start+ActualAsOfDate.get(index)+RedBold_End;
				AsOfDateDataToAppend = "<tr><td>"+TestcaseName+"</td><td><font color=\"Red\">FAIL</font></td><td>"+AsOfDateStep+"</td><td>"+AsOfDateDescription+"</td><td>"+Time+"</td><td>"+AsOfDateSnapshotvalue+"</td></tr>";
			} else {
				AsOfDateDescription = "Expected 'As Of Date' Value is : "+BlueBold_Start+ExpectedAsOfDate.get(index)+BlueBold_End+NextLine+"Actual 'As Of Date' value is : "+BlueBold_Start+ActualAsOfDate.get(index)+BlueBold_End;
				AsOfDateDataToAppend = "<tr><td>"+TestcaseName+"</td><td>WARN</td><td>"+AsOfDateStep+"</td><td>"+AsOfDateDescription+"</td><td>"+Time+"</td><td>"+AsOfDateSnapshotvalue+"</td></tr>";
			}

			if (FinalAsOfDateDataToAppend == null || FinalAsOfDateDataToAppend.isEmpty()) {
				FinalAsOfDateDataToAppend = AsOfDateDataToAppend;
			} else {
				FinalAsOfDateDataToAppend = FinalAsOfDateDataToAppend+AsOfDateDataToAppend;
			}
			
		}
	    
	    //Get the values to a string
	    if (FinalUIVsDBDataToAppend != null) {
			FinalDataToAppend = FinalUIDataToAppend+FinalUIVsDBDataToAppend+FinalAsOfDateDataToAppend;
		} else {
			FinalDataToAppend = FinalUIDataToAppend+FinalAsOfDateDataToAppend;
		}
	    		
	    //Update the values to the HTML File
	    try {
			FileWriter filewrite = new FileWriter(HTML_FilePath,true);
			BufferedWriter BufferWrite = new BufferedWriter(filewrite);
			PrintWriter write = new PrintWriter(BufferWrite);
		    write.println(FinalDataToAppend);
			write.close();
			BufferWrite.close();
			filewrite.close();
		} catch (Exception e) {

		} finally {

		}
	    
	}
	
	public static int fn_Calculate_No_Of_Business_Days() {
		
		String First_Date_Of_Current_Month = null;
		int Decrementer = 0;
		int BusinessDay_Counter = 0;
		boolean BusinessDay_Calculated = false;
		
		DateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
		
		//Get the First Date of the Current Month
		First_Date_Of_Current_Month = fn_FirstDate_Of_CurrentMonth();
				
		do {
			
			int value = 0;
			String Previous_Date = null;
			Calendar cal = Calendar.getInstance();
									
			//Decrement the Date
			cal.add(Calendar.DAY_OF_MONTH, -Decrementer);
			
			//Get the Previous Date
			Previous_Date = DF.format(cal.getTime());
			
			//Get the day of the date
			value = cal.get(Calendar.DAY_OF_WEEK);
						
			//Increment the Business day counter except for the Saturday and Sunday
			if (!((value == 1) || (value == 7))) {
				BusinessDay_Counter ++;				
			}
			
			//Verify the Previous date reached to the First date of the month
			if (Previous_Date.trim().equalsIgnoreCase(First_Date_Of_Current_Month.trim())) {
				BusinessDay_Calculated = true;
			}
			
			Decrementer = Decrementer+1;
			
		} while (!BusinessDay_Calculated);
		
		return BusinessDay_Counter;
	}
	
	public static String fn_FirstDate_Of_CurrentMonth() {
		
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date firstdateofmonth = cal.getTime();
		String Current_Month_First_Date = new SimpleDateFormat("dd/MM/yyyy").format(firstdateofmonth);		
		
		return Current_Month_First_Date;
		
	}
	
	//Get the fund names in "|" separated symbol
	public static String fn_Get_Fund_Names_With_Pipe_Seperation() {
		
		ArrayList<String> Fund_Names = new ArrayList<>();
		String FinalFundNames = null;
		String FundName = null;
		FundName = "";
		
		try {
			
			//Get the Fund Names in the array list
			Fund_Names = fn_Get_Fund_Names();
			
			//Shuffle the array list
			Collections.shuffle(Fund_Names);
			
			//Iterate the array and get all the fund names in a single string
			for (int fund = 0; fund < Fund_Names.size(); fund++) {
				String Fund = null;
				Fund = Fund_Names.get(fund);
				FundName = FundName+"|"+Fund;
			}
			
			FinalFundNames = FundName.replaceFirst("\\|", "");
			
		} catch (Throwable e) {
			
			e.printStackTrace();
			FinalFundNames = "";
			
		}
		
		return FinalFundNames;
		
	}
	
	//Get the Script Names
	public static ArrayList<String> fn_Get_Script_Names() throws Throwable {
	
		ArrayList<String> ScriptName = new ArrayList<>();
		
		try {
			
			FileInputStream Fip = new FileInputStream(Common_Functions.MasterInputExcel);
			HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
			HSSFSheet Sheet = wrkbk.getSheet("Script_Execution");
			
			HSSFRow row = Sheet.getRow(0);
				
			Iterator<Cell> Cell = row.cellIterator();
			
			Cell.next();
			
			while (Cell.hasNext()) {
				
				HSSFCell cell = (HSSFCell) Cell.next();
				
				ScriptName.add(cell.getStringCellValue());
				
			}
			
			//Close the workbook
			wrkbk.close();
			Fip.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return ScriptName;
		
	}
	
	//Get the fund names in ArrayList
	public static ArrayList<String> fn_Get_Fund_Names() throws Throwable {
		
		ArrayList<String> FundName = new ArrayList<>();
				
		try {
			
			FileInputStream Fip = new FileInputStream(Common_Functions.MasterInputExcel);
			HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
			HSSFSheet Sheet = wrkbk.getSheet("Script_Execution");
			
			Iterator<Row> Row = Sheet.rowIterator();
			
			Row.next();
			
			while (Row.hasNext()) {
				
				HSSFRow row = (HSSFRow) Row.next();
				
				HSSFCell cell = row.getCell(0);
				
				FundName.add(cell.getStringCellValue());
				
			}
			
			//Close the workbook
			wrkbk.close();
			Fip.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
		return FundName;
		
	}

	//Get the HTML reporter status
	public static String fn_Get_Test_Status(String HTMLFilePath) throws Exception {
		
		String content = null;
		String SplitContent = null;
		String FinalStatus = "Fail";
		
		try {
			
			FileInputStream fileinput = new FileInputStream(HTMLFilePath);
			content = IOUtils.toString(fileinput, "UTF-8");
			SplitContent = ">FAIL </font></td><td class=\"2\">";
			String[] SplitValue = content.split(SplitContent);
			
			//Find the first occurrence of the string '<'
			int position = SplitValue[1].indexOf("<", 0);
			
			try {
				
				//Get the Fail Count
				int FailCount = Integer.valueOf(SplitValue[1].substring(0, position));
				
				if (FailCount > 0) {
					FinalStatus = "Fail";
				} else if (FailCount == 0) {
					FinalStatus = "Pass";
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				FinalStatus = "Fail";
				
			}
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return FinalStatus;
	}
	
	//Update the ORADEVPIM DB during the end of the execution
	public static void fn_End_ORADEVPIM_Update(String BatchMachineName, String ResultFileLocation, String Fund, String TestScript) throws Throwable {
		
		String Select_Query_Output = null;
		
		String MonthNumber = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
		String Date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		String Current_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		String ExecutionStarte_date = Date+"/"+MonthNumber+"/"+Year;
		String ExecutionStart_Time = Current_Time;
		String Execution_End = ExecutionStarte_date+"-"+ExecutionStart_Time;
		
		String URL = null;
		String UID = null;
		String PWD = null;
		
		//Load the Properties File
		File propfile = new File(Common_Functions.DB_PropertiesFilePath);
		FileInputStream Fip;
		Fip = new FileInputStream(propfile);
		Properties prop = new Properties();
		prop.load(Fip);
					
		URL = prop.getProperty("ORADEVPIM");
		UID = prop.getProperty("DEVPIM_DB_UID");
		PWD = prop.getProperty("DEVPIM_DB_PWD");
		
		String SelectSQLQuery = "SELECT EXECUTION_START From "+ETF_Result_Schema+".ETF_SELENIUM_TEST_MONITOR Where TEST_SCRIPT = '"+TestScript+"' and FUND = '"+Fund+"' and EXECUTION_START LIKE '"+ExecutionStarte_date+"%' and RESULT_FILE_LOCATION = '"+ResultFileLocation+"'";
		
		try {
			
			//Establish the connection
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection Con = DriverManager.getConnection(URL,UID,PWD);
			
			Statement stmt = Con.createStatement();
			ResultSet rs = stmt.executeQuery(SelectSQLQuery);
			
			//Verify the DB record exists
			while (rs.next()){
				Select_Query_Output = rs.getString(1);
			}
			
			//Verify the entry is already in the DB
			if ((Select_Query_Output != null)) {
				
				String[] SplitStartDate = Select_Query_Output.split("-");
				String[] SplitStartTime = SplitStartDate[1].split(":");
				
				long longstarthour = Long.parseLong(SplitStartTime[0])*60*60*1000;
				long longstartminute = Long.parseLong(SplitStartTime[1])*60*1000;
				long longstartsecond = Long.parseLong(SplitStartTime[2])*1000;
				long StartTimemillisecond = longstarthour+longstartminute+longstartsecond;
				
				String End_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
				String[] SplitEndtime = End_Time.split(":");
				long longendhour = Long.parseLong(SplitEndtime[0])*60*60*1000;
				long longendminute = Long.parseLong(SplitEndtime[1])*60*1000;
				long longendsecond = Long.parseLong(SplitEndtime[2])*1000;
				long EndTimemillisecond = longendhour+longendminute+longendsecond;
				
				//Calculate the time difference
				long timedifference = EndTimemillisecond - StartTimemillisecond;
				
				//Convert the time difference to hh:mm:ss format
				String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timedifference),TimeUnit.MILLISECONDS.toMinutes(timedifference) % TimeUnit.HOURS.toMinutes(1),TimeUnit.MILLISECONDS.toSeconds(timedifference) % TimeUnit.MINUTES.toSeconds(1));
				String[] splittimeduration = hms.split(":");
				String ExecutionDuration = splittimeduration[0]+" hr:"+splittimeduration[1]+" min:"+splittimeduration[2]+" sec";
				
				//Get the Status from the HTML File
				String Test_Status = Common_Functions.fn_Get_Test_Status(ResultFileLocation);
				
				String UpdateSQLQuery = "UPDATE "+ETF_Result_Schema+".ETF_SELENIUM_TEST_MONITOR SET EXECUTION_END = '"+Execution_End+"', STATUS = '"+Test_Status+"', EXECUTION_DURATION = '"+ExecutionDuration+"' Where TEST_SCRIPT = '"+TestScript+"' and FUND = '"+Fund+"' and EXECUTION_START LIKE '"+ExecutionStarte_date+"%' and RESULT_FILE_LOCATION = '"+ResultFileLocation+"'";
				
				//Execute the UPDATE Query
				Statement stmt1 = Con.createStatement();
				stmt1.executeQuery(UpdateSQLQuery);
				stmt1.executeQuery("commit");
				
				//Close the connection
				Con.close();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Update the ORADEVPIM DB during the start of the execution
	public static String fn_Start_ORADEVPIMDB_Update(String BatchMachineName, String Fund, String TestScript) throws Throwable {
		
		String MonthNumber = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
		String Date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		String ExecutionStarte_date = Date+"/"+MonthNumber+"/"+Year;
		String URL = null;
		String UID = null;
		String PWD = null;
		String Already_Executed = "N";
		int Select_Query_Output = 0;
		
		//Load the Properties File
		File propfile = new File(Common_Functions.DB_PropertiesFilePath);
		FileInputStream Fip;
		Fip = new FileInputStream(propfile);
		Properties prop = new Properties();
		prop.load(Fip);
					
		URL = prop.getProperty("ORADEVPIM");
		UID = prop.getProperty("DEVPIM_DB_UID");
		PWD = prop.getProperty("DEVPIM_DB_PWD");
		
		String SelectSQLQuery = "SELECT COUNT(*) From "+ETF_Result_Schema+".ETF_SELENIUM_TEST_MONITOR Where TEST_SCRIPT = '"+TestScript+"' and FUND = '"+Fund+"' and EXECUTION_START LIKE '"+ExecutionStarte_date+"%'";
		
		try {
			
			//Establish the connection
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection Con = DriverManager.getConnection(URL,UID,PWD);
			
			Statement stmt = Con.createStatement();
			ResultSet rs = stmt.executeQuery(SelectSQLQuery);
			
			//Verify the DB record exists
			while (rs.next()){
				Select_Query_Output = rs.getInt(1);
			}
			
			//Verify the entry is already in the DB
			if (Select_Query_Output > 0) {
				Already_Executed = "Y";				
			} else {
				Already_Executed = "N";
			}
			
			//Close the connection
			Con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Already_Executed;
				
	}

	//Update Trigger status in the ORADEVPIM DB
	public static void fn_ORADEVPIMDB_Trigger_Status() throws Throwable {
		
		String MonthNumber = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
		String Date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		String Current_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		String ExecutionStarte_date = Date+"/"+MonthNumber+"/"+Year;
		String ExecutionStart_Time = Current_Time;
		String Execution_Start = ExecutionStarte_date+"-"+ExecutionStart_Time;
		String ORADEVPIM_URL = null;
		String UID = null;
		String PWD = null;
		int Select_Query_Output = 0;
		
		String SelectSQLQuery = "SELECT DISTINCT(COUNT(*)) FROM "+ETF_Result_Schema+".ETF_SELENIUM_TRIGGER_STATUS WHERE TRIGGER_DATE LIKE '"+ExecutionStarte_date+"%' AND TRIGGER_STATUS = 'TRIGGERED'";
		String InsertSQLQuery = "INSERT INTO "+ETF_Result_Schema+".ETF_SELENIUM_TRIGGER_STATUS (TRIGGER_DATE, TRIGGER_STATUS) VALUES ('"+Execution_Start+"','TRIGGERED')";
		
		try {
			
			//Load the Properties File
			File propfile = new File(Common_Functions.DB_PropertiesFilePath);
			FileInputStream Fip;
			Fip = new FileInputStream(propfile);
			Properties prop = new Properties();
			prop.load(Fip);
			
			ORADEVPIM_URL = prop.getProperty("ORADEVPIM");
			UID = prop.getProperty("DEVPIM_DB_UID");
			PWD = prop.getProperty("DEVPIM_DB_PWD");
			
			//Establish the DB connection
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection DEVPIM_DB_Con = DriverManager.getConnection(ORADEVPIM_URL, UID, PWD);
			
			Statement stmt = DEVPIM_DB_Con.createStatement();
			ResultSet rs = stmt.executeQuery(SelectSQLQuery);
			
			//Execute the Select Query
			while (rs.next()){
				Select_Query_Output = rs.getInt(1);
			}
			
			//Execute the INSERT Query if the record doesn't exist
			if (Select_Query_Output == 0) {
				Statement stmt1 = DEVPIM_DB_Con.createStatement();
				stmt1.executeQuery(InsertSQLQuery);
				stmt1.executeQuery("commit");
				Runtime.getRuntime().exec("cscript "+Common_Functions.VBS_TRIGGER_INTIMATION);
			}

			//Close the connection
			DEVPIM_DB_Con.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//Update the ORADEVPIM DB during the start of the execution
	public static String fn_Start_ORADEVPIMDB_Update(String BatchMachineName, String Fund, String TestScript, String ResultFilePath) throws Throwable {
		
		String MonthNumber = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
		String Date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
		String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		String Current_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		String ExecutionStarte_date = Date+"/"+MonthNumber+"/"+Year;
		String ExecutionStart_Time = Current_Time;
		String Execution_Start = ExecutionStarte_date+"-"+ExecutionStart_Time;
		String URL = null;
		String UID = null;
		String PWD = null;
		String Already_Executed = "N";
		
		//Load the Properties File
		File propfile = new File(Common_Functions.DB_PropertiesFilePath);
		FileInputStream Fip;
		Fip = new FileInputStream(propfile);
		Properties prop = new Properties();
		prop.load(Fip);
		
		URL = prop.getProperty("ORADEVPIM");
		UID = prop.getProperty("DEVPIM_DB_UID");
		PWD = prop.getProperty("DEVPIM_DB_PWD");
		
		String InsertSQLQuery = "INSERT INTO "+ETF_Result_Schema+".ETF_SELENIUM_TEST_MONITOR (BATCH_MACHINE_NAME, EXECUTION_START, STATUS, FUND, TEST_SCRIPT, RESULT_FILE_LOCATION) VALUES ('"+BatchMachineName+"', '"+Execution_Start+"', 'IN_PROGRESS', '"+Fund+"', '"+TestScript+"', '"+ResultFilePath+"' )";
		
		try {
			
			//Establish the connection
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection Con = DriverManager.getConnection(URL,UID,PWD);		
			
			//Execute the INSERT Query
			Statement stmt1 = Con.createStatement();
			stmt1.executeQuery(InsertSQLQuery);
			stmt1.executeQuery("commit");
						
			//Close the connection
			Con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Already_Executed;
				
	}

	//Generate the Random numbers
	public static long fn_generate_Random_Number(int Minimum, int Maximum) {
		
		int min = Minimum;
		int max = Maximum;
		
		long randomNum = (long) (min + (Math.random() * (max - min)));
		return randomNum;
		
	}
	
	//Get the Execution Flag for the Funds and Scripts
	public static HashMap<String,String> fn_Get_ExecutionFlag(ArrayList<String> FundName, ArrayList<String> ScriptName) throws Throwable {
		
		HashMap<String, String> ExecutionFlag = new HashMap<>();
		
		try {
			
			FileInputStream Fip = new FileInputStream(Common_Functions.MasterInputExcel);
			HSSFWorkbook Wrkbk = new HSSFWorkbook(Fip);
			HSSFSheet Sheet = Wrkbk.getSheet("Script_Execution");
			
			for (int Rownum = 0; Rownum < FundName.size(); Rownum++) {
				
				String Fund_Name = null;
				
				//Get the Fund Name
				Fund_Name = FundName.get(Rownum);
				
				HSSFRow Row = (HSSFRow) Sheet.getRow(Rownum+1);
				
				for (int Colnum = 0; Colnum < ScriptName.size(); Colnum++) {
					
					String Script_Name = null;
					String Key = null;
					String Value = null;
					
					//Get the Script Name
					Script_Name = ScriptName.get(Colnum);
					
					HSSFCell Cell = Row.getCell(Colnum+1);
					
					Key = Fund_Name+"|"+Script_Name;
					Value = Cell.getStringCellValue().toString().trim();
					
					//Update the Hash Map
					ExecutionFlag.put(Key,Value);
					
				}
				
			}
			
			//Close the workbook
			Wrkbk.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return ExecutionFlag;
	
	}
	
	//Wait property
	public static void fn_Wait_Property(WebDriver Driver, String CSSLocator, long waittime) {
		
		//Create object for wait
		WebDriverWait wait = new WebDriverWait(Driver, waittime);
		
		//Driver will wait until the css selector is visible
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSSLocator)));
		
	}
	
	//Wait for the page load
	public static void fn_WaitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

	
	//Close and quit the browser
	public static void fn_Close_Drvier(WebDriver Driver){
		try {
			Driver.quit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static String fn_ReaddatafromExcelUsingcolumnName(String InputTestDataFile, String ColumnName)
			   throws EncryptedDocumentException, InvalidFormatException, IOException {
			  String SheetName = "TestData";
			  File file = new File(InputTestDataFile);
			  FileInputStream fi = new FileInputStream(file);
			  Workbook wb = WorkbookFactory.create(fi);
			  Sheet sheet = wb.getSheet(SheetName);
			  // it will take value from first row
			  Row row = sheet.getRow(0);
			// it will give you count of row which is used or filled
			  short lastcolumnused = row.getLastCellNum();

			  int colnum = 0;
			  for (int i = 0; i < lastcolumnused; i++) {
			   if (row.getCell(i).getStringCellValue().equalsIgnoreCase(ColumnName)) {
			    colnum = i;
			    break;
			   }
			  }

			  // it will take value from Second row
			  row = sheet.getRow(1);
			  Cell column = row.getCell(colnum);
			  String CellValue;
			  if ((column.getCellType()) == CellType.NUMERIC) 
			  {
			  		CellValue = String.valueOf(column.getNumericCellValue());}
			  else {
				  	CellValue = column.getStringCellValue();}

			  return CellValue;

			 }
	
	public static boolean fn_ClickElement(WebElement we){
		
		//Validate that the WebElement exists in the page
		if (we.isDisplayed() && we.isEnabled()){
			we.click();
			return true;
		}else{
			return false; 
		}
	}
	
	public static String getDriverLocation(String browser){
		
		/*Retrieve which driver to run the test*/
		String l_browser = browser;
		String driverLocation = "./drivers/";
		
		if (l_browser == "GoogleChrome"){
			driverLocation = driverLocation + "chromedriver.exe"; }
		else if(l_browser == "Firefox") {
			driverLocation = driverLocation + "geckodriver.exe"; }
		else 
			driverLocation = driverLocation + "geckodriver.exe";
		return driverLocation;
	}

}





