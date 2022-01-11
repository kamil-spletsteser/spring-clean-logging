package pl.k4mil.usageexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.k4mil.springcleanlogging.EnableSCL;
import javax.annotation.PostConstruct;

@EnableSCL
@SpringBootApplication
public class UsageExampleApplication {

	@Autowired
	TestBean testBean;

	public static void main(String[] args) {
		SpringApplication.run(UsageExampleApplication.class, args);
	}

	@PostConstruct
	public synchronized void run() {

		System.out.println("\n\nLog with id 1");
		Address a1 = testBean.createAddress("Warsaw", "Mazowiecka");

		System.out.println("\n\nLog with id 2");
		Person p1 = testBean.createPersonWithAddress("John", "Doe", a1);

		System.out.println("\n\nLog with id 3");
		Person p2 = testBean.createPerson("Donald", "Trump");

		System.out.println("\n\n");
	}
}
