package org.example;

import java.util.List;
import java.util.Scanner;

public class LibraryConsole {

    public static void main(String[] args) {
        new LibraryConsole().run();
    }

    private Library library = new Library();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            showMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1:
                    handleBookManagement();
                    break;
                case 2:
                    handleUserManagement();
                    break;
                case 3:
                    handleBorrowing();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== Library ===");
        System.out.println("1. Books");
        System.out.println("2. Users");
        System.out.println("3. Borrowing");
        System.out.println("0. Exit");
    }

    private void handleBookManagement() {
        while (true) {
            System.out.println("\n=== Books ===");
            System.out.println("1. Add book");
            System.out.println("2. Remove book");
            System.out.println("3. Find book");
            System.out.println("4. Search books");
            System.out.println("0. Back");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    findBook();
                    break;
                case 4:
                    searchBooks();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addBook() {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        library.addBook(title, author, isbn, genre);
        System.out.println("Book added");
    }

    private void removeBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        if (library.removeBook(isbn)) {
            System.out.println("Book removed");
        } else {
            System.out.println("Book not found");
        }
    }

    private void findBook() {
        System.out.print("ISBN: ");
        Book book = library.findBook(scanner.nextLine());

        if (book == null) {
            System.out.println("Book not found");
        } else {
            printBook(book);
        }
    }

    private void searchBooks() {
        System.out.print("Search: ");
        List<Book> result = library.searchBooks(scanner.nextLine());

        if (result.isEmpty()) {
            System.out.println("Nothing found");
            return;
        }

        for (Book book : result) {
            printBook(book);
        }
    }

    private void handleUserManagement() {
        while (true) {
            System.out.println("\n=== Users ===");
            System.out.println("1. Register user");
            System.out.println("2. Find user");
            System.out.println("0. Back");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    findUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void registerUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Guest");

        int choice = getIntInput("User type: ");

        UserType type;

        if (choice == 1) {
            type = UserType.STUDENT;
        } else if (choice == 2) {
            type = UserType.FACULTY;
        } else if (choice == 3) {
            type = UserType.GUEST;
        } else {
            System.out.println("Invalid user type");
            return;
        }

        library.registerUser(name, userId, email, type);
        System.out.println("User registered");
    }

    private void findUser() {
        System.out.print("User ID: ");
        User user = library.findUser(scanner.nextLine());

        if (user == null) {
            System.out.println("User not found");
            return;
        }

        System.out.println(
                "Name: " + user.getName()
                        + ", ID: " + user.getUserId()
                        + ", Type: " + user.getUserType()
                        + ", Books: " + user.getBorrowedBooks()
        );
    }

    private void handleBorrowing() {
        while (true) {
            System.out.println("\n=== Borrowing ===");
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. Overdue books");
            System.out.println("0. Back");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    showOverdueBooks();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void borrowBook() {
        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        if (library.borrowBook(userId, isbn)) {
            System.out.println("Book borrowed");
        } else {
            System.out.println("Cannot borrow book");
        }
    }

    private void returnBook() {
        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        if (library.returnBook(userId, isbn)) {
            System.out.println("Book returned");
        } else {
            System.out.println("Cannot return book");
        }
    }

    private void showOverdueBooks() {
        List<BorrowingRecord> records = library.getOverdueBooks();

        if (records.isEmpty()) {
            System.out.println("No overdue books");
            return;
        }

        for (BorrowingRecord record : records) {
            System.out.println(
                    "User: " + record.getUserId()
                            + ", ISBN: " + record.getIsbn()
                            + ", Due date: " + record.getDueDate()
            );
        }
    }

    private void printBook(Book book) {
        System.out.println(
                "Title: " + book.getTitle()
                        + ", Author: " + book.getAuthor()
                        + ", ISBN: " + book.getIsbn()
                        + ", Genre: " + book.getGenre()
                        + ", Available: " + book.isAvailable()
        );
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
}