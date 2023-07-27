package com.forum.userservice.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("within(com.forum.userservice.controller.*)")
    public void inControllerLayer(){}

    @Pointcut("bean(*Service)")
    public void inService(){}

    @Pointcut("execution(* com.forum.userservice.dao.UserDAO.*(..))")
    public void inDAOLayer(){}
}
