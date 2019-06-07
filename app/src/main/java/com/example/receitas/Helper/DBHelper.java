package com.example.receitas.Helper;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    private static final String DB_NAME="ReceitasDB.db";
    private static final int DB_VER=1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
}
