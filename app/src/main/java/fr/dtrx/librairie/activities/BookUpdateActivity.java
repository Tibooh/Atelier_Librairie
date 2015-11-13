package fr.dtrx.librairie.activities;

import android.content.Intent;
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
import fr.dtrx.librairie.model.DatabaseHelper;

public class BookUpdateActivity extends ActionBarActivity {

    private DatabaseHelper databaseHelper = null;
    EditText edit_text_book_title;
    EditText edit_text_book_author;
    EditText edit_text_book_year;
    EditText edit_text_book_edition;
    EditText edit_text_book_description;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_update);

        edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);

        book = (Book) getIntent().getExtras().getSerializable("bookDetail");

        edit_text_book_title.setText(book.getTitle());
        edit_text_book_author.setText(book.getAuthor());
        edit_text_book_year.setText(book.getYear());
        edit_text_book_edition.setText(book.getEdition());
        edit_text_book_description.setText(book.getDescription());
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void btnCreateBook(View view) {
        String book_title = edit_text_book_title.getText().toString();
        String book_author = edit_text_book_author.getText().toString();
        String book_year = edit_text_book_year.getText().toString();
        String book_edition = edit_text_book_edition.getText().toString();
        String book_description = edit_text_book_description.getText().toString();

        if (book_title != null && book_title.length() > 0) {
            if (book_author != null && book_author.length() > 0) {

                // Once click on "Submit", it's first creates the TeacherDetails object

                // Then, set all the values from user
                book.setTitle(book_title);
                book.setAuthor(book_author);
                book.setYear(book_year);
                book.setEdition(book_edition);
                book.setDescription(book_description);

                try {
                    // This is how, a reference of DAO object can be done
                    final Dao<Book, Integer> bookDao = getHelper().getBookDao();

                    //This is the way to insert data into a database table
                    bookDao.update(book);

                    Toast.makeText(getApplicationContext(), "Livre modifié", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), BookCatalogActivity.class);
                    startActivity(intent);



                } catch (SQLException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(getApplicationContext(), "Le nom de l'auteur est nul ou n'est pas assez long" , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Le titre est nul ou n'est pas assez long", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

		/*
		 * You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


}


