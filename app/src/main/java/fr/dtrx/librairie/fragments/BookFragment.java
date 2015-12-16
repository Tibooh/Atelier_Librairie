package fr.dtrx.librairie.fragments;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookCatalog;
import fr.dtrx.librairie.functions.ImageFunctions;

public class BookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    public void update(int position_book) {
        View view = getView();

        RatingBar book_rate = (RatingBar) view.findViewById(R.id.book_rate);
        ImageView book_image = (ImageView) view.findViewById(R.id.book_image);
        TextView book_genre = (TextView) view.findViewById(R.id.book_genre);
        TextView book_title = (TextView) view.findViewById(R.id.book_title);
        TextView book_author = (TextView) view.findViewById(R.id.book_author);
        TextView book_year = (TextView) view.findViewById(R.id.book_year);
        TextView book_edition = (TextView) view.findViewById(R.id.book_edition);
        TextView book_collection = (TextView) view.findViewById(R.id.book_collection);
        TextView book_isbn = (TextView) view.findViewById(R.id.book_isbn);
        TextView book_description = (TextView) view.findViewById(R.id.book_description);
        ProgressBar book_progress= (ProgressBar) view.findViewById(R.id.book_progress);
        TextView book_pages = (TextView) view.findViewById(R.id.book_pages);
        TextView book_progress_percentage = (TextView) view.findViewById(R.id.book_progress_percentage);

        Book book = BookCatalog.list.search(position_book);

        book_genre.setText(book.getGenre());
        book_rate.setRating(book.getRate());
        book_image.setImageBitmap(BitmapFactory.decodeFile(book.getImage()));
        book_title.setText(book.getTitle());
        book_author.setText(book.getAuthor());
        book_year.setText(book.getYear());
        book_edition.setText(book.getEdition());
        book_collection.setText(book.getCollection());
        book_isbn.setText(book.getIsbn());
        book_description.setText(book.getDescription());
        book_progress.setMax(book.getPages()); book_progress.setProgress(book.getProgress());
        book_pages.setText("" + book.getPages());
        book_progress_percentage.setText("" + book.getProgressPercentage());
    }

}
