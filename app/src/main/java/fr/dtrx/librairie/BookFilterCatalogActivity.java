package fr.dtrx.librairie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.dtrx.librairie.fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.fr.dtrx.librairie.model.BookFilterCatalog;

public class BookFilterCatalogActivity extends AppCompatActivity {

    public static String ID_FILTER = "fr.dtrx.librairie.ID_FILTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_filter_catalog);

        ListView listView = (ListView) findViewById(R.id.book_filter_list);

        ArrayAdapter<BookFilter> adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1, android.R.id.text1, BookFilterCatalog.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), CollectionBookActivity.class);
                    intent.putExtra(ID_FILTER, position);
                    startActivity(intent);
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_filter_catalog, menu);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this, BookFilterCatalogActivity.class);
        startActivity(intent);
        finish();
    }
}
