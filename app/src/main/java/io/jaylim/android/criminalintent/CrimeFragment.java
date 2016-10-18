package io.jaylim.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jaylim on 10/6/2016.
 *
 */

public class CrimeFragment extends Fragment {

    private static final String TAG = "CrimeFragment";

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String ARG_IS_NEW_CRIME = "is_new_crime";

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Crime mCrime;
    private boolean mIsNewCrime;

    private EditText mTitleField;

    private Button mDateButton;
    private Button mTimeButton;
    private Button mDateButton2;
    private Button mTimeButton2;

    private CheckBox mSolvedCheckBox;

    private Button mNegativeButton;
    private Button mPositiveButton;

    private String mChangedTitle;
    private Date mChangedDate;
    private boolean mChangedSolved;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        mIsNewCrime = getArguments().getBoolean(ARG_IS_NEW_CRIME);

        mChangedTitle = mCrime.getTitle();
        mChangedDate = mCrime.getDate();
        mChangedSolved = mCrime.isSolved();

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        // This instance constructed from onCreate(Bundle)
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        // Title Field
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // Retrieve user input
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mCrime.setTitle(s.toString());
                mChangedTitle = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Date Picker Button
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getFormattedDate());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(mChangedDate);

                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });


        mTimeButton = (Button) v.findViewById(R.id.crime_time);
        mTimeButton.setText(mCrime.getFormattedTime());
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog =
                        TimePickerFragment.newInstance(mChangedDate);

                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        // Solved Check Box
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mCrime.setSolved(isChecked);
                mChangedSolved = isChecked;
            }
        });

        // Date Picker Button
        mDateButton2 = (Button) v.findViewById(R.id.crime_date_2);
        mDateButton2.setText(mCrime.getFormattedDate());
        mDateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DatePickerActivity.newIntent(getActivity(), mChangedDate);
                startActivityForResult(intent, REQUEST_DATE);
            }
        });


        mTimeButton2 = (Button) v.findViewById(R.id.crime_time_2);
        mTimeButton2.setText(mCrime.getFormattedTime());
        mTimeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TimePickerActivity.newIntent(getActivity(), mChangedDate);
                startActivityForResult(intent, REQUEST_TIME);
            }
        });

        mNegativeButton = (Button) v.findViewById(R.id.crime_negative_button);
        if (mIsNewCrime) {
            mNegativeButton.setText(R.string.cancel_new_crime);
            mNegativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrimeLab crimeLab = CrimeLab.get(getActivity());
                    crimeLab.removeCrime(mCrime.getId());
                    getActivity().onBackPressed();
                }
            });
        } else {
            mNegativeButton.setText(R.string.delete_existing_crime);
            mNegativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrimeLab crimeLab = CrimeLab.get(getActivity());
                    crimeLab.removeCrime(mCrime.getId());
                    getActivity().finish();
                }
            });
        }

        mPositiveButton = (Button) v.findViewById(R.id.crime_positive_button);
        if (mIsNewCrime) {
            mPositiveButton.setText(R.string.add_new_crime);
            mPositiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCrime.setTitle(mChangedTitle);
                    mCrime.setDate(mChangedDate);
                    mCrime.setSolved(mChangedSolved);
                    getActivity().finish();
                }
            });
        } else {
            mPositiveButton.setText(R.string.modify_existing_crime);
            mPositiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCrime.setTitle(mChangedTitle);
                    mCrime.setDate(mChangedDate);
                    mCrime.setSolved(mChangedSolved);
                    getActivity().finish();
                }
            });
        }
        return v;

    }

    public static CrimeFragment newInstance(UUID crimeId, boolean isNewCrime) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        args.putBoolean(ARG_IS_NEW_CRIME, isNewCrime);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            mCrime.setDate(date);
            mChangedDate = date;
            mDateButton.setText(getFormattedDate(mChangedDate));
            mDateButton2.setText(getFormattedDate(mChangedDate));

        } else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
//            mCrime.setDate(date);
            mChangedDate = date;
            mTimeButton.setText(getFormattedTime(mChangedDate));
            mTimeButton2.setText(getFormattedTime(mChangedDate));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getFormattedDate(Date date) {
        if (date != null) {
            return DateFormat.format("EEE, MMM d", date).toString();
        }
        return "";

    }

    private String getFormattedTime(Date date) {
        if (date != null) {
            return DateFormat.format("HH:mm:ss", date).toString();
        }
        return "";

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        CrimeLab.get(getActivity()).updateCrime(mCrime);
//    }
}
