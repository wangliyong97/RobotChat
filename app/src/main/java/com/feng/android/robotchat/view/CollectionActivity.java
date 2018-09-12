package com.feng.android.robotchat.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.feng.android.robotchat.R;
import com.feng.android.robotchat.database.RecordLab;
import com.feng.android.robotchat.model.Message;

import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private RecyclerView collectionRecyclerView;
    private CollectionAdapter collectionAdapter;
    private Button backButton;
    private List<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();
    }

    public void initView(){
        backButton = (Button)findViewById(R.id.collection_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collectionRecyclerView = (RecyclerView)findViewById(R.id.collection_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        collectionRecyclerView.setLayoutManager(manager);

        mMessages = RecordLab.get(getApplicationContext()).getComments();
        Log.d("h",mMessages.toString());
        collectionAdapter = new CollectionAdapter(mMessages,CollectionActivity.this);
        collectionRecyclerView.setAdapter(collectionAdapter);
    }
}
