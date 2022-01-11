package pl.k4mil.usageexample;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TestBean {


    public Person createPersonWithAddress(String firstName, String lastName, Address address) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        return person;
    }

    public Person createPerson(String firstName, String lastName) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    public Address createAddress(String city, String street) {
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        return address;
    }

    public void setTheSameAddress(Person p1, Person p2) {
        p2.setAddress(p1.getAddress());
    }

    public void printFullName(Person person) {
        System.out.println("Person full name: " + person.getFirstName() + " " + person.getLastName());
    }
}
