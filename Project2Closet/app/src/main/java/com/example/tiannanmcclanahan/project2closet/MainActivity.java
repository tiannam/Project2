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

public class MainActivity extends AppCompatActivity{


    CursorAdapter cursorAdapter;
    private ClothingSQLiteHelper mHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(((Toolbar) findViewById(R.id.app_bar)));

        listView = (ListView) findViewById(R.id.category_list);
        mHelper = new ClothingSQLiteHelper(MainActivity.this);
        final Cursor cursor = mHelper.getClothingItems();


        if (cursorAdapter == null) {

            cursorAdapter = new SimpleCursorAdapter(
                    MainActivity.this,
                    R.layout.list_item_layout,
                    cursor,
                    new String[]{ClothingSQLiteHelper.COL_NAME, ClothingSQLiteHelper.COL_DESCRIPTION},
                    new int[]{R.id.clothing_item, R.id.item_description},
                    0
            );

//            CursorAdapter cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
//                @Override
//                public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//                    LayoutInflater inflater = LayoutInflater.from(context);
//                    View view = inflater.inflate(R.layout.list_item_layout,viewGroup,false);
//                    return view;
//                }
//
//                @Override
//                public void bindView(View view, Context context, Cursor cursor) {
////                TextView textView = (TextView)view.findViewById(android.R.id.text1);
////
////                textView.setText(cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_NAME)));
//
//                    TextView nameTextView = (TextView) view.findViewById(R.id.clothing_item);
//                    TextView descriptionTextView = (TextView) view.findViewById(R.id.item_description);
//
//                    nameTextView.setText(cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_NAME)));
//                    descriptionTextView.setText(cursor.getString(cursor.getColumnIndex(ClothingSQLiteHelper.COL_DESCRIPTION)));
//                }
//            };
            listView.setAdapter(cursorAdapter);
        }else {
            cursorAdapter.swapCursor(cursor);
        }
        listView.setAdapter(cursorAdapter);



        handleIntent(getIntent());

        //onItemClickListener for each list item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(MainActivity.this,DetailActivity.class);
                Cursor selectedCursor = (Cursor) parent.getAdapter().getItem(position);

                intent.putExtra("id",selectedCursor.getInt(selectedCursor.getColumnIndex(ClothingSQLiteHelper.COL_ID)));
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
            cursorAdapter.swapCursor(cursor);
            cursorAdapter.notifyDataSetChanged();

//            Toast.makeText(MainActivity.this,"Searching for "+query,Toast.LENGTH_SHORT).show();
        }
    }





}