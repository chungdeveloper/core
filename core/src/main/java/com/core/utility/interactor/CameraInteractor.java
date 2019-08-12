package com.core.utility.interactor;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Le Duc Chung on 2018-04-12.
 * on project 'TutorVNandroid'
 */
public interface CameraInteractor {
    void getPhotosFromCamera(int code);

    void getPhotosFromGallery(int code);

    void onResultListener(int requestCode, int resultCode, Intent data);

    void beginCrop(int code, Uri source);
}
