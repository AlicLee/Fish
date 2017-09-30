package com.example.kys.fish.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lee on 2017/9/11.
 */

public class ScienceDBHelper extends SQLiteOpenHelper {
    private final String TAG = ScienceDBHelper.class.getSimpleName();

    public ScienceDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE `science` (\n" +
                "  `id` Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "  `brief` text,\n" +
                "`kind` varchar(255) DEFAULT NULL,\n" +

                "  `breadingPoint` text ,\n" +
                "  `dieaseControl` text ,\n" +
                "  `name` varchar(255) NOT NULL ,\n" +
                "  `type` varchar(255) DEFAULT NULL \n" +
                ") ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "更新数据库--------->");
    }
}
