package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		
		features ="Feature",
		glue = "com.OrgangeHRM.TestingDataMapsPractice",
		dryRun=false,
		monochrome=true,
		tags="@ValidCredentials",
		plugin= {"pretty","html:target/cucumber-html-report.html","json:target/cucumber-json-report.json", "junit:target/cucumber-xml-report.xml"}
		)
public class LoginTestRunner {
	

}

