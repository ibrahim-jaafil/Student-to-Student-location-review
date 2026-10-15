package com.example.dsa2_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Student-review-Location.db";
    private static final int DB_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS ("+
                "STUid INTEGER PRIMARY KEY,"+
                "username TEXT,"+
                "email TEXT,"+
                "password TEXT)"
                );

        db.execSQL("CREATE TABLE IF NOT EXISTS CAMPUS ("+
                "cid INTEGER PRIMARY KEY,"+
                "Cname TEXT,"+
                "Location TEXT)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS PLACE ("+
                "pid INTEGER PRIMARY KEY,"+
                "Pname TEXT,"+
                "price_range INTEGER,"+
                "location_type TEXT,"+
                "starsRating INTEGER,"+
                "Location_Link TEXT,"+
                "cid INTEGER,"+
                "FOREIGN KEY (cid) REFERENCES CAMPUS(cid))"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS REVIEW ("+
                "rid INTEGER PRIMARY KEY,"+
                "review TEXT,"+
                "rating INTEGER,"+
                "date TEXT,"+
                "pid INTEGER,"+
                "STUid INTEGER,"+
                "FOREIGN KEY (pid) REFERENCES PLACE(pid),"+
                "FOREIGN KEY (STUid) REFERENCES USERS(STUid))"
                );

        db.execSQL("CREATE TABLE IF NOT EXISTS FAVORITES (" +
                "fid INTEGER PRIMARY KEY," +
                "STUid INTEGER," +
                "pid INTEGER," +
                "FOREIGN KEY (STUid) REFERENCES USERS(STUid)," +
                "FOREIGN KEY (pid) REFERENCES PLACE(pid))"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop in reverse order of dependencies
        db.execSQL("DROP TABLE IF EXISTS FAVORITES");
        db.execSQL("DROP TABLE IF EXISTS REVIEW");
        db.execSQL("DROP TABLE IF EXISTS PLACE");
        db.execSQL("DROP TABLE IF EXISTS CAMPUS");
        db.execSQL("DROP TABLE IF EXISTS USERS");
        onCreate(db);
    }
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

}
