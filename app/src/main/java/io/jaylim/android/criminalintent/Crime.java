package io.jaylim.android.criminalintent;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jaylim on 10/6/2016.
 *
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate = Calendar.getInstance().getTime();
    private boolean mSolved;

    /** Constructor */
    public Crime() {
        mId = UUID.randomUUID();
    }

    /** Getters and Setters */
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public String getFormattedDateTime() {
        if (mDate != null) {
            return DateFormat.format("EEE, MMM d HH:mm:ss zzz yyyy", mDate).toString();
        }
        return "";
    }

    public String getFormattedDate() {
        if (mDate != null) {
            return DateFormat.format("EEE, MMM d", mDate).toString();
        }
        return "";
    }

    public String getFormattedTime() {
        if (mDate != null) {
            return DateFormat.format("HH:mm:ss", mDate).toString();
        }
        return "";
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    private <T> T checkNullity(T object) {
        if (object != null) {
            return object;
        } else {
            return null;
        }
    }
}
