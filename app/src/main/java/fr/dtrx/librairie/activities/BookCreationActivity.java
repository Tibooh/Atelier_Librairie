package fr.dtrx.librairie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.DatabaseHelper;
import fr.dtrx.librairie.scanner.IntentIntegrator;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import fr.dtrx.librairie.scanner.IntentIntegrator;
import fr.dtrx.librairie.scanner.IntentResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BookCreationActivity extends Activity {

    private DatabaseHelper databaseHelper = null;
    Spinner spinner_book_image;
    EditText edit_text_book_title;
    EditText edit_text_book_author;
    EditText edit_text_book_year;
    EditText edit_text_book_edition;
    EditText edit_text_book_description;

    Integer[] imgid = {
      R.mipmap.les_miserables,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_creation);

        spinner_book_image = (Spinner) findViewById(R.id.edit_text_book_image);
        edit_text_book_title = (EditText) findViewById(R.id.edit_text_book_title);
        edit_text_book_author = (EditText) findViewById(R.id.edit_text_book_author);
        edit_text_book_year = (EditText) findViewById(R.id.edit_text_book_year);
        edit_text_book_edition = (EditText) findViewById(R.id.edit_text_book_edition);
        edit_text_book_description = (EditText) findViewById(R.id.edit_text_book_description);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1, imgid);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_book_image.setAdapter(adapter);
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void btnCreateBook(View view) {
        int book_image = (int) (spinner_book_image.getSelectedItem());
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


    public void btnCreateScanningBook(View view) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        //start scanning
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
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
                        "q=isbn:" + scanContent + "&key=YOUR_KEY";
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
                try{ edit_text_book_title.setText(volumeObject.getString("title")); }
                catch(JSONException jse){
                    edit_text_book_title.setText("");
                    jse.printStackTrace();
                }
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
                    edit_text_book_author.setText("");
                    jse.printStackTrace();
                }
                try{ edit_text_book_year.setText(volumeObject.getString("publishedDate")); }
                catch(JSONException jse){
                    edit_text_book_year.setText("");
                    jse.printStackTrace();
                }
                try{ edit_text_book_description.setText(volumeObject.getString("description")); }
                catch(JSONException jse){
                    edit_text_book_description.setText("");
                    jse.printStackTrace();
                }
            }
            catch (Exception e) {
                //no result
                e.printStackTrace();
                reset();
                edit_text_book_title.setText("NOT FOUND");
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
        spinner_book_image.setSelection(0);
        edit_text_book_title.setText("");
        edit_text_book_author.setText("");
        edit_text_book_year.setText("");
        edit_text_book_edition.setText("");
        edit_text_book_description.setText("");
    }

}
