package fr.dtrx.librairie.functions;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.dtrx.librairie.R;

public class FileFunctions {

    public static File storageDir = null;

    public static File saveImage(Bitmap bitmap) {
        File image = null;
        try {
            image = createImageFile();
            FileOutputStream fos = new FileOutputStream(image);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byte_array = baos.toByteArray();
            bos.write(byte_array);
            bos.flush();
            bos.close();
        } catch (IOException e) {}

        return image;
    }

    public static File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = R.string.app_name + "_" + timeStamp;
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {}

        return image;
    }

}
