package de.jsauerwein.fitcircle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;


public class TrainingSchedule extends Activity {
    private InteractionModel interactionModel;
    private int currentFragment = InteractionModel.WORKOUT_OVERVIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getActionBar().setBackgroundDrawable(this.getApplicationContext().getResources().getDrawable(R.drawable.actionbar_bg));
        if(savedInstanceState != null) {
            this.currentFragment = savedInstanceState.getInt(InteractionModel.TAG_CURRENT_FRAGMENT);
        }
        setContentView(R.layout.main_trainingschedule_activity);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(InteractionModel.TAG_CURRENT_FRAGMENT, this.interactionModel.getCurrentFragmentId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle arguments = new Bundle();
        arguments.putInt(InteractionModel.TAG_CURRENT_FRAGMENT, this.currentFragment);
        this.interactionModel = new InteractionModel();
        this.interactionModel.setArguments(arguments);
        this.getFragmentManager().beginTransaction().add(this.interactionModel, AppContract.TS_INTERACTION_MODEL).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
