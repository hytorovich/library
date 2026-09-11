package org.example;

import java.util.List;

public class Library implements LibraryOperations {


    @Override
    public void addBook(String title, String author, String isbn, String genre) {
    }

    @Override
    public boolean removeBook(String isbn) {
        return false;
    }

    @Override
    public Book findBook(String isbn) {
        return null;
    }

    @Override
    public List<Book> searchBooks(String query) {
        return List.of();
    }

    @Override
    public void registerUser(String name, String userId, String email, UserType type) {

    }

    @Override
    public User findUser(String userId) {
        return null;
    }

    @Override
    public boolean borrowBook(String userId, String isbn) {
        return false;
    }

    @Override
    public boolean returnBook(String userId, String isbn) {
        return false;
    }

    @Override
    public List<BorrowingRecord> getOverdueBooks() {
        return List.of();
    }
}
