package com.androidtopup.tripapptcd0502.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManagement {
    private ExpenseAppDataBaseHelper dataBaseHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManagement(Context context) {
        this.context = context;
    }

    public DatabaseManagement open() throws SQLException {
        dataBaseHelper = new ExpenseAppDataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }
}
