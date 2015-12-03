package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.functions.FileFunctions;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.DatabaseHelper;
import fr.dtrx.librairie.functions.ImageFunctions;

public class BookCreationActivity extends Activity {

    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int RESULT_LOAD_IMAGE = 1;

    private DatabaseHelper databaseHelper = null;
    private File tmp_image;

    ImageView image_view_book_image;
    EditText edit_text_book_title;
    EditText edit_text_book_author;
    EditText edit_text_book_year;
    EditText edit_text_book_edition;
    EditText edit_text_book_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_creation);

        image_view_book_image = (ImageView) findViewById(R.id.image_view_book_image);
        edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);

        if (FileFunctions.storageDir == null) FileFunctions.storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO /*&& resultCode == RESULT_OK*/)
            saveAndSetImageView(tmp_image.getAbsolutePath());

        if (requestCode == RESULT_LOAD_IMAGE /*&& resultCode == RESULT_OK*/ && data != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);

            if (cursor == null || cursor.getCount() < 1) return; // no cursor or no record. DO YOUR ERROR HANDLING
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex < 0) return; // DO YOUR ERROR HANDLING

            saveAndSetImageView(cursor.getString(columnIndex));

            cursor.close();
        }
    }

    private void saveAndSetImageView(String imagePath) {
        saveImage(imagePath);
        ImageFunctions.setIVBitmap(image_view_book_image, tmp_image.getAbsolutePath());
    }

    private void saveImage(String imagePath) {
        Bitmap bitmap;
        bitmap = ImageFunctions.rescaledBitmap(imagePath);
        bitmap = ImageFunctions.rotateBitmap(bitmap, imagePath);

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

            //TODO Ouvrir une image dans la gallerie n'ouvre pas l'image ... a corriger

            // Continue only if the File was successfully created
            if (photoFile != null) {
                tmp_image = photoFile;
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        }
    }

    public void btnCreateBook(View view) {
        String book_image = tmp_image.getAbsolutePath();
        String book_title = edit_text_book_title.getText().toString();
        String book_author = edit_text_book_author.getText().toString();
        String book_year = edit_text_book_year.getText().toString();
        String book_edition = edit_text_book_edition.getText().toString();
        String book_description = edit_text_book_description.getText().toString();

        if (book_title.length() > 0) {
            if (book_author.length() > 0) {

                // Once click on "Submit", it's first creates the BookDetails object
                final Book book = new Book();

                // Then, set all the values from user input
                book.setImage(book_image);
                book.setTitle(book_title);
                book.setAuthor(book_author);
                book.setYear(book_year);
                book.setEdition(book_edition);
                book.setDescription(book_description);

                try {
                    // This is how, a reference of DAO object can be done
                    final Dao<Book, Integer> bookDao = getHelper().getBookDao();

                    //This is the way to insert data into a database table
                    bookDao.create(book);
                    reset();
                    Toast.makeText(getApplicationContext(), "Livre créé" , Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

		/* You'll need this in your class to release the helper when done */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    // Clear the entered text
    private void reset() {
        image_view_book_image.setImageBitmap(null);
        edit_text_book_title.setText("");
        edit_text_book_author.setText("");
        edit_text_book_year.setText("");
        edit_text_book_edition.setText("");
        edit_text_book_description.setText("");
    }

}
