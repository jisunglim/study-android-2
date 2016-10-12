package io.jaylim.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by jaylim on 10/7/2016.
 *
 */

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    protected Fragment setArgument(Fragment fragment) {
        return fragment;
    }


   /*
    * What should activity do to host fragments
    * 1. Define the position where fragment view put
    * 2. Manage lifecycle of fragment instance
    */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate activity_fragment layout
        setContentView(R.layout.activity_fragment);

        // Get fragment manager and find fragment view object
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        fragment = setArgument(fragment);
    }
}
