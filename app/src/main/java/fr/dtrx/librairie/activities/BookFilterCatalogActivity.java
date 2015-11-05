package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.model.BookFilterCatalog;

public class BookFilterCatalogActivity extends Activity {

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
                    Intent intent = new Intent(view.getContext(), BookCatalogActivity.class);
                    intent.putExtra(ID_FILTER, position);
                    startActivity(intent);
                }
            }
        );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this, BookFilterCatalogActivity.class);
        startActivity(intent);
        finish();
    }

}
