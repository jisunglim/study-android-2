package io.jaylim.android.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by jaylim on 10/7/2016.
 *
 */
public class CrimeLab {

    //
    private List<Crime> mCrimes;

    // Private static variable of the same class that is the only instance of the class.
    private static CrimeLab sCrimeLab;

    // Public static method that returns the instance of the class,
    // this is the global access point for outer world to get the instance of the singleton class.
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    // Private constructor to restrict instantiation of the class from other classes.
    private CrimeLab(Context context) {

        Log.d("CrimeLab", "Constructor Called");

        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Criminal #" + ( i + 1 ));
            crime.setDate(Calendar.getInstance().getTime());
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    //
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    //
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

}