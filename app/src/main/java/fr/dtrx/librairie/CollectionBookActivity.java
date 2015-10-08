package fr.dtrx.librairie;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.dtrx.librairie.fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.fr.dtrx.librairie.model.BookFilterCatalog;
import fr.dtrx.librairie.fr.dtrx.librairie.model.BookLibrary;

public class CollectionBookActivity extends AppCompatActivity {

    private int position_filter = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_book);

        Intent intent = getIntent();
        position_filter = intent.getIntExtra(BookFilterCatalogActivity.ID_FILTER, -1);

        /*Book[] books_list = {
                new Book("aaa", "bbb"),
                new Book("ccc", "ddd"),
                new Book("eee", "fff"),
                new Book("ggg", "hhh"),
                new Book("iii", "jjj"),
                new Book("kkk", "lll")
        };*/
        BookLibrary books = null;

        if (position_filter != -1) books = filtered_books(position_filter);
        else books = BookLibrary.list;

        ListView listView = (ListView) findViewById(R.id.books_list);

        ArrayAdapter<Book> adapter = new ArrayAdapter<>
                (this,android.R.layout.two_line_list_item, android.R.id.text1, books);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collection_book, menu);
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
        } else if (id == R.id.action_delete) {
            if (position_filter > -1) {
                finish();
                BookFilterCatalog.list.remove(position_filter);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public BookLibrary filtered_books(int position_filter) {
        BookLibrary bl = new BookLibrary();
        BookFilter bf = BookFilterCatalog.list.get(position_filter);

        for (int i = 0; i < BookLibrary.list.size(); i++) {
            Book book = BookLibrary.list.get(i);
            if (bf.isSelected(book)) bl.add(book);
        }

        return bl;
    }

}
