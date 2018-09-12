package com.feng.android.robotchat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.feng.android.robotchat.model.Message;

public class MessageBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "messageBase.db";

    public MessageBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MessageDbSchema.RecordTable.NAME + "("
                + MessageDbSchema.RecordTable.Cols.ID + " integer primary key autoincrement, "
                + MessageDbSchema.CollectionTable.Cols.UUID + " text, "
                + MessageDbSchema.RecordTable.Cols.TIME + " text, "
                + MessageDbSchema.RecordTable.Cols.TYPE + " integer, "
                + MessageDbSchema.RecordTable.Cols.TEXT + " text, "
                + MessageDbSchema.RecordTable.Cols.CODE + " text,"
                + MessageDbSchema.RecordTable.Cols.COMMENT + " text,"
                + MessageDbSchema.RecordTable.Cols.IMAGE + " text,"
                + MessageDbSchema.RecordTable.Cols.URL + " text"
                + ")");

        db.execSQL("create table " + MessageDbSchema.CollectionTable.NAME+ "("
                + MessageDbSchema.CollectionTable.Cols.ID + " integer primary key autoincrement, "
                + MessageDbSchema.CollectionTable.Cols.UUID + " text, "
                + MessageDbSchema.CollectionTable.Cols.TIME + " text, "
                + MessageDbSchema.CollectionTable.Cols.TYPE + " integer, "
                + MessageDbSchema.CollectionTable.Cols.CONTENT + " text, "
                + MessageDbSchema.CollectionTable.Cols.IMAGE + " text, "
                + MessageDbSchema.CollectionTable.Cols.COMMENT + " text"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
