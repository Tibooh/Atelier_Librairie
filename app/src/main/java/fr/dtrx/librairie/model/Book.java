package fr.dtrx.librairie.model;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class Book implements Serializable {

    @DatabaseField(generatedId = true, columnName = "book_id")
    public int bookId;

    @DatabaseField(columnName = "book_image")
    private String image;

    @DatabaseField(columnName = "book_title")
    private String title;

    @DatabaseField(columnName = "book_author")
    private String author;

    @DatabaseField(columnName = "book_year")
    private String year;

    @DatabaseField(columnName = "book_edition")
    private String edition;

    @DatabaseField(columnName = "book_description")
    private String description;

    @DatabaseField(columnName = "book_collection")
    private String collection;

    @DatabaseField(columnName = "book_isbn")
    private String isbn;

    @DatabaseField(columnName = "book_genre")
    private String genre;


    public Book() {}

    // GETTERS
    public int getBookId() {
        return bookId;
    }
    public String getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getYear() {
        return year;
    }
    public String getEdition() {
        return edition;
    }
    public String getDescription() {
        return description;
    }
    public String getCollection() {
        return collection;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getGenre() {
        return genre;
    }


    // SETTERS
    public void setImage(String image) {
        this.image = image;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setEdition(String edition) {
        this.edition = edition;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCollection(String collection) {
        this.collection = collection;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return title + "\n" + author;
    }


}
