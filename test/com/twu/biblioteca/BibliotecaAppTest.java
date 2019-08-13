package com.twu.biblioteca;

import com.twu.biblioteca.helpers.InvalidMenuOptionException;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
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
    void shouldDisplayMessageIfInvalidMenuOptionSelected(int option, boolean validOption)
            throws InvalidMenuOptionException {
        // given option
        // when validOption == false

        // then
        if (!validOption) {
            assertThrows(InvalidMenuOptionException.class, () -> {
                app.executeMainMenuOption(option);
            });
        }
    }

    @Test
    void displayListOfBooks() {
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
    void checkoutBook() {
        // given
        app = new BibliotecaApp(outPrinter, errPrinter, scanner, library);
        
        int bookIndex = 0;
        String bookTitle = library.getBooks().get(bookIndex).getTitle();

        // when
        app.checkoutBook(bookTitle);

        // then
        app.displayAllBooks();
        verify(outPrinter).format("%s%30s%30s%n", "Title", "Author", "Publication Year");

        for (int i = 0; i < library.getBooks().size(); i++) {
            if (i != bookIndex) {
                Book book = library.getBooks().get(i);
                verify(outPrinter).println(book.toString());
            }
        }
        verifyNoMoreInteractions(outPrinter);
    }
}
