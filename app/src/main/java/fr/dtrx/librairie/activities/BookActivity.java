package fr.dtrx.librairie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookCatalog;

public class BookActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book);

        Intent intent = getIntent();

        int position_book = intent.getIntExtra(BookCatalogActivity.ID_BOOK, -1);
        if (position_book == -1) return;

        TextView text_view_title = (TextView) findViewById(R.id.book_title);
        TextView text_view_author = (TextView) findViewById(R.id.book_author);
        TextView text_view_year = (TextView) findViewById(R.id.book_year);
        TextView text_view_edition = (TextView) findViewById(R.id.book_edition);
        TextView text_view_description = (TextView) findViewById(R.id.book_description);

        Book book = BookCatalog.list.get(position_book);

        text_view_title.setText(book.getTitle());
        text_view_author.setText(book.getAuthor());
        text_view_year.setText(book.getYear());
        text_view_edition.setText(book.getEdition());
        text_view_description.setText(book.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
