package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by dkots on 26/12/2017.
 */

public class InventoryHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "inventory.db";
    public final static int DATABASE_VERSION = 1;

    public InventoryHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE =  "CREATE TABLE " + InventoryEntry.TABLE_NAME
                + " (" + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +InventoryEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                +InventoryEntry.COLUMN_DESCRIPTION + " TEXT, "
                +InventoryEntry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                +InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                +InventoryEntry.COLUMN_IMAGE + " BLOB, "
                +InventoryEntry.COLUMN_SUPPLIER_CONTACT + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
