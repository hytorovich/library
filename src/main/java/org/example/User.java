package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String name;
    protected String userId;
    protected String email;
    protected List<String> borrowedBooks;
    private final UserType userType;

    public User(String name, UserType userType, String email, String userId) {
        this.name = name;
        this.userType = userType;
        this.email = email;
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>();
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book.getIsbn());
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book.getIsbn());
    }

    public abstract int getMaxBooks();

    public abstract int getBorrowDays();

    public abstract double getFinePerDay();

    public boolean canBorrow() {
        return borrowedBooks.size() < getMaxBooks();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public List<String> getBorrowedBooks() {
        return List.copyOf(borrowedBooks);
    }
}

