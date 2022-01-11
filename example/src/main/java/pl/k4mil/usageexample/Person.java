package pl.k4mil.usageexample;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private Address address;
}
