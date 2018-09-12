package com.feng.android.robotchat.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feng.android.robotchat.R;
import com.feng.android.robotchat.model.Message;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private List<Message> mMessages;
    private Context mContext;

    public CollectionAdapter(List<Message> messages,Context context){
        mMessages = messages;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Message message = mMessages.get(position);
        holder.collectionTextview.setText(message.getTime() + "   " + message.getComment());
        holder.collectionTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SingleCollectionActivity.newIntent(mContext,message.getUuid().toString());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView collectionTextview;

        public ViewHolder(View view) {
            super(view);
            collectionTextview = (TextView)view.findViewById(R.id.record_item_textview);
        }
    }

}
