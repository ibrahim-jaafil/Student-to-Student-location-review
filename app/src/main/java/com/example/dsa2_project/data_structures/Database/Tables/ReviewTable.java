package com.example.dsa2_project.data_structures.Database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dsa2_project.data_structures.Database.DatabaseHelper;

public class ReviewTable {
    private final DatabaseHelper dbHelper;
    public ReviewTable(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public void insert(int pid, int STUid, String review, int rating, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pid", pid);
        cv.put("STUid", STUid);
        cv.put("review", review);
        cv.put("rating", rating);
        cv.put("date", date);
        db.insertWithOnConflict("REVIEW", null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }
    public Cursor getByPid(int pid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM REVIEW WHERE pid = ? ORDER BY rid DESC",
                new String[]{String.valueOf(pid)}
        );
    }

    // SELECT BY USER — called in ReviewsHistoryActivity to show user's reviews
    public Cursor getByUser(int STUid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM REVIEW WHERE STUid = ? ORDER BY rid DESC",
                new String[]{String.valueOf(STUid)}
        );
    }

    public void deleteReview(int STUid, int pid, String reviewText) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("REVIEW", "STUid = ? AND pid = ? AND review = ?",
                new String[]{String.valueOf(STUid), String.valueOf(pid), reviewText});
        db.close();
    }
}
