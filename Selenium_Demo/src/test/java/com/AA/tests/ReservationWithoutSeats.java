/*To test the AA.com site without selecting the seats*/

package com.AA.tests;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import org.apache.log4j.PropertyConfigurator;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.AA.CommonUtilities.Common_ConfigFile;
import com.AA.CommonUtilities.Common_Functions;
import com.AA.Pages.*;
import com.common.libraries.*;

public class ReservationWithoutSeats extends MasterSetup {

	@ Test
	public static void test() throws Throwable { 
		
		/*Declaration of Variables*/		
		String Hostname;
		WebDriver driver;
		String InputTestDataFile;	
		
		Common_Functions.TestCaseName = "ReservationWithoutSeats";
		//Input excel file location
		InputTestDataFile = Common_Functions.InputDatalocation+"\\"+Common_Functions.TestCaseName+".xlsx";
		//Retrieve the test data
		String strFrom = Common_Functions.fn_ReaddatafromExcelUsingcolumnName(InputTestDataFile, "From");
		String strTo = Common_Functions.fn_ReaddatafromExcelUsingcolumnName(InputTestDataFile, "To");
		String strDepartureDate = String.valueOf(Common_Functions.fn_ReaddatafromExcelUsingcolumnName(InputTestDataFile, "DepartureDate"));
		/*Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(strDepartureDate);*/
		
		//Get the Host Name
		Hostname = InetAddress.getLocalHost().getHostName();
		
	
		//PropertyConfigurator.configure("Log4j.properties");
					
		/*Create Pre-Requisites of the test (Creating HTML Folder, Result Folder ..)*/
		HtmlFile = Common_Functions.fn_Pre_Requiste_Execution("Reservation", "ReservationWithoutSeats");
		//logger.info("Folder and HTML Reporter files are created");
		
		/*Open Browser */
		driver = Common_Functions.fn_Open_Browser(ApplicationURL);
		Common_Functions.fn_Update_HTML(Common_Functions.HtmlFile, Common_Functions.TestCaseName, "PASS", "Host Name", "The script is running in the Host Name"+Common_Functions.NextLine+Hostname, driver, false);
		//logger.info("Browser is launched with the URL www.aa.com");
		
		//Search with Oneway
		HomePage hp = new HomePage(driver);
		hp.fn_SearchOneWay();
		//logger.info("Search for one way successful");
		
		//End the HTML reporter
		Common_Functions.fn_End_HTML(Common_Functions.HtmlFile);

	}

}
