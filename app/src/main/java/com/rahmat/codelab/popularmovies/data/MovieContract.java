package com.rahmat.codelab.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rahmat on 8/5/2017.
 */

public class MovieContract {
    public static final String AUTHORITY = "com.rahmat.codelab.popularmovies";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
    }
}
