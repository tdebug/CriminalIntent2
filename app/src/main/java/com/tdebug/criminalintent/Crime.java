package com.tdebug.criminalintent;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tdebug on 27/11/14.
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
//    private Date mTime;
    private boolean mSolved;



    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Date getDate()
    {
        return mDate;
    }

    public String getFormattedDate()
    {
        return DateFormat.format("EEEE, MMM dd, yyyy", mDate).toString();
    }

    public String getFormattedTime()
    {
        return DateFormat.format("HH:mm", mDate).toString();
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }


    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String toString()
    {
        return mTitle;
    }
}
