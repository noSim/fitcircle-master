package de.jsauerwein.fitcircle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Simon on 20.06.2015.
 */
public class ExercisesDatabaseHelper extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "Exercises.db";
    private static final int DATABASE_VERSION = 1;

    public ExercisesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ExerciseTable.onCreate(db);
        ExerciseToolsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ExerciseTable.onUpgrade(db,oldVersion,newVersion);
        ExerciseToolsTable.onUpgrade(db,oldVersion,newVersion);
    }
}
