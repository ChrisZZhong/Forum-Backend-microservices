package com.forum.historyservice.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("com.forum.historyservice.aop.PointCuts.inControllerLayer()")
    public void logStartTime(){
        logger.info("From LoggingAspect.logStartTime in controller: " + System.currentTimeMillis()); // advice
    }
    @After("com.forum.historyservice.aop.PointCuts.inControllerLayer()")
    public void logEndTime(){
        logger.info("From LoggingAspect.logStartTime in controller: " + System.currentTimeMillis()); // advice
    }
}
