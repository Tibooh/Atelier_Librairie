package fr.dtrx.librairie.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class BookFilter implements Serializable {

    @DatabaseField(generatedId = true, columnName = "book_filter_id")
    public int bookFilterId;

    @DatabaseField(columnName = "book_name")
    private String name;

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

    public BookFilter() {}

    // GETTERS

    public int getBookFilterId() { return bookFilterId; }
    public String getName() { return name; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public String getEdition() { return edition; }
    public String getDescription() { return description; }

    // SETTERS

    public void setName(String name) { this.name = name; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(String year) { this.year = year; }
    public void setEdition(String edition) { this.edition = edition; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Verifie les conditions pour le 'book'
     * @param book Le livre a verifier
     * @return true si le 'book' verifie les filtres, faux sinon
     */
    public boolean check(Book book) {
        return
            !((isFieldNull(getTitle()) &&           !book.getTitle().contains(getTitle()))
            || (isFieldNull(getAuthor()) &&         !book.getAuthor().contains(getAuthor()))
            || (isFieldNull(getYear()) &&           !book.getYear().equals(getYear()))
            || (isFieldNull(getEdition()) &&        !book.getEdition().contains(getEdition()))
            || (isFieldNull(getDescription()) &&    !book.getDescription().contains(getDescription())));
    }

    public boolean isFieldNull(String field) {
        return field != null && field.length() > 0;
    }

}
