package com.feng.android.robotchat.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feng.android.robotchat.Image.ImageUtils;
import com.feng.android.robotchat.R;
import com.feng.android.robotchat.database.MessageDbSchema;
import com.feng.android.robotchat.database.RecordLab;
import com.feng.android.robotchat.model.Message;
import com.feng.android.robotchat.net.ParseUtils;

public class SingleCollectionActivity extends AppCompatActivity {

    private static final String EXTRA_COLLECION_UUID = "com.feng.android.robotchat.collection.uuid";

    private TextView mTextView;
    private ImageView mImageView;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_collection);
        initView();
    }

    private void initView(){
        mTextView = (TextView)findViewById(R.id.single_collection_textview);
        StringBuilder builder = new StringBuilder();
        final Message message = RecordLab.get(getApplicationContext()).getUUIDMessage(getIntent().getStringExtra(EXTRA_COLLECION_UUID));
        mTextView.setText(builder.append(message.getTime())
                .append("\n")
                .append(message.getText())
                .append("\n"));
        mImageView = (ImageView)findViewById(R.id.single_imageview);
        if(message.getCode() != null && message.getCode().equals("400000")){
            Bitmap bitmap = ImageUtils.getLoacalBitmap(ParseUtils.PATH +message.getUuid().toString());

            if (bitmap != null){
                mImageView.setImageBitmap(bitmap);
            }
        }
        mEditText = (EditText)findViewById(R.id.single_edittext);
        mEditText.setText(message.getText());
        mButton = (Button)findViewById(R.id.single_confirm_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setComment(mEditText.getText().toString());
                RecordLab.get(SingleCollectionActivity.this).addComment(message.getUuid().toString(),message);
                Toast.makeText(SingleCollectionActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent newIntent(Context context,String uuid){
        Intent intent = new Intent(context,SingleCollectionActivity.class);
        intent.putExtra(EXTRA_COLLECION_UUID,uuid);
        return intent;
    }
}
