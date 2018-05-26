package com.example.kardana.androidcourse;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Dana on 26-May-18.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.example.kardana.androidcourse.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
