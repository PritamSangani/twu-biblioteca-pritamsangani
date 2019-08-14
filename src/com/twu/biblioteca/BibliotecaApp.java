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

    Library getLibrary() {
        return library;
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
        boolean exitApplication = false;

        do {
            menu.displayMenu();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                exitApplication = executeMainMenuOption(option);
            } catch (InvalidMenuOptionException e) {
                errPrinter.println(e.getMessage());
            }
        } while (!exitApplication);
    }

    void displayWelcomeMessage() {
        outPrinter.println(WELCOME_MESSAGE);
    }

    Boolean executeMainMenuOption(int option) throws InvalidMenuOptionException {
        switch (option) {
            case 1:
                displayAllBooks();
                break;
            case 2:
                displayAllBooks();
                executeCheckoutBookOption();
                break;
            case 3:
                executeReturnBookOption();
                break;
            case 4:
                return true;
            default:
                throw new InvalidMenuOptionException("Please select a valid option!");
        }
        return false;
    }

    void displayAllBooks() {
        outPrinter.format("%s%30s%30s%n", "Title", "Author", "Publication Year");
        for (Book book: library.getBooksNotCheckedOut()) {
            outPrinter.println(book.toString());
        }
    }

    private void executeCheckoutBookOption() {
        outPrinter.println("\nSelect a book to checkout");
        String bookTitle = scanner.nextLine();

        if(checkoutBook(bookTitle)) outPrinter.println("Thank you! Enjoy the book");
        else errPrinter.println("Sorry, that book is not available");
    }

    private Boolean checkoutBook(String bookTitle) {
        Optional<Book> bookToCheckout = library.getBookByTitle(bookTitle);
        return bookToCheckout.filter(book -> library.checkoutBook(book)).isPresent();
    }

    private void executeReturnBookOption() {
        outPrinter.println("\nEnter the title of the book you wish to return.");
        String bookTitle = scanner.nextLine();

        if (returnBook(bookTitle)) outPrinter.println("Thank you for returning the book");
        else errPrinter.println("That is not a valid book to return.");
    }

    private Boolean returnBook(String bookTitle) {
        Optional<Book> bookToReturn = library.getBookByTitle(bookTitle);

        return bookToReturn.filter(book -> library.returnBook(book)).isPresent();
    }
}
