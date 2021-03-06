package com.tdebug.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tdebug on 07/12/14.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.tdebug.criminalIntent.date";

    private Date mDate;

    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        //Create a Calendar to get a year, month and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);


        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Translate year, month, day into a Date object using a calendar
                mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth, hour, minute, second).getTime();

                //Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);

            }
        });

        return new AlertDialog.Builder(getActivity())
            .setView(v)
            .setTitle(R.string.data_picker_title)
            //.setPositiveButton(android.R.string.ok, null)
            .setPositiveButton(
                    android.R.string.ok,
        new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which)
        {
            sendResult(Activity.RESULT_OK);
        }
        })
            .create();
    }

    private void sendResult(int resultCode)
    {
        if (getTargetFragment() == null)
        {
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
