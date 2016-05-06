package com.example.tiannanmcclanahan.project2closet;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    Context context;

    public ApplicationTest() {
        super(Application.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getSystemContext();
    }

    @SmallTest
    public void testDatabase(){
        ClothingSQLiteHelper mHelper = new ClothingSQLiteHelper(context);
        final Cursor cursor = mHelper.getClothingItems();
//        System.out.println("testDatabase "+ cursor.getCount()+"");
        assertTrue(cursor.getCount() > 1);
        assertEquals(cursor.getCount(), 5);
    }

}