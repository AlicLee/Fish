package com.example.kys.fish.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lee on 2017/9/11.
 */

public class ScienceDBHelper extends SQLiteOpenHelper {
    private final String TAG=ScienceDBHelper.class.getSimpleName();
    public ScienceDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE `science` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `brief` text COMMENT '简介',\n" +
                "  `fishImgpath` varchar(255) DEFAULT NULL COMMENT '鱼的图片路径',\n" +
                "  `breadingPoint` text COMMENT '养殖要点',\n" +
                "  `dieaseControl` text COMMENT '病害防治',\n" +
                "  `name` varchar(255) NOT NULL COMMENT '名称',\n" +
                "  `type` varchar(255) DEFAULT NULL COMMENT '类型',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG,"更新数据库--------->");
    }
}
