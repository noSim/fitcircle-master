package de.jsauerwein.fitcircle;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TrainingScheduleProvider extends ContentProvider {

    ExercisesDatabaseHelper database;

    public TrainingScheduleProvider() {
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String[] columnNames;
        MatrixCursor mCursor;
        switch(TrainingScheduleContract.URI_MATCHER.match(uri)) {
            case TrainingScheduleContract.EXERCISE_LIST:
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                checkColumns(projection);
                queryBuilder.setTables(ExerciseTable.TABLE_EXERCISES);

//                queryBuilder.appendWhere(ExerciseTable.COLUMN_ID + "="
//                                + uri.getLastPathSegment());

                SQLiteDatabase db = database.getWritableDatabase();
                Cursor cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
                // make sure that potential listeners are getting notified
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

//                columnNames = new String[] { "_id", "type", "name", "difficulty" };
//                cursor = new MatrixCursor(columnNames);
//                cursor.addRow(new Object[]{0, 3, "Exercise 3", 2});
//                cursor.addRow(new Object[]{1, 14, "Exercise 14", 2});
//                cursor.addRow(new Object[]{2, 21, "Exercise 21", 5});
//                cursor.addRow(new Object[]{3, 21, "Exercise 28", 3});
//                cursor.addRow(new Object[]{4, 39, "Exercise 39", 7});
//                cursor.addRow(new Object[]{5, 41, "Exercise 41", 7});
//                cursor.addRow(new Object[]{6, 44, "Exercise 44", 4});
//                cursor.addRow(new Object[]{7, 45, "Exercise 45", 8});
//
//                return cursor;
            case TrainingScheduleContract.EXERCISE_TOOL_LIST:
                List<String> pathSegments = uri.getPathSegments();
                int exercise = Integer.valueOf(pathSegments.get(pathSegments.size() - 2));
                columnNames = new String[] { "tool" };
                mCursor = new MatrixCursor(columnNames);
                Object[][][]tools = new Object[][][] {{{1}}, {{1}}, {{1}}, {{1}}, {{4}}, {{3}}, {{2}, {4}}, {{2}, {3}}};
                for(Object[] row : tools[exercise]) {
                    mCursor.addRow(row);
                }

                return mCursor;
            default:
                throw new UnsupportedOperationException("Resource not support.");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch(TrainingScheduleContract.URI_MATCHER.match(uri)) {
            case TrainingScheduleContract.EXERCISE_LIST:
                return TrainingScheduleContract.Exercises.CONTENT_TYPE;
            case TrainingScheduleContract.EXERCISE_TOOL_LIST:
                return TrainingScheduleContract.Exercises.Tools.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Resource not support.");
        }
    }

    @Override
    public boolean onCreate() {
        database = new ExercisesDatabaseHelper(this.getContext());
        return false;
    }

    private void checkColumns(String[] projection) {
        String[] available = { ExerciseTable.COLUMN_DIFFICULTY, ExerciseTable.COLUMN_ID, ExerciseTable.COLUMN_NAME, ExerciseTable.COLUMN_TYPE };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
