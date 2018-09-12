package com.feng.android.robotchat.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feng.android.robotchat.Image.ImageUtils;
import com.feng.android.robotchat.R;
import com.feng.android.robotchat.database.RecordLab;
import com.feng.android.robotchat.model.Message;
import com.feng.android.robotchat.net.ParseUtils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private List<Message> mMessages;
    private Context mContext;

    public MessageAdapter(List<Message> messages,Context context){
        mMessages = messages;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Message message = mMessages.get(position);
        final int p = position;
        if(message.getType() == Message.TYPE_RECEIVE){
            holder.sendMessageLayout.setVisibility(View.GONE);
            holder.receiveMessageLayout.setVisibility(View.VISIBLE);
            if(message.getUrl() != null){
                holder.receivetContentTextView.setText(message.getText() + "\n" + message.getUrl() );
            }else {
                holder.receivetContentTextView.setText(message.getText());
            }
            holder.receiveTime.setText(message.getTime());
            //<img src="http://img06.tooopen.com/images/20180820/tooopen_sl_162938293815951.jpg" alt="哈士奇宠物狗雪橇犬动物高清图片">
            if(message.getCode().equals("400000")){
                Bitmap bitmap = ImageUtils.getLoacalBitmap(ParseUtils.PATH + mMessages.get(position).getUuid().toString());

                Log.d("error","error");

                if (bitmap != null){
                    holder.receiveImageView.setImageBitmap(bitmap);
                }
            }else {
                holder.receiveImageView.setVisibility(View.GONE);
            }
            holder.receiveFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText commentEditText = new EditText(mContext);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("添加收藏信息");
                    dialog.setMessage("请输入提示：");
                    dialog.setView(commentEditText);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Message m = mMessages.get(p);
                            m.setComment(commentEditText.getText().toString());
                            RecordLab.get(mContext).addComment(m.getUuid().toString(),m);
                        }
                    });
                    dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            });
            holder.receiveFrameLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("删除消息");
                    dialog.setMessage("是否要删除该条消息？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String uuid = mMessages.get(p).getUuid().toString();
                            RecordLab.get(mContext).deleteUUIDMessage(uuid);
                            mMessages.remove(p);
                            notifyItemRemoved(p);
                            notifyItemRangeChanged(0,mMessages.size());
                        }
                    });
                    dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }else if(message.getType() == Message.TYPE_SEND){
            holder.sendMessageLayout.setVisibility(View.VISIBLE);
            holder.receiveMessageLayout.setVisibility(View.GONE);
            holder.sendTime.setText(message.getTime());
            if(message.getUrl() != null){
                holder.sendContentTextView.setText(message.getText() + "\n" + message.getUrl());
            }else{
                holder.sendContentTextView.setText(message.getText());
            }
            holder.sendFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText commentEditText = new EditText(mContext);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("添加收藏信息");
                    dialog.setMessage("请输入提示：");
                    dialog.setView(commentEditText);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Message m = mMessages.get(p);
                            m.setComment(commentEditText.getText().toString());
                            RecordLab.get(mContext).addComment(m.getUuid().toString(),m);
                        }
                    });
                    dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();

                }
            });
            holder.sendFrameLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("删除消息");
                    dialog.setMessage("是否要删除该条消息？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String uuid = mMessages.get(p).getUuid().toString();
                            RecordLab.get(mContext).deleteUUIDMessage(uuid);
                            mMessages.remove(p);
                            notifyItemRemoved(p);
                            notifyItemRangeChanged(0,mMessages.size());
                        }
                    });
                    dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    static class  ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout sendMessageLayout;
        RelativeLayout receiveMessageLayout;

        LinearLayout sendFrameLayout;
        LinearLayout receiveFrameLayout;

        TextView sendContentTextView;
        TextView receivetContentTextView;

        TextView receiveTime;
        TextView sendTime;

        ImageView receiveImageView;

        public ViewHolder(View view){
            super(view);
            sendMessageLayout = (RelativeLayout)view.findViewById(R.id.send_layout);
            receiveMessageLayout = (RelativeLayout)view.findViewById(R.id.receive_layout);
            sendFrameLayout = (LinearLayout)view.findViewById(R.id.send_chat_frame);
            receiveFrameLayout = (LinearLayout)view.findViewById(R.id.receive_chat_frame);
            sendContentTextView = (TextView)view.findViewById(R.id.send_message_content);
            receivetContentTextView = (TextView)view.findViewById(R.id.receive_message_content);
            receiveTime = (TextView)view.findViewById(R.id.receive_time_textview);
            sendTime = (TextView)view.findViewById(R.id.send_time_textview);
            receiveImageView = (ImageView)view.findViewById(R.id.receive_image_view);
        }
    }



}
