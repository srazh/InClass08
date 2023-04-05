package com.example.inclass08_simplified.Interfaces;

import com.example.inclass08_simplified.Models.ChatRecord;
import com.example.inclass08_simplified.Models.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public interface IconnectToActivity {
    void populateMainFragment(FirebaseUser mUser);
    void populateRegisterFragment();
    void registerDone(FirebaseUser mUser, User user);
    void logoutPressed();
    void newMessageButtonPressedFromMainFragment(ArrayList<User> users);

    void onFriendSelectedFromSelectFriendFragment(User user);

    void onChatSelectedFromRecentChats(ChatRecord record);
}
