package pl.k4mil.springcleanlogging.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import pl.k4mil.springcleanlogging.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DisplayName("Integration test for autoconfiguration")
@SpringBootTest(classes = {
		Autoconfiguration.class,
		TestObjectService.class,
		ApplicationContext.class
})
class AutoconfigurationTest {

	@RegisterExtension
	LogTrackerStub logTrackerStub = LogTrackerStub
			.create()
			.recordForLevel(LogTracker.LogLevel.ERROR)
			.recordForType(TestObjectService.class);

	@Autowired
	Autoconfiguration autoconfiguration;

	@Autowired
	TestObjectService testObjectService;

	@Autowired
	ApplicationContext context;

	@DisplayName("Checking number of registered logger beans")
	@Test
	void test1() {
		long registeredAdvisorsNumber = Arrays
				.stream(context.getBeanDefinitionNames())
				.filter(b -> b.contains("loggingAdvisor-")).count();

		assertThat(registeredAdvisorsNumber).isEqualTo(2);
	}

	@DisplayName("Checking properties of created advices (loggers)")
	@Test
	void test2() {
		Set<String> advisorBeanNames = Arrays
				.stream(context.getBeanDefinitionNames())
				.filter(b -> b.contains("loggingAdvisor-"))
				.collect(Collectors.toSet());

		advisorBeanNames.forEach(beanName -> {
			Advisor advisor = context.getBean(beanName, Advisor.class);
			AspectJExpressionPointcut pointcut = (AspectJExpressionPointcut) ((DefaultPointcutAdvisor) advisor).getPointcut();

			assertThat(pointcut.getExpression())
					.as("Checking method reference")
					.isEqualTo("execution(* pl.k4mil.springeasylogging.TestObjectService.copyId(..))");

			String id = beanName.split("-")[1];

			if(id.equals("1")) {
				assertThat(advisor.getAdvice()).isInstanceOf(AfterExecutionLogger.class);
			}
			else if(id.equals("2")) {
				assertThat(advisor.getAdvice()).isInstanceOf(BeforeExecutionLogger.class);
			}
			else {
				fail("Advisor bean retrieved from context has incorrect type");
			}
		});
	}
}
