package com.core.utility.interactor.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;

import com.core.dc.utility.R;
import com.core.utility.interactor.CameraInteractor;
import com.core.utility.utils.ImageProcess;
import com.core.utility.utils.cropimage.Crop;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Le Duc Chung on 2018-04-12.
 * on project 'TutorVNandroid'
 */
@SuppressWarnings("unused")
public class CameraInteractorImpl implements CameraInteractor {

    private static final int PICK_FROM_CAMERA = 325;
    private static final int PICK_FROM_FILE = 987;

    private Context context;
    private Uri mImageCaptureUri;
    private int mCurrentCode;
    private OnPhotoListener onPhotoListener;

    public CameraInteractorImpl(Context context, OnPhotoListener onPhotoListener) {
        this.context = context;
        this.onPhotoListener = onPhotoListener;
    }

    @Override
    public void getPhotosFromCamera(int code) {
        mCurrentCode = code;
        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(context, R.string.camera_not_found, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(packageManager) != null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            Uri mImageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        }
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
        }
    }

    @Override
    public void getPhotosFromGallery(int code) {
        mCurrentCode = code;
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).startActivityForResult(galleryIntent, PICK_FROM_FILE);
            }
        } catch (Exception ex) {
            Toast.makeText(context, R.string.gallery_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResultListener(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (onPhotoListener == null) return;
                onPhotoListener.onPhotos(mCurrentCode, pickFromCameraProcess(data));
                break;
            case PICK_FROM_FILE:
                if (onPhotoListener == null) return;
                onPhotoListener.onPhotos(mCurrentCode, pickFromFileProcess(data));
                break;
            case Crop.REQUEST_CROP:
                if (onPhotoListener == null) return;
                onPhotoListener.onPhotos(mCurrentCode, handleCrop(resultCode, data));
                break;
        }
    }

    private Uri pickFromFileProcess(Intent data) {
        mImageCaptureUri = data.getData();
        Bitmap bmp = ImageProcess.getBitmap(mImageCaptureUri, context.getContentResolver());
        if (bmp == null) return null;
        bmp = ImageProcess.RotateBitmap(bmp,
                ImageProcess.getCameraPhotoOrientation(context, mImageCaptureUri,
                        ImageProcess.getRealPathFromURI(context, mImageCaptureUri)));
//        bmp = ImageProcess.resizeBitmap(bmp, 2040);
        ImageProcess.storeImage(bmp, context);
        return Uri.fromFile(new File(ImageProcess.getUriImage()));
    }

    private Uri pickFromCameraProcess(Intent data) {
        if (data != null) {
            mImageCaptureUri = data.getData();
        } else mImageCaptureUri = null;
        Bitmap bmp;
        if (mImageCaptureUri != null) {
            bmp = ImageProcess.getBitmap(mImageCaptureUri, context.getContentResolver());
            bmp = ImageProcess.RotateBitmap(bmp, ImageProcess.getCameraPhotoOrientation(
                    context,
                    mImageCaptureUri,
                    ImageProcess.getRealPathFromURI(context, mImageCaptureUri)));
        } else {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, filePathColumn, null, null, null);
            if (cursor == null)
                return null;
            cursor.moveToLast();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            mImageCaptureUri = ImageProcess.getImageContentUri(context, new File(filePath));
            bmp = ImageProcess.getBitmap(mImageCaptureUri, context.getContentResolver());
            bmp = ImageProcess.RotateBitmap(bmp, ImageProcess.getCameraPhotoOrientation(context, mImageCaptureUri, ImageProcess.getRealPathFromURI(context, mImageCaptureUri)));
        }
        ImageProcess.storeImage(bmp, context);
        return Uri.fromFile(new File(ImageProcess.getUriImage()));
    }

    @Override
    public void beginCrop(int code, Uri source) {
        mCurrentCode = code;
        Uri destination = Uri.fromFile(new File(context.getCacheDir(), "cropped"));
        Crop.of(source, destination).withAspect(1, 1).start((AppCompatActivity) context);
    }


    private Uri handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Bitmap photoTMP = ImageProcess.getBitmapFromUri(context, Crop.getOutput(result));
//            photoTMP = ImageProcess.RotateBitmap(photoTMP, Application.getInstance().getCurrentOrentication());
            photoTMP = ImageProcess.ResizeBitmap(photoTMP, 800, 800);

            ImageProcess.storeImage(photoTMP, context);
            return Uri.fromFile(new File(ImageProcess.getUriImage()));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(context, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public interface OnPhotoListener {
        void onPhotos(int code, Uri uri);
    }
}
