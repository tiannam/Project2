package com.example.tiannanmcclanahan.project2closet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tiannan.mcclanahan on 5/2/16.
 */
public class ClothingSQLiteHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ClothingItems.db";
    public static final String CLOTHING_TABLE_NAME = "Clothing_Items";

    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "Type";
    public static final String COL_COLOR = "Color";
    public static final String COL_SIZE = "Size";
    public static final String COL_BRAND = "Brand";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_PURCHASE_DATE = "Purchase Date";

    public static final String [] COLUMNS = {
            COL_ID, COL_NAME, COL_TYPE, COL_COLOR, COL_SIZE, COL_BRAND, COL_DESCRIPTION, COL_PURCHASE_DATE };

    private static ClothingSQLiteHelper instance;

    public static ClothingSQLiteHelper getInstance(Context context){
        if(instance == null){
            instance = new ClothingSQLiteHelper(context);
        }
        return instance;
    }

    public ClothingSQLiteHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor getClothing (int id){

        SQLiteDatabase database = this.getReadableDatabase();

        String [] projection = new String[]{
                COL_NAME, COL_TYPE};
        String selection = "ID = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = database.query("Jackets", projection, selection, selectionArgs, null, null, null, null);

        return cursor;
    }

}
