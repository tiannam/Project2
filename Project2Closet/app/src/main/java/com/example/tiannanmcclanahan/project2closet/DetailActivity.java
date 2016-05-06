package com.example.tiannanmcclanahan.project2closet;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setSupportActionBar(((Toolbar)findViewById(R.id.detail_app_bar)));

        //getting instance from database
        ClothingSQLiteHelper helper = ClothingSQLiteHelper.getInstance(DetailActivity.this);

        int id = getIntent().getIntExtra("id",0);

        if(id >= 0) {


            Cursor cursor = helper.getItemDetailsById(id);

            if (cursor.moveToFirst()) {

                String description = String.format(" %s \n %s \n %s \n %s \n %s", cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_DESCRIPTION)),
                        "Brand: " + cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_BRAND)),
                        "Color: " + cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_COLOR)),
                        "Size: " + cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_SIZE)),
                        "Purchased on: " + cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_PURCHASE_DATE)));


                int imageID = cursor.getInt(cursor.getColumnIndex(ClothingSQLiteHelper.COL_PICTURE));

                TextView textView = (TextView) findViewById(R.id.detail_text);
                textView.setText(description);

                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageResource(imageID);
            }
        }
    }
}
