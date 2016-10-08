package io.jaylim.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jaylim on 10/7/2016.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    // [INFO - HOSTING ACTIVITY] onCreate(Bundle) called
    // [INFO - HOSTING ACTIVITY] Fragment Manager constructed
    // [INFO - HOSTING ACTIVITY] Fragment Transaction processed

    // No roles
    @Override public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
    @Override public void  onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onStop() {
        super.onStop();
    }
    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
    }
    @Override public void onDestroy() {
        super.onDestroy();
    }
    @Override public void onDetach() {
        super.onDetach();
    }

    // get Crime List from CrimeLab -> set Adapter to RecyclerView ->
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            // Last resort
            // mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(0);
        }
    }


    /*
     * A ViewHolder describes
     * 1. an item view (Default) and, if any, additional fields
     * 2. metadata about its place within the RecyclerView.
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Crime mCrime;
        private TextView mTitleTextView; // additional filed
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }


        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getFormattedDate());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            // Send intent including extra data to Activity Manager
            // so that Child activity (CrimeActivity) can be started with proper data.
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
            /* TODO
            startActivityForResult(intent, GET_CRIME_INDEX);
            TODO */
        }
    }

    /* TODO
    private static final int GET_CRIME_INDEX = 1;

    private int mCrimeIndex;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != AppCompatActivity.RESULT_OK) {
            return;
        }
        if (requestCode == GET_CRIME_INDEX) {
            if (data == null) {
                return;
            }
            if ()
            mCrimeIndex = data.getIntExtra("", -1);
        }
    }
    TODO */

    /*
         * Adapters provide a "binding"
         * from an app-specific data set
         * to views that are displayed within a RecyclerView.
         */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Get layout inflater from hosting activity
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            // Inflate layout to view object, which will be added to parent
            // at some other point in time (ViewGroup).
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);

            // put only view object into view holder and return it to RecyclerView
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }
    }
}












