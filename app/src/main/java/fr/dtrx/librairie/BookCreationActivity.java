package fr.dtrx.librairie;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.dtrx.librairie.fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.fr.dtrx.librairie.model.BookLibrary;

public class BookCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_creation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_creation, menu);
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

    public void btnCreateBook(View view) {
        EditText edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        EditText edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        EditText edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        EditText edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        EditText edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);
        String book_title = edit_text_book_title.getText().toString();
        String book_author = edit_text_book_author.getText().toString();
        int book_year = Integer.parseInt(edit_text_book_year.getText().toString());
        String book_edition = edit_text_book_edition.getText().toString();
        String book_description = edit_text_book_description.getText().toString();

        if (book_title != null && book_title.length() > 0) {
            if (book_author != null && book_author.length() > 0) {
                BookLibrary.list.add(new Book(book_title, book_author, book_year, book_edition, book_description));
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
