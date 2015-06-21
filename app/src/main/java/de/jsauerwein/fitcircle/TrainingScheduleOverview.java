package de.jsauerwein.fitcircle;


import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Time;
import android.util.Log;
import android.view.*;
import android.view.animation.Transformation;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class TrainingScheduleOverview extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ExerciseCursorAdapter cursorAdapter;
    private boolean isPlaying;
    private ContentObserver observer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_trainingschedule_overview_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ListView exerciseList = (ListView) this.getActivity().findViewById(R.id.main_trainingschedule_exercises);

        cursorAdapter = new ExerciseCursorAdapter(this.getActivity(),null,0);
        exerciseList.setAdapter(cursorAdapter);
        getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        observer = new DataObserver(new Handler());
        getActivity().getContentResolver().registerContentObserver(TrainingScheduleContract.Exercises.CONTENT_URI, true, observer);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getContentResolver().unregisterContentObserver(observer);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.workout_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (isPlaying)
        {
            menu.findItem(R.id.play).setVisible(false);
            menu.findItem(R.id.pause).setVisible(true);
        }
        else
        {
            menu.findItem(R.id.play).setVisible(true);
            menu.findItem(R.id.pause).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                Intent add = new Intent(AppContract.BROADCAST_ACTION_WORKOUT);
                add.putExtra(InteractionModel.TAG_CURRENT_FRAGMENT, InteractionModel.WORKOUT_ADDING);
                LocalBroadcastManager.getInstance(this.getActivity()).sendBroadcast(add);
                break;
            case R.id.play:
                isPlaying = true;
                getActivity().invalidateOptionsMenu();
                ContentValues values = new ContentValues();
                values.put(ExerciseTable.COLUMN_DATE, System.currentTimeMillis()/1000);
                getActivity().getContentResolver().update(Uri.parse("content://de.jsauerwein.fitcircle.schedule/exercises/1"),values, null, null );
                break;
            case R.id.pause:
                isPlaying = false;
                getActivity().invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("Simon", "loader creating");
        Uri baseUri =  TrainingScheduleContract.Exercises.CONTENT_URI;
        return new CursorLoader(this.getActivity(), baseUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("Simon", "load finished");
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    public void restartLoader()
    {
        getLoaderManager().restartLoader(0,null,this);
    }

    public class DataObserver extends android.database.ContentObserver{

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DataObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            if (TrainingScheduleContract.URI_MATCHER.match(uri) == TrainingScheduleContract.EXERCISE_LIST_SINGLE_ITEM) {
               restartLoader();
            }
        }
    }

}
