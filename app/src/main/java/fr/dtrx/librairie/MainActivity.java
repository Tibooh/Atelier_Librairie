package fr.dtrx.librairie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fr.dtrx.librairie.fr.dtrx.librairie.model.BookFilterCatalog;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Intent intent = new Intent(this, BookCreationActivity.class);
        startActivity(intent);
    }

    public void btnCreateBookFilter(View view) {
        Intent intent = new Intent(this, BookFilterCreationActivity.class);
        startActivity(intent);
    }

    public void btnShowCollection(View view) {
        Intent intent = new Intent(this, CollectionBookActivity.class);
        startActivity(intent);
    }

    public void btnShowCollectionFilter(View view) {
        Intent intent = new Intent(this, BookFilterCatalogActivity.class);
        startActivity(intent);
    }

}
