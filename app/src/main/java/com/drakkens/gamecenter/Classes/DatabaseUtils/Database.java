package com.drakkens.gamecenter.Classes.DatabaseUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private final SQLiteDatabase writableDatabase;
    private final SQLiteDatabase readableDatabase;

    public Database(Context context, String name) {
        super(context, name, null, 1);
        Log.d("Database Creation", "Class Creation Successful");
//        context.deleteDatabase(name);
        writableDatabase = this.getWritableDatabase();
        readableDatabase = this.getReadableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Database Creation", "Table Creation Successful");
        try {
            sqLiteDatabase.execSQL("CREATE TABLE users (userName TEXT PRIMARY KEY, password TEXT)");
            sqLiteDatabase.execSQL("CREATE TABLE scores (scoreID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, time INTEGER, scoreValue INTEGER, date TEXT, FOREIGN KEY(userName) REFERENCES Users(userName))");

        } catch (Exception exception) {
            exception.printStackTrace();
            Log.d("Database Creation", "Table Creation Omitted");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertScore(String user, int score, int time) {
        ContentValues values = new ContentValues();
        values.put("userName", user);
        values.put("time", time);
        values.put("scoreValue", score);
        values.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        Log.d("Insert", "Score Inserted");

        writableDatabase.insert("scores", null, values);

    }

    public int insertUser(String user, String password) {
        ContentValues values = new ContentValues();
        values.put("userName", user);
        values.put("password", password);

        Log.d("Insert", "User Inserted");

        return (int) writableDatabase.insert("users", null, values);

    }

    public ArrayList<Score> getScores(String selection, String[] selectionArgs, String sortOrder) {
        ArrayList<Score> scores = new ArrayList<>();

        //eX: selection = userName = ? AND Score <"
        //ex selectionArgs = "Joan", "100"
        Cursor cursor = readableDatabase.query("scores", null, selection, selectionArgs, null, null, sortOrder);

        while (cursor.moveToNext()) {
            Score score = new Score(cursor.getString(cursor.getColumnIndexOrThrow("userName")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("scoreValue")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("time")),
                    cursor.getString(cursor.getColumnIndexOrThrow("date")));

            scores.add(score);


        }

        cursor.close();

        return scores;
    }

    public String getUserPassword(String userName) {

        Cursor cursor = readableDatabase.query("users", null, "userName = ?", new String[]{userName}, null, null, "userName ASC");
        try {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndexOrThrow("password"));
        } catch (Exception exception) {
            return "User Not Found";

        } finally {
            cursor.close();
        }

    }

    public void updatePassword(String userName, String password) {
        ContentValues values = new ContentValues();
        values.put("password", password);

        writableDatabase.update("users",values, "userName LIKE ?", new String[]{userName});

    }
}
