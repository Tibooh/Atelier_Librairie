package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fr.dtrx.librairie.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent intent = new Intent(this, BookCatalogActivity.class);
        startActivity(intent);
    }

    public void btnShowCollectionFilter(View view) {
        Intent intent = new Intent(this, BookFilterCatalogActivity.class);
        startActivity(intent);
    }

}
