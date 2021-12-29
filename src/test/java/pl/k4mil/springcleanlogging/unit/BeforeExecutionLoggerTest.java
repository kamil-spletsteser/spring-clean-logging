package pl.k4mil.springcleanlogging.unit;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pl.k4mil.springcleanlogging.*;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for BeforeExecutionLogger class")
class BeforeExecutionLoggerTest {

    @RegisterExtension
    LogTrackerStub logTrackerStub = LogTrackerStub
            .create()
            .recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(TestBean.class);

    BeforeExecutionLogger logger;
    Person person;
    Address address;
    String logMessage = "message text";

    @BeforeEach
    void setUp() {
        logger = new BeforeExecutionLogger(create());
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
    void test1() throws Throwable {

        MethodInvocation methodInvocation = createMethodInvocation();

        logger.invoke(methodInvocation);

        assertThat(logTrackerStub.contains(logMessage)).isTrue();
        assertThat(logTrackerStub.size()).isEqualTo(1);
    }

    private MethodInvocation createMethodInvocation() {
        return new MethodInvocation() {
            @Override
            public Method getMethod() {
                try {
                    return TestBean.class.getMethod("run", Person.class, Person.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Object[] getArguments() {
                return new Object[]{person};
            }

            @Override
            public Object proceed() throws Throwable {
                return null;
            }

            @Override
            public Object getThis() {
                return null;
            }

            @Override
            public AccessibleObject getStaticPart() {
                return null;
            }
        };
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