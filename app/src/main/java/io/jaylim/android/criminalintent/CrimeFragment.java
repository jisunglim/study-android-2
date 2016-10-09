package io.jaylim.android.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by jaylim on 10/6/2016.
 */

public class CrimeFragment extends Fragment {
    private final static String TAG = "CrimeFragment";

    private Crime mCrime;

    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;


    // [INFO - HOSTING ACTIVITY] onCreate(Bundle) called
    // [INFO - HOSTING ACTIVITY] Fragment Manager constructed
    // [INFO - HOSTING ACTIVITY] Fragment Transaction processed

    // Called when the fragment is first attached to its context.
    @Override public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

    // Instantiate Fragment Class
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    // Inflate layout of fragment views and return the view objects to hosting activity
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        // This instance constructed from onCreate(Bundle)
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        //
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // Retrieve user input
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getFormattedDate());
        mDateButton.setEnabled(false);

        //
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;

    }

    private static final String ARG_CRIME_ID = "crime_id";

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    // [INFO - HOSTING ACTIVITY] onCreate(Bundle) backed
    // Initialize some values. If any, restore instance state would be backed.
    // Also, we can call onViewStateRestored() to restore instance state.
    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()");
    }
    // onViewStateRestored() may be called, so that restore some states
    @Override public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
    @Override public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }
    // [INFO - HOSTING ACTIVITY] onStart()
    // [INFO - HOSTING ACTIVITY] onResume()
    @Override public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }
    @Override public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }
    // [INFO - HOSTING ACTIVITY] onPause()
    @Override public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }
    // [INFO - HOSTING ACTIVITY] onStop()
    // OnSaveInstanceState() may be called, so that store some states.
    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    // View Objects with its hirarchical states are destroyed
    @Override public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }
    // Fragment is no longer in use.
    @Override public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
    // Fragment is no longer attached to its activity.
    @Override public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }
    // [INFO - HOSTING ACTIVITY] onDestroy()
}
