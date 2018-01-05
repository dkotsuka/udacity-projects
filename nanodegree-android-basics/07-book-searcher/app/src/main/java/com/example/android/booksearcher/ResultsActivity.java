package com.example.android.booksearcher;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity implements android.app.LoaderManager
        .LoaderCallbacks<List<Book>>{
    private static final int SEARCH_LOADER_ID = 1;
    private Bundle extras;
    private ProgressBar loadingSpinner;
    private TextView emptyView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        extras = getIntent().getExtras();

        loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        emptyView = (TextView) findViewById(R.id.empty_tv);

        adapter = new BookAdapter(this, new ArrayList<Book>());
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Book currentItem = adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentItem.getInfoLink());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(SEARCH_LOADER_ID, null, this);

        } else {
            loadingSpinner.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new SearchLoader(this, extras.getString("PARAMS"));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        loadingSpinner.setVisibility(View.GONE);
        adapter.clear();
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        } else {
            emptyView.setText(R.string.nothing_found);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }
}
