package com.forum.fileservice.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("within(com.forum.fileservice.controller.*)")
    public void inControllerLayer(){}

    @Pointcut("bean(*Service)")
    public void inService(){}


}

