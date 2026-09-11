package org.example;

public class Guest extends User {
    public Guest(String name, String email, String userId) {
        super(name, UserType.GUEST, email, userId);
    }

    @Override
    public int getMaxBooks() {
        return 1;
    }

    @Override
    public int getBorrowDays() {
        return 7;
    }

    @Override
    public double getFinePerDay() {
        return 2;
    }
}
