package com.example.inclass08_simplified.RecyclerAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inclass08_simplified.Models.Message;
import com.example.inclass08_simplified.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<Message> messages;

    public MessageAdapter(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMessageText;
        private final ImageView imageViewMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewMessageText = itemView.findViewById(R.id.textView_MessageText);
            this.imageViewMessage = itemView.findViewById(R.id.imageViewMessage);
        }

        public TextView getTextViewMessageText() {
            return textViewMessageText;
        }

        public ImageView getImageViewMessage() {
            return imageViewMessage;
        }
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRecyclerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_messages, parent, false);
        return new ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = this.getMessages().get(position);
        holder.getTextViewMessageText().setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return this.getMessages().size();
    }
}
