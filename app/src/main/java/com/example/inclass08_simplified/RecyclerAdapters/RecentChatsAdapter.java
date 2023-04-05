package com.example.inclass08_simplified.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inclass08_simplified.Interfaces.IconnectToActivity;
import com.example.inclass08_simplified.Models.ChatRecord;
import com.example.inclass08_simplified.R;

import java.util.ArrayList;

public class RecentChatsAdapter extends RecyclerView.Adapter<RecentChatsAdapter.ViewHolder>{
    private ArrayList<ChatRecord> recentChats;
    private IconnectToActivity mListener;

    public RecentChatsAdapter(ArrayList<ChatRecord> recentChats, Context context) {
        this.recentChats = recentChats;
        if(context instanceof IconnectToActivity){
            this.mListener = (IconnectToActivity) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IeditButtonAction");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout rootRecentChats;
        private final TextView textView_RecentChatName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootRecentChats = itemView.findViewById(R.id.rootRecentChats);
            textView_RecentChatName = itemView.findViewById(R.id.textView_RecentChatName);
        }

        public ConstraintLayout getRootRecentChats() {
            return rootRecentChats;
        }

        public TextView getTextView_RecentChatName() {
            return textView_RecentChatName;
        }
    }

    @NonNull
    @Override
    public RecentChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRecyclerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recent_chats, parent, false);
        return new ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatsAdapter.ViewHolder holder, int position) {
        ChatRecord record = recentChats.get(position);

        holder.getTextView_RecentChatName().setText(record.getChat_name());
        holder.getRootRecentChats().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onChatSelectedFromRecentChats(record);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.recentChats.size();
    }

}
