package pl.k4mil.springcleanlogging.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pl.k4mil.springcleanlogging.*;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for AfterExecutionLogger class")
class AfterExecutionLoggerTest {

    @RegisterExtension
    LogTrackerStub logTrackerStub = LogTrackerStub
            .create()
            .recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(TestBean.class);

    AfterExecutionLogger logger;
    Person person;
    Address address;
    String logMessage = "message text";

    @BeforeEach
    void setUp() {
        logger = new AfterExecutionLogger(create());
        person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        address = new Address();
        address.setCity("Warsaw");
        address.setStreet("Sunset Street");
        person.setAddress(address);
    }

    @DisplayName("Should log specific message")
    @Test
    void test1() throws NoSuchMethodException {
        Method method = TestBean.class.getMethod("run", Person.class, Person.class);

        logger.afterReturning(person, method, new Person[]{person}, null);

        assertThat(logTrackerStub.contains(logMessage)).isTrue();
        assertThat(logTrackerStub.size()).isEqualTo(1);
    }

    private LogSpecs create() {
        LogSpecs logSpecs = new LogSpecs();
        logSpecs.setLevel(Level.INFO);
        logSpecs.setType(Type.BEFORE);
        logSpecs.setMessage(logMessage);
        logSpecs.setPaths(List.of());
        return logSpecs;
    }
}