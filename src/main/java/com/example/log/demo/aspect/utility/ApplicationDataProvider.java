package com.example.log.demo.aspect.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDataProvider {
	//Created to get application level data from application properties if required
	
	@Value("${logging.type}")
	private String logType;

	@Value("${event.db.frequency}")
	private int eventFrequency;
	
	@Value("${app.log.dir}")
	private String appLogDir;
	
	@Value("${app.log.filename}")
	private String logFileName;
	
	@Value("${app.eventnumber}")
	private int eventNumber;
	
	
	public int getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(int eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public int getEventFrequency() {
		return eventFrequency;
	}

	public void setEventFrequency(int eventFrequency) {
		this.eventFrequency = eventFrequency;
	}

	public String getAppLogDir() {
		return appLogDir;
	}

	public void setAppLogDir(String appLogDir) {
		this.appLogDir = appLogDir;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}
	
	
	
	

}
