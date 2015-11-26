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

    @Override
    public String toString() {
        return title + "\n" + author;
    }

}
