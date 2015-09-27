package com.naddy.entity;

import static org.springframework.cassandra.core.PrimaryKeyType.PARTITIONED;

import java.io.Serializable;

import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class Person implements Serializable {

    private static final long serialVersionUID = 5041091194167471972L;

    @PrimaryKeyColumn(name = "first_name", ordinal = 0, type = PARTITIONED)
    private String firstName;

    @PrimaryKeyColumn(name = "last_name", ordinal = 1, type = PARTITIONED)
    private String lastName;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Person)) {
            return false;
        }

        final Person person = (Person) o;

        if (!firstName.equals(person.firstName)) {
            return false;
        }

        if (!lastName.equals(person.lastName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    public Person(final String firstName, final String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
}
