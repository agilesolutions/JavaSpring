package com.agilesolutions.tests;

import com.agilesolutions.model.Person;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://www.baeldung.com/java-mapmulti
 * https://www.baeldung.com/junit-5
 * https://www.baeldung.com/java-unit-testing-best-practices
 */
public class MapMultiTest {

    @Test
    public void givenProcesses_whenReKey_thenReturnMultiMap() {

        List<Pair<String, String>> persons = (List<Pair<String, String>>) allEmployees().stream().<Pair<String, String>>mapMulti((person, consumer) -> {
            consumer.accept(new ImmutablePair<String, String>(person.name(), person.department()));
        }).collect(Collectors.toUnmodifiableList());


        assertAll("verify departments",
                () -> assertEquals(5, persons.size()));

    }


    private List<Person> allEmployees() {

        return Arrays.asList(
                new Person("Alice", "HR"),
                new Person("Bob", "Engineering"),
                new Person("Charlie", "HR"),
                new Person("David", "Engineering"),
                new Person("Eve", "Marketing")
        );
    }

}
