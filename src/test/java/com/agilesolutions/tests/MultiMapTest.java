package com.agilesolutions.tests;

import com.agilesolutions.model.Person;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://www.baeldung.com/guava-multimap
 * https://www.baeldung.com/junit-5
 * https://www.baeldung.com/java-unit-testing-best-practices
 */
public class MultiMapTest {

    @Test
    public void givenProcesses_whenReKey_thenReturnMultiMap() {

        // Create a Multimap
        Multimap<String, Person> departmentMap = ArrayListMultimap.create();

        allEmployees().forEach(p -> departmentMap.put(p.department(), p));

        assertAll("verify departments",
                () -> assertEquals(3, departmentMap.asMap().keySet().size()));

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
