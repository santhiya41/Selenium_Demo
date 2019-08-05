package com.common.libraries;

import java.awt.Button;

import org.openqa.selenium.*;

public class Common_ObjectRepository {

	/*** Declaration of HomePage Objects ***/
	public WebElement we_HomePage_OneWay;
	public EditField edt_HomePage_Origin,edt_HomePage_Destination,edt_HomePage_DepartDate;
	public Button btn_HomePage_Search; 
	public Link lnk_HomePage_JoinAAdvantage;
	public Page pg_HomePage;
	public WebElement oneway;
	
	/*** Declaration of SelectFlight Page Objects ***/
	public RadioGroup rg_SelectFlight_SelectPrice;
	public Button btn_SelectFlight_Continue;
	public Page pg_SelectFlights;
	
	/*** Declaration of CustomerDetails Page Objects ***/
	public EditField edt_CustDetails_FName, edt_CustDetails_LName, edt_CustDetails_phAreaCode,edt_CustDetails_phMblNum,edt_CustDetails_email;		
	public ListBox lst_CustDetails_PassengerType,lst_CustDetails_DOBMonth,lst_CustDetails_DOBDay,lst_CustDetails_DOBYear,lst_CustDetails_Gender,lst_CustDetails_phCouCode;
	public Button btn_CustDetails_Continue;
	public Page pg_CustDetails;	
	
	/*** Declaration of Reservation Options Page Objects ***/
	public Button btn_ReservOption_contWithoutSeats,btn_ReservOption_Submit,btn_img_ReservOption_Continue;
	public Image img_ReservOption_MainCabin,img_ReservOption_PreferableCabin;
	public Page pg_ReservOption,pg_SelectSeats;
	
	/*** Declaration of Review and Pay Page Objects ***/
	public Link lnk_ReviewPay_Hold;
	public Button btn_ReviewPay_Hold;
	public Page pg_ReviewPay;
	
	
	/*** Declaration of Your Information Page Objects ***/
	public EditField edt_YourInfo_FirstName,edt_YourInfo_LastName,edt_YourInfo_Address1,edt_YourInfo_City,
				edt_YourInfo_PostalCode,edt_YourInfo_Email,edt_YourInfo_ConfirmEmail,edt_YourInfo_PhoneNum,edt_YourInfo_ConfirmPwd,
				edt_YourInfo_Answer1,edt_YourInfo_Answer2,edt_YourInfo_Answer3,edt_YourInfo_CaptchaResponse,edt_YourInfo_Password;
	public ListBox lst_YourInfo_DOBMonth,lst_YourInfo_DOBDay,lst_YourInfo_DOBYear,lst_YourInfo_Country,lst_YourInfo_Gender,
				   lst_YourInfo_State,lst_YourInfo_PhType,lst_YourInfo_Ques1,lst_YourInfo_Ques2,lst_YourInfo_Ques3,lst_YourInfo_CountryCode;
	public WebElement we_YourInfo_CountryCode,we_YourInfo_TermsCondition;
	public Image img_YourInfo_Captcha;
	public Button btn_YourInfo_Continue;
	
	
	/*** Declaration of American Airline Reservation Confirmation Page Objects ***/
	public WebElement we_AAReserConfirm_RecordLoc, we_AAReserConfirm_OnHold;
	public Page pg_ReservConfirmationPage;
	
	
	/********** Building the object repository **************/
	
	public void homePage(WebDriver driver) throws Exception{
	
		/**Home Page Elements - Search flight**/
		we_HomePage_OneWay = driver.findElement(By.id("reservationFlightSearchForm.originAirport"));
		
	}
	
	public void searchFlight(Browser browser) throws Exception{

		/** Select Flight Page Elements**/
		rg_SelectFlight_SelectPrice = browser.describe(RadioGroup.class, new RadioGroupDescription.Builder().tagName("INPUT").name("returning").index(0).build());
		btn_SelectFlight_Continue = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Continue").build());
		pg_SelectFlights = browser.describe(Page.class, new PageDescription.Builder().title("Choose flights – Flight results – American Airlines").build());
		
	}
	
	public void customerDetails(Browser browser) throws Exception{

		/** Customer Details Page Elements **/
		edt_CustDetails_FName = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("passenger[0].secureFirstName").build());
		edt_CustDetails_LName = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("passenger[0].secureLastName").build());
		lst_CustDetails_PassengerType = browser.describe(ListBox.class, new ListBoxDescription.Builder().tagName("SELECT").name("passenger[0].travelerType").build());
		lst_CustDetails_DOBMonth = browser.describe(ListBox.class, new ListBoxDescription.Builder().tagName("SELECT").name("passenger[0].secureFlightData.birthDateMonth").build());
		lst_CustDetails_DOBDay = browser.describe(ListBox.class, new ListBoxDescription.Builder().tagName("SELECT").name("passenger[0].secureFlightData.birthDateDay").build());
		lst_CustDetails_DOBYear = browser.describe(ListBox.class, new ListBoxDescription.Builder().tagName("SELECT").name("passenger[0].secureFlightData.birthDateYear").build());
		lst_CustDetails_Gender = browser.describe(ListBox.class, new ListBoxDescription.Builder().tagName("SELECT").name("passenger[0].secureFlightData.gender").build());
		lst_CustDetails_phCouCode = browser.describe(ListBox.class, new ListBoxDescription.Builder().tagName("SELECT").name("phone[2].countryCode").build());
		edt_CustDetails_phAreaCode = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("phone[2].areaCode").build());
		edt_CustDetails_phMblNum = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("phone[2].number").build());
		edt_CustDetails_email = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("email").build());
		pg_CustDetails = browser.describe(Page.class, new PageDescription.Builder().title("Travelers – Passenger details for your reservation – American Airlines").build());
		btn_CustDetails_Continue = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").id("enterPassengerDetailsForm.button.continue").tagName("INPUT").name("Continue").build());
	}
	
	public void reservationOptions(Browser browser) throws Exception{

		/** Reservation Option Page Elements **/ 
		
		
		pg_ReservOption = browser.describe(Page.class, new PageDescription.Builder().title("Trip options – Meals, seats and more – American Airlines").build());
		btn_ReservOption_contWithoutSeats = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Continue without seats »").build());
		btn_ReservOption_Submit = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Submit").build());
		img_ReservOption_MainCabin = browser.describe(Image.class, new ImageDescription.Builder().alt("").type(com.hp.lft.sdk.web.ImageType.LINK).tagName("IMG").index(6).build());
		img_ReservOption_PreferableCabin = browser.describe(Image.class, new ImageDescription.Builder().alt("").type(com.hp.lft.sdk.web.ImageType.LINK).tagName("IMG").index(20).build());
		pg_SelectSeats = browser.describe(Page.class, new PageDescription.Builder().title("Choose your seat – Seat map selection – American Airlines").build());
		btn_img_ReservOption_Continue = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Continue").build());
		btn_SelectFlight_Continue = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Continue").build());
	}
	
	
	public void reviewConfirm(Browser browser) throws Exception{
		/** Review and Pay Page Elements **/
		lnk_ReviewPay_Hold = browser.describe(Link.class, new LinkDescription.Builder().tagName("A").innerText("Hold ").build());
		btn_ReviewPay_Hold = browser.describe(Button.class, new ButtonDescription.Builder().buttonType("submit").tagName("INPUT").name("Hold").build());
		pg_ReviewPay = browser.describe(Page.class, new PageDescription.Builder().title("Review & pay – Book your trip – American Airlines").build());
		we_AAReserConfirm_RecordLoc = browser.describe(WebElement.class, new WebElementDescription.Builder().className("aa-record-locator aaDarkRed").tagName("STRONG").build());
		pg_ReservConfirmationPage = browser.describe(Page.class, new PageDescription.Builder().title("Airline Reservation Confirmation | Finish | American Airlines | AA.com").build());
		we_AAReserConfirm_OnHold = browser.describe(WebElement.class, new WebElementDescription.Builder()
				.tagName("STRONG").innerText("ON HOLD ").build());
	}
	
	public void yourInformation(Browser browser) throws Exception{
		/** Your Information Page Elements **/
		
		edt_YourInfo_FirstName = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("personalInformationForm.firstName").build());
		edt_YourInfo_LastName = browser.describe(EditField.class, new EditFieldDescription.Builder().type("text").tagName("INPUT").name("personalInformationForm.lastName").build());
		lst_YourInfo_DOBMonth = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("personalInformationForm.dateOfBirth.month").build());
		lst_YourInfo_DOBDay = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("personalInformationForm.dateOfBirth.day").build());
		lst_YourInfo_DOBYear = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("personalInformationForm.dateOfBirth.year").build());
		lst_YourInfo_Country = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("addressInformationForm.country").build());
		lst_YourInfo_Gender = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("personalInformationForm.gender").build());
		edt_YourInfo_Address1 = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("addressInformationForm.address1").build());
		edt_YourInfo_City = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("addressInformationForm.city").build());
		lst_YourInfo_State = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("addressInformationForm.usState").build());
		edt_YourInfo_PostalCode = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("addressInformationForm.postalCode").build());
		edt_YourInfo_Email = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("emailPhoneForm.email").build());
		edt_YourInfo_ConfirmEmail = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("emailPhoneForm.confirmEmail").build());
		lst_YourInfo_PhType = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("emailPhoneForm.phones[0].type").build());
		we_YourInfo_CountryCode = browser.describe(WebElement.class, new WebElementDescription.Builder()
				.tagName("DIV").innerText("").index(6).build());
		edt_YourInfo_PhoneNum = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("tel").tagName("INPUT").name("emailPhoneForm.phones[0].number").build());
		lst_YourInfo_Ques1 = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("yourAccountForm.securityQuestions[0].question").build());
		edt_YourInfo_Answer1 = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("WebEdit").index(0).build());
		lst_YourInfo_Ques2 = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("yourAccountForm.securityQuestions[1].question").build());
		edt_YourInfo_Answer2 = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("WebEdit").index(1).build());
		lst_YourInfo_Ques3 = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("yourAccountForm.securityQuestions[2].question").build());
		edt_YourInfo_Answer3 = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("WebEdit").index(2).build());
		img_YourInfo_Captcha = browser.describe(Image.class, new ImageDescription.Builder()
				.alt("").type(com.hp.lft.sdk.web.ImageType.NORMAL).tagName("IMG").index(4).build());
		edt_YourInfo_CaptchaResponse = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("recaptcha_response_field").build());
		we_YourInfo_TermsCondition = browser.describe(WebElement.class, new WebElementDescription.Builder()
				.tagName("SPAN").innerText("").index(46).build());
		btn_YourInfo_Continue = browser.describe(Button.class, new ButtonDescription.Builder()
				.buttonType("submit").tagName("BUTTON").name("Continue").build());
		lst_YourInfo_CountryCode = browser.describe(ListBox.class, new ListBoxDescription.Builder()
				.tagName("SELECT").name("emailPhoneForm.phones[0].countryCode").build());
		edt_YourInfo_Password = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("password").tagName("INPUT").name("yourAccountForm.password").build());
		edt_YourInfo_ConfirmPwd = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("password").tagName("INPUT").name("yourAccountForm.confirmPassword").build());			
	}
}
