package de.jsauerwein.fitcircle;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.util.List;

public class TrainingScheduleProvider extends ContentProvider {
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
        MatrixCursor cursor;
        switch(TrainingScheduleContract.URI_MATCHER.match(uri)) {
            case TrainingScheduleContract.EXERCISE_LIST:
                columnNames = new String[] { "_id", "type", "name", "difficulty" };
                cursor = new MatrixCursor(columnNames);
                cursor.addRow(new Object[]{0, 3, "Exercise 3", 2});
                cursor.addRow(new Object[]{1, 14, "Exercise 14", 2});
                cursor.addRow(new Object[]{2, 21, "Exercise 21", 5});
                cursor.addRow(new Object[]{3, 21, "Exercise 28", 3});
                cursor.addRow(new Object[]{4, 39, "Exercise 39", 7});
                cursor.addRow(new Object[]{5, 41, "Exercise 41", 7});
                cursor.addRow(new Object[]{6, 44, "Exercise 44", 4});
                cursor.addRow(new Object[]{7, 45, "Exercise 45", 8});

                return cursor;
            case TrainingScheduleContract.EXERCISE_TOOL_LIST:
                List<String> pathSegments = uri.getPathSegments();
                int exercise = Integer.valueOf(pathSegments.get(pathSegments.size() - 2));
                columnNames = new String[] { "tool" };
                cursor = new MatrixCursor(columnNames);
                Object[][][]tools = new Object[][][] {{{1}}, {{1}}, {{1}}, {{1}}, {{4}}, {{3}}, {{2}, {4}}, {{2}, {3}}};
                for(Object[] row : tools[exercise]) {
                    cursor.addRow(row);
                }

                return cursor;
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
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }
}
