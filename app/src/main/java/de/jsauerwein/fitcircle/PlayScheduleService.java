package de.jsauerwein.fitcircle;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Simon on 23.06.2015.
 */
public class PlayScheduleService extends IntentService{

    public PlayScheduleService()
    {
        super("swag");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PlayScheduleService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Cursor cursor = getContentResolver().query(TrainingScheduleContract.Exercises.CONTENT_URI,null,null,null,null);
        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToNext();
            int exercise = cursor.getInt(cursor.getColumnIndex(ExerciseTable.COLUMN_ID));

            ContentValues values = new ContentValues();
            values.put(ExerciseTable.COLUMN_DATE, System.currentTimeMillis()/1000);
            getContentResolver().update(Uri.parse("content://de.jsauerwein.fitcircle.schedule/exercises/" + exercise), values, null, null);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
