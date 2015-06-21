package de.jsauerwein.fitcircle;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddExercise extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_trainingschedule_add_exercise, container, false);
        return view;
    }

    public void backToOverview()
    {
        Intent overview = new Intent(AppContract.BROADCAST_ACTION_WORKOUT);
        overview.putExtra(InteractionModel.TAG_CURRENT_FRAGMENT, InteractionModel.WORKOUT_OVERVIEW);
        LocalBroadcastManager.getInstance(this.getActivity()).sendBroadcast(overview);
    }

}
