package com.bjpowernode.finance.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ComponentScan  //组件自动扫描
@EnableAspectJAutoProxy //spring自动切换JDK动态代理和CGLIB
public class LogAspect {
    /**
     * 自定义日志
     */
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 在方法执行前进行切面 controller层下的所有方法
     */
    @Pointcut("execution(* com.bjpowernode.finance.*.*(..))")
    public void log() {
    }

    @AfterReturning(value = "log()", returning = "returnValue")
    public void afterReturning(JoinPoint point, Object returnValue) {
        logger.info("-------------------------请求开始-------------------------");
        logger.info("请求类方法:" + point.getSignature());
        String[] paramNames = ((CodeSignature) point.getSignature()).getParameterNames();
        Object[] paramValues = point.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            logger.info("请求类方法参数:" + paramNames[i] + ":" + paramValues[i]);
        }
        if(returnValue!=null) {
            logger.info("返回结果:" + returnValue.toString());
        }
        logger.info("-------------------------请求结束-------------------------");
    }

}
