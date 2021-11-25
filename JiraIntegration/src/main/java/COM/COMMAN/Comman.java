package COM.COMMAN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

public class Comman {
	
	public static void OpenBrowser(){
		//Driver Launching
		if(COM.CONFIGURATION.Config.Browser_Name.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
			COM.CONFIGURATION.Driver.driver = new ChromeDriver();

		}
	}
	//For Reading the Login CSV File
	public static String[] readCSV(String fname) throws IOException {
		File f=new File(fname);
		FileReader fr=new FileReader(f.getAbsoluteFile());
		BufferedReader br=new BufferedReader(fr);
		String line=br.readLine();
		String [] data=line.split(",");
		return data;
		
	}
	//create Jira issue bug
	public static String createJiraIssue(String ProjectName, String issueSummary, String issueDescription, String priority, String label, String assignee) throws ClientProtocolException, IOException, ParseException {
	String issueID=null; //To store Issue ID
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		String url=COM.CONFIGURATION.Config.Jira_URL+"/rest/api/3/issue";
		HttpPost postRequest= new HttpPost(url);
		postRequest.addHeader("content-type","application/json");
		
		String encoding= Base64.getEncoder().encodeToString((COM.CONFIGURATION.Config.Jira_Username+":"+COM.CONFIGURATION.Config.Jira_Secret_Key).getBytes());
		postRequest.setHeader("Authorization","Basic" +encoding);
		
		StringEntity params=new StringEntity(createPayLoadForCreateJiraIssue());
		postRequest.setEntity(params);
		HttpResponse response=httpClient.execute(postRequest);
		
		//Converting HttpResponse to String
		String jsonString = EntityUtils.toString(response.getEntity());
		
		//convert String to JSON
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(jsonString);
		
		//extract issuekey from JSON
		issueID =(String) json.get("key");
		
		return issueID;
		
	}
	private static String createPayLoadForCreateJiraIssue() {
		
		return "{r\n" 
		        +"  \"fields\": {r\n" 
		        +"     \"projects\":\r\n" 
		        +"   {\r\n" 
		        +"       \"key\": \"JIR\"\r\n" 
		        +"   },r\n" 
		        +"   \"summary\": \"REST API Defect Summary\",\r\n"   
		        +"   \"description\" {\r\n" 
		        +"      \"type\":\"doc\",\r\n" 
		        +"      \"version\" 1,\r\n" 
		        +"      \"content\": [\r\n" 
		        +"         {\r\n" 
		        +"             \"type\": \"paragraph\",\r\n" 
		        +"             \"content\": [\r\n" 
		        +"                        {\r\n" 
		        +"                            \"text\": \"Bug Description Here\",\r\n" 
		        +"                            \"type\": \"text\"\r\n" 
		        +"                        }\r\n"  
		        +"                    ]\r\n" 
		        +"               }\r\n" 
		        +"           ]\r\n" 
		        +"       },  \r\n" 
		        +"      \"issuetype\": {\r\n" 
		        +"        \"name\": \"Bug\"\r\n" 
		        +"      },r\n" 
		        +"      \"labels\": [\r\n" 
		        +"         \"bugfix\",\r\n" 
		        +"          \"AutomationDefect\"\r\n" 
		        +"       ],\r\n" 
		        +"       \"assignee\": [\r\n" 
		        +"          \"id\": \"6108cb0e8ad5b60070e3dac3\"\r\n" 
		        +"       }\r\n" 
		        +"     ]\r\n" 
		        +"  }"; 
		       }
		        
	//Add Attachment To Jira
	public static void addAttachmentToJiraIssue(String issueId, String filePath) throws ClientProtocolException, IOException {
		
		String pathname= filePath;
		File fileUpload =new File(pathname);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		String url= COM.CONFIGURATION.Config.Jira_URL+"/rest/api/3/issue/"+issueId+"/attachments";
		HttpPost postRequest= new HttpPost(url);
		
		String encoding = Base64.getEncoder().encodeToString((COM.CONFIGURATION.Config.Jira_Username+":"+COM.CONFIGURATION.Config.Jira_Secret_Key).getBytes());
		postRequest.setHeader("Authorization", "Basic" +encoding);
		postRequest.setHeader("X-Atlassian-Token","nocheck");
		
	    MultipartEntityBuilder entity=MultipartEntityBuilder.create();
	    entity.addPart("file",new FileBody(fileUpload));
	    postRequest.setEntity(entity.build());
	    HttpResponse response = httpClient.execute(postRequest);
	    System.out.println(response.getStatusLine());
	    
	    if(response.getStatusLine().toString().contains("200 OK"))
	    {
	    	System.out.println("Attachment Uploaded");
	    }
	    else{
	    	 System.out.println("Attachment Not Uploaded");
	    }
	}
	       
		        
	}


