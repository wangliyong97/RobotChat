package com.feng.android.robotchat.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.feng.android.robotchat.R;
import com.feng.android.robotchat.database.RecordLab;
import com.feng.android.robotchat.model.Message;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private RecyclerView recordRecyclerview;
    private MessageAdapter messageAdapter;
    private Button backButton;
    private List<Message> mMessages = new ArrayList<>();
    private Button searchButton;
    private EditText searchEdittext;
    private Button clearButton;
    private Boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initView();
    }

    public void initView(){
        searchEdittext = (EditText)findViewById(R.id.record_search_edittext);

        clearButton = (Button)findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessages.clear();
                RecordLab.get(RecordActivity.this).deleteAll();
                messageAdapter.notifyDataSetChanged();
            }
        });

        searchButton = (Button)findViewById(R.id.record_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchEdittext.getText().toString().length() > 0){
                    mMessages.clear();
                    mMessages.addAll(RecordLab.get(RecordActivity.this).searchRecords(searchEdittext.getText().toString())) ;
                    flag = false;
                    messageAdapter.notifyDataSetChanged();
                }

            }
        });
        backButton = (Button)findViewById(R.id.record_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    finish();
                }else {
                    mMessages.clear();
                    mMessages.addAll(RecordLab.get(getApplicationContext()).getMessages());
                    flag = true;
                    searchEdittext.setText("");
                    messageAdapter.notifyDataSetChanged();
                }

            }
        });
        recordRecyclerview = (RecyclerView)findViewById(R.id.record_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recordRecyclerview.setLayoutManager(linearLayoutManager);

        mMessages = RecordLab.get(getApplicationContext()).getMessages();
        messageAdapter = new MessageAdapter(mMessages,this);
        recordRecyclerview.setAdapter(messageAdapter);

    }
}
