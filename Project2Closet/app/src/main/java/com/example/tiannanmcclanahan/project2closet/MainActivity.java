package com.example.tiannanmcclanahan.project2closet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    CursorAdapter simpleCursorAdapter;
    private ClothingSQLiteHelper mHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(((Toolbar)findViewById(R.id.app_bar)));

        listView = (ListView)findViewById(R.id.category_list);
        mHelper = new ClothingSQLiteHelper(MainActivity.this);
         final Cursor cursor = mHelper.getClothingItems();

        simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this,android.R.layout.simple_list_item_1,cursor,new String[]{ClothingSQLiteHelper.COL_NAME}, new int[]{android.R.id.text1},0);

        listView.setAdapter(simpleCursorAdapter);

        handleIntent(getIntent());

        //onItemClickListener for each list item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(MainActivity.this,DetailActivity.class);
                cursor.moveToPosition(position);
                intent.putExtra("id",cursor.getInt(cursor.getColumnIndex(ClothingSQLiteHelper.COL_ID)));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        //getting the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Cursor cursor = mHelper.searchClothing(query);
            simpleCursorAdapter.swapCursor(cursor);
            simpleCursorAdapter.notifyDataSetChanged();

            Toast.makeText(MainActivity.this,"Searching for "+query,Toast.LENGTH_SHORT).show();
        }
    }





}