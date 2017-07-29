package ir.ac.ut.ece.moallem.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by saeed on 10/5/16.
 */
public class FileUtil {
    public static String saveBitmapToInternalStorage(Context context, Bitmap bitmapImage, String directoryName, String imageName) {
        File f = context.getFilesDir();
        f = new File(String.format("%s/%s", f.getAbsolutePath(), directoryName));
        if (!f.exists())
            f.mkdirs();
        f = new File(f, imageName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f.getAbsolutePath();
    }
 public static String saveFileToInternalStorage(Context context, File file, String directoryName, String fileName) {
     File f = context.getFilesDir();
     f = new File(String.format("%s/%s", f.getAbsolutePath(), directoryName));
     if (!f.exists())
         f.mkdirs();
     f = new File(f, fileName);
     FileOutputStream fos = null;
     try {
         fos = new FileOutputStream(f);
         Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
         bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
         //fos.write(file.getAbsolutePath().getBytes());
         //fos.close();
     } catch (Exception e) {
         e.printStackTrace();
     } finally {
         try {
             fos.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     return f.getAbsolutePath();





    }

    public static String getDirectoryPath(Context context, String directoryName) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);
        return directory.getAbsolutePath();
    }

    public static String getFileNameFromURL(String urlString) {
        return urlString.substring(urlString.lastIndexOf('/') + 1);
    }

    public static Bitmap loadImageFromStorage(Context context, String path, String imageName) {
        try {
            File f = context.getFilesDir();
            f = new File(String.format("%s/%s/%s", f.getAbsolutePath(), path, imageName));
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
