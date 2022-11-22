package com.example.log.demo.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.log.demo.aspect.utility.LogEventUtility;
import com.example.log.demo.json.Event;

@Aspect
@Component

//Aspect class for Generating log files inside around advice
public class LogCreator {

	@Autowired
	private LogEventUtility logEventUtility;
	
	@Pointcut("@annotation(com.example.log.demo.aspect.LoggingRequired)")
	private void loggingOperation() {
	}

	@Around("loggingOperation()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		try {
			Log log = getLog(joinPoint);
			long startTime = System.currentTimeMillis();
			
			Event event = new Event();
			event.setId(logEventUtility.generateEventId());
			logEventUtility.populateEvent(event,startTime,"before");
			log.info(event.toString());
			
			Object result = joinPoint.proceed();
			
			long endTime = System.currentTimeMillis();
			logEventUtility.populateEvent(event,endTime,"after");
			log.info(event.toString());
			return result;
		} catch (Throwable e) {
			throw e;
		}
	}

	protected Log getLog(JoinPoint joinPoint) {
		return LogFactory.getLog(joinPoint.getTarget().getClass());
				
	}
}