package com.example.dsa2_project.Database.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Database.DatabaseHelper;

public class CampusTable {
    private final DatabaseHelper dbHelper;
    public CampusTable(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insert(String name, String location) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Cname", name);
        cv.put("Location", location);
        db.insertWithOnConflict("CAMPUS", null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }
    public Cursor getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM CAMPUS", null);
    }
    public int getCidByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT cid FROM CAMPUS WHERE Cname = ?",
                new String[]{name}
        );
        int cid = -1;
        if (c.moveToFirst()) {
            cid = c.getInt(c.getColumnIndexOrThrow("cid"));
        }
        c.close();
        return cid;
    }

    public Campus getByCid(int cid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM CAMPUS WHERE cid = ?",
                new String[]{String.valueOf(cid)}
        );

        Campus campus = null;

        if (c.moveToFirst()) {
            String name     = c.getString(c.getColumnIndexOrThrow("Cname"));
            String location = c.getString(c.getColumnIndexOrThrow("Location"));
            campus = new Campus(name, location);
        }

        c.close();
        return campus;
    }

    public int getCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM CAMPUS", null);
        int count = 0;
        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }
}
