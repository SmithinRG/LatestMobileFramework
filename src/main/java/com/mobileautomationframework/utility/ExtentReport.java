package com.mobileautomationframework.utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.mobileautomationframework.base.DriverSetup;

public class ExtentReport {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTest logger;
	
	public static  ExtentReports createReport(){
	
	DateFormat currTimeDate = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
	Date date = new Date();
	String dateTD= currTimeDate.format(date);

	File file = new File(System.getProperty("user.dir") + "/Results/"+dateTD +"_TestReport");
		
	if (!file.exists())
	{
		System.out.println("File created " + file);
		file.mkdirs();
	}

	htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Results/"+dateTD +"_TestReport/AutomationExecutionReport.html");
	//	htmlReporter = new ExtentHtmlReporter("TestReport.html");
	htmlReporter.config().setReportName("V2_Share_iOS_Automation Suite Execution Report");
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);
	extent.setSystemInfo("Author", "Smithin R Gopi");
	extent.setSystemInfo("Application", "FrameworkTest");
	extent.setSystemInfo("Build Version", "1.0");
	return extent;
}

	
}
