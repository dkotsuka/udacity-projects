package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<List<ArticlePreview>>{

    private static final int LOADER_ID = 1;
    private ArticlePreviewAdapter mAdapter;
    private ListView listView;
    private ProgressBar loadingSpinner;
    private TextView emptyView;
    private Spinner sectionSpinner;
    private String sectionId;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        sectionSpinner = (Spinner) findViewById(R.id.section_spinner);
        listView = (ListView) findViewById(R.id.list);

        mAdapter = new ArticlePreviewAdapter(this, new ArrayList<ArticlePreview>());
        listView.setAdapter(mAdapter);
        listView.setEmptyView(findViewById(R.id.empty_view));
        emptyView = (TextView) listView.getEmptyView();

        loaderManager = getLoaderManager();

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array
                .sections, R.layout.section_item);
        sectionAdapter.setDropDownViewResource(R.layout.section_item);
        sectionSpinner.setAdapter(sectionAdapter);

        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long
                    id) {
                if(hasConnection()) {
                    selectSectionIdBySpinner(position);
                    restartLoader();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(hasConnection()) {
                    selectSectionIdBySpinner(0);
                    restartLoader();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ArticlePreview currentArticle = mAdapter.getItem(position);
                Uri articleUri = Uri.parse(currentArticle.getWebUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(websiteIntent);
            }
        });
    }
    private void selectSectionIdBySpinner(int position){
        switch (position){
            case 0:
                sectionId = "world";
                break;
            case 1:
                sectionId = "artanddesign";
                break;
            case 2:
                sectionId = "technology";
                break;
            case 3:
                sectionId = "money";
                break;
            case 4:
                sectionId = null;
                break;
        }
    }
    @Override
    public Loader<List<ArticlePreview>> onCreateLoader(int i, Bundle bundle) {
        return new ArticlesLoader(this, sectionId);
    }
    @Override
    public void onLoadFinished(Loader<List<ArticlePreview>> loader, List<ArticlePreview> articles) {
        loadingSpinner.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        mAdapter.clear();

        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        } else {
            emptyView.setText(R.string.nothing_found);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<ArticlePreview>> loader) {
        mAdapter.clear();
    }

    private void restartLoader(){
        listView.setVisibility(View.INVISIBLE);
        loadingSpinner.setVisibility(View.VISIBLE);
        loaderManager.restartLoader(LOADER_ID,null,MainActivity.this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if(hasConnection()) {
                restartLoader();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            emptyView.setVisibility(View.GONE);
            loaderManager.initLoader(LOADER_ID, null, this);
            return true;
        } else {
            loadingSpinner.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
            return false;
        }
    }
}
