package fr.dtrx.librairie.functions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import java.io.IOException;

public class ImageFunctions {

    public static Bitmap rotateBitmap(Bitmap bitmap, String path) {
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);
        } catch (IOException e) {}

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch(orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:   return rotateImage(bitmap, 0);
            case ExifInterface.ORIENTATION_ROTATE_180:  return rotateImage(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_270:  return rotateImage(bitmap, 180);
            default:                                    return rotateImage(bitmap, 270);
        }
    }

    public static Bitmap rotateImage(Bitmap bitmap, int rotation) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap rescaledBitmap(String string_book_image) {
        int targetW = 72;
        int targetH = 72;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(string_book_image, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(string_book_image, bmOptions);
    }

    public static void setIVBitmap(ImageView image_view_book_image, String string_book_image) {
        Bitmap bitmap = BitmapFactory.decodeFile(string_book_image);
        image_view_book_image.setRotation(90);
        image_view_book_image.setImageBitmap(bitmap);
    }

}
