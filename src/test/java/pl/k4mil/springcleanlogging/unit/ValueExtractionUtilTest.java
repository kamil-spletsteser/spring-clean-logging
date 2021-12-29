package pl.k4mil.springcleanlogging.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.k4mil.springcleanlogging.ValueExtractionUtil;
import pl.k4mil.springcleanlogging.Address;
import pl.k4mil.springcleanlogging.Person;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Unit tests for ValueExtractionUtil class")
class ValueExtractionUtilTest {

    ValueExtractionUtil valueExtractionUtil = new ValueExtractionUtil();
    Person person;
    Address address;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        address = new Address();
        address.setCity("Warsaw");
        address.setStreet("Sunset Street");
    }

    @DisplayName("Testing case with valid paths and object passed, should return 4 specific values")
    @Test
    void test1() throws NoSuchFieldException, IllegalAccessException {
        List<String> paths = List.of("args[0].firstName", "args[0].address.city", "args[1].street", "retVal.id");
        person.setAddress(address);
        person.setId(1L);

        List<String> extractedValues = valueExtractionUtil.extractValues(paths, new Object[]{person, address}, person);

        assertThat(extractedValues.size()).isEqualTo(4);
        assertThat(extractedValues.get(0)).isEqualTo(person.getFirstName());
        assertThat(extractedValues.get(1)).isEqualTo(person.getAddress().getCity());
        assertThat(extractedValues.get(2)).isEqualTo(address.getStreet());
        assertThat(extractedValues.get(3)).isEqualTo(person.getId().toString());
    }

    @DisplayName("Testing case with wrong syntax paths, should return empty list")
    @Test
    void test2() throws NoSuchFieldException, IllegalAccessException {
        List<String> paths = List.of("retVaa.id", "asd", "arg[0].firstName", "args{0}.firstName");
        person.setAddress(address);
        person.setId(1L);

        List<String> extractedValues = valueExtractionUtil.extractValues(paths, new Object[]{person, address}, person);

        assertThat(extractedValues).isEmpty();
    }

    @DisplayName("Testing case with wrong property name, should throw exception")
    @Test
    void test3() {
        List<String> paths = List.of("args[0].nonExistingProp");
        person.setAddress(address);
        person.setId(1L);

        assertThatExceptionOfType(NoSuchFieldException.class).isThrownBy(() -> {
            valueExtractionUtil.extractValues(paths, new Object[]{person, address}, person);
        });
    }

    @DisplayName("Testing case with null args and return value, should return empty list")
    @Test
    void test4() throws NoSuchFieldException, IllegalAccessException {
        List<String> paths = List.of("args[0].firstName", "retVaa.id");

        List<String> extractedValues = valueExtractionUtil.extractValues(paths, null, null);

        assertThat(extractedValues).isEmpty();
    }
}