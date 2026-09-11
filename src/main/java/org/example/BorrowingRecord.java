package org.example;

import java.time.LocalDate;

public class BorrowingRecord {
    private String userId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public BorrowingRecord(String userId, LocalDate returnDate, LocalDate dueDate, LocalDate borrowDate, String isbn) {
        this.userId = userId;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.borrowDate = borrowDate;
        this.isbn = isbn;
    }

    public String getUserId() {
        return userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}

