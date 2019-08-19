package com.twu.biblioteca.model;

public class Movie {
    private String title;
    private String director;
    private String releaseYear;
    private int rating;
    private boolean checkedOut;

    public Movie(String title, String director, String releaseYear, int rating) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.checkedOut = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    @Override
    public String toString() {
        return String.format("%s%30s%30s%30d", title, director, releaseYear, rating);
    }
}
