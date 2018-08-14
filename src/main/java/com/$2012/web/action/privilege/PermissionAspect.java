package com.$2012.web.action.privilege;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
/*
 * 权限切面
 *  本项目处理权限拦截用的是struts2的拦截器PermissionInterceptor，没有选择spring的AOP技术，但两者的思路一样
 */
//@Component @Aspect
public class PermissionAspect {
	@SuppressWarnings("unused")
	@Pointcut("execution(public String com.youcai.web.action..*.*())")
    private void anyMethod() {}
	
	@Around("anyMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }
}
