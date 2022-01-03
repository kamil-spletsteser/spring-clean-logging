package pl.k4mil.springcleanlogging;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestObjectService {

    public Person copyId(Person p1, Person p2) {
        p1.setId(p2.getId());
        return p1;
    }

    public Person create() {
        var person = new Person();
        person.setId(new Random().nextLong());
        person.setFirstName(randomString());
        person.setLastName(randomString());
        var address = new Address();
        address.setCity(randomString());
        address.setStreet(randomString());
        person.setAddress(address);
        return person;
    }

    public LogSpecs create(String message) {
        LogSpecs logSpecs = new LogSpecs();
        logSpecs.setLevel(Level.INFO);
        logSpecs.setType(Type.BEFORE);
        logSpecs.setMessage(message);
        logSpecs.setPaths(List.of());
        return logSpecs;
    }

    public MethodInvocation createMethodInvocation(Person person) {
        return new MethodInvocation() {
            @Override
            public Method getMethod() {
                try {
                    return TestObjectService.class.getMethod("copyId", Person.class, Person.class);
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

    public String randomString() {
        return UUID.randomUUID().toString();
    }
}