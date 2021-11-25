package COM.SPECS;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterTest;

public class LoginM {
	
  @Test
   public static void validateLogin() throws IOException, InterruptedException, ParseException, JSONException {
			
			COM.COMMAN.Comman.OpenBrowser();
			COM.CONFIGURATION.Driver.driver.get(COM.CONFIGURATION.Config.URL);
			String[] data =COM.COMMAN.Comman.readCSV(COM.CONFIGURATION.Config.File_Name);
			COM.PAGES.LoginPage.GetUname().sendKeys(data[0]);
			COM.PAGES.LoginPage.GetPass().sendKeys(data[1]);
			Thread.sleep(5000);
			COM.PAGES.LoginPage.GetButton().click();
			Thread.sleep(5000);
			String actTxt = COM.PAGES.LoginPage.GetWelcome().getText();
			if(actTxt.contentEquals(COM.CONFIGURATION.Config.Exp_Txt)) {
				System.out.println("Assertion Passed");
			}else {
				//adding Screenshot for Failed Test
				File src = ((TakesScreenshot)COM.CONFIGURATION.Driver.driver).getScreenshotAs(OutputType.FILE);
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy-HH-mm-ss");
				Date date = new Date();
				String actualDate = format.format(date);
				 
				String screenshotPath = System.getProperty("user.dir")+"/Reports/Screenshots"+actualDate+".jpeg";
				File dest = new File (screenshotPath);
				
				FileUtils.copyFile(src, dest);
				
				//JIRA Defect Creation part
				String automaticJIRAcreation = COM.CONFIGURATION.Config.automatic_ISSUE_Creation_In_Jira;
				if(automaticJIRAcreation.trim().equalsIgnoreCase("ON")) {
					String issueS= "Automation Test Failed";
					String issueD= "Test Description To Be Passed";
					String issueNumber = null;
					
					issueNumber = COM.COMMAN.Comman.createJiraIssue("JIR", issueS, issueD, "4", "JIR", "6108cb0e8ad5b60070e3dac3");
					COM.COMMAN.Comman.addAttachmentToJiraIssue(issueNumber, screenshotPath);
					
				}else {
					System.out.println("Issue Not Created in JIRA");
				}
				Thread.sleep(5000);
			}
	  }
			
 
  @BeforeTest
  public void beforeTest() {
	  COM.COMMAN.Comman.OpenBrowser();
  }

  @AfterTest
  public void afterTest() {
	  COM.CONFIGURATION.Driver.driver.close();
  }

}
