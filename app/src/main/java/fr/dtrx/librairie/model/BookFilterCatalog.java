package fr.dtrx.librairie.model;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
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

    public static void refresh(Dao<BookFilter, Integer> bookFilterDao) {
        try {
            list.clear();
            list.addAll(bookFilterDao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
