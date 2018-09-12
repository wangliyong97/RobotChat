package com.feng.android.robotchat.view;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feng.android.robotchat.net.OKHttpUtils;
import com.feng.android.robotchat.net.ParseUtils;
import com.feng.android.robotchat.R;
import com.feng.android.robotchat.database.RecordLab;
import com.feng.android.robotchat.model.Message;
import com.feng.android.robotchat.model.RequestMessage;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView messageRecyclerView;
    private List<Message> mMessages = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private Button recordButton;
    private Button collectButton;
    private Button sendButton;
    private Button backButton;
    private EditText editSendMessageTextview;

    private Message tmpMessage;
    private static final String API_KEY = "bbd581fea793412e9d188e637b4dace8";
    private String loaction = "南京市栖霞区";
    private String account = android.os.Build.SERIAL;            //登陆的账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void getResponse(String content){
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setKey(API_KEY);
        requestMessage.setInfo(content);
        requestMessage.setLoc(loaction);
        requestMessage.setUserid(account);

        Gson gson = new Gson();
        String json = gson.toJson(requestMessage);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),json);

        Request request = new Request.Builder().url("http://www.tuling123.com/openapi/api").post(requestBody).build();
        //发送消息

        Call call = OKHttpUtils.getInstance().sOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatActivity.this,"对不起，系统故障~~~",Toast.LENGTH_SHORT).show();;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                tmpMessage = new Message();
                tmpMessage = ParseUtils.parseResponse(response.body().string());
                Date date = new Date();
                tmpMessage.setTime(date.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addNewMessage2RecyclerView(tmpMessage);
                    }
                });
            }
        });
    }

    public void addNewMessage2RecyclerView(Message message){
        mMessages.add(message);
        RecordLab.get(getApplicationContext()).addRecord(message);

        messageAdapter.notifyItemChanged(mMessages.size() - 1);
        messageRecyclerView.scrollToPosition(mMessages.size() - 1);
    }

    private void initView(){

        editSendMessageTextview = (EditText) findViewById(R.id.edit_send_message);

        backButton = (Button)findViewById(R.id.chat_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置收藏按钮
        collectButton = (Button) findViewById(R.id.collection_button);
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //设置消息记录按钮
        recordButton = (Button) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });


        //设置RecyclerView
        messageRecyclerView = (RecyclerView)findViewById(R.id.message_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(linearLayoutManager);

        mMessages = RecordLab.get(getApplicationContext()).getMessages();
        messageAdapter = new MessageAdapter(mMessages,this);
        messageRecyclerView.setAdapter(messageAdapter);

        //设置发送按钮
        sendButton = (Button)findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Message message = new Message();
                message.setTime(date.toString());
                message.setText(editSendMessageTextview.getText().toString());
                message.setType(1);

                addNewMessage2RecyclerView(message);

                editSendMessageTextview.setText("");

                getResponse(message.getText());
            }
        });
    }


}
