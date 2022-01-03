package pl.k4mil.springcleanlogging.unit;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pl.k4mil.springcleanlogging.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Unit tests for BeforeExecutionLogger class")
class BeforeExecutionLoggerTest {

    @RegisterExtension
    LogTrackerStub logTrackerStub = LogTrackerStub
            .create()
            .recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(TestObjectService.class);

    TestObjectService testObjectService = new TestObjectService();

    @DisplayName("Should log specific message")
    @Test
    void test1() throws Throwable {
        String message = testObjectService.randomString();
        Person person = testObjectService.create();
        BeforeExecutionLogger logger = new BeforeExecutionLogger(testObjectService.create(message));
        MethodInvocation methodInvocation = testObjectService.createMethodInvocation(person);

        logger.invoke(methodInvocation);

        assertThat(logTrackerStub.contains(message)).isTrue();
        assertThat(logTrackerStub.size()).isEqualTo(1);
    }
}