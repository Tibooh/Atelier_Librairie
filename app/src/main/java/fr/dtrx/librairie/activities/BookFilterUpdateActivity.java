package fr.dtrx.librairie.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.model.DatabaseHelper;

public class BookFilterUpdateActivity extends ActionBarActivity {

    private DatabaseHelper databaseHelper = null;
    EditText edit_text_book_filter_name;
    EditText edit_text_book_title;
    EditText edit_text_book_author;
    EditText edit_text_book_year;
    EditText edit_text_book_edition;
    EditText edit_text_book_description;

    BookFilter bookFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_filter_update);

        edit_text_book_filter_name = (EditText) findViewById(R.id.edit_text_book_filter_name);
        edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);

        bookFilter = (BookFilter) getIntent().getExtras().getSerializable("bookFilterDetails");

        edit_text_book_filter_name.setText(bookFilter.getName());
        edit_text_book_title.setText(bookFilter.getTitle());
        edit_text_book_author.setText(bookFilter.getAuthor());
        edit_text_book_year.setText(bookFilter.getYear());
        edit_text_book_edition.setText(bookFilter.getEdition());
        edit_text_book_description.setText(bookFilter.getDescription());

    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null)
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        return databaseHelper;
    }

    public void btnCreateBookFilter(View view) {
        String book_filter_name = edit_text_book_filter_name.getText().toString();
        String book_title = edit_text_book_title.getText().toString();
        String book_author = edit_text_book_author.getText().toString();
        String book_year = edit_text_book_year.getText().toString();
        String book_edition = edit_text_book_edition.getText().toString();
        String book_description = edit_text_book_description.getText().toString();

        if (book_filter_name.length() > 0) {

            bookFilter.setName(book_filter_name);
            bookFilter.setTitle(book_title);
            bookFilter.setAuthor(book_author);
            bookFilter.setYear(book_year);
            bookFilter.setEdition(book_edition);
            bookFilter.setDescription(book_description);

            try {
                // This is how, a reference of DAO object can be done
                final Dao<BookFilter, Integer> bookFilterDao = getHelper().getBookFilterDao();

                //This is the way to insert data into a database table
                bookFilterDao.update(bookFilter);
                Toast.makeText(getApplicationContext(), "Filtre modifié", Toast.LENGTH_SHORT).show();
                finish();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Le nom du filtre est nul ou n'est pas assez long", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // You'll need this in your class to release the helper when done.
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
