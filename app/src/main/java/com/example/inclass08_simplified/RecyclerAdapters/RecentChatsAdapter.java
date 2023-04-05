package com.example.inclass08_simplified.RecyclerAdapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inclass08_simplified.Models.ChatRecord;
import com.example.inclass08_simplified.R;

import java.util.ArrayList;

public class RecentChatsAdapter extends RecyclerView.Adapter<RecentChatsAdapter.ViewHolder>{
    private ArrayList<ChatRecord> recentChats;

    public RecentChatsAdapter(ArrayList<ChatRecord> recentChats) {
        this.recentChats = recentChats;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
