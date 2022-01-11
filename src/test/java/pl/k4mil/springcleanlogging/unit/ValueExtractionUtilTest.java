package pl.k4mil.springcleanlogging.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.k4mil.springcleanlogging.TestObjectService;
import pl.k4mil.springcleanlogging.ValueExtractionUtil;
import pl.k4mil.springcleanlogging.Person;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Unit tests for ValueExtractionUtil class")
class ValueExtractionUtilTest {

    ValueExtractionUtil valueExtractionUtil = new ValueExtractionUtil();
    TestObjectService testObjectService = new TestObjectService();
    Person p1, p2;

    @BeforeEach
    void setUp() {
        p1 = testObjectService.create();
        p2 = testObjectService.create();
    }

    @DisplayName("Testing case with valid paths and object passed, should return 4 specific values")
    @Test
    void test1() throws NoSuchFieldException, IllegalAccessException {
        List<String> paths = List.of("args[0].firstName", "args[0].address.city", "args[1].firstName", "retVal.id");
        List<String> extractedValues = valueExtractionUtil.extractValues(paths, new Object[]{p1, p2}, p1);

        assertThat(extractedValues.size()).isEqualTo(4);
        assertThat(extractedValues.get(0)).isEqualTo(p1.getFirstName());
        assertThat(extractedValues.get(1)).isEqualTo(p1.getAddress().getCity());
        assertThat(extractedValues.get(2)).isEqualTo(p2.getFirstName());
        assertThat(extractedValues.get(3)).isEqualTo(p1.getId().toString());
    }

    @DisplayName("Testing case with wrong syntax paths, should return empty list")
    @Test
    void test2() throws NoSuchFieldException, IllegalAccessException {
        List<String> paths = List.of("retVaa.id", "asd", "arg[0].firstName", "args{0}.firstName");

        List<String> extractedValues = valueExtractionUtil.extractValues(paths, new Object[]{p1, p2}, p1);

        assertThat(extractedValues).isEmpty();
    }

    @DisplayName("Testing case with wrong property name, should throw exception")
    @Test
    void test3() {
        List<String> paths = List.of("args[0].nonExistingProp");

        assertThatExceptionOfType(NoSuchFieldException.class)
                .isThrownBy(() -> valueExtractionUtil.extractValues(paths, new Object[]{p1, p2}, p1));
    }

    @DisplayName("Testing case with null args and return value, should return empty list")
    @Test
    void test4() throws NoSuchFieldException, IllegalAccessException {
        List<String> paths = List.of("args[0].firstName", "retVaa.id");

        List<String> extractedValues = valueExtractionUtil.extractValues(paths, null, null);

        assertThat(extractedValues).isEmpty();
    }
}