package pl.k4mil.springcleanlogging.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import pl.k4mil.springcleanlogging.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Unit tests for AdvisorCreator class")
class AdvisorCreatorTest {

    final String methodReference = "pl.k4mil.springcleanlogging.TestBean.run";
    final String pointcutExpression = "execution(* pl.k4mil.springcleanlogging.TestBean.run(..))";
    AdvisorCreator advisorCreator = new AdvisorCreator();
    DefaultPointcutAdvisor advisor;
    AspectJExpressionPointcut pointcut;

    @BeforeEach
    void setUp() {
        advisor = (DefaultPointcutAdvisor) advisorCreator.createAdvisor(create());
        pointcut = (AspectJExpressionPointcut) advisor.getPointcut();
    }

    @DisplayName("Checking advice type")
    @Test
    void test1() {
        assertThat(pointcut.getExpression()).isEqualTo(pointcutExpression);
    }

    @DisplayName("Checking pointcut expression")
    @Test
    void test2() {
        assertThat(pointcut.getExpression()).isEqualTo(pointcutExpression);
    }

    private LogSpecs create() {
        LogSpecs logSpecs = new LogSpecs();
        logSpecs.setLevel(Level.INFO);
        logSpecs.setType(Type.BEFORE);
        logSpecs.setMessage("message");
        logSpecs.setReference(methodReference);
        return logSpecs;
    }
}