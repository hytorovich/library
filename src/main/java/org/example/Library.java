package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library implements LibraryOperations {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private final List<BorrowingRecord> borrowingRecords = new ArrayList<>();

    @Override
    public void addBook(String title, String author, String isbn, String genre) {
        books.put(isbn, new Book(title, author, isbn, genre));
    }

    @Override
    public boolean removeBook(String isbn) {
        return books.remove(isbn) != null;
    }

    @Override
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        String searchQuery = query.toLowerCase();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(searchQuery)
                    || book.getAuthor().toLowerCase().contains(searchQuery)
                    || book.getIsbn().toLowerCase().contains(searchQuery)) {
                result.add(book);
            }
        }
        return result;
    }


    @Override
    public void registerUser(String name, String userId, String email, UserType type) {
        if (UserType.FACULTY.equals(type)) {
            users.put(userId, new Faculty(name, email, userId));
        } else if (UserType.GUEST.equals(type)) {
            users.put(userId, new Guest(name, email, userId));
        } else if (UserType.STUDENT.equals(type)) {
            users.put(userId, new Student(name, email, userId));
        } else {
            System.out.println("Unknown user type: " + type);
        }
    }

    @Override
    public User findUser(String userId) {
        return users.get(userId);
    }

    @Override
    public boolean borrowBook(String userId, String isbn) {
        if (users.get(userId) == null
                || books.get(isbn) == null
                || !books.get(isbn).isAvailable()
                || !users.get(userId).canBorrow()) {
            return false;
        }

        users.get(userId).addBorrowedBook(books.get(isbn));
        books.get(isbn).setAvailable(false);

        if (users.get(userId).getUserType() == UserType.STUDENT) {
            borrowingRecords.add(new BorrowingRecord(userId, LocalDate.now(),
                    LocalDate.now().plusDays(14), isbn));
        } else if (users.get(userId).getUserType() == UserType.FACULTY) {
            borrowingRecords.add(new BorrowingRecord(userId, LocalDate.now(),
                    LocalDate.now().plusDays(30), isbn));
        } else {
            borrowingRecords.add(new BorrowingRecord(userId,
                    LocalDate.now(), LocalDate.now().plusDays(7), isbn));
        }
        return true;
    }

    @Override
    public boolean returnBook(String userId, String isbn) {
        if (users.get(userId) == null || books.get(isbn) == null
                || books.get(isbn).isAvailable()) {
            return false;
        }
        if (!users.get(userId).getBorrowedBooks().contains(isbn)) {
            return false;
        }

        for (BorrowingRecord record : borrowingRecords) {
            if (record.getUserId().equals(userId) && record.getIsbn().equals(isbn) &&
                    record.getReturnDate() == null) {
                record.setReturnDate(LocalDate.now());
                users.get(userId).removeBorrowedBook(books.get(isbn));
                books.get(isbn).setAvailable(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BorrowingRecord> getOverdueBooks() {
        List<BorrowingRecord> overdueBooks = new ArrayList<>();
        for (BorrowingRecord record : borrowingRecords) {
            if (record.getDueDate().isBefore(LocalDate.now())
                    && record.getReturnDate() == null) {
                overdueBooks.add(record);
            }
        }
        return overdueBooks;
    }
}
