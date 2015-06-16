package de.jsauerwein.fitcircle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

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

    private final List<Exercise> exercises;

    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        super(context, R.layout.main_trainingschedule_overview_exercise, exercises);
        this.exercises = exercises;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.main_trainingschedule_overview_exercise, null);
            viewHolder = new ViewHolder();
            viewHolder.poseView = (ImageView) convertView.findViewById(R.id.main_trainingschedule_workout_type);
            viewHolder.nameView = (TextView) convertView.findViewById(R.id.main_trainingschedule_workout_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Exercise exercise = this.exercises.get(position);
        viewHolder.poseView.setImageResource(this.exerciseIcons[exercise.getWorkoutType() - 1]);
        viewHolder.nameView.setText(exercise.getName());

        return convertView;
    }

    private static class ViewHolder {
        private ImageView poseView;
        public TextView nameView;
    }
}
