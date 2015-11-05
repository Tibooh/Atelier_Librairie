package fr.dtrx.librairie.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by doutriaux on 05/10/15.
 */
public class BookCatalog extends ArrayList<Book> {

    public static BookCatalog list = initialization();

    public BookCatalog() { super(); }
    public BookCatalog(int capacity) { super(capacity); }
    public BookCatalog(Collection<? extends Book> collection) { super(collection); }

    private static BookCatalog initialization() {
        BookCatalog bc = new BookCatalog();
        bc.ajouter();
        return bc;
    }

    private void ajouter() {
        add(new Book("Les misérables", "Victor Hugo", "1862", "Lacroix", "Les Misérables est un roman de Victor Hugo paru en 1862.\n" +
                "\n" +
                "Dans ce roman, un des plus emblématiques de la littérature française, Victor Hugo décrit la vie de misérables dans Paris et la France provinciale du xixe siècle et s'attache plus particulièrement aux pas du bagnard Jean Valjean qui n'est pas sans rappeler le condamné à mort du Dernier Jour d'un condamné ou Claude Gueux."));

        add(new Book("Notre-Dame de Paris", "Victor Hugo", "1831", "", "Notre-Dame de Paris (titre complet : Notre-Dame de Paris. 1482) est un roman de l'écrivain français Victor Hugo, publié en 1831.\n" +
                "\n" +
                "Le titre fait référence à la cathédrale de Paris, Notre-Dame, qui est un des lieux principaux de l'intrigue du roman."));

        add(new Book("Germinal", "Emile Zola", "1885", "Gil Blas", "Germinal est un roman d'Émile Zola publié en 1885. Il s'agit du treizième roman de la série des Rougon-Macquart. Écrit d'avril 1884 à janvier 1885, le roman paraît d'abord en feuilleton entre novembre 1884 et février 1885 dans le Gil Blas. Il connaît sa première édition en mars 1885. Depuis il a été publié dans plus d'une centaine de pays."));
    }

}