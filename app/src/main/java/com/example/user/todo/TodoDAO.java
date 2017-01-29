package com.example.user.todo;

/**
 * Created by user on 2017/1/28.
 */

        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

        import android.content.ClipData;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

// 資料功能類別
public class TodoDAO {
    // 表格名稱
    public static final String TABLE_NAME = "item";
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它表格欄位名稱
    public static final String CONTENT_COLUMN = "content";
    public static final String MONTH_COLUMN = "month";
    public static final String DAY_COLUMN = "day";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MONTH_COLUMN + "  TEXT NOT NULL, "+
                    DAY_COLUMN + "  TEXT NOT NULL, "+
                    CONTENT_COLUMN + "  TEXT NOT NULL )";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public TodoDAO(Context context) {

        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public  TodoData insert(TodoData todoData) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        cv.put(CONTENT_COLUMN, todoData.getContent());
        cv.put(MONTH_COLUMN, todoData.getMonth());
        cv.put(DAY_COLUMN, todoData.getDay());
        long id = db.insert(TABLE_NAME, null, cv);
        // 回傳結果
        todoData.setId(id);
        return todoData;
    }

    // 修改參數指定的物件
    public boolean update(TodoData todoData) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(CONTENT_COLUMN, todoData.getContent());
        cv.put(MONTH_COLUMN, todoData.getMonth());
        cv.put(DAY_COLUMN, todoData.getDay());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + todoData.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<TodoData> getAll() {
        List<TodoData> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public TodoData get(long id) {
        // 準備回傳結果用的物件
        TodoData item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public TodoData getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        TodoData result = new TodoData();

        result.setId(cursor.getLong(0));
        result.setMonth(cursor.getString(1));
        result.setDay(cursor.getString(2));
        result.setContent(cursor.getString(3));



        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

}
