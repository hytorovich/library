package org.example;

public class Student extends User {

    public Student(String name, String email, String userId) {
        super(name, UserType.STUDENT, email, userId);
    }

    @Override
    public int getMaxBooks() {
        return 3;
    }

    @Override
    public int getBorrowDays() {
        return 14;
    }

    @Override
    public double getFinePerDay() {
        return 1;
    }
}
