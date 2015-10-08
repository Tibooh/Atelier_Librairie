package fr.dtrx.librairie.fr.dtrx.librairie.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by doutriaux on 05/10/15.
 */
public class BookLibrary extends ArrayList<Book> {

    public static BookLibrary list = new BookLibrary();

    public BookLibrary() { super(); }
    public BookLibrary(int capacity) { super(capacity); }
    public BookLibrary(Collection<? extends Book> collection) { super(collection); }

}
