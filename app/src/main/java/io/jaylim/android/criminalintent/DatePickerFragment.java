package io.jaylim.android.criminalintent;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jaylim on 10/10/2016.
 *
 */

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "io.jaylim.android.criminalintent.date";

    private static final String ARG_DATETIME = "datetime";

    public static DatePickerFragment newInstance(Date date) {
        return newInstance(new DatePickerFragment(), date);
    }

    public static DatePickerFragment newInstance(DatePickerFragment datePickerFragment, Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATETIME, date);

        datePickerFragment.setArguments(args);
        return datePickerFragment;
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

    private DatePicker mDatePicker2;
    private Button mCancelButton;
    private Button mOkButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        initDateTime();

        View view = inflater.inflate(R.layout.dialog_date_2, container, false);

        mDatePicker2 = (DatePicker) view.findViewById(R.id.dialog_date_2_date_picker);
        setDatePicker();

        mCancelButton = (Button) view.findViewById(R.id.dialog_date_2_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mOkButton = (Button) view.findViewById(R.id.dialog_date_2_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setDate();
                Date datetime = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute).getTime();

                setSelectedDateResult(datetime);
                getActivity().finish();
            }
        });

        return view;
    }

    private void setSelectedDateResult(Date date) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DATE, date);
        getActivity().setResult(RESULT_OK, data);
    }

    // USE DIALOG

    private DatePicker mDatePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initDateTime();

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        setDatePicker();

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setDate();

                                Date datetime = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute).getTime();
                                sendResult(RESULT_OK, datetime);

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
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void setDatePicker() {
        mDatePicker.init(mYear, mMonth, mDay, null);

    }

    private void setDate() {
        mYear = mDatePicker2.getYear();
        mMonth = mDatePicker2.getMonth();
        mDay = mDatePicker2.getDayOfMonth();
    }
}
