package de.jsauerwein.fitcircle;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Simon on 21.06.2015.
 */
public class ExerciseToolsTable {
    public static final String TABLE_EXERCISE_TOOLS = "exercise_tools";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXERCISE_ID = "exercise";
    public static final String COLUMN_TOOL_ID = "tool";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EXERCISE_TOOLS
            + "(" + COLUMN_ID  + " integer primary key autoincrement, "
            + COLUMN_EXERCISE_ID  + " integer, "
            + COLUMN_TOOL_ID  + " integer"
            + ");";

    Object[][][]tools = new Object[][][] {{{1}}, {{1}}, {{1}}, {{1}}, {{4}}, {{3}}, {{2}, {4}}, {{2}, {3}}};
    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(insertSQL("1","1"));
        db.execSQL(insertSQL("2","1"));
        db.execSQL(insertSQL("3","1"));
        db.execSQL(insertSQL("4","1"));
        db.execSQL(insertSQL("5","4"));
        db.execSQL(insertSQL("6","3"));
        db.execSQL(insertSQL("7","2"));
        db.execSQL(insertSQL("7","4"));
        db.execSQL(insertSQL("8","2"));
        db.execSQL(insertSQL("8","3"));
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE_TOOLS);
        onCreate(db);
    }

    private static String insertSQL(String exercise, String tool)
    {
        return ("INSERT INTO "
                + TABLE_EXERCISE_TOOLS
                + " (" + COLUMN_EXERCISE_ID + "," + COLUMN_TOOL_ID + ")"
                + " VALUES ('" + exercise + "', '" + tool + "');");
    }


}
