package com.twu.biblioteca.model;

public class Book {
    private String title;
    private String author;
    private String publicationYear;

    public Book(String title, String author, String publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    @Override
    public String toString() {
        return title + "\t" + author;
    }
}
