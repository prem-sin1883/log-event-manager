package com.example.log.demo.aspect.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.log.demo.json.Event;

@Component
public class LogEventUtility {

	@Autowired
	ApplicationDataProvider dataProvider;

	//To populate Events
	public Event populateEvent(Event event, long startTime, String flag) {
		String host = StringUtils.EMPTY;
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		event.setHost(host);
		String status = StringUtils.EMPTY;
		if ("before".equalsIgnoreCase(flag))
			status = Status.STARTED.getValue();
		else
			status = Status.FINISHED.getValue();

		event.setState(status);
		event.setTimestamp(startTime);

		event.setType(dataProvider.getLogType());
		return event;

	}

	//To generate random Event ID for each event
	public String generateEventId() {

		return RandomStringUtils.randomAlphanumeric(12).toUpperCase();

	}
}

enum Status {
	STARTED("STARTED"), FINISHED("FINISHED");

	private final String value;

	Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

enum LogType {
	APPLICATION("application"), SERVER("server"), NONE("none");

	private final String value;

	LogType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static LogType fromValue(String text) {
		return Arrays.stream(values()).filter(v -> v.getValue().equals(text)).findFirst().orElse(null);
	}
}
