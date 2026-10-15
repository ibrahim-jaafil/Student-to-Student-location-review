package com.example.dsa2_project.Database.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dsa2_project.Database.DatabaseHelper;
import com.example.dsa2_project.login_sys.User;

public class UsersTable {

    private final DatabaseHelper dbHelper;


    public UsersTable(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insert(String username, String email, String password, int STUid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("STUid",    STUid);
        cv.put("username", username);
        cv.put("email",    email);
        cv.put("password", password);
        db.insertWithOnConflict("USERS", null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public Cursor loginVulnerable(int STUid, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM USERS" +
                " WHERE STUid = '" + STUid +
                "' AND password = '" + password + "'";
        return db.rawQuery(query, null);

    }
    public Cursor loginSecure(int STUid, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM USERS WHERE STUid = ? AND password = ?",
                new String[]{String.valueOf(STUid), password}
        );
    }
    public Cursor getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM USERS", null);
    }

    public Cursor getById(int STUid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM USERS WHERE STUid = ?",
                new String[]{String.valueOf(STUid)}
        );
    }

    public User getByUid(int STUid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERS WHERE STUid = ?", new String[]{String.valueOf(STUid)});
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndexOrThrow("username"));
            String email = c.getString(c.getColumnIndexOrThrow("email"));
            String pass = c.getString(c.getColumnIndexOrThrow("password"));
            com.example.dsa2_project.login_sys.User user = new com.example.dsa2_project.login_sys.User(name, email, pass, STUid);
            c.close();
            return user;
        }
        c.close();
        return null;
    }
}
