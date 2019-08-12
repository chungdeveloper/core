package com.core.utility.presenter;

import androidx.annotation.StringRes;

/**
 * Created by Le Duc Chung on 2018-04-04.
 * on project 'TutorVNandroid'
 */
public interface BaseFragmentView {

    void showProgressbar(@StringRes int prg_logging);

    void showProgressbar(String content);

    void hideProgressbar();

    void ToastLocal(String content);

    void ToastLocal(@StringRes int content);

    void ToastLocalLong(String content);

    void ToastLocalLong(@StringRes int content);


}
