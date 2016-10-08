package io.jaylim.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by jaylim on 10/7/2016.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    private static final String TAG = "CrimeListActivity";


    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    // Life Cycle logging
    @Override protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);
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
