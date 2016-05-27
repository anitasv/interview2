package com.company.interview.eqh;

public class Person2 {

    private final int age;

    private final String name;

    public Person2(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }

        Person2 person2 = (Person2) other;

        if (age != person2.age) {
            return false;
        }
        return name != null ? name.equals(person2.name) : person2.name == null;

    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
