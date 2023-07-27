package com.forum.authenticationservice.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("within(com.forum.authenticationservice.controller.*)")
    public void inControllerLayer(){}

    @Pointcut("bean(*Service)")
    public void inService(){}

//    @Pointcut("execution(* com.beaconfire.springaop.AOPDemo.dao.DemoDAO.*Demo())")
//    public void inDAOLayer(){}

}
