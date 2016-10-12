package io.jaylim.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.Date;

/**
 * Created by jaylim on 10/12/2016.
 *
 */

public class DatePickerActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_DATE =
            "io.jaylim.android.criminalintent.crime_date";

    @Override
    protected Fragment createFragment() {
        return new DatePickerFragment();
    }

    @Override
    protected Fragment setArgument(Fragment fragment) {
        Date crimeDate = (Date) getIntent()
                .getSerializableExtra(EXTRA_CRIME_DATE);
        Log.i("DatePickerActivity", "setArgument(Fragment fragment");
        return DatePickerFragment.newInstance((DatePickerFragment) fragment, crimeDate);
    }


    /*@Override
    protected Fragment setArgument(Fragment fragment) {

        Date crimeDate = (Date) getIntent()
                .getSerializableExtra(EXTRA_CRIME_DATE);

        return DatePickerFragment.newInstance(fragment, crimeDate);
    }*/

    public static Intent newIntent(Context packageContext, Date date) {
        Intent intent = new Intent(packageContext, DatePickerActivity.class);
        intent.putExtra(EXTRA_CRIME_DATE, date);
        return intent;
    }
}
