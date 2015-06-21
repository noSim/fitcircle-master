package de.jsauerwein.fitcircle;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddExercise extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_trainingschedule_add_exercise, container, false);
        Button button = ((Button) view.findViewById(R.id.btn_add_exercise));
        button.setOnClickListener(new OnAddExerciseClickListener());
        return view;
    }

    public void backToOverview()
    {
        Intent overview = new Intent(AppContract.BROADCAST_ACTION_WORKOUT);
        overview.putExtra(InteractionModel.TAG_CURRENT_FRAGMENT, InteractionModel.WORKOUT_OVERVIEW);
        LocalBroadcastManager.getInstance(this.getActivity()).sendBroadcast(overview);
    }

    private class OnAddExerciseClickListener implements View.OnClickListener
    {
        private String name;
        private String type;
        private String difficulty;
        private boolean isMat;
        private boolean isExpander;
        private boolean isBall;
        private boolean isChair;
        @Override
        public void onClick(View v) {
            name = ((EditText) view.findViewById(R.id.edit_txt_name)).getText().toString();
            type = ((EditText) view.findViewById(R.id.edit_txt_exercise_type)).getText().toString();
            difficulty = ((EditText) view.findViewById(R.id.edit_txt_difficulty)).getText().toString();
            isMat = ((CheckBox) view.findViewById(R.id.tool1_matt)).isChecked();
            isExpander = ((CheckBox) view.findViewById(R.id.tool2_expander)).isChecked();
            isBall = ((CheckBox) view.findViewById(R.id.tool3_ball)).isChecked();
            isChair = ((CheckBox) view.findViewById(R.id.tool4_chair)).isChecked();

            if (validInput())
            {
                addDataToDatabase();
                backToOverview();
            }
            else
            {
                //Show invalid inputs on ui
            }
        }

        private void addDataToDatabase() {
            ContentValues values = new ContentValues();
            values.put(ExerciseTable.COLUMN_NAME, name);
            values.put(ExerciseTable.COLUMN_DIFFICULTY, difficulty);
            values.put(ExerciseTable.COLUMN_TYPE, type);
            Uri baseUri =  TrainingScheduleContract.Exercises.CONTENT_URI;
            Uri target = getActivity().getContentResolver().insert(baseUri, values);

            String id = target.getLastPathSegment();
            Log.d("Simon", id);

            if (isMat)
            {
                addToolToDatabase(id,"1");
            }
            if (isExpander)
            {
                addToolToDatabase(id,"2");
            }
            if (isBall)
            {
                addToolToDatabase(id,"3");
            }
            if (isChair)
            {
                addToolToDatabase(id,"4");
            }
        }

        private void addToolToDatabase(String exercise,String tool){
            ContentValues toolValues = new ContentValues();
            toolValues.put(ExerciseToolsTable.COLUMN_EXERCISE_ID, exercise);
            toolValues.put(ExerciseToolsTable.COLUMN_TOOL_ID, tool);
            Uri toolsBaseUri = Uri.parse("content://de.jsauerwein.fitcircle.schedule/exercises/" + exercise + "/tools");
            Uri uri = getActivity().getContentResolver().insert(toolsBaseUri, toolValues);
            Log.d("Simon",uri.toString());
        }

        private boolean validInput() {
            boolean valid = true;
            if (name == null || name.equals(""))
            {
                valid = false;
            }
            if (type == null || type.equals("") || Integer.parseInt(type) > 45 || Integer.parseInt(type) <= 0)
            {
                valid = false;
            }
            if (difficulty == null || difficulty.equals(""))
            {
                valid = false;
            }
            return valid;
        }
    }
}
