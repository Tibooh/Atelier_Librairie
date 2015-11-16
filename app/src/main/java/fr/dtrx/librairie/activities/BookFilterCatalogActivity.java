package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.BookCatalog;
import fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.model.BookFilterCatalog;
import fr.dtrx.librairie.model.DatabaseHelper;

public class BookFilterCatalogActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static String ID_FILTER = "fr.dtrx.librairie.ID_FILTER";

    private ListView listView;

    private DatabaseHelper databaseHelper = null;
    private Dao<BookFilter, Integer> bookFilterDao;

    private List<BookFilter> bookFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_filter_catalog);

        listView = (ListView) findViewById(R.id.book_filter_list);

        try {
            bookFilterDao = getHelper().getBookFilterDao();
            BookFilterCatalog.list.clear();
            BookFilterCatalog.list.addAll(bookFilterDao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bookFilters = BookFilterCatalog.list;

        ArrayAdapter<BookFilter> adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1, android.R.id.text1, BookFilterCatalog.list);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this, BookFilterCatalogActivity.class);
        startActivity(intent);
        finish();
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private int id_book_filter = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), BookCatalogActivity.class);
        intent.putExtra(ID_FILTER, ((BookFilter) listView.getItemAtPosition(position)).getBookFilterId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id) {
        // If the pressed row is not a header, update selectedRecordPosition and show dialog for further selection
        id_book_filter = position;
        showDialog();
        return true;
    }

    private void showDialog() {
        // Before deletion of the long pressed record, need to confirm with the user. So, build the AlartBox first
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Add a positive button and it's action. In our case action would be deletion of the data
        alertDialogBuilder.setNegativeButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            // This is how, data from the database can be deleted
                            bookFilterDao.delete(bookFilters.get(id_book_filter));

                            // Removing the same from the List to remove from display as well
                            bookFilters.remove(id_book_filter);
                            listView.invalidateViews();

                            // Reset the value of selectedRecordPosition
                            id_book_filter = -1;
                            populateNoRecordMsg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        alertDialogBuilder.setNeutralButton("Modifier",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            Intent intent = new Intent(getApplicationContext(), BookFilterUpdateActivity.class);
                            intent.putExtra("bookFilterDetails", bookFilters.get(id_book_filter));
                            startActivityForResult(intent, 0);

                            populateNoRecordMsg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        // Add a negative button and it's action. In our case, just hide the dialog box
        alertDialogBuilder.setPositiveButton("Annuler",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        // Now, create the Dialog and show it.
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void populateNoRecordMsg() {
        // If, no record found in the database, appropriate message needs to be displayed.
        if(bookFilters.size() == 0) {
            final TextView tv = new TextView(this);
            tv.setPadding(5, 5, 5, 5);
            tv.setTextSize(15);
            tv.setText("Aucun filtre");
            listView.addFooterView(tv);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BookFilterCatalog.refresh(bookFilterDao);
    }

}
