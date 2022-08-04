package com.OrgangeHRM.TestingDataMapsPractice;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import org.apache.tools.ant.util.FileUtils;
//import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.common.io.Files;

public class LoginPage {
	
	public WebDriver driver;
	
	@Before public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Resources\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
	
	public void takeSnapShot(String fileWithPath) throws IOException {
	/*	TakesScreenshot scrShot =((TakesScreenshot)driver);
		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		File DestFile=new File(fileWithPath);
		//Copy file at destination
		Files.move(SrcFile, DestFile); */
		//FileUtils.getFileUtils().copyFile(SrcFile, DestFile);
		//TakesScreenshot is an Interface
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
		FileUtils.getFileUtils().copyFile(scrFile, new File(fileWithPath));
		
		}
		
	
	public static void capturescreen(WebDriver webdriver, String screenshotname, String status) 
	{
		try {
			TakesScreenshot takesScreenshot = (TakesScreenshot) webdriver;

			File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			if (status.equals("FAILURE")) {
				FileUtils.getFileUtils().copyFile(scrFile, new File("./ScreenshotsFailure/" + screenshotname + ".png"));

			} else if (status.equals("SUCCESS")) {
				FileUtils.getFileUtils().copyFile(scrFile, new File("./ScreenshotsSuccess/" + screenshotname + ".png"));
			}
			System.out.println("Printing screen shot taken for className " + screenshotname);
		} catch (IOException e) {
			
			System.out.println("Exception while taking screenshot " + e.getMessage());
		}

	}	
	
	@Given("User is on HRMLogin page \"([^\"]*)\"$")
	public void user_is_on_hrm_login_page(String url) throws IOException {
		System.out.println("Open the URL");
	    // Write code here that turns the phrase above into concrete actions
		driver.get(url);
		//takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\BeforeLoginScreen.png");
		
		capturescreen(driver,"TestLogin","SUCCESS");
	}

	@When("User enters invalid credentials and Login will be unsuccessfull with error message")
	public void user_enters_invalid_credentials_and_login_will_be_unsuccessfull_with_error_message(io.cucumber.datatable.DataTable userTable) throws InterruptedException, IOException {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    
		
		System.out.println("Enter Credentials");                    
        List<Map<String, String>> user = userTable.asMaps(String.class, String.class);
        
        for (Map<String, String> form : user) {
        	
        String userName = form.get("Username");                  
        System.out.println("Username :" + userName);
        driver.findElement(By.id("txtUsername")).sendKeys(userName); 
         
        String passWord = form.get("Password");
        System.out.println("Password :" + passWord);
        driver.findElement(By.name("txtPassword")).sendKeys(passWord); 
        
        driver.findElement(By.id("btnLogin")).submit();
        takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\LoginFailPage.png");
        Thread.sleep(3000);
        
        String errorMessage = form.get("ErrorMessage");        
        String actualErrorMessage = driver.findElement(By.id("spanMessage")).getText();         
        System.out.println("Actual Error Message :" + actualErrorMessage);
        
        Assert.assertTrue(actualErrorMessage.equalsIgnoreCase(errorMessage));    
        }
	}
        
	
	// This scenario is for valid credentials
	
	@When("User enters valid credentials")
	public void user_enters_valid_credentials(io.cucumber.datatable.DataTable dataTable) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		
		System.out.println("Credentials Entered");          
        List<String> signUpForm = dataTable.asList();         
        String userName = signUpForm.get(0);
        String passWord = signUpForm.get(1);          
        driver.findElement(By.name("txtUsername")).sendKeys(userName);
        driver.findElement(By.name("txtPassword")).sendKeys(passWord);       
        driver.findElement(By.id("btnLogin")).submit();
        takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\AfterLoginPage.png");
	
	}

	@And("User should be able to login sucessfully and new page open")
	public void user_should_be_able_to_login_sucessfully_and_new_page_open() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
        String newPageText = driver.findElement(By.id("welcome")).getText();
        System.out.println("newPageText :" + newPageText);
        //Assert.assertEquals(newPageText, driver.findElement(By.id("welcome")).getText());
        Assert.assertTrue(newPageText.contains("Welcome")); 
        
        // This is to test if the dash board page is displayed as default upon login
        WebElement dashboard = driver.findElement(By.id("menu_dashboard_index"));
        takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\DashboardPage.png");
        if (dashboard.isEnabled()) {
         System.out.println("Dashboard page is displayed upon login successfully");

        } else {
         System.out.println("Dashboard page is not enabled - test failed");
        }
	}
	
	
	@Then("User should be able to logout sucessfully")
	public void user_should_be_able_to_logout_sucessfully() throws IOException {
		
		 WebElement Element = driver.findElement(By.id("welcome"));
		 Actions a = new Actions(driver);
		 //move to element and click
		 a.moveToElement(Element).click().perform();
		 
		 //WebElement m=driver.findElement(By.xpath("//*[text()='Logout']"));
		 
		 WebElement m=driver.findElement(By.linkText("Logout"));
		 //move to element and click
		 a.moveToElement(m).click().perform();
		 
         takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\LogoutPage.png");
		 System.out.println("Logout successfully :) ");
		 
		/*//Get list of web elements
		 List<WebElement> options = select.getOptions();
		 System.out.println(options);
		 select.selectByValue("Logout"); */
		 
	}
	
	@And("User navigates to Assign Leave page")
	public void user_navigates_to_Assign_Leave_page() throws IOException {
		
		driver.findElement(By.partialLinkText("Assign Leave")).click();
		//To check if the user is in Assign Leave page
		WebElement assignLeave = driver.findElement(By.id("menu_leave_assignLeave"));
        takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\AssignLeavePage.png");
		if(assignLeave.isEnabled()) {
			System.out.println("Assign Leave page is displayed successfully: " + assignLeave.getText() );
        } else {
         System.out.println("Assign Leave page is not enabled - test failed");
        }
		
	}
	
	@And("Key in all the details to assign the leave")
	public void key_in_all_the_details_to_assign_the_leave(io.cucumber.datatable.DataTable dataTable1) throws IOException {
		
		System.out.println("Enter the Assign details");          
        List<String> signUpForm = dataTable1.asList();         
        String employeeName = signUpForm.get(0);
        String comment = signUpForm.get(1);   
        
        String fromDate = signUpForm.get(2);
        String toDate = signUpForm.get(3); 
        System.out.println(employeeName + " " + comment);
        //driver.findElement(By.id("assignleave_txtEmployee_empName")).sendKeys(employeeName, Keys.ARROW_DOWN, Keys.ENTER);
        driver.findElement(By.id("assignleave_txtEmployee_empName")).sendKeys(employeeName, Keys.ENTER);
        driver.findElement(By.id("assignleave_txtComment")).sendKeys(comment);
        
        Select sel1 = new Select(driver.findElement(By.id("assignleave_txtLeaveType")));
        //sel1.selectByValue("casual");
        sel1.selectByIndex(1);
        
        driver.findElement(By.id("assignleave_txtFromDate")).clear();
        driver.findElement(By.id("assignleave_txtFromDate")).sendKeys(fromDate, Keys.ENTER);
        driver.findElement(By.id("assignleave_txtToDate")).clear();
        driver.findElement(By.id("assignleave_txtToDate")).sendKeys(toDate, Keys.ENTER);

        driver.findElement(By.id("assignBtn")).submit();
        takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\AfterLeaveSubmitPage.png");
        //driver.switchTo().alert().dismiss();
        //driver.switchTo().alert().accept();	
	}
	
	
    
    @And("To check the Leave List")
    public void to_check_the_leave_list(io.cucumber.datatable.DataTable dataTable2) throws IOException {
    	driver.findElement(By.id("menu_leave_viewLeaveList")).click();
    	
        List<String> leaveList = dataTable2.asList();                 
        String fromDate = leaveList.get(0);
        String toDate = leaveList.get(1); 
        String employeeName = leaveList.get(2); 
        String msg = "No Records Found";
        System.out.println(fromDate + " " + toDate);
        
        //driver.findElement(By.id("leaveList_chkSearchFilter_checkboxgroup_allcheck")).click();
        
        driver.findElement(By.id("calFromDate")).clear();
        driver.findElement(By.id("calFromDate")).sendKeys(fromDate, Keys.ENTER);
        driver.findElement(By.id("calToDate")).clear();
        driver.findElement(By.id("calToDate")).sendKeys(toDate, Keys.ENTER);
       
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    	//driver.findElement(By.id("leaveList_chkSearchFilter_checkboxgroup_allcheck")).click();
        driver.findElement(By.cssSelector("label[for='leaveList_chkSearchFilter_checkboxgroup_allcheck']")).click();
     
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	driver.findElement(By.id("leaveList_txtEmployee_empName")).sendKeys(employeeName, Keys.ENTER);
    	
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	driver.findElement(By.id("btnSearch")).click();
    	
    	String ele = driver.findElement(By.xpath("//td[text()='No Records Found']")).getText();
    	takeSnapShot("C:\\Users\\65838\\eclipse-workspace\\com.OrgangeHRM.TestingDataMapsPractice\\Takescreenshot\\LeaveListPage.png");
    	
    	if(ele.equals(msg)) {
    		System.out.println("No records found:" + ele);
    	} else {
    		System.out.println("Records found ");
    	}
    	
    	
    }
    
    
     public void tearDown1(ITestResult result) {
		
		if(ITestResult.FAILURE==result.getStatus()) {
            LoginPage.capturescreen(driver,result.getName(),"FAILURE");
            System.out.println("CaptureTeardown code was executed");
        }
        else {
        	LoginPage.capturescreen(driver,result.getName(),"SUCCESS");
        	 System.out.println("CaptureTeardown code was not executed");
        }
    }

	@After public void tearDown() {
				
		driver.close();
	}
	

}
