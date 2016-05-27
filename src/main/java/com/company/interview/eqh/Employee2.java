package com.company.interview.eqh;

public class Employee2 extends Person2 {

    private final String role;

    public Employee2(int age, String name, String role) {
        super(age, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) return false;

        Employee2 employee2 = (Employee2) other;

        return role != null ? role.equals(employee2.role) : employee2.role == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
