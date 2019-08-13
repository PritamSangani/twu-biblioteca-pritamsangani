package com.twu.biblioteca;

import com.twu.biblioteca.helpers.InvalidMenuOptionException;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Library;
import com.twu.biblioteca.service.Menu;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class BibliotecaApp {
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";
    private Menu menu = new Menu();
    private Library library = new Library();
    private PrintStream outPrinter = System.out;
    private PrintStream errPrinter = System.err;
    private Scanner scanner = new Scanner(System.in);

    private BibliotecaApp() { }

    private BibliotecaApp(Library library) {
        this.library = library;
    }

    BibliotecaApp(PrintStream outPrinter, PrintStream errPrinter, Scanner scanner) {
        this.outPrinter = outPrinter;
        this.errPrinter = errPrinter;
        this.scanner = scanner;
    }

    BibliotecaApp(PrintStream outPrinter, PrintStream errPrinter, Scanner scanner, Library library) {
        this.outPrinter = outPrinter;
        this.errPrinter = errPrinter;
        this.scanner = scanner;
        this.library = library;
    }

    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>(
                Arrays.asList(
                        new Book(
                                "Harry Potter and the Philosopher's Stone",
                                "J.K. Rowling",
                                "1997"),
                        new Book(
                                "Enlightenment Now",
                                "Steven Pinker",
                                "2018"))
        );

        Library library = new Library(books);
        BibliotecaApp app = new BibliotecaApp(library);
        app.start();
    }

    private void start() {
        displayWelcomeMessage();

        menu.displayMenu();
        try {
            int option = scanner.nextInt();
            executeMainMenuOption(option);
            scanner.nextLine();
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
            case 2:
                displayAllBooks();
                boolean checkoutBookStatus = executeCheckoutBookOption();
                break;
            default:
                throw new InvalidMenuOptionException("Please select a valid option!");
        }
    }

    void displayAllBooks() {
        outPrinter.format("%s%30s%30s%n", "Title", "Author", "Publication Year");
        for (Book book: library.getBooksNotCheckedOut()) {
            outPrinter.println(book.toString());
        }
    }

    private Boolean executeCheckoutBookOption() {
        outPrinter.println("\nSelect a book to checkout");

        String bookTitle = scanner.nextLine();

        return checkoutBook(bookTitle);
    }

    Boolean checkoutBook(String bookTitle) {
        Optional<Book> bookToCheckout = library.getBookByTitle(bookTitle);
        return bookToCheckout.filter(book -> library.checkoutBook(book)).isPresent();
    }

}
