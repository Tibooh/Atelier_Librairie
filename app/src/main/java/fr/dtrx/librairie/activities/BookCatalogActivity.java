package fr.dtrx.librairie.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.fragments.BookFragment;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.model.BookFilterCatalog;
import fr.dtrx.librairie.model.BookLibrary;

public class BookCatalogActivity extends FragmentActivity {

    private int position_filter = -1;
    public static String ID_BOOK = "fr.dtrx.librairie.ID_BOOK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_catalog);

        Intent intent = getIntent();
        position_filter = intent.getIntExtra(BookFilterCatalogActivity.ID_FILTER, -1);

        BookLibrary books;

        if (position_filter != -1) books = filtered_books(position_filter);
        else books = BookLibrary.list;

        ListView listView = (ListView) findViewById(R.id.books_list);

        ArrayAdapter<Book> adapter = new ArrayAdapter<>
                (this,android.R.layout.two_line_list_item, android.R.id.text1, books);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), BookActivity.class);
                intent.putExtra(ID_BOOK, position);
                startActivity(intent);
            }
        });
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

    /*
    public void onItemChose(Uri objet) {
        BookFragment viewer = (BookFragment) getFragmentManager().findFragmentById(R.id.book_fragment);

        if (viewer == null || !viewer.isInLayout()) {
            Intent detailIntent = new Intent(getApplicationContext(), BookActivity.class);
            detailIntent.setData(objet);
            startActivity(detailIntent);
        } else {
            viewer.afficherDetail(objet);
        }
    }
    */

}
