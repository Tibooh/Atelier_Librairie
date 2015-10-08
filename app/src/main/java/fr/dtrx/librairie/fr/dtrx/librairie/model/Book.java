package fr.dtrx.librairie.fr.dtrx.librairie.model;

/**
 * Created by doutriaux on 05/10/15.
 */
public class Book {

    private String title;
    private String author;
    private int year;
    private String edition;
    private String description;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author, int year, String edition, String description) {
        this(title, author);
        this.year = year;
        this.edition = edition;
        this.description = description;
    }

    // GETTERS

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getYear() { return year; }
    public String getEdition() { return edition; }
    public String getDescription() { return description; }

    // SETTERS

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setYear(int year) { this.year = year; }
    public void setEdition(String edition) { this.edition = edition; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return title + "\n" + author;
    }
}
