package fr.dtrx.librairie.model;

import java.util.ArrayList;
import java.util.Collection;

public class BookFilterCatalog extends ArrayList<BookFilter> {

    public static BookFilterCatalog list = new BookFilterCatalog();

    public BookFilterCatalog() { super(); }
    public BookFilterCatalog(int capacity) { super(capacity); }
    public BookFilterCatalog(Collection<? extends BookFilter> collection) { super(collection); }

    public BookFilter search(int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBookFilterId() == id) return get(i);
        }

        return null;
    }

    private void ajouter() {
        /*
        add(new BookFilter("Livre de Victor Hugo", "", "Victor Hugo"));
        add(new BookFilter("Livre d'Emile Zola", "", "Emile Zola"));
        */
    }

}
