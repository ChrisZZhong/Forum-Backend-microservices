package com.forum.fileservice.aop;

import com.forum.fileservice.dto.response.URLResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {


    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("com.forum.fileservice.aop.PointCuts.inControllerLayer()")
    public ResponseEntity<URLResponse> logStartAndEndTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // before
        logger.info(String.valueOf(proceedingJoinPoint.getSignature()));
        long startTime = System.currentTimeMillis();

        //Invoke the actual object
        ResponseEntity<URLResponse> responseEntity = (ResponseEntity<URLResponse>) proceedingJoinPoint.proceed();

        // after
        long executingTime = System.currentTimeMillis()-startTime;
        logger.info(proceedingJoinPoint.getSignature()+" processing time: " + (executingTime / 1000));
        return responseEntity;
    }

    @Before("com.forum.fileservice.aop.PointCuts.inService()")
    public void logStartTime(){
        logger.info("From LoggingAspect.logStartTime in service: " + System.currentTimeMillis()); // advice
    }
    @After("com.forum.fileservice.aop.PointCuts.inService()")
    public void logEndTime(){
        logger.info("From LoggingAspect.logStartTime in service: " + System.currentTimeMillis()); // advice
    }


}
