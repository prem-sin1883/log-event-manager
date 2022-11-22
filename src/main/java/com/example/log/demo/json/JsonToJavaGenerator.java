package com.example.log.demo.json;

import java.io.File;
import java.io.IOException;


import com.example.log.demo.json.Event;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonToJavaGenerator {

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		
		Event request = new Event();
		
		try {
			request = mapper.readValue(new File("D:/myprojects/exercise/event-manager/event.json"), Event.class);
			
			//mapper.readValue
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(request);
	}
}
