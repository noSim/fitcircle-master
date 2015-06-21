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
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EXERCISES
            + "(" + COLUMN_ID  + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null,"
            + COLUMN_TYPE + " text not null,"
            + COLUMN_DIFFICULTY + " text not null,"
            + COLUMN_DATE + " Integer"
            +");";


    public static void onCreate(SQLiteDatabase db) {
        Log.d("Simon", "db Created");
        db.execSQL(DATABASE_CREATE);
        db.execSQL(insertSQL("Exercise 3", "3" , "2"));
        db.execSQL(insertSQL("Exercise 14", "14" , "2"));
        db.execSQL(insertSQL("Exercise 21", "21" , "5"));
        db.execSQL(insertSQL("Exercise 28", "28" , "3"));
        db.execSQL(insertSQL("Exercise 39", "39" , "7"));
        db.execSQL(insertSQL("Exercise 41", "41" , "7"));
        db.execSQL(insertSQL("Exercise 44", "44" , "4"));
        db.execSQL(insertSQL("Exercise 45", "45" , "8"));
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

    private static String insertSQL(String exercise, String type, String difficulty)
    {
        return ("INSERT INTO "
            + TABLE_EXERCISES
            + " (" + COLUMN_NAME + "," + COLUMN_TYPE + "," + COLUMN_DIFFICULTY +"," + COLUMN_DATE + ")"
            + " VALUES ('" + exercise + "', '" + type + "','" + difficulty + "','-1');");
    }
}
