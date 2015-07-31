package com.yuxingxin.yxtx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuxingxin.yxtx.model.ArticleTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 15/7/26.
 */
public class SqliteManager {

    public SqliteManager(Context context){
        this.context = context;
        createDataBase(context);
    }

    public static final String TABLE_NAME = "article";

    public static final String CREATE_ARTICLE = "create table "+ TABLE_NAME +" " +
            "("+" title text,"
            + "summary text,"
            + "date text,"
            + "url text,"
            + "tag text,"
            + "category text"
            +")";

    private Context context;
    private String DATABASE_NAME = "articles.db";
    private int VERSION = 1;
    private DatabaseHelper helper ;

    private SQLiteDatabase db ;

    private String[] TAGS = {"TAG"};
    private String[] CATEGORIES = {"CATEGORY"};

    public boolean hasDatabase(String name){
        return context.getDatabasePath(name).exists();
    }

    /**
     * 创建数据库
     * @param context
     */
    public void createDataBase(Context context){
        this.context = context;
        if (context != null) {
            DatabaseHelper.CreateDataBaseListener createDataBaseListener = new DatabaseHelper.CreateDataBaseListener() {
                @Override
                public void onCreate(SQLiteDatabase db) {
                    db.execSQL(CREATE_ARTICLE);
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                }
            };

            helper = new DatabaseHelper(context,DATABASE_NAME,null,VERSION,createDataBaseListener);
            db = helper.getWritableDatabase();
        }
    }

    /**
     * insert data
     * @param list
     */
    public void insertData(List<ArticleTable> list){
        initDB();
        for (int i = 0; i < list.size();i++){
            ContentValues values = new ContentValues();
            values.put("title", list.get(i).getTitle());
            values.put("summary", list.get(i).getSummary());
            values.put("date", list.get(i).getDate());
            values.put("url", list.get(i).getUrl());
            values.put("tag", list.get(i).getTag());
            values.put("category", list.get(i).getCategory());
            db.insert(TABLE_NAME, null, values);
        }
    }

    /**
     * 删除表数据
     * @return
     */
    public boolean deleteData(){
        initDB();
        int delete = db.delete(TABLE_NAME, null, null);
        if (delete > 0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改表数据
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean alterData(ContentValues values, String whereClause, String[] whereArgs){
        initDB();
        int update = db.update(TABLE_NAME, values, whereClause, whereArgs);
        if (update > 0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查询表数据
     * @param selection
     * @param selectionArgs
     * @return
     */
    public ArrayList<ArticleTable> queryData(String selection, String[] selectionArgs){
        initDB();
        Cursor query = null;
        query = db.query(TABLE_NAME,null, selection, selectionArgs, null, null, null);
        ArrayList<ArticleTable> list = new ArrayList<>();
        if (query.moveToFirst())
        {
            do{
                ArticleTable table = new ArticleTable();

                String title = query.getString(query.getColumnIndex("title"));
                String summary = query.getString(query.getColumnIndex("summary"));
                String date = query.getString(query.getColumnIndex("date"));
                String url = query.getString(query.getColumnIndex("url"));
                String category = query.getString(query.getColumnIndex("category"));
                String tag = query.getString(query.getColumnIndex("tag"));
                table.setTitle(title);
                table.setSummary(summary);
                table.setDate(date);
                table.setUrl(url);
                table.setCategory(category);
                table.setTag(tag);
                list.add(table);
            }while (query.moveToNext());
        }
        query.close();
        return list;
    }

    /**
     * 查询所有Tag/Category
     * @param clu
     * @return
     */
    public List<String> queryBySection(String clu){
        initDB();
        Cursor query = null;
        if ("tag".equals(clu)){
            query = db.query(true,TABLE_NAME,TAGS, null, null, null, null, null,null);
        }else{
            query = db.query(true,TABLE_NAME,CATEGORIES, null, null, null, null, null,null);
        }
        List<String> list = new ArrayList<>();
        if (query.moveToFirst())
        {
            do{
                list.add(query.getString(query.getColumnIndex(clu)));
            }while (query.moveToNext());
        }
        query.close();
        return list;
    }

    /**
     * 关闭SQLiteDatabase
     */
    public void close (){
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * 初始化SQLiteDatabase
     */
    private void initDB () {
        if (db == null) {
            db = helper.getWritableDatabase();
        }
    }

}
