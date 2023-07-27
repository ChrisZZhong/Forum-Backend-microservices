package com.forum.userservice.AOP;

import com.forum.userservice.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class LoggingAspect {


//    @Before("com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inControllerLayer()")
//    public void logStartTime(){
//        logger.info("From LoggingAspect.logStartTime in controller: " + System.currentTimeMillis()); // advice
//    }
//
//    @After("com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inService()")
//    public void logEndTime(JoinPoint joinPoint){
//        logger.info("From LoggingAspect.logEndTime in service: " + System.currentTimeMillis()  + ": " + joinPoint.getSignature());
//    }
//
//    @AfterReturning(value = "com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inDAOLayer()", returning = "res")
//    public void logReturnObject(JoinPoint joinPoint, Object res){
//        logger.info("From LoggingAspect.logReturnObject in DAO: " + res + ": " + joinPoint.getSignature());
//    }
//
//    @AfterThrowing(value = "com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inControllerLayer()", throwing = "ex")
//    public void logThrownException(JoinPoint joinPoint, Throwable ex){
//        logger.error("From LoggingAspect.logThrownException in controller: " + ex.getMessage() + ": " + joinPoint.getSignature());
//    }
private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    @Before("com.forum.userservice.AOP.PointCuts.inControllerLayer()")
    public void logStartTime(){
        logger.info("From LoggingAspect.logStartTime in controller: " + System.currentTimeMillis()); // advice
    }
    @After("com.forum.userservice.AOP.PointCuts.inControllerLayer()")
    public void logEndTime(){
        logger.info("From LoggingAspect.logStartTime in controller: " + System.currentTimeMillis()); // advice
    }
}
