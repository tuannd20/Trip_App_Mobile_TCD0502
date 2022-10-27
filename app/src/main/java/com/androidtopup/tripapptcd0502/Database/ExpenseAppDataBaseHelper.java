package com.androidtopup.tripapptcd0502.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseAppDataBaseHelper extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION = 15;
    private static final String DATABASE_NAME = "Expense_Management.db";
    private final Context context;

    private static final String TABLE_TRIP = "TRIPS";
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String DESTINATION = "destination";
    private static final String DATE_OF_TRIP = "dateOfTrip";
    private static final String REQUIRE_ASSESSMENT= "requireAssessment";
    private static final String DESC = "description";

    private static final String TABLE_EXPENSES = "EXPENSES";
    private static final String ID = "id";
    private static final String TRIP_ID = "trip_id";
    private static final String TYPE = "type";
    private static final String AMOUNT = "amount";
    private static final String DATE_OF_EXPENSE = "dateOfExpense";



    private static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_TRIP + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + DESTINATION + " TEXT, "
            + DATE_OF_TRIP + " TEXT, "
            + REQUIRE_ASSESSMENT + " TEXT, "
            + DESC + " TEXT);";

    private static final String QUERY_CREATE_TABLE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSES +" ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRIP_ID + " INTERGER NOT NULL, "
            + TYPE + " TEXT, "
            + AMOUNT + " TEXT, "
            + DATE_OF_EXPENSE + " TEXT, "
            + " FOREIGN KEY (" + TRIP_ID + ") REFERENCES " + TABLE_TRIP+" (" + _ID +"))";


    public ExpenseAppDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
        db.execSQL(QUERY_CREATE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRIP + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_EXPENSES + "'");
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

        db.insert(TABLE_TRIP, null, values);
//        long result = db.insert(TABLE_TRIP, null, values);
//        if (result == -1) {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
//        }
        db.close();
    }

     public Cursor displayAllTrip(String keySearch) {

        if (keySearch.length() == 0) {
            String query = "SELECT * FROM " + TABLE_TRIP;
            SQLiteDatabase result = this.getReadableDatabase();
            Cursor cursor = null;
            if (result != null){
                cursor = result.rawQuery(query, null);
            }
            return cursor;
        } else  {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = null;
                String query = "SELECT * FROM "+TABLE_TRIP+" WHERE "+NAME+" LIKE '%"+keySearch+"%'";
                cursor = db.rawQuery(query,null);
            return cursor;
        }
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

    public Cursor getTripById(String id) {
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_TRIP + " WHERE " + _ID + " = " + id;
        SQLiteDatabase result = this.getWritableDatabase();
        if (result != null) {
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

//        long result = db.update(TABLE_TRIP, valueUpdate, "_id=?", new String[]{trip_id});
//        if (result == -1) {
//            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
//        }
    }

    public void deleteOneTripById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIP, "_id=?", new String[]{id});
//        long result = db.delete(TABLE_TRIP, "_id=?", new String[]{id});
//        if (result == -1) {
//            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
//        }
    }

    public void deleteAllTrip() {
        SQLiteDatabase exeDelete = this.getWritableDatabase();
        exeDelete.execSQL("DELETE FROM " + TABLE_TRIP);
    }

    // =======================================================================
    // Query of expense
    public void createExpenses(int idTrip, String typOfExpense, String time, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TRIP_ID, idTrip);
        values.put(TYPE, typOfExpense);
        values.put(AMOUNT, amount);
        values.put(DATE_OF_EXPENSE, time);

//        long result = db.insert(TABLE_EXPENSES, null, values);
//        if (result == -1) {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
//        }
        db.close();
    }

    public Cursor readAllExpenseOfTrip(int tripId) {
        String query = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " + TRIP_ID + " = " + tripId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
