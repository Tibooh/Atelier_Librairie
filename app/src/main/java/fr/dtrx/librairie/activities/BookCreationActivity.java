package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookCatalog;

public class BookCreationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_creation);
    }

    public void btnCreateBook(View view) {
        EditText edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        EditText edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        EditText edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        EditText edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        EditText edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);
        String book_title = edit_text_book_title.getText().toString();
        String book_author = edit_text_book_author.getText().toString();
        String book_year = edit_text_book_year.getText().toString();
        String book_edition = edit_text_book_edition.getText().toString();
        String book_description = edit_text_book_description.getText().toString();

        if (book_title != null && book_title.length() > 0) {
            if (book_author != null && book_author.length() > 0) {
                BookCatalog.list.add(new Book(book_title, book_author, book_year, book_edition, book_description));
                Toast.makeText(getApplicationContext(), "Livre créé" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Le nom de l'auteur est nul ou n'est pas assez long" , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Le titre est nul ou n'est pas assez long" , Toast.LENGTH_SHORT).show();
        }
    }

}
