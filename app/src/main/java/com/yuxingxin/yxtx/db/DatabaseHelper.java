package com.yuxingxin.yxtx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sean on 15/7/26.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version
                        , CreateDataBaseListener createDataBaseListener) {
        super(context, name, factory, version);
        this.createDataBaseListener = createDataBaseListener;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (createDataBaseListener != null) {
            createDataBaseListener.onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (createDataBaseListener != null) {
            createDataBaseListener.onUpgrade(db, oldVersion, newVersion);
        }
    }
    private CreateDataBaseListener createDataBaseListener ;

    interface CreateDataBaseListener{
        void onCreate (SQLiteDatabase db);
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) ;
    }
}
