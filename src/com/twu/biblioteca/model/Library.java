package com.twu.biblioteca.model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {

    private ArrayList<LibraryItem> inventory;

    public Library() {
        this.inventory = new ArrayList<>();
    }

    public Library(ArrayList<LibraryItem> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Book> getBooks() {
        return (ArrayList<Book>) this.inventory.stream()
                .filter(libraryItem -> libraryItem instanceof Book)
                .map(libraryItem -> (Book) libraryItem)
                .collect(Collectors.toList());
    }

    public ArrayList<Movie> getMovies() {
        return (ArrayList<Movie>) this.inventory.stream()
                .filter(libraryItem -> libraryItem instanceof Movie)
                .map(libraryItem -> (Movie) libraryItem)
                .collect(Collectors.toList());
    }

    public ArrayList<Book> getBooksNotCheckedOut() {
        return (ArrayList<Book>) getBooks().stream()
                .filter(book -> !book.isCheckedOut())
                .collect(Collectors.toList());
    }

    public Optional<Book> getBookByTitle(String bookTitle) {
        return getBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle))
                .findFirst();
    }


    public boolean checkoutBook(Book bookToCheckout) {
        if (bookToCheckout.isCheckedOut()) {
            return false;
        } else {
            bookToCheckout.setCheckedOut(true);
            return true;
        }
    }

    public boolean returnBook(Book bookToReturn) {
        if (bookToReturn.isCheckedOut()) {
            bookToReturn.setCheckedOut(false);
            return true;
        } else {
            return false;
        }
    }
}
