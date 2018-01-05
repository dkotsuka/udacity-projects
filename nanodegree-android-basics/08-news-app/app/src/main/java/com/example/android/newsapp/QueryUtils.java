package com.example.android.newsapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dkots on 29/11/2017.
 */

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String RESPONSE_JSON = "response";
    private static final String RESULTS_JSON = "results";
    private static final String WEB_TITLE_JSON = "webTitle";
    private static final String WEB_URL_JSON = "webUrl";
    private static final String SECTION_NAME_JSON = "sectionName";
    private static final String PUBLICATION_DATE_JSON = "webPublicationDate";
    private static final String DATE_HOUR_FORMAT = "yyyy-MM-ddhh:mm:ss";
    private static final String EMPTY_STRING = "";
    private static final String CHAR_T = "T";
    private static final String CHAR_Z = "Z";
    private static final String GET_METHOD = "GET";

    private QueryUtils() {
    }

    public static List<ArticlePreview> requestForArticles(URL requestUrl, Context context) {

        URL url = requestUrl;
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, context);
        } catch (IOException e) {
            Log.e(LOG_TAG, context.getString(R.string.problem_http), e);
        }

        List<ArticlePreview> articles = extractArticles(jsonResponse, context);
        return articles;
    }

    public static ArrayList<ArticlePreview> extractArticles(String jsonResponse,Context context) {

        ArrayList<ArticlePreview> articles = new ArrayList<>();

        if(jsonResponse == null || jsonResponse == EMPTY_STRING){
            return articles;
        }

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject(RESPONSE_JSON);
            JSONArray results = response.getJSONArray(RESULTS_JSON);

            for (int i = 0; i < results.length(); i++){
                JSONObject article = results.getJSONObject(i);
                String webTitle = article.optString(WEB_TITLE_JSON);
                String webUrl = article.optString(WEB_URL_JSON);
                String sectionName = article.optString(SECTION_NAME_JSON);

                String webPublicationDate = article.optString(PUBLICATION_DATE_JSON);
                Date convertedDate = new Date();
                if(webPublicationDate.length()==20) {
                    webPublicationDate = webPublicationDate.replace(CHAR_T, EMPTY_STRING);
                    webPublicationDate = webPublicationDate.replace(CHAR_Z, EMPTY_STRING);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_HOUR_FORMAT);
                    try {
                        convertedDate = dateFormat.parse(webPublicationDate);
                    } catch (ParseException e) {
                        Log.e(LOG_TAG, context.getString(R.string.problem_parsing_date),e);
                    }
                }
                articles.add(new ArticlePreview(webTitle,convertedDate,webUrl,sectionName));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, context.getString(R.string.problem_parsing_json), e);
        }
        return articles;
    }

    public static String makeHttpRequest(URL url,Context context) throws IOException {

        String jsonResponse = EMPTY_STRING;

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod(GET_METHOD);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, context.getString(R.string.error_code) +
                        urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, context.getString(R.string.error_json_result), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
