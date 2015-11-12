package fr.dtrx.librairie.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.fragments.BookFragment;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookCatalog;
import fr.dtrx.librairie.model.BookFilter;
import fr.dtrx.librairie.model.BookFilterCatalog;
import fr.dtrx.librairie.model.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class BookCatalogActivity extends FragmentActivity implements AdapterView.OnItemLongClickListener {

    private ListView listView;


    private int id_filter = -1;
    private int id_book = 0;

    public static String ID_BOOK = "fr.dtrx.librairie.ID_BOOK";

    private DatabaseHelper databaseHelper = null;

    private Dao<Book, Integer> bookDao;

    private List<Book> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_catalog);

        Intent intent = getIntent();
        id_filter = intent.getIntExtra(BookFilterCatalogActivity.ID_FILTER, -1);

        listView = (ListView) findViewById(R.id.books_list);


        try {
            bookDao = getHelper().getBookDao();
            BookCatalog.list.clear();
            BookCatalog.list.addAll(bookDao.queryForAll());

            if (id_filter != -1) books = filtered_books(id_filter);
            else books = BookCatalog.list;

            ArrayAdapter<Book> adapter = new ArrayAdapter<>
                    (this,android.R.layout.two_line_list_item, android.R.id.text1, books);

            listView.setAdapter(adapter);

            listView.setOnItemLongClickListener(this);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BookFragment viewer = (BookFragment) getFragmentManager().findFragmentById(R.id.book_fragment);

                    if (viewer == null || !viewer.isInLayout()) {
                        Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                        intent.putExtra("bookDetails", books.get(id_book));
                        startActivity(intent);
                    } else viewer.update(position);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BookCatalog filtered_books(int position_filter) {
        BookCatalog bl = new BookCatalog();
        BookFilter bf = BookFilterCatalog.list.get(position_filter);

        for (Book book : BookCatalog.list)
            if (bf.isSelected(book))
                bl.add(book);

        return bl;
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id) {
        // If the pressed row is not a header, update selectedRecordPosition and show dialog for further selection
        id_book = position;
        showDialog();
        return true;
    }


    private void showDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set the appropriate message into it.
        //alertDialogBuilder.setMessage("");

        // Add a positive button and it's action. In our case action would be deletion of the data
        alertDialogBuilder.setNegativeButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            // This is how, data from the database can be deleted
                            bookDao.delete(books.get(id_book));

                            // Removing the same from the List to remove from display as well
                            books.remove(id_book);
                            listView.invalidateViews();

                            // Reset the value of selectedRecordPosition
                            id_book = -1;
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

                            Intent intent = new Intent(getApplicationContext(), BookCreationActivity.class);
                            intent.putExtra("bookDetails", books.get(id_book));
                            startActivity(intent);

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
        if(books.size() == 0)
        {
            final TextView tv = new TextView(this);
            tv.setPadding(5, 5, 5, 5);
            tv.setTextSize(15);
            tv.setText("No Record Found !!");
            listView.addFooterView(tv);
        }
    }
}
