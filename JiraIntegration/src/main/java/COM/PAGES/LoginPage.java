package COM.PAGES;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage {
	
	public static WebElement uname,pass,btnlogin,wel;
	
	//For User name
	public static WebElement GetUname() {
		uname=COM.CONFIGURATION.Driver.driver.findElement(By.name("txtUsername"));
		return uname;
		}
	
	//For Password
	public static WebElement GetPass() {
		pass=COM.CONFIGURATION.Driver.driver.findElement(By.name("txtPassword"));
		return pass;
	}
	
	//For Login Button
	public static WebElement GetButton() {
		btnlogin=COM.CONFIGURATION.Driver.driver.findElement(By.className("button"));
		return btnlogin;
	}
	
	//After Login Expecting a WELCOME text
	public static WebElement GetWelcome() {
		wel=COM.CONFIGURATION.Driver.driver.findElement(By.partialLinkText("Welcome"));
		return wel;
	}
	

}
