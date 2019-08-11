package com.twu.biblioteca;

import com.twu.biblioteca.helpers.InvalidMenuOptionException;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Library;
import com.twu.biblioteca.service.Menu;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BibliotecaApp {
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
    private Menu menu = new Menu();
    private Library library = new Library();
    private PrintStream outPrinter = System.out;
    private PrintStream errPrinter = System.err;

    private BibliotecaApp() { }

    BibliotecaApp(Library library) {
        this.library = library;
    }

    BibliotecaApp(PrintStream outPrinter, PrintStream errPrinter) {
        this.outPrinter = outPrinter;
        this.errPrinter = errPrinter;
    }

    BibliotecaApp(PrintStream outPrinter, PrintStream errPrinter, Library library) {
        this.outPrinter = outPrinter;
        this.errPrinter = errPrinter;
        this.library = library;
    }

    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>(
                Arrays.asList(
                        new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling"),
                        new Book("Enlightenment Now: The Case for Reason, Science, Humanism, and Progress", "Steven Pinker"))
        );

        Library library = new Library(books);
        BibliotecaApp app = new BibliotecaApp(library);
        app.start();
    }

    void start() {
        displayWelcomeMessage();

        menu.displayMenu();
        try {
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            executeMainMenuOption(option);
        } catch (InvalidMenuOptionException e) {
            errPrinter.println(e.getMessage());
        }
    }

    void displayWelcomeMessage() {
        outPrinter.println(WELCOME_MESSAGE);
    }

    void executeMainMenuOption(int option) throws InvalidMenuOptionException {
        switch (option) {
            case 1:
                displayAllBooks();
                break;
            default:
                throw new InvalidMenuOptionException("Please select a valid option!");
        }
    }

    void displayAllBooks() {
        for (Book book: library.getBooks()) {
            outPrinter.println(book.toString());
        }
    }
}
