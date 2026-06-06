package com.example.dsa2_project.data_structures.Database.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dsa2_project.data_structures.Database.DatabaseHelper;

public class FavoritesTable {
    private final DatabaseHelper dbHelper;

    public FavoritesTable(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addFavorite(int STUid, int pid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("STUid", STUid);
        cv.put("pid", pid);
        db.insert("FAVORITES", null, cv);
    }

    public void removeFavorite(int STUid, int pid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("FAVORITES", "STUid = ? AND pid = ?", new String[]{String.valueOf(STUid), String.valueOf(pid)});
    }

    public Cursor getFavoritesForUser(int STUid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT pid FROM FAVORITES WHERE STUid = ?", new String[]{String.valueOf(STUid)});
    }

    public boolean isFavorite(int STUid, int pid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM FAVORITES WHERE STUid = ? AND pid = ?", 
                new String[]{String.valueOf(STUid), String.valueOf(pid)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
