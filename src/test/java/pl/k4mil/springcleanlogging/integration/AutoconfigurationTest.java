package pl.k4mil.springcleanlogging.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import pl.k4mil.springcleanlogging.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	final String logTemplate = "Firstname of first person is %s and lastname of second person id %s, id of returned person is %s";

	@DisplayName("Checking number of registered logger beans")
	@Test
	void test1() {
		long registeredAdvisorsNumber = Arrays
				.stream(context.getBeanDefinitionNames())
				.filter(b -> b.contains("loggingAdvisor-")).count();

		assertThat(registeredAdvisorsNumber).isEqualTo(2);
	}

//	@DisplayName("Checking properties of created advisors")
//	@Test
//	void test2() {
//		Set<String> advisorBeanNames = Arrays
//				.stream(context.getBeanDefinitionNames())
//				.filter(b -> b.contains("loggingAdvisor-"))
//				.collect(Collectors.toSet());
//
//		List<Advisor> advisors = new ArrayList<>();
//		advisorBeanNames.forEach(beanName -> {
//			Advisor advisor = context.getBean(beanName, Advisor.class);
//
//			if(advisor instanceof AfterExecutionLogger) {
//
//			}
//			else if(advisor instanceof BeforeExecutionLogger) {
//
//			}
//			else {
//				fail("")
//			}
//		});
//	}

//	Person p1 = testObjectService.create();
//	Person p2 = testObjectService.create();
//		testObjectService.copyId(p1, p2);
//
//	String message = String.format(logTemplate, p1.getFirstName(), p2.getFirstName(), p1.getId());
//
//	//		assertThat(logTrackerStub.contains(message))
////				.as("Checking if specific message was logged").isTrue();
//	assertThat(logTrackerStub.size())
//			.as("Checking number of logged messages").isEqualTo(1);
}
