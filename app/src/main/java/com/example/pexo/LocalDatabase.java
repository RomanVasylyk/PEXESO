package com.example.pexo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 2;

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE words (id INTEGER PRIMARY KEY, word TEXT)");
        insertWords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE words ADD COLUMN new_column TEXT");
        }
        db.execSQL("DROP TABLE IF EXISTS words");
        onCreate(db);
    }

    private void insertWords(SQLiteDatabase db) {
        db.execSQL("INSERT INTO words (word) VALUES ('Cat')");
        db.execSQL("INSERT INTO words (word) VALUES ('Dog')");
        db.execSQL("INSERT INTO words (word) VALUES ('Bird')");
        db.execSQL("INSERT INTO words (word) VALUES ('Fish')");
        db.execSQL("INSERT INTO words (word) VALUES ('Lion')");
        db.execSQL("INSERT INTO words (word) VALUES ('Tiger')");
        db.execSQL("INSERT INTO words (word) VALUES ('Horse')");
        db.execSQL("INSERT INTO words (word) VALUES ('Mouse')");
        db.execSQL("INSERT INTO words (word) VALUES ('Elephant')");
        db.execSQL("INSERT INTO words (word) VALUES ('Monkey')");
        db.execSQL("INSERT INTO words (word) VALUES ('Giraffe')");
        db.execSQL("INSERT INTO words (word) VALUES ('Zebra')");
        db.execSQL("INSERT INTO words (word) VALUES ('Bear')");
        db.execSQL("INSERT INTO words (word) VALUES ('Wolf')");
        db.execSQL("INSERT INTO words (word) VALUES ('Fox')");
        db.execSQL("INSERT INTO words (word) VALUES ('Deer')");
        db.execSQL("INSERT INTO words (word) VALUES ('Rabbit')");
        db.execSQL("INSERT INTO words (word) VALUES ('Kangaroo')");
        db.execSQL("INSERT INTO words (word) VALUES ('Panda')");
        db.execSQL("INSERT INTO words (word) VALUES ('Koala')");
        db.execSQL("INSERT INTO words (word) VALUES ('Dolphin')");
        db.execSQL("INSERT INTO words (word) VALUES ('Whale')");
        db.execSQL("INSERT INTO words (word) VALUES ('Shark')");
    }
}
