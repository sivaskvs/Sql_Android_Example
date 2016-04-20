package com.myapplication.siva.sqlexample;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";

    String TAG = "com.myapplication.siva.sqlexample";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dba) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME
                + " TEXT," + COLUMN_QUANTITY + " INTEGER" + ")";
        dba.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        Log.i(TAG, "Product Added ");
        db.close();
    }
    /*public Product findProduct() {

        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE 1";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

       while (cursor.getString(cursor.getColumnIndex("productname"))!= null) {
            cursor.moveToFirst();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        }

        db.close();
        return product;
    }*/
    public String findProduct() {

        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE 1";
        String ret = "";
        Log.i(TAG, "Inside findProduct");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i(TAG, "Inside findProduct 2 ");
        Cursor cursor = db.rawQuery(query, null);
        Log.i(TAG, "Inside findProduct 3 ");
        cursor.moveToFirst();
        Log.i(TAG, "Inside findProduct 4 ");
        //(cursor.getString(cursor.getColumnIndex("productname"))!= null)
        if(cursor.moveToNext()==false)
        {
            Log.i(TAG, "Success");
            ret = "No data Available";
            return ret;
        }
        do {
            Log.i(TAG, "Inside findProduct 5");
            ret +=cursor.getString(cursor.getColumnIndex("productname"));
            ret +="\n";
            cursor.moveToNext();
        }while (cursor.moveToNext());
        Log.i(TAG, "Inside findProduct 6 ");

        db.close();
        return ret;
    }

    /*public String findProduct(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            if(cursor.getString(cursor.getColumnIndex("productname"))!= null)
            {
                dbString += cursor.getString(cursor.getColumnIndex("productname"));
                dbString +="\t"+cursor.getString(cursor.getColumnIndex("products"));
                dbString +="\n";
            }
        }
        db.close();
        return dbString;
    }
*/
    public boolean deleteProduct(String productname) {

        boolean result = false;
        if(productname == null)
        {
            return result;
        }
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(product.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}