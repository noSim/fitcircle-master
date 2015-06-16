package de.jsauerwein.fitcircle;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Simon on 12.06.2015.
 */
public class ExerciseCursorAdapter extends CursorAdapter {

    private final int[] exerciseIcons= new int[] {
            R.drawable.pose1,
            R.drawable.pose2,
            R.drawable.pose3,
            R.drawable.pose4,
            R.drawable.pose5,
            R.drawable.pose6,
            R.drawable.pose7,
            R.drawable.pose8,
            R.drawable.pose9,
            R.drawable.pose10,
            R.drawable.pose11,
            R.drawable.pose12,
            R.drawable.pose13,
            R.drawable.pose14,
            R.drawable.pose15,
            R.drawable.pose16,
            R.drawable.pose17,
            R.drawable.pose18,
            R.drawable.pose19,
            R.drawable.pose20,
            R.drawable.pose21,
            R.drawable.pose22,
            R.drawable.pose23,
            R.drawable.pose24,
            R.drawable.pose25,
            R.drawable.pose26,
            R.drawable.pose27,
            R.drawable.pose28,
            R.drawable.pose29,
            R.drawable.pose30,
            R.drawable.pose31,
            R.drawable.pose32,
            R.drawable.pose33,
            R.drawable.pose34,
            R.drawable.pose35,
            R.drawable.pose36,
            R.drawable.pose37,
            R.drawable.pose38,
            R.drawable.pose39,
            R.drawable.pose40,
            R.drawable.pose41,
            R.drawable.pose42,
            R.drawable.pose43,
            R.drawable.pose44,
            R.drawable.pose45
    };

    public ExerciseCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d("Simon","new view in CursorAdpater");
        View view = LayoutInflater.from(context).inflate(R.layout.main_trainingschedule_overview_exercise, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.poseView = (ImageView) view.findViewById(R.id.main_trainingschedule_workout_type);
        viewHolder.nameView = (TextView) view.findViewById(R.id.main_trainingschedule_workout_name);
        viewHolder.difficultyView = (TextView) view.findViewById(R.id.main_trainingschedule_difficulty);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (cursor != null && cursor.getColumnCount() >= 4) {
            String name = cursor.getString(2);
            String difficulty = cursor.getString(3);
            int typ = cursor.getInt(1);
            viewHolder.difficultyView.setText(context.getResources().getString(R.string.exercise_difficulty).replace("$VALUE",difficulty));
            viewHolder.nameView.setText(name);
            viewHolder.poseView.setImageResource(this.exerciseIcons[typ - 1]);

            Cursor toolsCursor = context.getContentResolver().query( Uri.parse("content://de.jsauerwein.fitcircle.schedule/exercises/" + cursor.getString(cursor.getColumnIndex("_id")) + "/tools"),null,null,null,null);
            Log.d("Simon",toolsCursor.getColumnName(toolsCursor.getColumnIndex("tools")));
        }
        else
        {
            Log.d("Simon", "cursor null");
        }

    }

    private static class ViewHolder {
        private ImageView poseView;
        public TextView nameView;
        public TextView difficultyView;
    }
}
