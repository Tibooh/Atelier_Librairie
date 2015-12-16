package fr.dtrx.librairie.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.functions.ImageFunctions;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookCatalog;

public class BookActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book);

        Intent intent = getIntent();

        int id_book = intent.getIntExtra(BookCatalogActivity.ID_BOOK, -1);
        if (id_book == -1) return;

        RatingBar book_rate = (RatingBar) findViewById(R.id.book_rate);
        ImageView book_image = (ImageView) findViewById(R.id.book_image);
        TextView book_genre = (TextView) findViewById(R.id.book_genre);
        TextView book_title = (TextView) findViewById(R.id.book_title);
        TextView book_author = (TextView) findViewById(R.id.book_author);
        TextView book_year = (TextView) findViewById(R.id.book_year);
        TextView book_edition = (TextView) findViewById(R.id.book_edition);
        TextView book_collection = (TextView) findViewById(R.id.book_collection);
        TextView book_isbn = (TextView) findViewById(R.id.book_isbn);
        TextView book_description = (TextView) findViewById(R.id.book_description);
        ProgressBar book_progress= (ProgressBar) findViewById(R.id.book_progress);
        TextView book_pages = (TextView) findViewById(R.id.book_pages);
        TextView book_progress_percentage = (TextView) findViewById(R.id.book_progress_percentage);

        Book book = BookCatalog.list.search(id_book);

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
