package com.example.pexo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WordsRepository {

    private LocalDatabase dbHelper;

    public WordsRepository(Context context) {
        dbHelper = new LocalDatabase(context);
    }

    public List<String> getAllWords() {
        List<String> words = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT word FROM words", null);

        if (cursor.moveToFirst()) {
            do {
                words.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return words;
    }
}
