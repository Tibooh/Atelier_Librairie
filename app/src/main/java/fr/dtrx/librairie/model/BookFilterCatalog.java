package fr.dtrx.librairie.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Quentin on 06/10/2015.
 */
public class BookFilterCatalog extends ArrayList<BookFilter> {

    public static BookFilterCatalog list = new BookFilterCatalog();

    public BookFilterCatalog() { super(); }
    public BookFilterCatalog(int capacity) { super(capacity); }
    public BookFilterCatalog(Collection<? extends BookFilter> collection) { super(collection); }

}
