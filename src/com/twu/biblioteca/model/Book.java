package com.twu.biblioteca.model;

public class Book extends LibraryItem {
    private String author;
    private String publicationYear;

    public Book(String title, String author, String publicationYear) {
        super(title, false);
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    @Override
    public String toString() {
        return String.format("%s%30s%30s", super.title, author, publicationYear);
    }
}
