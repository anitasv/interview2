package com.company.interview.eqh;

import com.company.interview.eqh.Employee2;
import com.company.interview.eqh.Person2;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Created by anita on 5/27/16.
 */
public class Lib2Test {

    @Test
    public void equalPerson() {
        Person2 p1 = new Person2(20, "Alice");
        Person2 p2 = new Person2(20, "Alice");
        Person2 p3 = new Person2(20, "Bob");
        Person2 p4 = new Person2(30, "Alice");
        Person2 p5 = new Person2(30, "Bob");

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
        Employee2 p1 = new Employee2(20, "Alice", "Engineer");
        Employee2 p2 = new Employee2(20, "Alice", "Engineer");
        Employee2 p3 = new Employee2(20, "Bob", "Engineer");
        Employee2 p4 = new Employee2(30, "Alice", "Engineer");
        Employee2 p5 = new Employee2(20, "Alice", "Manager");

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
        Person2 p1 = new Person2(20, "Alice");
        Employee2 p2 = new Employee2(20, "Alice", "Engineer");

        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);

        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

}
