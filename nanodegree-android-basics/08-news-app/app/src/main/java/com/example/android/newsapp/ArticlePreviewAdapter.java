package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dkots on 29/11/2017.
 */

public class ArticlePreviewAdapter extends ArrayAdapter<ArticlePreview> {

    public ArticlePreviewAdapter(Activity context, ArrayList<ArticlePreview> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        ViewHolderItem holderView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            holderView = new ViewHolderItem();
            holderView.articleTitleTextView = (TextView) listItemView.findViewById(R.id.article_title);
            listItemView.setTag(holderView);

            holderView.sectionTextView = (TextView) listItemView.findViewById(R.id.section_name);
            listItemView.setTag(holderView);

            holderView.publicationDateTextView = (TextView) listItemView.findViewById(R.id.publication_date);
            listItemView.setTag(holderView);

        }else{
            holderView = (ViewHolderItem) convertView.getTag();
        }
        ArticlePreview currentItem = getItem(position);

        if(currentItem != null) {
            holderView.articleTitleTextView.setText(currentItem.getWebTitle());

            long timeInMiliseconds = currentItem.getWebPublicationDate().getTime();
            Date dateObject = new Date(timeInMiliseconds);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

            holderView.publicationDateTextView.setText(listItemView.getResources()
                    .getString(R.string.published_in)+" "+dateFormatter.format(dateObject));

            holderView.sectionTextView.setText(currentItem.getSectionName() + "   ");
        }

        return listItemView;
    }

    static class ViewHolderItem {
        TextView sectionTextView;
        TextView articleTitleTextView;
        TextView publicationDateTextView;
    }
}
