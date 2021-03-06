package de.jsauerwein.fitcircle;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
        long id = 0;
        SQLiteDatabase db = database.getWritableDatabase();
        switch(TrainingScheduleContract.URI_MATCHER.match(uri))
        {
            case TrainingScheduleContract.EXERCISE_LIST:
                id  = db.insert(ExerciseTable.TABLE_EXERCISES, null, values);
                break;
            case TrainingScheduleContract.EXERCISE_TOOL_LIST:
                id  = db.insert(ExerciseToolsTable.TABLE_EXERCISE_TOOLS, null, values);
                break;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse(uri + "/" + id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = database.getWritableDatabase();

        switch(TrainingScheduleContract.URI_MATCHER.match(uri)) {
            case TrainingScheduleContract.EXERCISE_LIST:
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                checkColumns(projection);
                queryBuilder.setTables(ExerciseTable.TABLE_EXERCISES);

                Cursor cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
                getContext().getContentResolver().notifyChange(uri, null);
                return cursor;
            case TrainingScheduleContract.EXERCISE_TOOL_LIST:
                List<String> pathSegments = uri.getPathSegments();
                int exercise = Integer.valueOf(pathSegments.get(pathSegments.size() - 2));

                SQLiteQueryBuilder queryToolsBuilder = new SQLiteQueryBuilder();
                queryToolsBuilder.setTables(ExerciseToolsTable.TABLE_EXERCISE_TOOLS);
                queryToolsBuilder.appendWhere(ExerciseToolsTable.COLUMN_EXERCISE_ID + "=" + exercise);

                Cursor toolsCursor = queryToolsBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
                getContext().getContentResolver().notifyChange(uri,null);
                return toolsCursor;
            default:
                throw new UnsupportedOperationException("Resource not support.");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch(TrainingScheduleContract.URI_MATCHER.match(uri)) {
            case TrainingScheduleContract.EXERCISE_LIST_SINGLE_ITEM:
                String id = uri.getLastPathSegment();
                SQLiteDatabase db = database.getWritableDatabase();
                int numberOfRowsAffected;
                if (TextUtils.isEmpty(selection)) {
                    numberOfRowsAffected = db.update(ExerciseTable.TABLE_EXERCISES, values, ExerciseTable.COLUMN_ID + "=" + id, null);
                } else {
                    numberOfRowsAffected = db.update(ExerciseTable.TABLE_EXERCISES, values, ExerciseTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return numberOfRowsAffected;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
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

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (method.equals("startPlayScheduleService"))
        {
            startPlayScheduleService();
        }
        return super.call(method, arg, extras);
    }

    private void startPlayScheduleService()
    {
        Intent mServiceIntent = new Intent(getContext(), PlayScheduleService.class);
        getContext().startService(mServiceIntent);
    }
}
