package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dkots on 25/12/2017.
 */

public class InventoryContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY = "inventory";

    private InventoryContract(){}

    public static abstract class InventoryEntry implements BaseColumns{

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        public static final String TABLE_NAME = "inventory";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ITEM_NAME = "item";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SUPPLIER_CONTACT = "supplier";
    }
}
