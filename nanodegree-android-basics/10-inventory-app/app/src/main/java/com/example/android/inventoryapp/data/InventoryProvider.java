package com.example.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.inventoryapp.R;

import static com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by dkots on 26/12/2017.
 */

public class InventoryProvider extends ContentProvider {

    private static final int INVENTORY_LIST = 1;
    private static final int INVENTORY_ITEM_ID = 2;
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    private InventoryHelper mDbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY,InventoryContract.PATH_INVENTORY,INVENTORY_LIST);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY,InventoryContract.PATH_INVENTORY +
                "/#", INVENTORY_ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case INVENTORY_LIST:
                cursor = database.query(InventoryEntry.TABLE_NAME,projection,selection,
                        selectionArgs,
                        null,null,sortOrder);
                break;
            case INVENTORY_ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.exception_query_unknown_uri) + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY_LIST:
                return InventoryEntry.CONTENT_LIST_TYPE;
            case INVENTORY_ITEM_ID:
                return InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException(getContext().getString(R.string
                        .exception_unknown_uri) + uri + getContext().getString(R.string
                        .exception_with_match) + match);
        }
    }

    @Override
    public Uri insert(Uri uri,ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY_LIST:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.exception_insert_error) + uri);
        }
    }

    private Uri insertItem(Uri uri, ContentValues values) {

        checkValues(values);

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(InventoryEntry.TABLE_NAME,null,values);

        if (id == -1) {
            Log.e(LOG_TAG, getContext().getString(R.string.exception_insert_row) + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case INVENTORY_LIST:
                rowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case INVENTORY_ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException(getContext().getString(R.string.exception_delete_error) + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update( Uri uri, ContentValues contentValues, String
            selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {

            case INVENTORY_LIST:
                return updateItem(uri, contentValues, selection, selectionArgs);

            case INVENTORY_ITEM_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateItem(uri, contentValues, selection, selectionArgs);

            default:
                throw new IllegalArgumentException(getContext().getString(R.string.exception_update_error) + uri);
        }
    }

    private int updateItem(Uri uri, ContentValues values, String selection, String[]
            selectionArgs) {

        checkValues(values);

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    private void checkValues(ContentValues values){

        if (values.containsKey(InventoryEntry.COLUMN_ITEM_NAME)) {
            String itemName = values.getAsString(InventoryEntry.COLUMN_ITEM_NAME);
            if (itemName == null) {
                throw new IllegalArgumentException(getContext().getString(R.string
                        .exception_insert_name_error));
            }
        }
        if (values.containsKey(InventoryEntry.COLUMN_DESCRIPTION)) {
            String description = values.getAsString(InventoryEntry.COLUMN_DESCRIPTION);
            if (description == null) {
                throw new IllegalArgumentException(getContext().getString(R.string
                        .exception_insert_description_error));
            }
        }
        if (values.containsKey(InventoryEntry.COLUMN_QUANTITY)) {
            Integer quantity = values.getAsInteger(InventoryEntry.COLUMN_QUANTITY);
            if (quantity == null) {
                throw new IllegalArgumentException(getContext().getString(R.string
                        .exception_insert_quantity_error));
            }
        }
        if (values.containsKey(InventoryEntry.COLUMN_PRICE)) {
            Integer price = values.getAsInteger(InventoryEntry.COLUMN_PRICE);
            if (price == null) {
                throw new IllegalArgumentException(getContext().getString(R.string
                        .exception_insert_price_error));
            }
        }
    }
}
