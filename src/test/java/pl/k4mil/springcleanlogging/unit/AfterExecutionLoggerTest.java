package pl.k4mil.springcleanlogging.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pl.k4mil.springcleanlogging.*;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Unit tests for AfterExecutionLogger class")
class AfterExecutionLoggerTest {

    @RegisterExtension
    LogTrackerStub logTrackerStub = LogTrackerStub
            .create()
            .recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(TestObjectService.class);

    TestObjectService testObjectService = new TestObjectService();

    @DisplayName("Should log specific message")
    @Test
    void test1() throws NoSuchMethodException {
        Method method = TestObjectService.class.getMethod("copyId", Person.class, Person.class);
        String message = testObjectService.randomString();
        AfterExecutionLogger logger = new AfterExecutionLogger(testObjectService.create(message));

        logger.afterReturning(null, method, null, null);

        assertThat(logTrackerStub.contains(message)).isTrue();
        assertThat(logTrackerStub.size()).isEqualTo(1);
    }
}