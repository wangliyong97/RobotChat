package com.feng.android.robotchat.net;

import android.os.Environment;
import android.util.Log;

import com.feng.android.robotchat.Image.ImageUtils;
import com.feng.android.robotchat.model.Cookbook;
import com.feng.android.robotchat.model.Message;
import com.feng.android.robotchat.model.News;
import com.feng.android.robotchat.model.ResponseMessage;
import com.feng.android.robotchat.model.ResponseMessageCook;
import com.feng.android.robotchat.model.ResponseMessageNews;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseUtils {
    private static final String SAVE_PIC_PATH=Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
    public static final String PATH = SAVE_PIC_PATH+ "/robet/image/";//保存的确切位置
    //public static final String PATH = Environment.getExternalStorageDirectory()+"/robet/image/";

    public static Message parseResponse(String response){

        Message message = new Message();
        
        try {
            Gson gson = new Gson();
            ResponseMessage responseMessage = gson.fromJson(response,ResponseMessage.class);
            if (responseMessage.getCode().equals("100000")){
                message.setCode(responseMessage.getCode());
                message.setText(responseMessage.getText());
            }else if(responseMessage.getCode().equals("200000")){
                message.setCode(responseMessage.getCode());
                if(responseMessage.getUrl().startsWith("http://m.image.so.com")){
                    message.setCode("400000");
                    message.setText(responseMessage.getText());
                    message.setPicture(getNetworkImage("http://www.tooopen.com/search/" + responseMessage.getUrl().replace("http://m.image.so.com/i?q=","") + ".aspx").get(0));
                    //ImageUtils.saveImage(ImageUtils.getBitmapByUrl(message.getPicture()),PATH,message.getUuid().toString());

                    ImageUtils.saveFile(ImageUtils.getBitmapByUrl(message.getPicture()),PATH,message.getUuid().toString());
                    message.setPicture(PATH + message.getUuid().toString());

                    message.setUrl(responseMessage.getUrl());
                }else {
                    message.setCode(responseMessage.getCode());
                    message.setText(responseMessage.getText());
                    message.setUrl(responseMessage.getUrl());
                }
            }else if(responseMessage.getCode().equals("302000")){
                ResponseMessageNews messageNews = gson.fromJson(response,ResponseMessageNews.class);
                message.setCode(messageNews.getCode());
                StringBuilder builder = new StringBuilder();
                builder.append(messageNews.getText()).append("\n");
                for(News news : messageNews.getList()){
                    builder.append(news.toString());
                }
                message.setText(builder.toString());
            }else if(responseMessage.getCode().equals("308000")){
                ResponseMessageCook messageCook = gson.fromJson(response,ResponseMessageCook.class);
                message.setCode(messageCook.getCode());
                StringBuilder builder = new StringBuilder();
                builder.append(messageCook.getText()).append("\n");
                for(Cookbook cook : messageCook.getList()){
                    builder.append(cook.toString());
                }
                message.setText(builder.toString());
            }

            /*
            if(message.getCode().equals("100000")){
                map.put("code","100000") ;
                map.put("text",message.getText());
                map.put("url",message.getUrl());
            }else if(message.getCode().equals("200000")){
                map.put("code","200000");
                map.put("text",message.getText());
                map.put("url",message.getUrl());
            }else if(message.getCode().equals("302000") || message.getCode().equals("308000")){
                map.put("code","302000") ;
                map.put("text",message.getText());
                map.put("url",message.getUrl());
            }else{
                content = "对不起，我找不到相关信息 ~~~";
            }
            */

        }catch (Exception e){
            message.setCode("500000");
            String sOut = "";
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement s : trace) {
                sOut += "\tat " + s + "\r\n";
            }
            message.setText("对不起，我找不到相关信息 ~~~" + sOut);
        }

        return message;
    }

    public static List<String> getNetworkImage(String networkUrl) throws IOException{
        //输入输出流
        List<String> list = new ArrayList<>();
        Document doument;
        Elements elements;
        try {
            //获取网站资源
            doument = (Document) Jsoup.connect(networkUrl).get();
            //获取网站资源图片
            elements = doument.select("img[src]");
            //循环读取
            for (Element e : elements) {//读取网站所有图片
                String str = e.attr("src");
                if(str.startsWith("http")) {
                    list.add(str);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
