package com.example.android.booksearcher;

import java.util.List;

/**
 * Created by dkots on 26/11/2017.
 */

public class Book {

    String mBookTitle;
    List<String> mAuthors;
    String mInfoLink;

    public Book (String bookTitle, List<String> authors, String infoLink){
        mBookTitle = bookTitle;
        mAuthors = authors;
        mInfoLink = infoLink;
    }

    public List<String> getAuthors() {
        return mAuthors;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public String getInfoLink() {
        return mInfoLink;
    }

    @Override
    public String toString() {
        return "Title: " + mBookTitle + "; " + "Author(s): " + mAuthors;
    }
}
