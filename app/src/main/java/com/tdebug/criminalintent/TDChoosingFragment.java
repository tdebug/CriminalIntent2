package com.tdebug.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

/**
 * Created by tdebug on 14/12/14.
 */
public class TDChoosingFragment extends DialogFragment {

    private static final String EXTRA_RETURN = "com.tdebug.criminalIntent.chooser";

    public static final int time_code = 1;
    public static final int date_code = 2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.timedate_chooser, null);

        AlertDialog.Builder a = new AlertDialog.Builder(getActivity());

        a.setView(v);
        a.setTitle("Time or Date");
        a.setPositiveButton("Time",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(time_code);
                    }
                });
        a.setNeutralButton("Date",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(date_code);
                    }
                });

        return a.create();


    }

    private void sendResult(int resultCode)
    {
        if (getTargetFragment() == null)
        {
            return;
        }

        //Intent i = new Intent();
        //i.putExtra(EXTRA_CHOOSER, mTime);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, null);
    }

}
