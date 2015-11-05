package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.model.BookFilterCatalog;

public class BookFilterCreationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_filter_creation);
    }

    public void btnCreateBookFilter(View view) {
        EditText edit_text_book_filter_name = (EditText) findViewById(R.id.edit_text_book_filter_name);
        EditText edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        EditText edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        String book_filter_name = edit_text_book_filter_name.getText().toString();
        String book_title = edit_text_book_title.getText().toString();
        String book_author = edit_text_book_author.getText().toString();

        if (book_filter_name != null && book_filter_name.length() > 0) {
            BookFilterCatalog.list.add(new BookFilter(book_filter_name, book_title, book_author));
            Toast.makeText(getApplicationContext(), "Filtre créé", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Le nom du filtre est nul ou n'est pas assez long", Toast.LENGTH_SHORT).show();
        }
    }

}
