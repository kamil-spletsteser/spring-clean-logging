package pl.k4mil.springcleanlogging.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import pl.k4mil.springcleanlogging.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Unit tests for AdvisorCreator class")
class AdvisorCreatorTest {

    final String methodReference = "pl.k4mil.springcleanlogging.TestObjectService.copyId";
    final String pointcutExpression = "execution(* pl.k4mil.springcleanlogging.TestObjectService.copyId(..))";
    AdvisorCreator advisorCreator = new AdvisorCreator();
    TestObjectService testObjectService = new TestObjectService();

    @DisplayName("Checking properties of created instance, advice type should be BeforeExecutionLogger")
    @Test
    void test1() {
        LogSpecs logSpecs = testObjectService.create("");
        logSpecs.setReference(methodReference);
        logSpecs.setType(Type.BEFORE);

        DefaultPointcutAdvisor advisor = (DefaultPointcutAdvisor) advisorCreator.createAdvisor(logSpecs);
        assertThat(advisor.getAdvice()).isInstanceOf(BeforeExecutionLogger.class);
        AspectJExpressionPointcut pointcut = (AspectJExpressionPointcut) advisor.getPointcut();
        assertThat(pointcut.getExpression()).isEqualTo(pointcutExpression);
    }

    @DisplayName("Checking properties of created instance, advice type should be AfterExecutionLogger")
    @Test
    void test2() {
        LogSpecs logSpecs = testObjectService.create("");
        logSpecs.setReference(methodReference);
        logSpecs.setType(Type.AFTER);

        DefaultPointcutAdvisor advisor = (DefaultPointcutAdvisor) advisorCreator.createAdvisor(logSpecs);
        assertThat(advisor.getAdvice()).isInstanceOf(AfterExecutionLogger.class);
        AspectJExpressionPointcut pointcut = (AspectJExpressionPointcut) advisor.getPointcut();
        assertThat(pointcut.getExpression()).isEqualTo(pointcutExpression);
    }
}