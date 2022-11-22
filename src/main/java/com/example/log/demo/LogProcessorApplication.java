package com.example.log.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.log.demo.aspect.utility.ApplicationDataProvider;
import com.example.log.demo.entity.Alert;
import com.example.log.demo.json.Event;
import com.example.log.demo.service.AlertSerrvice;
import com.example.log.demo.service.impl.EventProcessorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class LogProcessorApplication implements CommandLineRunner {

	@Autowired
	private AlertSerrvice alertSerrvice;
	
	@Autowired
	private EventProcessorImpl eventProcessorImpl;
	
	@Autowired
	private ApplicationDataProvider applicationDataProvider;
	
	public static void main(String[] args) {
		SpringApplication.run(LogProcessorApplication.class, args);

	}
	@Override
	public void run(String... args) throws Exception {
		try {
			//Proccessing event in threads
			ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
			for(int i=0;i<applicationDataProvider.getEventNumber();i++){
				executorService.submit(new Callable<Void>(){
					@Override
					public Void call() throws Exception {
						eventProcessorImpl.processEvent();
						return null;
					}
				});
			}
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.DAYS);
			
			//To process Alert message for above event logs generated
			processAlerts();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void processAlerts() throws IOException {
		Path currentWorkingDir = Paths.get(StringUtils.EMPTY).toAbsolutePath();
		String logFilePath = currentWorkingDir.normalize().toString() + applicationDataProvider.getAppLogDir() +applicationDataProvider.getLogFileName();
		
		List<Event> eventList = new ArrayList<Event>();
		List<Alert> alertList = new ArrayList<Alert>();
		
		//Parsing log file and storing in list of events
		try (LineIterator li = FileUtils.lineIterator(new File(logFilePath))){
			 while(li.hasNext()){
				 Event event= new ObjectMapper().readValue(li.next(), Event.class);
				 eventList.add(event);
			 }
		 }
		//collecting events groupBy their ID's. That means, Each map should have 2 event for the status STARTED and FINISHED
		Map<String, List<Event>> evantMap = eventList.stream().collect(Collectors.groupingBy(Event::getId));

		
		//Itrating through Each Map to populate the Alert and manipulate duration for each event
		evantMap.entrySet().forEach(entry -> {
			String key = entry.getKey();
			List<Event> listEvent = entry.getValue();
			long startTime = listEvent.stream().filter(event -> "STARTED".equalsIgnoreCase(event.getState())).map(ev -> ev.getTimestamp()).findFirst().get();
			long endTime = listEvent.stream().filter(event -> "FINISHED".equalsIgnoreCase(event.getState())).map(ev -> ev.getTimestamp()).findFirst().get();
			Alert alert = new Alert();
			alert.setId(key);
			int duration = Math.toIntExact(endTime-startTime);
			alert.setDuration(duration);
			alert.setHost(listEvent.get(0).getHost());
			alert.setType(listEvent.get(0).getType());
			
			if(duration>4)
				alert.setAlert(true);
			else
				alert.setAlert(false);
			
			alertList.add(alert);
			
		});
		
		//Saving all Alerts to Database file
		alertSerrvice.saveAlert(alertList);
		
		
	}

	
}
