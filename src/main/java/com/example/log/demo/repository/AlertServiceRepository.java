package com.example.log.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.log.demo.entity.Alert;

@Repository
public interface AlertServiceRepository extends CrudRepository<Alert, String>{

	
}
