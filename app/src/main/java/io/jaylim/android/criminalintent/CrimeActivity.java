package io.jaylim.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String TAG = "CrimeActivity";

    private static final String EXTRA_CRIME_ID =
            "io.jaylim.android.criminalintent.crime_id";

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    // Life Cycle logging
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle)");
    }
    @Override protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }
    @Override protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }
    @Override protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }
    @Override protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
