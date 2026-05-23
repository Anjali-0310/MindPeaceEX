package com.example.mindpeaceex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AppDBHelper extends SQLiteOpenHelper {

    public AppDBHelper(Context context) {
        super(context, "MindPeace.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE gratitude_entries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mood TEXT," +
                "template TEXT," +
                "text TEXT)");

        db.execSQL("CREATE TABLE journal_entries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mood TEXT," +
                "text TEXT," +
                "time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS gratitude_entries");
        db.execSQL("DROP TABLE IF EXISTS journal_entries");
        onCreate(db);
    }

    // 🔹 GRATITUDE SAVE
    public void insertGratitude(String mood, String template, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("mood", mood);
        cv.put("template", template);
        cv.put("text", text);

        db.insert("gratitude_entries", null, cv);
    }

    // ✅ NEW: GET GRATITUDE DATA (FOR DISPLAY + EDIT + DELETE)
    public Cursor getAllGratitudeData() {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM gratitude_entries ORDER BY id DESC", null);
    }

    // ✅ NEW: DELETE GRATITUDE
    public void deleteGratitude(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("gratitude_entries", "id=?", new String[]{String.valueOf(id)});
    }

    // ✅ NEW: UPDATE GRATITUDE
    public void updateGratitude(int id, String newText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("text", newText);

        db.update("gratitude_entries", cv, "id=?",
                new String[]{String.valueOf(id)});
    }

    // 🔹 JOURNAL SAVE
    public void insertJournal(String mood, String text, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("mood", mood);
        cv.put("text", text);
        cv.put("time", time);

        db.insert("journal_entries", null, cv);
    }

    // 🔹 GET JOURNAL (OLD METHOD)
    public ArrayList<String> getJournalEntries() {
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase()
                .rawQuery("SELECT * FROM journal_entries ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String mood = cursor.getString(1);
                String text = cursor.getString(2);
                String time = cursor.getString(3);

                list.add(mood + "  " + time + "\n" + text);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    // ✅ GET FULL JOURNAL DATA
    public Cursor getAllJournalData() {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM journal_entries ORDER BY id DESC", null);
    }

    // ✅ DELETE JOURNAL
    public void deleteJournal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("journal_entries", "id=?", new String[]{String.valueOf(id)});
    }

    // ✅ UPDATE JOURNAL
    public void updateJournal(int id, String newText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("text", newText);

        db.update("journal_entries", cv, "id=?",
                new String[]{String.valueOf(id)});
    }
}