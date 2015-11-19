package fr.dtrx.librairie.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;

public class BookCatalogAdapter extends ArrayAdapter<Book> {

    private final Activity context;
    private final List<Book> books;

    public BookCatalogAdapter(Activity context, List<Book> books) {
        super(context, R.layout.adapter_book_catalog, books);

        this.context = context;
        this.books = books;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_book_catalog, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.book_image);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.book_title);
        TextView yeartxt = (TextView) rowView.findViewById(R.id.book_year);
        TextView extratxt = (TextView) rowView.findViewById(R.id.book_description);

        imageView.setImageResource(books.get(position).getImage());
        txtTitle.setText(books.get(position).getTitle());
        yeartxt.setText(books.get(position).getYear());
        extratxt.setText(books.get(position).getDescription());
        return rowView;
    };

}
