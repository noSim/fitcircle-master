package de.jsauerwein.fitcircle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WorkoutTrigger extends BroadcastReceiver {

    private final InteractionModel interactionModel;

    public WorkoutTrigger(InteractionModel interactionModel) {
        this.interactionModel = interactionModel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.interactionModel.showFragment(intent.getIntExtra(InteractionModel.TAG_CURRENT_FRAGMENT, -1));
    }
}
