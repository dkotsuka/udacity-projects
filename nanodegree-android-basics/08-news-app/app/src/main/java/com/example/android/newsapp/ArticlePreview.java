package com.example.android.newsapp;

import java.util.Date;

/**
 * Created by dkots on 29/11/2017.
 */

public class ArticlePreview {

    String mWebTitle;
    Date mWebPublicationDate;
    String mWebUrl;
    String mSectionName;

    public ArticlePreview(String webTitle, Date publicationDate, String webUrl, String sectionName){
        mWebTitle = webTitle;
        mWebPublicationDate = publicationDate;
        mWebUrl = webUrl;
        mSectionName = sectionName;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public Date getWebPublicationDate() {
        return mWebPublicationDate;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getSectionName() {
        return mSectionName;
    }
}
