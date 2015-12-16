package fr.dtrx.librairie.adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.functions.ImageFunctions;

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

        Book book = books.get(position);

        ImageView book_image = (ImageView) rowView.findViewById(R.id.book_image);
        TextView book_title = (TextView) rowView.findViewById(R.id.book_title);
        TextView book_year = (TextView) rowView.findViewById(R.id.book_year);
        TextView book_description = (TextView) rowView.findViewById(R.id.book_description);
        ProgressBar book_progress = (ProgressBar) rowView.findViewById(R.id.book_progress);
        TextView book_pages = (TextView) rowView.findViewById(R.id.book_pages);
        TextView book_progress_percentage = (TextView) rowView.findViewById(R.id.book_progress_percentage);

        book_image.setImageBitmap(BitmapFactory.decodeFile(book.getImage()));
        book_title.setText(book.getTitle());
        book_year.setText(book.getYear());
        book_description.setText(book.getDescription());
        book_progress.setMax(book.getPages()); book_progress.setProgress(book.getProgress());
        book_pages.setText("" + book.getPages());
        book_progress_percentage.setText("" + book.getProgressPercentage());

        return rowView;
    }

}
