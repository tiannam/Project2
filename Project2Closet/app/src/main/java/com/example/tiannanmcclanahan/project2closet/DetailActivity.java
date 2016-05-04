package com.example.tiannanmcclanahan.project2closet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ClothingSQLiteHelper helper = ClothingSQLiteHelper.getInstance(DetailActivity.this);

        int id = getIntent().getIntExtra("id",-1);

        if(id >= 0) {
            String description = helper.getDetailsById(id);
            TextView textView = (TextView) findViewById(R.id.detail_text);
            textView.setText(description);
        }
    }
}
