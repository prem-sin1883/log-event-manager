package com.example.log.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.log.demo.aspect.utility.ApplicationDataProvider;
import com.example.log.demo.entity.Alert;
import com.example.log.demo.repository.AlertServiceRepository;
import com.example.log.demo.service.AlertSerrvice;

@Service
public class AlertServiceImpl implements AlertSerrvice {

	@Autowired
	private AlertServiceRepository repository;

	@Autowired
	private ApplicationDataProvider applicationDataProvider;

	@Override
	@Transactional
	public void saveAlert(List<Alert> alerts) {
		List<Alert> temList = new ArrayList<>();
		//frequency of events to be processed at a time
		int dbFrequency = applicationDataProvider.getEventFrequency();
		while (alerts.size() > dbFrequency) {
			temList = alerts.stream().limit(dbFrequency).collect(Collectors.toList());
			System.out.println("Saving first 1000 event: " + temList.size());
			repository.saveAll(temList);
			alerts = alerts.stream().skip(dbFrequency).collect(Collectors.toList());
			System.out.println("Remaining events: " + alerts.size());
		}
		// to Save remaning
		System.out.println("Remaining events after while loop: " + alerts.size());
		repository.saveAll(alerts);
	}

}
