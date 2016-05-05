package com.example.tiannanmcclanahan.project2closet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tiannan.mcclanahan on 5/2/16.
 */
public class ClothingSQLiteHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "ClothingItems.db";
    public static final String CLOTHING_TABLE = "Clothing_Items";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "Type";
    public static final String COL_COLOR = "Color";
    public static final String COL_SIZE = "Size";
    public static final String COL_BRAND = "Brand";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_PURCHASE_DATE = "Purchase_Date";

    public static final String[] COLUMNS = {
            COL_ID, COL_NAME, COL_TYPE, COL_COLOR, COL_SIZE, COL_BRAND, COL_DESCRIPTION, COL_PURCHASE_DATE };

    private static final String CREATE_CLOTHING_TABLE = "CREATE TABLE " + CLOTHING_TABLE + "(" +
        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_NAME + " TEXT, " +
        COL_TYPE + " TEXT, " +
        COL_COLOR + " TEXT, " +
        COL_SIZE + " TEXT, " +
        COL_BRAND + " TEXT, " +
        COL_DESCRIPTION + " TEXT, " +
        COL_PURCHASE_DATE + " TEXT)";

    //Singleton class
    private static ClothingSQLiteHelper instance;

    public static ClothingSQLiteHelper getInstance(Context context){
        if(instance == null){
            instance = new ClothingSQLiteHelper(context);
        }
        return instance;
    }

    public ClothingSQLiteHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE sql statement
        db.execSQL(CREATE_CLOTHING_TABLE);
        addDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLOTHING_TABLE);
        onCreate(db);

    }
    //Create default data for database
    public void addDefaultData(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, "Jacket 1");
        values.put(COL_TYPE, "jacket");
        values.put(COL_COLOR, "blue");
        values.put(COL_SIZE, "small");
        values.put(COL_BRAND, "GAP");
        values.put(COL_DESCRIPTION, "Dark blue denim jacket");
        values.put(COL_PURCHASE_DATE, "3/1/2016");
        db.insert(CLOTHING_TABLE, null ,values);

        values = new ContentValues();
        values.put(COL_NAME, "Jacket 2");
        values.put(COL_TYPE, "jacket");
        values.put(COL_COLOR, "black");
        values.put(COL_SIZE, "small");
        values.put(COL_BRAND, "Marc Jacobs");
        values.put(COL_DESCRIPTION, "Leather jacket");
        values.put(COL_PURCHASE_DATE, "12/15/2012");
        db.insert(CLOTHING_TABLE, null ,values);

        values = new ContentValues();
        values.put(COL_NAME, "Jacket 3");
        values.put(COL_TYPE, "jacket");
        values.put(COL_COLOR, "brown");
        values.put(COL_SIZE, "small");
        values.put(COL_BRAND, "Banana Republic");
        values.put(COL_DESCRIPTION, "Tweed jacket");
        values.put(COL_PURCHASE_DATE, "1/2/2013");
        db.insert(CLOTHING_TABLE, null ,values);

        values = new ContentValues();
        values.put(COL_NAME, "Jacket 4");
        values.put(COL_TYPE, "jacket");
        values.put(COL_COLOR, "khaki");
        values.put(COL_SIZE, "extra small");
        values.put(COL_BRAND, "Banana Republic");
        values.put(COL_DESCRIPTION, "Khaki blazer");
        values.put(COL_PURCHASE_DATE, "5/6/2014");
        db.insert(CLOTHING_TABLE, null ,values);

        values = new ContentValues();
        values.put(COL_NAME, "Jacket 5");
        values.put(COL_TYPE, "jacket");
        values.put(COL_COLOR, "green");
        values.put(COL_SIZE, "small");
        values.put(COL_BRAND, "Express");
        values.put(COL_DESCRIPTION, "Olive green jacket with hood");
        values.put(COL_PURCHASE_DATE, "10/25/2015");
        db.insert(CLOTHING_TABLE, null ,values);
    }

    public Cursor getClothingItems (){

        SQLiteDatabase database = this.getReadableDatabase();

        String [] projection = new String[]{COL_ID, COL_NAME, COL_TYPE};

        Cursor cursor = database.query(CLOTHING_TABLE, projection, null, null, null, null, null, null);

        DatabaseUtils.dumpCursor(cursor);

        return cursor;

    }
    public Cursor searchClothing(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor sCursor = db.query(CLOTHING_TABLE,
                COLUMNS,
                COL_COLOR + " LIKE ? OR " + COL_BRAND + " LIKE ? OR " + COL_SIZE + " LIKE ? OR " + COL_NAME + " LIKE ? OR " + COL_PURCHASE_DATE + " LIKE ? OR " + COL_DESCRIPTION + " LIKE ? ",
                new String[]{"%" + query + "%", "%" + query + "%", "%" + query + "%", "%" + query + "%", "%" + query + "%", "%" + query + "%"},
                null,
                null,
                null,
                null);
        return sCursor;

    }
    private static final String TAG = "getItemDetailsById";
    public String getItemDetailsById(int id){

        SQLiteDatabase database = this.getReadableDatabase();

        String [] projection = new String[]{COL_NAME, COL_COLOR, COL_BRAND, COL_DESCRIPTION, COL_SIZE, COL_PURCHASE_DATE};

        Cursor cursor = database.query(CLOTHING_TABLE,projection, COL_ID + "= ?", new String []{String.valueOf(id)}, null, null, null, null);

        DatabaseUtils.dumpCursor(cursor);

        if (cursor.moveToFirst()){
            Log.i(TAG, "Hi");
            String string = String.format(" %s \n %s \n %s \n %s \n %s",cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)),
                    "Brand: " + cursor.getString(cursor.getColumnIndex(COL_BRAND)),
                    "Color: " + cursor.getString(cursor.getColumnIndex(COL_COLOR)),
                    "Size: " + cursor.getString(cursor.getColumnIndex(COL_SIZE)),
                    "Purchased on: " + cursor.getString(cursor.getColumnIndex(COL_PURCHASE_DATE)));
            return string;
        }else{
            return "No Description Found";
        }
    }

}
