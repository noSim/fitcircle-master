package de.jsauerwein.fitcircle;

import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

public class InteractionModel extends Fragment {

    public static final int WORKOUT_OVERVIEW = 1;
    public static final int WORKOUT_ADDING = 2;

    public static final String TAG_CURRENT_FRAGMENT = "CURRENT_FRAGMENT";

    private int currentFragmentId = 0;
    private Fragment currentFragment;

    private WorkoutTrigger trigger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentFragmentId = getArguments().getInt(TAG_CURRENT_FRAGMENT);
        this.setRetainInstance(true);
        this.trigger = new WorkoutTrigger(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.showFragment();
        LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(this.trigger, new IntentFilter(AppContract.BROADCAST_ACTION_WORKOUT));
    }

    private void showFragment() {
        this.showFragment(this.currentFragmentId);
    }

    public void showFragment(int fragmentId) {
        Fragment fragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch(fragmentId) {
            case WORKOUT_OVERVIEW:
                fragment = new TrainingScheduleOverview();

                if (this.currentFragment != null) {
                    transaction.remove(this.currentFragment);
                }
                transaction.add(R.id.fragment_container, fragment);

                break;
            case WORKOUT_ADDING:
                fragment = new AddExercise();
                transaction.replace(R.id.fragment_container, fragment).addToBackStack("overview");
                break;
            default:
                throw new UnsupportedOperationException("Unknown fragment.");
        }
        this.currentFragment = fragment;
        transaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this.getActivity()).unregisterReceiver(this.trigger);
    }

    public int getCurrentFragmentId() {
        return this.currentFragmentId;
    }
}
