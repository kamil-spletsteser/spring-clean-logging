package pl.k4mil.springcleanlogging;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.BeforeAdvice;

public class BeforeExecutionLogger extends AbstractExecutionLogger implements BeforeAdvice, MethodInterceptor {

    private LogSpecs logSpecs;

    public BeforeExecutionLogger(LogSpecs logSpecs) {
        this.logSpecs = logSpecs;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log(logSpecs, invocation.getMethod(), invocation.getArguments());
        return invocation.proceed();
    }
}
