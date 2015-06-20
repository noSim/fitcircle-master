package de.jsauerwein.fitcircle;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Simon on 20.06.2015.
 */
public class ExerciseTable {

    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DIFFICULTY = "difficulty";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EXERCISES
            + "(" + COLUMN_ID  + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null,"
            + COLUMN_TYPE + " text not null,"
            + COLUMN_DIFFICULTY + " text not null"
            +");";

    private static final String DATABASE_FILL = "INSERT INTO "
            + TABLE_EXERCISES
            + " (" + COLUMN_NAME + "," + COLUMN_TYPE + "," + COLUMN_DIFFICULTY + ")"
            + " VALUES ('Exercise 1', '3','2');" ;

    public static void onCreate(SQLiteDatabase db) {
        Log.d("Simon", "db Created");
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_FILL);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }
}
