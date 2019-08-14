package com.twu.biblioteca;

import com.twu.biblioteca.helpers.InvalidMenuOptionException;
import com.twu.biblioteca.helpers.SystemLambda;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.twu.biblioteca.helpers.SystemLambda.catchSystemExit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BibliotecaAppTest {
    private BibliotecaApp app;
    private Library library;
    private ArrayList<Book> books;

    @Mock
    PrintStream outPrinter;
    @Mock
    PrintStream errPrinter;
    @Mock
    Scanner scanner;

    @Captor
    ArgumentCaptor<String> captor;

    @BeforeEach
    void setUp() {
        books = new ArrayList<>(
                Arrays.asList(
                        new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "1997"),
                        new Book("Enlightenment Now: The Case for Reason, Science, Humanism, and Progress", "Steven Pinker", "2018"))
        );
        library = new Library(books);

        app = new BibliotecaApp(outPrinter, errPrinter, scanner);
    }

    @Test
    void shouldDisplayWelcomeMessageOnStart() {
        // given
        final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
        // when
        app.displayWelcomeMessage();
        // then
        verify(outPrinter).println(WELCOME_MESSAGE);
    }

    private static Stream<Arguments> shouldDisplayMessageIfInvalidMenuOptionSelectedArguments() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, false),
                Arguments.of(4, false)
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDisplayMessageIfInvalidMenuOptionSelectedArguments")
    void shouldDisplayMessageIfInvalidMenuOptionSelected(int option, boolean validOption) {
        // given option
        // when validOption == false

        // then
        if (!validOption) {
            assertThrows(InvalidMenuOptionException.class, () -> app.executeMainMenuOption(option));
        }
    }

    @Test
    void testThatListOfBooksAreDisplayedInTheCorrectFormat() {
        // given
        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);

        // when
        app.displayAllBooks();

        // then
        verify(outPrinter).format("%s%30s%30s%n", "Title", "Author", "Publication Year");
        for (Book book: books) {
            verify(outPrinter).println(book.toString());
        }
    }

    @Test
    void testThatACheckedOutBookIsNotDisplayedInTheListOfBooksShownToCustomer() {
        // given
        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);

        int bookIndex = 0;
        Book bookToCheckout = library.getBooks().get(bookIndex);
        String bookTitle = bookToCheckout.getTitle();

        when(scanner.nextLine()).thenReturn(bookTitle);
        // when
        try {
            app.executeMainMenuOption(2);
        } catch (InvalidMenuOptionException e) {
            e.printStackTrace();
        }

        // then
        Library libraryAfterBookCheckedOut = app.getLibrary();

        assertThat(libraryAfterBookCheckedOut.getBooksNotCheckedOut(), not(hasItem(bookToCheckout)));
    }

    @Test
    void testThatASuccessMessageIsShownToCustomerWhenSuccessfullyCheckingOutABook() {
        // given

        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);

        int bookIndex = 0;
        String bookTitle = library.getBooks().get(bookIndex).getTitle();
        when(scanner.nextLine()).thenReturn(bookTitle);
        // when
        try {
            app.executeMainMenuOption(2);
        } catch (InvalidMenuOptionException e) {
            e.printStackTrace();
        }

        // then
        verify(outPrinter,
                times(library.getBooks().size() + 2)).println(captor.capture());

        assertThat(captor.getValue(), is("Thank you! Enjoy the book"));
    }

    @Test
    void testThatAnUnsuccessfulMessageIsShownToCustomerWhenUnsuccessfullyCheckingOutABook() {
        // given

        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);

        String bookTitle = "No such book";
        when(scanner.nextLine()).thenReturn(bookTitle);
        // when
        try {
            app.executeMainMenuOption(2);
        } catch (InvalidMenuOptionException e) {
            e.printStackTrace();
        }

        // then
        verify(errPrinter).println("Sorry, that book is not available");
    }

    @Test
    void testThatAReturnedBookIsDisplayedInListOfBooksShownToCustomer() {
        // given
        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);

        int bookIndex = 0;
        Book bookToCheckout = library.getBooks().get(bookIndex);
        String bookTitle = bookToCheckout.getTitle();
        app.getLibrary().checkoutBook(bookToCheckout);

        when(scanner.nextLine()).thenReturn(bookTitle);
        // when
        try {
            app.executeMainMenuOption(3);
        } catch (InvalidMenuOptionException e) {
            e.printStackTrace();
        }

        // then
        Library libraryAfterBookCheckedOut = app.getLibrary();

        assertThat(libraryAfterBookCheckedOut.getBooksNotCheckedOut(), hasItem(bookToCheckout));
    }

    @Test
    void testThatASuccessMessageIsShownToCustomerWhenSuccessfullyReturningACheckedOutBook() {
        // given
        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);

        int bookIndex = 0;
        Book bookToReturn = library.getBooks().get(bookIndex);
        String bookTitle = bookToReturn.getTitle();
        app.getLibrary().checkoutBook(bookToReturn);
        when(scanner.nextLine()).thenReturn(bookTitle);
        // when
        try {
            app.executeMainMenuOption(3);
        } catch (InvalidMenuOptionException e) {
            e.printStackTrace();
        }

        // then
        verify(outPrinter,
                times(2)).println(captor.capture());
        assertThat(captor.getValue(), is("Thank you for returning the book"));
    }

    @Test
    void testThatAnUnsuccessfulMessageIsShownToCustomerWhenUnsuccessfullyReturningABook() {
        // given
        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);

        int bookIndex = 0;
        Book bookToReturn = library.getBooks().get(bookIndex);
        String bookTitle = bookToReturn.getTitle();

        when(scanner.nextLine()).thenReturn(bookTitle);
        // when
        try {
            app.executeMainMenuOption(3);
        } catch (InvalidMenuOptionException e) {
            e.printStackTrace();
        }

        // then
        verify(errPrinter).println("That is not a valid book to return.");
    }

    @Test
    void testApplicationExitsWhenQuitApplicationOptionSelected() {
        // given
        int statusCode = 0;

        // when
        try {
            app.executeMainMenuOption(4);
        } catch (InvalidMenuOptionException e) {
            e.printStackTrace();
        }

        // then
        try {
            statusCode = catchSystemExit(() -> System.exit(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(statusCode, is(0));
    }
}
