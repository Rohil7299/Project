package COM.CONFIGURATION;
import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

public class Driver {
	
	public static WebDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException, ParseException, JSONException {
		COM.SPECS.Login.validateLogin();		

	}

}
