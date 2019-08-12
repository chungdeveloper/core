package com.core.utility.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SuppressWarnings("ALL")
public class FilesUtils {
    public static final int MAX_IMAGE_SIZE = 1280;

    /* public static TypedFile getTypedFileFromUri(Context context, Uri uri) {
         String mimetype;
         if("content".equalsIgnoreCase(uri.getScheme())) {
             mimetype = context.getContentResolver().getType(uri);
         } else {
             String path = uri.getPath();
             path = path.replaceAll("[^a-zA-Z_0-9\\.\\-\\(\\)\\%]", "");
             String extension = MimeTypeMap.getFileExtensionFromUrl(path);
             mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
         }

         if(mimetype == null) {
             return null;
         }

         String path = getPath(context, uri);
         // File path and mimetype are required
         if(path == null)
             return null;

         File file = new File(path);

         return new TypedFile(mimetype, file);
     }*/

    public static String readFileString(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public static String resizeFile(Context context, String filePath) {
        String folder = "cache";
        File mkDir = new File(
                context.getFilesDir(),
//                Environment.getExternalStorageDirectory(), ".shokuji/"
                "KCP/"
                        + folder);
        mkDir.mkdirs();
        String id = "" + System.currentTimeMillis();
        File pictureFile = new File(
                context.getFilesDir(),
//                Environment.getExternalStorageDirectory(), ".shokuji/"
                "KCP/"
                        + folder + "/" + id + ".jpg");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) return filePath;
        int width = bitmap.getWidth();
        float scale = (float) width / (float) MAX_IMAGE_SIZE;
        if (bitmap.getHeight() / MAX_IMAGE_SIZE > scale) {
            scale = bitmap.getHeight() / MAX_IMAGE_SIZE;
        }
        if (scale > 0) {
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmap.getWidth() / scale), (int) ((float) bitmap.getHeight() / scale), false);
        } else {
            return filePath;
        }
        int angle = 0;
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    angle = 0;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            angle = 0;
        }
        if (bitmap == null) return filePath;
        Matrix mat = new Matrix();
//        if (angle == 0 && bitmap.getWidth() > bitmap.getHeight())
//            mat.postRotate(90);
//        else
        if (angle > 0)
            mat.postRotate(angle);
        try {
            float pp = (bitmap.getByteCount() / (10f * 1024 * 1024));
            Log.d("PP RESIZE", "" + pp);
            if (pp > 1 || scale > 0) {
                if (pp <= 1 && scale > 0) {
                    pp = 1;
                }
                FileOutputStream purge = new FileOutputStream(pictureFile);
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true).compress(Bitmap.CompressFormat.JPEG, (int) (100f / pp), purge);
                purge.close();
                if (!TextUtils.isEmpty(pictureFile.getPath()))
                    return pictureFile.getPath();
                else return filePath;
            } else return filePath;

        } catch (FileNotFoundException e) {
            Log.d("DG_DEBUG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("DG_DEBUG", "Error accessing file: " + e.getMessage());
        }
        if (!TextUtils.isEmpty(pictureFile.getPath()))
            return pictureFile.getPath();
        else return filePath;
    }

    public static String encodeToBase64(Bitmap image) {
        try {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void converToFileImage(Context mContext, Bitmap bitmap, String id_photo) {
        try {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath(), "kcp_testvalues_img");
            if (!file.exists()) {
                file.mkdirs();
            }
            String uriSting = (file.getAbsolutePath() + "/"
                    + System.currentTimeMillis() + id_photo + ".jpg");
            FileOutputStream out = null;
            String filename = uriSting;
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImageById(final String id_photo) {
        if (id_photo == null) return null;
        Bitmap bitmap = null;
        File dir = new File("/sdcard/kcp_testvalues_img");
        String[] fileNames = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (new File(dir, name).isDirectory())
                    return false;
                return name.toLowerCase().endsWith(id_photo + ".png");
            }
        });
        if (fileNames != null)
            bitmap = BitmapFactory.decodeFile(dir.getPath() + "/" + fileNames[0]);
        return bitmap;
    }

    public static String getFileExtension(Context context, Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @SuppressWarnings("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            else if (isGoogleDocUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static boolean isGoogleDocUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(byte[] data, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //options.inPurgeable = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }


    /** Return combined path to file
     *
     * @param node - parent node
     * @param filename - name of file
     * @param fileExtension - extension of file
     * @return String - path to file
     */
   /* public static String getFilePath(Node node, String filename, String fileExtension) {
        String path = node.linkPath;
        if(!path.endsWith("/"))
            path += "/";

        path += filename + "." + fileExtension;

        return path;
    }

    */

    /**
     * Opens file from uri for reading and write its content to temporary file in cache directory
     *
     * @param context
     * @param fileUri - uri of file to save
     * @return - built TypedFile
     *//*
    public static TypedFile buildTypedFile(Context context, Uri fileUri) {
        TypedFile typedFile = null;
        ContentResolver cr = context.getContentResolver();

        try {
            ParcelFileDescriptor descriptor = cr.openFileDescriptor(fileUri, "r");
            InputStream is = new FileInputStream(descriptor.getFileDescriptor());

            File outputFile = File.createTempFile("prefix", "extension", context.getCacheDir());
            OutputStream os = new FileOutputStream(outputFile);

            outputFile.setWritable(true);


            byte[] buffer = new byte[1024];
            int length;

            while((length = is.read(buffer)) > 0){
                os.write(buffer, 0, length);
            }

            typedFile = new TypedFile(cr.getType(fileUri), outputFile);
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return typedFile;
    }*/
    public static String createFileDirectory(String pathDirectory) {
        File file = new File(pathDirectory);
        if (!file.exists()) {
            return file.mkdirs() ? pathDirectory : null;
        }
        return pathDirectory;
    }

    public static String createTextFile(String data, String path, String fileName) {
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                createFileDirectory(path);
            }
            File gpxfile = new File(directory, fileName);
            if (!gpxfile.exists()) {
                gpxfile.createNewFile();
            }
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            return "";
        }
        return path + fileName;
    }

    public static Uri getContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID},
                MediaStore.Audio.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Audio.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static boolean isExist(String path) {
        return path != null && new File(path).exists();
    }

    public static void deleteFile(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteFile(child);

        fileOrDirectory.delete();
    }

    public static String renameFile(String path, String name) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) throw new FileNotFoundException();
        return file.renameTo(new File(name)) ? name : null;
    }

    public static byte[] readBytesFromFile(String filePath) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytesArray;
    }

    public static String createMusicListFile(List<String> files, String outputDir) {
        String data = "";
        for (String str : files) {
            data += "file " + "'" + str + "'" + "\n";
        }
        return createTextFile(data, outputDir, System.nanoTime() + ".txt");
    }

    public static boolean unZip(String path, String name) {
        InputStream is;
        ZipInputStream zis;
        try {
            String filename;
            is = new FileInputStream(path + name);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
