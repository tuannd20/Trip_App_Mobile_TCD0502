package com.androidtopup.tripapptcd0502.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ExpenseAppDataBaseHelper extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Expense_Management.db";
    private final Context context;

    private static final String TABLE_TRIP = "TRIPS";
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String DESTINATION = "destination";
    private static final String DATE_OF_TRIP = "dateOfTrip";
    private static final String REQUIRE_ASSESSMENT= "requireAssessment";
    private static final String DESC = "description";

    private static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_TRIP + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + DESTINATION + " TEXT, "
            + DATE_OF_TRIP + " TEXT, "
            + REQUIRE_ASSESSMENT + " TEXT, "
            + DESC + " TEXT);";


    public ExpenseAppDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRIP + "'");
        onCreate(db);
    }

    public void create(String nameTrip, String destination, String date, String assessment, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, nameTrip);
        values.put(DESTINATION, destination);
        values.put(DATE_OF_TRIP, date);
        values.put(REQUIRE_ASSESSMENT, assessment);
        values.put(DESC, desc);

        long result = db.insert(TABLE_TRIP, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

     public Cursor displayAllTrip() {
        String query = "SELECT * FROM " + TABLE_TRIP;
        SQLiteDatabase result = this.getReadableDatabase();

        Cursor cursor = null;
        if (result != null){
            cursor = result.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getDescriptionById(String id) {
        Cursor cursor = null;
        String query = "SELECT " + DESC + " FROM " + TABLE_TRIP + " WHERE " + _ID + " = " + id;
        SQLiteDatabase result = this.getWritableDatabase();
        if (result != null) {
            cursor = result.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String trip_id, String nameTrip, String destination, String date, String assessment, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valueUpdate = new ContentValues();
        valueUpdate.put(NAME, nameTrip);
        valueUpdate.put(DESTINATION, destination);
        valueUpdate.put(DATE_OF_TRIP, date);
        valueUpdate.put(REQUIRE_ASSESSMENT, assessment);
        valueUpdate.put(DESC, desc);

        long result = db.update(TABLE_TRIP, valueUpdate, "_id=?", new String[]{trip_id});
        if (result == -1) {
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneTripById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TRIP, "_id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllTrip() {
        SQLiteDatabase exeDelete = this.getWritableDatabase();
        exeDelete.execSQL("DELETE FROM " + TABLE_TRIP);
    }

    @SuppressLint("Recycle")
    public Cursor searchTrip(String keySearch) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {_ID,NAME,DESTINATION,DATE_OF_TRIP,REQUIRE_ASSESSMENT};
        Cursor cursor = null;

        if(keySearch != null && keySearch.length() > 0) {
            String query = "SELECT * FROM "+TABLE_TRIP+" WHERE "+NAME+" LIKE '%"+keySearch+"%'";
            cursor = db.rawQuery(query,null);
            return cursor;
        }

        cursor = db.query(TABLE_TRIP,columns,null,null,null,null,null);
        return cursor;
    }
}
