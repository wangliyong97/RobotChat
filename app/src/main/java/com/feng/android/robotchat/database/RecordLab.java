package com.feng.android.robotchat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.feng.android.robotchat.Image.ImageUtils;
import com.feng.android.robotchat.model.Message;
import com.feng.android.robotchat.view.MessageAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordLab {

    private static RecordLab sRecordLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static RecordLab get(Context context){
        if(sRecordLab == null){
            sRecordLab = new RecordLab(context);
        }
        return sRecordLab;
    }

    private RecordLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new MessageBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Message message){
        ContentValues values = new ContentValues();
        values.put(MessageDbSchema.RecordTable.Cols.UUID , message.getUuid().toString());
        values.put(MessageDbSchema.RecordTable.Cols.TIME , message.getTime());
        values.put(MessageDbSchema.RecordTable.Cols.TYPE , message.getType());
        values.put(MessageDbSchema.RecordTable.Cols.IMAGE , message.getPicture());
        values.put(MessageDbSchema.RecordTable.Cols.TEXT , message.getText());
        values.put(MessageDbSchema.RecordTable.Cols.CODE , message.getCode());
        values.put(MessageDbSchema.RecordTable.Cols.COMMENT , message.getComment());
        values.put(MessageDbSchema.RecordTable.Cols.URL , message.getUrl());
        return values;
    }

    //添加记录
    public void addRecord(Message message){
        ContentValues values = getContentValues(message);
        mDatabase.insert(MessageDbSchema.RecordTable.NAME,null,values);
    }

    //
    public void addComment(String uuid,Message message){
        ContentValues values = getContentValues(message);
        mDatabase.update(MessageDbSchema.RecordTable.NAME,values,"uuid like ?",new String[]{uuid});
    }

    //获取所有记录
    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        RecordCursorWrapper cursor = queryRecord(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Message message = cursor.getMessages();
                messages.add(message);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return messages;
    }

    private RecordCursorWrapper queryRecord(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MessageDbSchema.RecordTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new RecordCursorWrapper(cursor);
    }

    private int deleteRecord(String whereClasue,String[] whereArgs){
        int result = mDatabase.delete(
                MessageDbSchema.RecordTable.NAME,
                whereClasue,
                whereArgs
        );
        return result;
    }

    //获取有收藏内容的记录
    public List<Message> getComments(){
        List<Message> messages = new ArrayList<>();
        RecordCursorWrapper cursor = queryRecord(MessageDbSchema.RecordTable.Cols.COMMENT + " is not null",null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Message message = cursor.getMessages();
                messages.add(message);
                Log.d("s",message.getComment());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return messages;
    }

    //获取含有字段s的字段
    public List<Message> searchRecords(String s){
        List<Message> messages = new ArrayList<>();
        RecordCursorWrapper cursor = queryRecord(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Message message = cursor.getMessages();
                if(message.getTime().indexOf(s) != -1 || message.getText().indexOf(s) != -1){
                    messages.add(message);
                    Log.d("hi",message.getText());
                }
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return messages;
    }

    public Message getUUIDMessage(String s){
        Message message;
        RecordCursorWrapper cursor = queryRecord(MessageDbSchema.RecordTable.Cols.UUID +" like ?" ,new String[]{s});
        try {
            cursor.moveToFirst();
            message = cursor.getMessages();
        }finally {
            cursor.close();
        }
        return message;
    }

    public void deleteUUIDMessage(String s){
        Message message = getUUIDMessage(s);
        if(message.getCode().equals("400000")){
            File file = new File(ImageUtils.PATH + s);
            if(file.exists()){
                file.delete();
            }
        }
        deleteRecord(MessageDbSchema.RecordTable.Cols.UUID + " like ?",new String[]{s});
    }

    public void deleteAll(){
        RecordCursorWrapper cursor = queryRecord(MessageDbSchema.RecordTable.Cols.CODE + " like ?",new String[]{"400000"});
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Message message = cursor.getMessages();
                File file = new File(ImageUtils.PATH + message.getUuid().toString());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        deleteRecord(null,null);
    }
}
