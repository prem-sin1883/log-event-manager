package com.example.log.demo.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

public class LogException {

	public void exceptionLog(JoinPoint joinPoint,Throwable e){
		if(e instanceof ArithmeticException)
		{
			logArithmeticException(joinPoint, e);
			throw (ArithmeticException) e;
		}
		else if(e instanceof NullPointerException)
		{
			logNullPointerException(joinPoint, e);
			throw (NullPointerException) e;
		}
	}

	private void logArithmeticException(JoinPoint joinPoint, Throwable ex) {
		Log log = getLog(joinPoint);
		log.error("Exception Getting----"+ex.getMessage(), ex);
        logReturn(joinPoint, log);
    }    
	
	private void logNullPointerException(JoinPoint joinPoint, Throwable ex) {
		Log log = getLog(joinPoint);
		log.error("Exception Getting----"+ex.getMessage(), ex);
        logReturn(joinPoint, log);
    }    
	private Log logReturn(JoinPoint joinPoint, Log log) {
		return log;
	}
	
	protected Log getLog(JoinPoint joinPoint) {
        return LogFactory.getLog(joinPoint.getTarget().getClass());
    }
}
