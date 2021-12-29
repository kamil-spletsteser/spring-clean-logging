package pl.k4mil.springcleanlogging.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.k4mil.springcleanlogging.Autoconfiguration;
import pl.k4mil.springcleanlogging.Person;
import pl.k4mil.springcleanlogging.TestBean;

@DisplayName("Integration test")
@SpringBootTest(classes = {
		Autoconfiguration.class,
		TestBean.class
})
class AdvisorBeanCreationTest {

	@Autowired
	Autoconfiguration autoconfiguration;

	@Autowired
	TestBean testBean;

	@Test
	void test1() {
		Person p1 = new Person();
		p1.setFirstName("f1");
		p1.setLastName("l1");
		Person p2 = new Person();
		p2.setFirstName("f2");
		p2.setLastName("l2");
		testBean.run(p1, p2);
	}
}
