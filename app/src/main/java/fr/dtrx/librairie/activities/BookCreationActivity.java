package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.DatabaseHelper;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.IOException;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import fr.dtrx.librairie.scanner.IntentIntegrator;
import fr.dtrx.librairie.scanner.IntentResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.functions.FileFunctions;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.DatabaseHelper;
import fr.dtrx.librairie.functions.ImageFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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

                // Once click on "Submit", it's first creates the TeacherDetails object
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


    public void btnCreateScanningBook(View view) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_TAKE_PHOTO /*&& resultCode == RESULT_OK*/)
            saveAndSetImageView(tmp_image.getAbsolutePath());

        if (requestCode == RESULT_LOAD_IMAGE /*&& resultCode == RESULT_OK*/ && intent != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(intent.getData(), filePathColumn, null, null, null);

            if (cursor == null || cursor.getCount() < 1) return; // no cursor or no record. DO YOUR ERROR HANDLING
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex < 0) return; // DO YOUR ERROR HANDLING

            saveAndSetImageView(cursor.getString(columnIndex));

            cursor.close();
        }
       
       
        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult
                = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //check we have a valid result
        if (scanningResult != null) {
            //get content from Intent Result
            String scanContent = scanningResult.getContents();
            //get format name of data scanned
            String scanFormat = scanningResult.getFormatName();
            Log.v("SCAN", "content: " + scanContent + " - format: " + scanFormat);
            if (scanContent != null && scanFormat != null && scanFormat.equalsIgnoreCase("EAN_13")) {
                String bookSearchString = "https://www.googleapis.com/books/v1/volumes?" +
                        "q=isbn:" + scanContent + "&key=AIzaSyCL0nL1-XvuxmKgMfU4Xt_G2Jka63XEA6s";

                new GetBookInfo().execute(bookSearchString);
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private class GetBookInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... bookURLs) {
            StringBuilder bookBuilder = new StringBuilder();
            for (String bookSearchURL : bookURLs) {
                HttpClient bookClient = new DefaultHttpClient();
                try {
                    HttpGet bookGet = new HttpGet(bookSearchURL);
                    HttpResponse bookResponse = bookClient.execute(bookGet);
                    StatusLine bookSearchStatus = bookResponse.getStatusLine();
                    if (bookSearchStatus.getStatusCode() == 200) {
                        HttpEntity bookEntity = bookResponse.getEntity();
                        InputStream bookContent = bookEntity.getContent();
                        InputStreamReader bookInput = new InputStreamReader(bookContent);
                        BufferedReader bookReader = new BufferedReader(bookInput);
                        String lineIn;
                        while ((lineIn = bookReader.readLine()) != null) {
                            bookBuilder.append(lineIn);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return bookBuilder.toString();
        };

        protected void onPostExecute(String result) {
            //parse search results

            try{
                 //parse results
                JSONObject resultObject = new JSONObject(result);
                JSONArray bookArray = resultObject.getJSONArray("items");
                JSONObject bookObject = bookArray.getJSONObject(0);
                JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");

                //Récupere le titre
                try{ edit_text_book_title.setText(volumeObject.getString("title")); }
                catch(JSONException jse){
                    jse.printStackTrace();
                }

               // Récupere les auteurs
                StringBuilder authorBuild = new StringBuilder("");
                try{
                    JSONArray authorArray = volumeObject.getJSONArray("authors");
                    for(int a=0; a<authorArray.length(); a++){
                        if(a>0) authorBuild.append(", ");
                        authorBuild.append(authorArray.getString(a));
                    }
                    edit_text_book_author.setText(authorBuild.toString());
                }
                catch(JSONException jse){
                    jse.printStackTrace();
                }

               // Récupere l'année
                try{ edit_text_book_year.setText(volumeObject.getString("publishedDate")); }
                catch(JSONException jse){
                    jse.printStackTrace();
                }

                //Récupere la description
                try{ edit_text_book_description.setText(volumeObject.getString("description")); }
                catch(JSONException jse){
                    jse.printStackTrace();
                }

            }
            catch (Exception e) {
                //no result
                e.printStackTrace();
                reset();
                edit_text_book_title.setText("NOT FOUND "+result);
            }
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
