package io.jaylim.android.criminalintent;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jaylim on 10/10/2016.
 *
 */

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME =
            "io.jaylim.android.criminalintent.time";

    private static final String ARG_DATETIME = "datetime";

    public static TimePickerFragment newInstance(Date date) {
        return newInstance(new TimePickerFragment(), date);
    }

    public static TimePickerFragment newInstance(TimePickerFragment timePickerFragment, Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATETIME, date);

        timePickerFragment.setArguments(args);
        return timePickerFragment;
    }

    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;

    private void initDateTime() {
        Date date = (Date) getArguments().getSerializable(ARG_DATETIME);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

    }

    // USE VIEW
    private TimePicker mTimePicker2;
    private Button mCancelButton;
    private Button mOkButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        initDateTime();

        View view = inflater.inflate(R.layout.fragment_time, container, false);

        mTimePicker2 = (TimePicker) view.findViewById(R.id.dialog_time_2_time_picker);
        setTimePicker(TIME_PICKER_VIEW);


        mCancelButton = (Button) view.findViewById(R.id.dialog_time_2_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mOkButton = (Button) view.findViewById(R.id.dialog_time_2_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setTime(TIME_PICKER_VIEW);
                Date datetime = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute).getTime();

                setSelectedDateResult(datetime);
                getActivity().finish();
            }
        });

        return view;
    }

    private void setSelectedDateResult(Date date) {
        Intent data = new Intent();
        data.putExtra(EXTRA_TIME, date);
        getActivity().setResult(RESULT_OK, data);
    }

    // USE DIALOG

    private TimePicker mTimePicker;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initDateTime();

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_date_time_picker);
        mTimePicker.setIs24HourView(true);
        setTimePicker(TIME_PICKER_DIALOG);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setTime(TIME_PICKER_DIALOG);

                                Date datetime = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute).getTime();
                                sendResult(Activity.RESULT_OK, datetime);

                            }
                        }
                )
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private static final int TIME_PICKER_DIALOG = 0;
    private static final int TIME_PICKER_VIEW = 1;


    private void setTimePicker(int selectDatePicker) {
        if (selectDatePicker == TIME_PICKER_DIALOG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTimePicker.setHour(mHour);
                mTimePicker.setMinute(mMinute);
            } else {
                mTimePicker.setCurrentHour(mHour);
                mTimePicker.setCurrentMinute(mMinute);
            }
        } else if (selectDatePicker == TIME_PICKER_VIEW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTimePicker2.setHour(mHour);
                mTimePicker2.setMinute(mMinute);
            } else {
                mTimePicker2.setCurrentHour(mHour);
                mTimePicker2.setCurrentMinute(mMinute);
            }
        }
    }

    private void setTime(int selectDatePicker) {
        if (selectDatePicker == TIME_PICKER_DIALOG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mHour = mTimePicker.getHour();
                mMinute = mTimePicker.getMinute();
            } else {
                mHour = mTimePicker.getCurrentHour();
                mMinute = mTimePicker.getCurrentMinute();
            }
        } else if (selectDatePicker == TIME_PICKER_VIEW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mHour = mTimePicker2.getHour();
                mMinute = mTimePicker2.getMinute();
            } else {
                mHour = mTimePicker2.getCurrentHour();
                mMinute = mTimePicker2.getCurrentMinute();
            }
        }
    }
}
