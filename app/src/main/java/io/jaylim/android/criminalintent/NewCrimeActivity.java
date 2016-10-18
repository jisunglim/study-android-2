package io.jaylim.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;
import java.util.UUID;

/**
 * Created by jaylim on 10/16/2016.
 *
 */
public class NewCrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID =
            "io.jaylim.android.criminalintent.crime_id";

    private UUID mCrimeId;

    @Override
    protected Fragment createFragment() {
        mCrimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(mCrimeId, true);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext ,NewCrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    public void onBackPressed() {
        CrimeLab crimeLab = CrimeLab.get(this);
        crimeLab.removeCrime(mCrimeId);

        super.onBackPressed();
    }
}
