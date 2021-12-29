package pl.k4mil.springcleanlogging;

import org.springframework.aop.AfterReturningAdvice;
import java.lang.reflect.Method;

public class AfterExecutionLogger extends AbstractExecutionLogger implements AfterReturningAdvice {

    private LogSpecs logSpecs;

    public AfterExecutionLogger(LogSpecs logSpecs) {
        this.logSpecs = logSpecs;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
        log(logSpecs, method, args, returnValue);
    }
}
