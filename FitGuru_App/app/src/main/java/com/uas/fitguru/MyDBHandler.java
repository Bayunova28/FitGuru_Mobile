package com.uas.fitguru;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "datafood.db";
    private static final String TABLE_NAME = "food";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FOODNAME = "foodName";
    private static final String COLUMN_FOODDESCRIPTION = "foodDescription";
    private static final String COLUMN_FOODCALORIES = "foodCalories";

    //Constructor untuk Class MyDBHandler
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Method untuk Create Database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Membuat Tabel Food
        String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FOODNAME + " VARCHAR(50) NOT NULL, " + COLUMN_FOODDESCRIPTION + " VARCHAR(50) NOT NULL, " + COLUMN_FOODCALORIES + " LONG NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_FOOD);
    }
    //Method yang dipakai untuk upgrade tabel
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /*---- Insert, Select, Update, Delete	*/

    private SQLiteDatabase database;

    //Method untuk open database connection
    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    //Inisialisasi semua kolom di tabel database
    private String[] allColumns =
            {COLUMN_ID, COLUMN_FOODNAME, COLUMN_FOODDESCRIPTION, COLUMN_FOODCALORIES};

    //Method untuk memindahkan isi cursor ke objek Food
    private Food cursorToFood(Cursor cursor) {
        Food food = new Food();

        food.setID(cursor.getLong(0));
        food.setFoodName(cursor.getString(1));
        food.setFoodDescription(cursor.getString(2));
        food.setFoodCalories(cursor.getLong(3));
        return food;

    }
    //Method untuk menambahkan food baru
    public void createFood(String foodName, String foodDescription, long foodCalories) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOODNAME, foodName);
        values.put(COLUMN_FOODDESCRIPTION, foodDescription);
        values.put(COLUMN_FOODCALORIES, foodCalories);

        database.insert(TABLE_NAME, null, values);
    }

    //Method untuk mendapatkan detail per food
    public Food getFood(long id) {
        Food food = new Food();

        Cursor cursor =
                database.query(TABLE_NAME, allColumns, "_id=" + id, null, null, null, null);
        cursor.moveToFirst();
        food = cursorToFood(cursor);
        cursor.close();
        return food;
    }

    //Method untuk mendapatkan data semua food di tabel food
    public ArrayList<Food> getAllFood() {
        ArrayList<Food> foodList = new ArrayList<Food>();

        Cursor cursor =
                database.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Food food = cursorToFood(cursor);
            foodList.add(food);
            cursor.moveToNext();
        }

        cursor.close();
        return foodList;
    }

    //Method untuk mengupdate data food
    public void updateFood(Food food) {
        String filter = "_id=" + food.getID();
        ContentValues args = new ContentValues();
        args.put(COLUMN_FOODNAME, food.getFoodName());
        args.put(COLUMN_FOODDESCRIPTION, food.getFoodDescription());
        args.put(COLUMN_FOODCALORIES, food.getFoodCalories());

        database.update(TABLE_NAME, args, filter, null);
    }

    //Method untuk menghapus data food
    public void deleteFood(long id) {
        String filter = "_id=" + id;

        database.delete(TABLE_NAME, filter, null);
    }

    //Method untuk menghitung jumlah calories
    public int getTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(foodCalories) FROM " + TABLE_NAME, null);
        c.moveToFirst();
        int i = c.getInt(0);
        c.close();
        return i;
    }

    //Method untuk menghapus semua food di tabel food
    public void deleteAllFood() {
        database.delete(TABLE_NAME, null, null);
        database.close();
    }
}
