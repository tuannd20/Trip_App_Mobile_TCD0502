package com.androidtopup.tripapptcd0502;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ExpenseAppDataBaseHelper extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Expense_Management.db";
    private  Context context;

    private static final String TABLE_TRIP = "TRIPS";
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String DESTINATION = "destination";
    private static final String DATE_OF_TRIP = "dateOfTrip";
    private static final String REQUIRE_ASSESSMENT= "requireAssessment";
    private static final String DESC = "description";

    private static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_TRIP + " (" + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + DESTINATION
            + " TEXT NOT NULL, " + DATE_OF_TRIP + " TEXT NOT NULL, " + REQUIRE_ASSESSMENT +
            " TEXT, " + DESC + " TEXT);";


    public ExpenseAppDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    void create(String nameTrip, String destination, String date, String assessment, String desc) {
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
}
