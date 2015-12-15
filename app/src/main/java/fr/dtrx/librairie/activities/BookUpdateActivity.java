package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import fr.dtrx.librairie.R;
import fr.dtrx.librairie.functions.FileFunctions;
import fr.dtrx.librairie.functions.ImageFunctions;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.DatabaseHelper;
import fr.dtrx.librairie.scanner.IntentIntegrator;
import fr.dtrx.librairie.scanner.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class BookUpdateActivity extends Activity {

    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int RESULT_LOAD_IMAGE = 2;

    private DatabaseHelper databaseHelper = null;
    private File tmp_image;

    RatingBar rating_bar_star;
    ImageView image_view_book_image;
    Spinner genreSpinner;
    EditText edit_text_book_title;
    EditText edit_text_book_author;
    EditText edit_text_book_year;
    EditText edit_text_book_edition;
    EditText edit_text_book_collection;
    EditText edit_text_book_isbn;
    EditText edit_text_book_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_update);

        rating_bar_star = (RatingBar) findViewById(R.id.book_rate);
        image_view_book_image = (ImageView) findViewById(R.id.image_view_book_image);
        genreSpinner = (Spinner) findViewById(R.id.spinner);
        edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        edit_text_book_collection = (EditText) findViewById(R.id.edit_text_book_collection);
        edit_text_book_isbn = (EditText) findViewById(R.id.edit_text_book_isbn);
        edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);

        if (FileFunctions.storageDir == null) FileFunctions.storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Book.getGenres());

        // attaching data adapter to spinner
        genreSpinner.setAdapter(dataAdapter);

        Book book = (Book) getIntent().getExtras().getSerializable("bookDetail");

        //tmp_image = new File(book.getImage());
        rating_bar_star.setRating(book.getRate());
        image_view_book_image.setImageBitmap(BitmapFactory.decodeFile(book.getImage()));
        genreSpinner.setSelection(book.getGenrePosition());
        edit_text_book_title.setText(book.getTitle());
        edit_text_book_author.setText(book.getAuthor());
        edit_text_book_year.setText(book.getYear());
        edit_text_book_edition.setText(book.getEdition());
        edit_text_book_collection.setText(book.getCollection());
        edit_text_book_isbn.setText(book.getIsbn());
        edit_text_book_description.setText(book.getDescription());
    }


    private void saveAndSetImageView(String imagePath) {
        saveImage(imagePath);
        image_view_book_image.setImageBitmap(BitmapFactory.decodeFile(tmp_image.getAbsolutePath()));
    }

    private void saveImage(String imagePath) {
        Bitmap bitmap = ImageFunctions.correctBitmap(imagePath);

        tmp_image.delete();
        tmp_image = FileFunctions.saveImage(bitmap);
        try {
            tmp_image.createNewFile();
        } catch (IOException e) {}
    }

    public void btnTakePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = FileFunctions.createImageFile();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                tmp_image = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void btnOpenGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Ensure that there's a gallery activity to handle the intent
        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = FileFunctions.createImageFile();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                tmp_image = photoFile;
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        }
    }

    public void btnCreateBook(View view) {
        float book_rate = rating_bar_star.getRating();
        String book_image = tmp_image != null ? tmp_image.getAbsolutePath() : "";
        String book_genre = genreSpinner.getSelectedItem().toString();
        String book_title = edit_text_book_title.getText().toString();
        String book_author = edit_text_book_author.getText().toString();
        String book_year = edit_text_book_year.getText().toString();
        String book_edition = edit_text_book_edition.getText().toString();
        String book_collection = edit_text_book_collection.getText().toString();
        String book_isbn = edit_text_book_isbn.getText().toString();
        String book_description = edit_text_book_description.getText().toString();

        if (book_title.length() > 0) {
            if (book_author.length() > 0) {

                // Once click on "Submit", it's first creates the TeacherDetails object
                final Book book = new Book();

                // Then, set all the values from user input
                book.setRate(book_rate);
                book.setImage(book_image);
                book.setGenre(book_genre);
                book.setTitle(book_title);
                book.setAuthor(book_author);
                book.setYear(book_year);
                book.setEdition(book_edition);
                book.setCollection(book_collection);
                book.setIsbn(book_isbn);
                book.setDescription(book_description);

                try {
                    // This is how, a reference of DAO object can be done
                    final Dao<Book, Integer> bookDao = getHelper().getBookDao();

                    //This is the way to insert data into a database table
                    bookDao.update(book);
                    Toast.makeText(getApplicationContext(), "Livre modifié", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Le nom de l'auteur est nul ou n'est pas assez long" , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Le titre est nul ou n'est pas assez long", Toast.LENGTH_SHORT).show();
        }
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
            saveAndSetImageView(tmp_image.getAbsolutePath());

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && intent != null) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(intent.getData(), filePathColumn, null, null, null);

            if (cursor == null || cursor.getCount() < 1)
                return; // no cursor or no record. DO YOUR ERROR HANDLING
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex < 0) return; // DO YOUR ERROR HANDLING

            saveAndSetImageView(cursor.getString(columnIndex));

            cursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
