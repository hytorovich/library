import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();

        library.addBook(
                "1984",
                "George Orwell",
                "111",
                "Dystopia"
        );

        library.addBook(
                "The Hobbit",
                "J.R.R. Tolkien",
                "222",
                "Fantasy"
        );

        library.registerUser(
                "Ivan",
                "user1",
                "ivan@mail.com",
                UserType.STUDENT
        );
    }

    @Test
    void shouldAddAndFindBook() {
        Book book = library.findBook("111");

        assertNotNull(book);
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
    }

    @Test
    void shouldReturnNullWhenBookDoesNotExist() {
        Book book = library.findBook("999");

        assertNull(book);
    }

    @Test
    void shouldRegisterAndFindUser() {
        User user = library.findUser("user1");

        assertNotNull(user);
        assertEquals("Ivan", user.getName());
        assertEquals(UserType.STUDENT, user.getUserType());
    }

    @Test
    void shouldBorrowBook() {
        boolean result = library.borrowBook("user1", "111");

        assertTrue(result);

        Book book = library.findBook("111");
        assertFalse(book.isAvailable());

        User user = library.findUser("user1");
        assertTrue(user.getBorrowedBooks().contains("111"));
    }

    @Test
    void shouldNotBorrowAlreadyBorrowedBook() {
        library.borrowBook("user1", "111");

        boolean secondBorrow = library.borrowBook("user1", "111");

        assertFalse(secondBorrow);
    }

    @Test
    void shouldNotBorrowBookForUnknownUser() {
        boolean result = library.borrowBook("unknown", "111");

        assertFalse(result);
    }

    @Test
    void shouldReturnBook() {
        library.borrowBook("user1", "111");

        boolean result = library.returnBook("user1", "111");

        assertTrue(result);
        assertTrue(library.findBook("111").isAvailable());

        assertFalse(
                library.findUser("user1")
                        .getBorrowedBooks()
                        .contains("111")
        );
    }

    @Test
    void shouldNotReturnBookThatWasNotBorrowed() {
        boolean result = library.returnBook("user1", "111");

        assertFalse(result);
    }

    @Test
    void shouldRemoveBook() {
        boolean result = library.removeBook("111");

        assertTrue(result);
        assertNull(library.findBook("111"));
    }

    @Test
    void shouldSearchBooksByAuthorIgnoringCase() {
        List<Book> result = library.searchBooks("tolkien");

        assertEquals(1, result.size());
        assertEquals("The Hobbit", result.get(0).getTitle());
    }

    @Test
    void shouldSearchBooksByIsbn() {
        List<Book> result = library.searchBooks("222");

        assertEquals(1, result.size());
        assertEquals("The Hobbit", result.get(0).getTitle());
    }

    @Test
    void studentShouldNotBorrowMoreThanThreeBooks() {
        library.addBook("Book 3", "Author", "333", "Genre");
        library.addBook("Book 4", "Author", "444", "Genre");

        assertTrue(library.borrowBook("user1", "111"));
        assertTrue(library.borrowBook("user1", "222"));
        assertTrue(library.borrowBook("user1", "333"));

        assertFalse(library.borrowBook("user1", "444"));
    }

    @Test
    void shouldNotBorrowUnknownBook() {
        boolean result = library.borrowBook("user1", "999");

        assertFalse(result);
    }

    @Test
    void shouldNotReturnBookByAnotherUser() {
        library.registerUser(
                "Petr",
                "user2",
                "petr@mail.com",
                UserType.STUDENT
        );

        library.borrowBook("user1", "111");

        boolean result = library.returnBook("user2", "111");

        assertFalse(result);
        assertFalse(library.findBook("111").isAvailable());
        assertTrue(library.findUser("user1").getBorrowedBooks().contains("111"));
    }

    @Test
    void newlyBorrowedBookShouldNotBeOverdue() {
        library.borrowBook("user1", "111");

        List<BorrowingRecord> overdueBooks = library.getOverdueBooks();

        assertTrue(overdueBooks.isEmpty());
    }
}
