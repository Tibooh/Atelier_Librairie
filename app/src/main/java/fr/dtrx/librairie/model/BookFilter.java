package fr.dtrx.librairie.model;

/**
 * Created by Quentin on 06/10/2015.
 */
public class BookFilter extends Book {

    private String name;

    public BookFilter(String name, String title, String author) {
        super(title, author);
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Verifie les conditions pour le 'book'
     * @param book Le livre a verifier
     * @return true si le 'book' verifie les filtres, faux sinon
     */
    public boolean isSelected(Book book) {
        if (getTitle() != null && getTitle().length() > 0 && !book.getTitle().equals(getTitle())) return false;
        if (getAuthor() != null && getAuthor().length() > 0 && !book.getAuthor().equals(getAuthor())) return false;

        return true;
    }

}
