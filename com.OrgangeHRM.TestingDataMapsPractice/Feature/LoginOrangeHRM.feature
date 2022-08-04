Feature: To test login module

@In-validCredentials
   Scenario: Login with invalid credentials 
   Given User is on HRMLogin page "https://opensource-demo.orangehrmlive.com/"
   When User enters invalid credentials and Login will be unsuccessfull with error message
   |Username    |Password  | ErrorMessage       |
   |Admin1      |admin123!|Invalid credentials  |
   |Admina      |admin123a|Invalid credentials  |
  
 @ValidCredentials 
    Scenario: Login with valid credentials
		Given User is on HRMLogin page "https://opensource-demo.orangehrmlive.com/"
    When User enters valid credentials
    |Admin|admin123|
    And User should be able to login sucessfully and new page open
    #And Takescreenshot "fileWithPath"
    And User navigates to Assign Leave page
    And Key in all the details to assign the leave
    |Orange Test   | This is for maternity leave |2022-03-22|2022-03-25|
    And To check the Leave List
    |2023-01-01|2023-12-31|Orange Test   |
  
  
    
    
    Then User should be able to logout sucessfully
 
 @DashboardPage-Assign-Leave   
 Scenario: To test Assign Leave page
 Given User navigates to Assign Leave page
 When Key in all the details to assign leave
 Then Click to Assign and verify if the leave is assigned
 