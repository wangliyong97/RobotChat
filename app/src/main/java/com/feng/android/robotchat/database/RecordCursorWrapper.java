package com.feng.android.robotchat.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.feng.android.robotchat.database.MessageDbSchema.RecordTable;
import com.feng.android.robotchat.model.Message;

import java.util.UUID;

public class RecordCursorWrapper extends CursorWrapper {

    public RecordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Message getMessages(){
        String uuid = getString(getColumnIndex(RecordTable.Cols.UUID));
        String time = getString(getColumnIndex(RecordTable.Cols.TIME));
        String content = getString(getColumnIndex(RecordTable.Cols.TEXT));
        int type = getInt(getColumnIndex(RecordTable.Cols.TYPE));
        String image = getString(getColumnIndex(RecordTable.Cols.IMAGE));
        String code = getString(getColumnIndex(RecordTable.Cols.CODE));
        String comment = getString(getColumnIndex(RecordTable.Cols.COMMENT));
        String url = getString(getColumnIndex(RecordTable.Cols.URL));

        Message message = new Message(UUID.fromString(uuid));
        message.setTime(time);
        message.setPicture(image);
        message.setText(content);
        message.setType(type);
        message.setCode(code);
        message.setComment(comment);
        message.setUrl(url);
        return message;
    }
}
