package pl.k4mil.springcleanlogging;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AdvisorCreator {

    public Advisor createAdvisor(LogSpecs logSpecs) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(createExpression(logSpecs.getReference()));
        Advice advice = getAdvice(logSpecs);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    private String createExpression(String methodReference) {
        return String.format("execution(* %s(..))", methodReference);
    }

    private Advice getAdvice(LogSpecs logSpecs) {
        if(logSpecs.getType().equals(Type.AFTER)) {
            return new AfterExecutionLogger(logSpecs);
        }
        else if(logSpecs.getType().equals(Type.BEFORE)) {
            return new BeforeExecutionLogger(logSpecs);
        }
        throw new IllegalArgumentException(String.format("Log type '%s' not implemented", logSpecs.getType().name()));
    }
}
