package de.jsauerwein.fitcircle;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by j.a.n.s on 18.11.2014.
 */
public class TrainingScheduleContract {
    public static final String AUTHORITY = "de.jsauerwein.fitcircle.schedule";

    public static final String SCHEME = "content://";

    public static final int EXERCISE_LIST = 1;
    public static final int EXERCISE_TOOL_LIST = 2;
    public static final int EXERCISE_LIST_SINGLE_ITEM = 3;

    public static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, Exercises.PATH, EXERCISE_LIST);
        URI_MATCHER.addURI(AUTHORITY, Exercises.Tools.PATH, EXERCISE_TOOL_LIST);
        URI_MATCHER.addURI(AUTHORITY, Exercises.PATH + "/#", EXERCISE_LIST_SINGLE_ITEM);
    }

    public static final class Exercises implements BaseColumns {
        public static final String PATH = "exercises";
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + PATH);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.de.jsauerwein.exercises";

        public static final class Tools implements BaseColumns {
            public static final String PATH = Exercises.PATH + "/#/" + "tools";
            public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + PATH);
            public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.de.jsauerwein.exercises.tools";
        }
    }
}
