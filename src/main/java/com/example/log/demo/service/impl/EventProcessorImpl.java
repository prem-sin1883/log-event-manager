package com.example.log.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.log.demo.aspect.LoggingRequired;
import com.example.log.demo.service.EventProcessor;

@Service
public class EventProcessorImpl implements EventProcessor {

	@Override
	@LoggingRequired
	public void processEvent() {
		//System.out.println("processing event..");
		try {
			//Real event can be processed here.. for demo sleeping for 2ms
			Thread.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Event processing completed");
	}

		
	
	
}
