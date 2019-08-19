package com.twu.biblioteca.model;

public class Movie extends LibraryItem {
    private String director;
    private String releaseYear;
    private int rating;

    public Movie(String title, String director, String releaseYear, int rating) {
        super(title, false);
        this.director = director;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    @Override
    public String toString() {
        String ratingString;
        if (this.rating == -1) {
            ratingString = "Unrated";
        } else {
            ratingString = Integer.toString(rating);
        }
        return String.format("%s%30s%30s%30s", super.title, director, releaseYear, ratingString);
    }
}
