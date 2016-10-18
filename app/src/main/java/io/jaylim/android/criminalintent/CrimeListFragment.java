package io.jaylim.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jaylim on 10/7/2016.
 *
 */

public class CrimeListFragment extends Fragment {

    private static final String TAG = "CrimeListFragment";

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    // No roles
    @Override public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inform to FragmentManager that onCreateOptionsMenu(...) method
        // of the fragment itself has to be called back.
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override public void  onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // get Crime List from CrimeLab -> set Adapter to RecyclerView ->
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else if (mSelectedCrimeIndex != -1) {
            mAdapter.setCrimes(crimes);
            mAdapter.notifyItemChanged(mSelectedCrimeIndex);
        } else { // Last resort
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private int mSelectedCrimeIndex = -1;

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
        private int mCrimeIndex;

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

        public void bindCrime(Crime crime, int position) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getFormattedDateTime());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
            mCrimeIndex = position;
        }

        @Override
        public void onClick(View v) {
            mSelectedCrimeIndex = mCrimeIndex;
            // Send intent including extra data to Activity Manager
            // so that Child activity (CrimePagerActivity) can be started with proper data.
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }


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

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
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
            holder.bindCrime(crime, position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Conventionally, preferred to call super class at first.
        super.onCreateOptionsMenu(menu, inflater);

        // Inflate toolbar menu objects
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_new_crime :
                Crime crime = new Crime();

                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = NewCrimeActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;

            case R.id.menu_item_show_subtitle :
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean mSubtitleVisible;

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_format_plurals, crimeCount, crimeCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

}












