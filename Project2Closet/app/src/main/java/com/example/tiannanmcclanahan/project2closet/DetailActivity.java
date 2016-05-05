package com.example.tiannanmcclanahan.project2closet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setSupportActionBar(((Toolbar)findViewById(R.id.detail_app_bar)));

        ClothingSQLiteHelper helper = ClothingSQLiteHelper.getInstance(DetailActivity.this);

        int id = getIntent().getIntExtra("id",-1);

        if(id >= 0) {
            String description = helper.getItemDetailsById(id);
            TextView textView = (TextView) findViewById(R.id.detail_text);
            textView.setText(description);
        }
    }
}
