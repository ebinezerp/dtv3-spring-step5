package com.stackroute.activitystream.aspect;

import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	
	private Logger logger;
	
	public LoggingAspect() {
		// TODO Auto-generated constructor stub
		logger=LoggerFactory.getLogger(LoggingAspect.class);
	}

	/*Write loggers for each of the methods of service, 
	any particular method will have all the four aspectJ annotation
	(@Before, @After, @AfterReturning, @AfterThrowing).
    */
	
	@Before("execution(public boolean save(*))")
	public void save()
	{
		logger.info("Object is saved");
	}
    
}
