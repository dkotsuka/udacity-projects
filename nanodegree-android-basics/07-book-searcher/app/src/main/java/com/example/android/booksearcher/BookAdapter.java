package com.example.android.booksearcher;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dkots on 26/11/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        ViewHolderItem bookTitleViewHolder;
        ViewHolderItem authorsViewHolder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            bookTitleViewHolder = new ViewHolderItem();
            bookTitleViewHolder.textViewItem = (TextView) listItemView.findViewById(R.id
                    .book_title_text_view);
            listItemView.setTag(bookTitleViewHolder);

            authorsViewHolder = new ViewHolderItem();
            authorsViewHolder.textViewItem = (TextView) listItemView.findViewById(R.id.authors_text_view);
            listItemView.setTag(bookTitleViewHolder);

        }else{
            bookTitleViewHolder = (ViewHolderItem) convertView.getTag();
            authorsViewHolder = (ViewHolderItem) convertView.getTag();
        }
        Book currentItem = getItem(position);
        bookTitleViewHolder.textViewItem.setText(currentItem.getBookTitle());

        if(currentItem.getAuthors().size() > 0) {
            String authorsText = currentItem.getAuthors().get(0);
            for (int i = 1; i < currentItem.getAuthors().size(); i++) {
                authorsText += ", " + currentItem.getAuthors().get(i);
            }
            authorsViewHolder.textViewItem.setText(authorsText);
        } else {
            authorsViewHolder.textViewItem.setText(R.string.unidentified);
        }
        return listItemView;
    }
    static class ViewHolderItem {
        TextView textViewItem;
    }
}
