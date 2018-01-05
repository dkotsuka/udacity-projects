package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by dkots on 29/11/2017.
 */

public class ArticlesLoader extends AsyncTaskLoader {
    private final String ENDPOINT_URL = "http://content.guardianapis.com/search?";
    private String mSection;
    private Context mContext;

    public ArticlesLoader(Context context, String section) {
        super(context);
        mSection = section;
        mContext = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ArticlePreview> loadInBackground() {

        URL mUrl = makeUrl();

        if (mUrl == null) {
            return QueryUtils.requestForArticles(mUrl,mContext);
        }
        List<ArticlePreview> articles = QueryUtils.requestForArticles(mUrl, mContext);

        return articles;
    }

    private URL makeUrl(){
        URL url = null;
        Uri baseUri = Uri.parse(ENDPOINT_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        if(mSection!=null){
            uriBuilder.appendQueryParameter("section", mSection);
        }
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("orderby", "newest");
        uriBuilder.appendQueryParameter("api-key", "test");

        try {
            return new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
