package com.twu.biblioteca.model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {
    private ArrayList<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public Library(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return this.books;
    }

    public ArrayList<Book> getBooksNotCheckedOut() {
        return (ArrayList<Book>) this.books.stream()
                .filter(book -> !book.isCheckedOut()).collect(Collectors.toList());
    }

    public Optional<Book> getBookByTitle(String bookTitle) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle)).findFirst();
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
