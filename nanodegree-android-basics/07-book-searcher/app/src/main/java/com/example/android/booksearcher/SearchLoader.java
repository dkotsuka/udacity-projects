package com.example.android.booksearcher;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by dkots on 26/11/2017.
 */

public class SearchLoader extends AsyncTaskLoader<List<Book>> {

    private final String URL_PATCH = "https://www.googleapis.com/books/v1/volumes?q=";
    private String mUrl;

    public SearchLoader(Context context, String urlParams) {
        super(context);
        mUrl = URL_PATCH + urlParams;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        if (mUrl == null) {
            return QueryUtils.searchBooks(mUrl);
        }
        List<Book> books = QueryUtils.searchBooks(mUrl);

        return books;
    }
}
