package com.boris.pokemontool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PlayerNotesDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "playerDB.db";
    private static final String TABLE_PLAYERS = "players";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NOTES = "notes";

    public PlayerNotesDBHandler(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PLAYERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT"
                + COLUMN_NOTES + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        onCreate(db);
    }

    public void addPlayer(Player player) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, player.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PLAYERS, null, values);
        db.close();
    }

    public List<Player> getPlayers() {
        String query = "Select * FROM " + TABLE_PLAYERS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Player> players = new ArrayList<Player>();

        while (cursor.moveToNext()) {
            players.add(new Player(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1)));
        }

        db.close();
        return players;
    }

    public boolean deletePlayer(Player player) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_PLAYERS
                + " WHERE " + COLUMN_ID + " =  \""
                + player.getId() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_PLAYERS, COLUMN_ID + " = ?",
                    new String[]{ String.valueOf(player.getId()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

} 