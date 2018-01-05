package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.widget.Toast.makeText;

public class EditorActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<Cursor> {

    private static final int EDITOR_LOADER_ID = 11;
    private static final int ADD_VALUE = 1;
    private static final int REMOVE_VALUE = -1;
    private static final int IMAGE_REQUEST_CODE = 100;

    private static final String ACTIVITY_TAG = EditorActivity.class.getSimpleName();

    private EditText mItemTitle;
    private EditText mDescription;
    private EditText mPrice;
    private EditText mQuantityEditText;
    private TextView mQuantityTextView;
    private ImageButton addButton;
    private ImageButton removeButton;
    private ImageView imageView;
    private Button orderButton;
    private EditText mSupplierContact;
    private LinearLayout mQuantityEditField;
    private EditText inputEditText;

    private Uri currentItemUri;
    private boolean mHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mHasChanged = true;
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mItemTitle = (EditText) findViewById(R.id.editor_item_name);
        mDescription = (EditText) findViewById(R.id.editor_item_description);
        mPrice = (EditText) findViewById(R.id.editor_price);
        mQuantityEditText = (EditText) findViewById(R.id.editor_quantity_edittext);
        mQuantityTextView = (TextView) findViewById(R.id.editor_quantity_textview);
        addButton = (ImageButton) findViewById(R.id.add_button);
        removeButton = (ImageButton) findViewById(R.id.remove_button);
        imageView = (ImageView) findViewById(R.id.editor_image_view);
        orderButton = (Button) findViewById(R.id.editor_order_button);
        mSupplierContact = (EditText) findViewById(R.id.editor_supplier_contact);
        mQuantityEditField = (LinearLayout) findViewById(R.id.quantity_edit_field);

        inputEditText = new EditText(this);
        inputEditText.setInputType(TYPE_CLASS_NUMBER);
        inputEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        Intent intent = getIntent();
        currentItemUri = intent.getData();

        if (currentItemUri == null) {
            setTitle(getString(R.string.add_item));
            mQuantityEditField.setVisibility(View.GONE);
            orderButton.setVisibility(View.GONE);

        } else {
            setTitle(getString(R.string.edit_item));
            getLoaderManager().initLoader(EDITOR_LOADER_ID, null, this);
            mQuantityEditText.setVisibility(View.GONE);
        }

        mItemTitle.setOnTouchListener(mTouchListener);
        mDescription.setOnTouchListener(mTouchListener);
        mPrice.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);
        imageView.setOnTouchListener(mTouchListener);
        addButton.setOnTouchListener(mTouchListener);
        removeButton.setOnTouchListener(mTouchListener);
        mSupplierContact.setOnTouchListener(mTouchListener);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener addButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int input = Integer.parseInt(inputEditText.getText().toString());
                                clearInput();
                                input *= ADD_VALUE;
                                recalculateQuantity(input);
                            }
                        };
                showAddQuantityDialog(addButtonClickListener);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener removeButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int input = Integer.parseInt(inputEditText.getText().toString());
                                clearInput();
                                input *= REMOVE_VALUE;
                                recalculateQuantity(input);
                            }
                        };
                showRemoveQuantityDialog(removeButtonClickListener);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imagePicker = new Intent(Intent.ACTION_PICK);
                imagePicker.setType("image/*");
                startActivityForResult(imagePicker, IMAGE_REQUEST_CODE);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mSupplierContact.getText()));
                startActivity(intent);
            }
        });
    }

    private void saveItem() {

        String itemName = mItemTitle.getText().toString();

        String stringPrice = mPrice.getText().toString();
        Double doublePrice = Double.valueOf(stringPrice.replace(",", "."));
        int intPrice = (int) (doublePrice * 100);

        String quantity = mQuantityEditText.getText().toString();
        String description = mDescription.getText().toString();
        String supplierContact = mSupplierContact.getText().toString();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageView.setDrawingCacheEnabled(true);
        Bitmap image = imageView.getDrawingCache();
        image.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] imageByte = outputStream.toByteArray();

        if (currentItemUri == null &&
                TextUtils.isEmpty(itemName) && TextUtils.isEmpty(doublePrice.toString()) &&
                TextUtils.isEmpty(quantity)) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_ITEM_NAME, itemName);
        values.put(InventoryEntry.COLUMN_DESCRIPTION, description);
        values.put(InventoryEntry.COLUMN_PRICE, intPrice);
        values.put(InventoryEntry.COLUMN_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_IMAGE, imageByte);
        values.put(InventoryEntry.COLUMN_SUPPLIER_CONTACT, supplierContact);

        if (currentItemUri == null) {
            getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
            Toast toast = makeText(this, getString(R.string.toast_item_saved), Toast.LENGTH_SHORT);
            toast.show();
        } else {
            getContentResolver().update(currentItemUri, values, null, null);
            Toast toast = makeText(this, getString(R.string.toast_item_updated), Toast
                    .LENGTH_SHORT);
            toast.show();
        }
    }

    private void deleteItem() {
        getContentResolver().delete(currentItemUri, null, null);
        Toast toast = makeText(this, getString(R.string.toast_item_deleted), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_ITEM_NAME,
                InventoryEntry.COLUMN_DESCRIPTION,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY,
                InventoryEntry.COLUMN_IMAGE,
                InventoryEntry.COLUMN_SUPPLIER_CONTACT
        };
        return new CursorLoader(this, currentItemUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            String itemName = cursor.getString(cursor.getColumnIndex(InventoryEntry
                    .COLUMN_ITEM_NAME));
            String description = cursor.getString(cursor.getColumnIndex(InventoryEntry
                    .COLUMN_DESCRIPTION));
            int price = cursor.getInt(cursor.getColumnIndex(InventoryEntry
                    .COLUMN_PRICE));
            Integer quantity = cursor.getInt(cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY));
            String supplierContact = cursor.getString(cursor.getColumnIndex(InventoryEntry
                    .COLUMN_SUPPLIER_CONTACT));
            byte[] imageByte = cursor.getBlob(cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE));
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByte);
            Bitmap image = BitmapFactory.decodeStream(imageStream);

            mItemTitle.setText(itemName);
            mDescription.setText(description);
            mPrice.setText(String.format("%.2f", ((double) price / 100)));
            mQuantityEditText.setText(quantity.toString());
            mQuantityTextView.setText(quantity.toString());
            imageView.setImageBitmap(image);
            imageView.setPadding(0,0,0,0);
            mSupplierContact.setText(supplierContact);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (mHasChanged) {
                    if (inputIsValid()) {
                        saveItem();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(this, R.string.invalid_input, Toast
                                .LENGTH_LONG);
                        toast.show();
                    }
                } else {
                    finish();
                }
                return true;
            case R.id.action_cancel:
                if (mHasChanged) {
                    DialogInterface.OnClickListener cancelButtonClickListener = new
                            DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    };
                    showUnsavedChangesDialog(cancelButtonClickListener);
                } else {
                    finish();
                }
                return true;
            case R.id.action_delete:
                if (currentItemUri == null) {
                    setTitle(getString(R.string.add_item));
                    invalidateOptionsMenu();
                } else {
                    DialogInterface.OnClickListener deleteButtonClickListener =
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteItem();
                                    finish();
                                }
                            };
                    showDeleteConfirmationDialog(deleteButtonClickListener);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog(DialogInterface.OnClickListener
                                                      deleteButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirmation);
        builder.setPositiveButton(R.string.delete, deleteButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {

        if (!mHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsave_confirmation);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAddQuantityDialog(DialogInterface.OnClickListener addButtonClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.add_message);
        builder.setView(inputEditText);
        builder.setPositiveButton(R.string.add,addButtonClickListener);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                    clearInput();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showRemoveQuantityDialog(DialogInterface.OnClickListener removeButtonClickListener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.remove_message);
        builder.setView(inputEditText);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                    clearInput();
                }
            }
        });
        builder.setPositiveButton(R.string.remove,removeButtonClickListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (currentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    private boolean inputIsValid() {
        if (TextUtils.isEmpty(mItemTitle.getText().toString()) ||
                TextUtils.isEmpty(mPrice.getText().toString()) ||
                TextUtils.isEmpty(mQuantityEditText.getText().toString())) {
            return false;
        }
        return true;
    }

    private void recalculateQuantity(int value) {
        int quantity = Integer.parseInt(mQuantityEditText.getText().toString());
        Integer result = quantity + value;
        if (result < 0) {
            Toast toast = Toast.makeText(this,getString(R.string.toast_greater_than_quantity),Toast
                    .LENGTH_LONG);
            toast.show();
            return;
        }
        mQuantityEditText.setText(result.toString());
        mQuantityTextView.setText(result.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                Uri selectedImage = data.getData();
                if (resultCode == RESULT_OK) {
                    try {
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap image = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(image);
                        imageView.setPadding(0,0,0,0);
                    } catch (FileNotFoundException e) {
                        Log.e(ACTIVITY_TAG, getString(R.string.exception_inputstream), e);
                    }
                }

        }
    }

    private void clearInput(){
        ((ViewGroup)inputEditText.getParent()).removeView(inputEditText);
        inputEditText.setText("");
    }
}
