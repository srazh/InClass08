package com.example.inclass08_simplified.RecyclerAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inclass08_simplified.Models.Message;
import com.example.inclass08_simplified.Models.User;
import com.example.inclass08_simplified.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<Message> messages;
    private User currentLocalUser;

    public MessageAdapter(ArrayList<Message> messages, User currentLocalUser) {
        this.messages = messages;
        this.currentLocalUser = currentLocalUser;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewChatUserName;
        private final ImageView imageViewChatUser;
        private final TextView textViewMessageText;
        private final ImageView imageViewMessage;

        private final ConstraintLayout chatHolderRoot;
        private final CardView chatHolderCardView;
        private final ConstraintLayout chatElementsHolderLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewChatUserName = itemView.findViewById(R.id.textView_ChatUserName);
            this.imageViewChatUser = itemView.findViewById(R.id.imageViewChatUser);
            this.textViewMessageText = itemView.findViewById(R.id.textView_MessageText);
            this.imageViewMessage = itemView.findViewById(R.id.imageViewMessage);

            this.chatHolderRoot = itemView.findViewById(R.id.chatHolderLayout);
            this.chatHolderCardView = itemView.findViewById(R.id.chatHolderCardView);
            this.chatElementsHolderLayout = itemView.findViewById(R.id.chatElementsHolder);
        }

        public ConstraintLayout getChatHolderRoot() {
            return chatHolderRoot;
        }

        public CardView getChatHolderCardView() {
            return chatHolderCardView;
        }

        public ConstraintLayout getChatElementsHolderLayout() {
            return chatElementsHolderLayout;
        }

        public TextView getTextViewChatUserName() {
            return textViewChatUserName;
        }

        public ImageView getImageViewChatUser() {
            return imageViewChatUser;
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
        View itemRecyclerView;
        switch (viewType){
            case 1:
                itemRecyclerView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_messages_sender, parent, false);
                break;
            default:
                itemRecyclerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_messages, parent, false);
                break;
        }

        return new ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = this.getMessages().get(position);
        User sender = message.getSender();
        holder.getTextViewChatUserName().setText(message.getSender().getFirstname());
        holder.getTextViewMessageText().setText(message.getText());

        String userImageURI = sender.getImageuri();
        if(userImageURI!=null){
//            TODO: implement images later....
        }

        String messageImageURI = message.getImageuri();
        if (messageImageURI == null){
            holder.getImageViewMessage().setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return this.getMessages().size();
    }

    @Override
    public int getItemViewType(int position) {
        //        Set the viewType depending on the sender or Reciever.......
        if(this.messages.get(position).getSender().getEmail().equals(currentLocalUser.getEmail())){
//            View type is 1 if this user is the sender...
            return 1;

        }else{
            return 0;
        }
    }
}
