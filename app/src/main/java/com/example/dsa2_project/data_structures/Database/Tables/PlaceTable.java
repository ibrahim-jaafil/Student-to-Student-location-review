package com.example.dsa2_project.data_structures.Database.Tables;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.MainActivity;
import com.example.dsa2_project.data_structures.Database.DatabaseHelper;

public class PlaceTable {

    private final DatabaseHelper dbHelper;

    public PlaceTable(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public int insert(String name, int cid, String location_type, int price_range, int stars,String Location_Link){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Pname",    name);
        cv.put("cid",    cid);
        cv.put("location_type", location_type);
        cv.put("price_range", price_range);
        cv.put("starsRating", stars);
        cv.put("Location_Link", Location_Link);
        db.insertWithOnConflict("PLACE", null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        db.close();
        return getPidByName(name);
    }

    public Cursor getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM PLACE", null);
    }

    public int getPidByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT pid FROM PLACE WHERE Pname = ?",
                new String[]{name}
        );
        int pid = -1;
        if (c.moveToFirst()) {
            pid = c.getInt(c.getColumnIndexOrThrow("pid"));
        }
        c.close();
        return pid;
    }

    public Place getByPid(int pid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM PLACE WHERE pid = ?",
                new String[]{String.valueOf(pid)}
        );

        Place place = null;

        if (c.moveToFirst()) {
            String name  = c.getString(c.getColumnIndexOrThrow("Pname"));
            int    price = c.getInt(c.getColumnIndexOrThrow("price_range"));
            String type  = c.getString(c.getColumnIndexOrThrow("location_type"));
            int    stars = c.getInt(c.getColumnIndexOrThrow("starsRating"));
            String link  = c.getString(c.getColumnIndexOrThrow("Location_Link"));
            int    cid   = c.getInt(c.getColumnIndexOrThrow("cid"));

            Campus campus = MainActivity.campusTable.getByCid(cid);

            place = new Place(name, campus, type, price, stars);
            place.setPid(pid);
            place.setLocation_Link(link);
        }

        c.close();
        return place;
    }
}
