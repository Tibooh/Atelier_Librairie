package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.DatabaseHelper;
import fr.dtrx.librairie.model.BookCatalog;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class BookCreationActivity extends Activity {


    private DatabaseHelper databaseHelper = null;
    EditText edit_text_book_title;
    EditText edit_text_book_author;
    EditText edit_text_book_year;
    EditText edit_text_book_edition;
    EditText edit_text_book_description;
    String book_title;
    String book_author;
    String book_year;
    String book_edition;
    String book_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_creation);

        edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);
        book_title = edit_text_book_title.getText().toString();
        book_author = edit_text_book_author.getText().toString();
        book_year = edit_text_book_year.getText().toString();
        book_edition = edit_text_book_edition.getText().toString();
        book_description = edit_text_book_description.getText().toString();
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
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


    public void btnCreateBook(View view) {


        if (book_title != null && book_title.length() > 0) {
            if (book_author != null && book_author.length() > 0) {

                // Once click on "Submit", it's first creates the TeacherDetails object
                final Book book = new Book();

                // Then, set all the values from user input
                book.setTitle(book_title);
                book.setAuthor(book_author);
                book.setYear(book_year);
                book.setEdition(book_edition);
                book.setDescription(book_description);

                try {
                    // This is how, a reference of DAO object can be done
                    final Dao<Book, Integer> bookDao = getHelper().getBookDao();

                    //This is the way to insert data into a database table
                    bookDao.create(book);
                    reset();
                    Toast.makeText(getApplicationContext(), "Livre créé" , Toast.LENGTH_SHORT).show();


                } catch (SQLException e) {
                    e.printStackTrace();
                }

             /*   BookCatalog.list.add(new Book(book_title, book_author, book_year, book_edition, book_description));
                Toast.makeText(getApplicationContext(), "Livre créé" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/


            } else {
                Toast.makeText(getApplicationContext(), "Le nom de l'auteur est nul ou n'est pas assez long" , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Le titre est nul ou n'est pas assez long" , Toast.LENGTH_SHORT).show();
        }

    }

    // Clear the entered text
    private void reset()
    {
        edit_text_book_title.setText("");
        edit_text_book_author.setText("");
        edit_text_book_year.setText("");
        edit_text_book_edition.setText("");
        edit_text_book_description.setText("");
    }

}
