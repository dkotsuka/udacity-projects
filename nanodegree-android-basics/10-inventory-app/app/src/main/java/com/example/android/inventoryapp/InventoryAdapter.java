package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

import static com.example.android.inventoryapp.R.string.price;

/**
 * Created by dkots on 27/12/2017.
 */

public class InventoryAdapter extends CursorAdapter {

    private Context mContext;
    private final static int ITEMS_SOLD = 1;

    public InventoryAdapter(Context context, Cursor c) {
        super(context, c, 0);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.text_view_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.text_view_price);
        TextView tvQuantity = (TextView) view.findViewById(R.id.text_view_quantity);
        Button buttonSale = (Button) view.findViewById(R.id.button_sale);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_ITEM_NAME));
        int intPrice = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_PRICE));
        final Integer quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_QUANTITY));

        tvName.setText(name);
        tvPrice.setText(mContext.getString(price)+ " " + mContext.getString(R.string.currency)+ " " + String.format("%.2f", ((double) intPrice / 100)));
        tvQuantity.setText(mContext.getString(R.string.in_stock) + " " + quantity.toString());

        final long currentItemId = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryEntry._ID));
        buttonSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0){
                    int newQuantity = quantity - ITEMS_SOLD;

                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_QUANTITY, newQuantity);
                    Uri currentItemUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI,currentItemId);
                    mContext.getContentResolver().update(currentItemUri, values,null,null);
                }
            }
        });

    }
}
