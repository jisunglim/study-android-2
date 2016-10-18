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

    private static final String EXTRA_CRIME_DATETIME =
            "io.jaylim.android.criminalintent.crime_datetime";

    @Override
    protected Fragment createFragment() {
        Date crimeDate = (Date) getIntent()
                .getSerializableExtra(EXTRA_CRIME_DATETIME);
        return DatePickerFragment.newInstance(crimeDate);
    }


    public static Intent newIntent(Context packageContext, Date date) {
        Intent intent = new Intent(packageContext, DatePickerActivity.class);
        intent.putExtra(EXTRA_CRIME_DATETIME, date);
        return intent;
    }
}
