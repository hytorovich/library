package org.example;

public class Faculty extends User {
    public Faculty(String name, UserType userType, String email, String userId) {
        super(name, UserType.FACULTY, email, userId);
    }

    @Override
    public int getMaxBooks() {
        return 10;
    }

    @Override
    public int getBorrowDays() {
        return 30;
    }

    @Override
    public double getFinePerDay() {
        return 0.5;
    }
}
