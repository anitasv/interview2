package com.company.interview.eqh;

import com.company.interview.eqh.Employee1;
import com.company.interview.eqh.Person1;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class Lib1Test {

    @Test
    public void equalPerson() {
        Person1 p1 = new Person1(20, "Alice");
        Person1 p2 = new Person1(20, "Alice");
        Person1 p3 = new Person1(20, "Bob");
        Person1 p4 = new Person1(30, "Alice");
        Person1 p5 = new Person1(30, "Bob");

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p1, p5);

        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
        assertNotEquals(p1.hashCode(), p5.hashCode());
    }

    @Test
    public void equalEmployee() {
        Employee1 p1 = new Employee1(20, "Alice", "Engineer");
        Employee1 p2 = new Employee1(20, "Alice", "Engineer");
        Employee1 p3 = new Employee1(20, "Bob", "Engineer");
        Employee1 p4 = new Employee1(30, "Alice", "Engineer");
        Employee1 p5 = new Employee1(20, "Alice", "Manager");

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p1, p5);

        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
        assertNotEquals(p1.hashCode(), p5.hashCode());
    }

    @Test
    public void equalCross() {
        Person1 p1 = new Person1(20, "Alice");
        Employee1 p2 = new Employee1(20, "Alice", "Engineer");

        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);

        assertNotEquals(p1.hashCode(), p2.hashCode());
    }
}
