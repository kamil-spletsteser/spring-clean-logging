package pl.k4mil.springcleanlogging;

import org.springframework.stereotype.Component;

@Component
public class TestBean {

    public Person run(Person p1, Person p2) {
        p1.setId(10L);
        return p1;
    }
}