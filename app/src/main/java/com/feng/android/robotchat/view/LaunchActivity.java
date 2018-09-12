package com.feng.android.robotchat.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.feng.android.robotchat.R;
import com.feng.android.robotchat.model.WeatherRequest;
import com.feng.android.robotchat.model.WeatherResponse;
import com.feng.android.robotchat.net.OKHttpUtils;
import com.feng.android.robotchat.net.ParseUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LaunchActivity extends AppCompatActivity {

    private FloatingActionButton launchChatButton;
    private FloatingActionButton launchRecordButton;
    private FloatingActionButton launchCollectButton;

    private WeatherResponse weatherResponse;

    private TextView launchTmpTextView;
    private TextView launchLocTextView;
    private TextView launchCondTextTextView;
    private TextView launchWindDirTextView;
    private TextView launchWindScTextView;
    private TextView launchPcpnTextView;
    private TextView launchPresTextView;
    private TextView launchVisTextView;




    private final static String WEATHER_URL = "https://free-api.heweather.com/s6/weather/now?parameters";
    private final static String WEATHER_KEY = "9d5a225d9edb45c39753750fbe6fa28d";


    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);
        }

        initView();

    }

    private void initView(){
        getResponse("118.93,32");

        launchTmpTextView = (TextView)findViewById(R.id.launch_tmp_textview);

        launchLocTextView = (TextView)findViewById(R.id.launch_loc_textview);

        launchCondTextTextView = (TextView)findViewById(R.id.launch_cond_text_textview);


        launchWindDirTextView = (TextView)findViewById(R.id.launch_wind_dir_textview);

        launchWindScTextView = (TextView)findViewById(R.id.launch_wind_sc_textview);

        launchPcpnTextView = (TextView)findViewById(R.id.launch_pcpn_textview);

        launchPresTextView = (TextView)findViewById(R.id.launch_pres_textview);

        launchVisTextView = (TextView)findViewById(R.id.launch_vis_textview);


//        //setImageTextView("http://m.image.so.com/i?q=%E7%8B%97");
 //       weatherInfoTextview = (TextView)findViewById(R.id.launch_weather_textview);


        launchChatButton = (FloatingActionButton)findViewById(R.id.launch_chat_button);
        launchChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LaunchActivity.this , ChatActivity.class);
                startActivity(i);
            }
        });

        launchRecordButton = (FloatingActionButton)findViewById(R.id.launch_record_button);
        launchRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LaunchActivity.this , RecordActivity.class);
                startActivity(i);
            }
        });
        launchCollectButton = (FloatingActionButton)findViewById(R.id.launch_collect_button);
        launchCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LaunchActivity.this,CollectionActivity.class);
                startActivity(i);
            }
        });
    }

    private void getResponse(String location){
        final WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setLocation(location);
        weatherRequest.setKey(WEATHER_KEY);

        final Gson gson = new Gson();

        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("location",location)
                .add("key",WEATHER_KEY);//传递键值对参数

        Request request = new Request.Builder().url(WEATHER_URL).post(formBody.build()).build();

        Call call = OKHttpUtils.getInstance().sOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LaunchActivity.this,"对不起，系统故障~~~", Toast.LENGTH_SHORT).show();;

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                weatherResponse = gson.fromJson(response.body().string(),WeatherResponse.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        launchTmpTextView.setText(weatherResponse.getHeWeather6().get(0).getNow().getTmp() + "℃");
                        launchLocTextView.setText(weatherResponse.getHeWeather6().get(0).getUpdate().getLoc());
                        launchVisTextView.setText(weatherResponse.getHeWeather6().get(0).getNow().getVis());
                        launchPresTextView.setText(weatherResponse.getHeWeather6().get(0).getNow().getPres());
                        launchPcpnTextView.setText(weatherResponse.getHeWeather6().get(0).getNow().getPcpn());
                        launchWindScTextView.setText(weatherResponse.getHeWeather6().get(0).getNow().getWind_sc());
                        launchWindDirTextView.setText(weatherResponse.getHeWeather6().get(0).getNow().getWind_dir());
                        launchCondTextTextView.setText(weatherResponse.getHeWeather6().get(0).getNow().getCond_txt());
                    }
                });
            }
        });
    }
}
