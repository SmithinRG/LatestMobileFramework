package com.mobileautomationframework.base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServer {
	AppiumServiceBuilder builder;
	 AppiumDriverLocalService service;

	public void startServer() {
		//Build the Appium service

	    builder = new AppiumServiceBuilder();
	    builder.withIPAddress("127.0.0.1");
	    builder.usingPort(4723);
	    builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
	    builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");

	    //Start the server with the builder

	    service = AppiumDriverLocalService.buildService(builder);
	    service.start();
	}
	public void stopServer() {
	    service.stop();
	}
}
