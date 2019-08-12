package com.core.utility.interactor.impl;

import android.app.DatePickerDialog;
import android.content.Context;

import com.core.utility.interactor.DatePickerInteractor;

public class DateInteractorImpl implements DatePickerInteractor {

    private DatePickerDialog datePickerDialog;

    public DateInteractorImpl(Context context, int startYear, int starthMonth, int startDay, DatePickerDialog.OnDateSetListener onDateSetListener) {
        datePickerDialog = new DatePickerDialog(context, onDateSetListener, startYear, starthMonth, startDay);
    }

    @Override
    public void show() {
        datePickerDialog.show();
    }

}
