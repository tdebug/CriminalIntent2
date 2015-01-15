package com.tdebug.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tdebug on 28/11/14.
 */
public class CrimeFragment extends android.support.v4.app.Fragment//Fragment
{
    public static final String EXTRA_CRIME_ID = "com.tdebug.criminalIntent.crime_id";

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final String DIALOG_CHOOSER = "chooser";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CHOOSER = 2;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mSelectButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //mCrime = new Crime();
        //UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:

                if (NavUtils.getParentActivityName(getActivity()) != null )
                {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mCrime.setTitle(mCrime.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCrime.setTitle(s.toString());
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v)
        {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            //DatePickerFragment dialog = new DatePickerFragment();
            DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
            dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
            dialog.show(fm, DIALOG_DATE);
        }}
        );

        mTimeButton = (Button)v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v)
        {
            FragmentManager fm = getActivity().getSupportFragmentManager();

            TimePickerFragment timedialog = TimePickerFragment.newInstance(mCrime.getDate());
            timedialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
            timedialog.show(fm, DIALOG_TIME);
        }
        });


        // chose time or date popup
        mSelectButton = (Button)v.findViewById(R.id.datetime_set);
        mSelectButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v)
            {

                FragmentManager fm = getActivity().getSupportFragmentManager();

                TDChoosingFragment choosingFragment = new TDChoosingFragment();
                choosingFragment.setTargetFragment(CrimeFragment.this, REQUEST_CHOOSER);
                choosingFragment.show(fm, DIALOG_CHOOSER);

            }
        }


        );


        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    public static CrimeFragment newInstance(UUID crimeId)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /*@Override
    public void onPause()
    {
        mCrime.setTitle(mTitleField.getText().toString());
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

       //mCrime.setTitle(mTitleField.getText().toString());
       // if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE)
        {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();

        }

        if (requestCode == REQUEST_TIME)
        {
            Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            updateTime();

        }

        if (requestCode == REQUEST_CHOOSER)
        {
            if (resultCode == TDChoosingFragment.date_code)
            {
                mDateButton.performClick(); //callOnClick();
            }

            if (resultCode == TDChoosingFragment.time_code)
            {
                mTimeButton.performClick();//callOnClick();
            }
        }

    }

    private void updateDate()
    {
        mDateButton.setText(mCrime.getFormattedDate());
    }


    private void updateTime()
    {
       // if (mCrime != null)
        mTimeButton.setText(mCrime.getFormattedTime());
    }


}
