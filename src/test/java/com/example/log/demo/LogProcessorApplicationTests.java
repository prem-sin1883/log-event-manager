package com.example.log.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.log.demo.entity.Alert;
import com.example.log.demo.repository.AlertServiceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class LogProcessorApplicationTests {
    @Autowired
    private AlertServiceRepository repository;


	@Test
    public void findCustimorTest() {
    	boolean expectedAlert = false;
        Alert alert =repository.findById("ZWC6IVKOKC29").get();
        boolean actualAlert = alert.getAlert();
       // System.out.println(actualAlert);
        assertThat(actualAlert).isEqualTo(expectedAlert);
    }
	
	@Test
    public void findCustimorNegativeTest() {
    	boolean expectedAlert = true;
        Alert alert =repository.findById("QWGONTL8JNWD").get();
        boolean actualAlert = alert.getAlert();
       // System.out.println(actualAlert);
        assertThat(actualAlert).isEqualTo(expectedAlert);
    }
   
}