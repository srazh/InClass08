package com.example.inclass08_simplified.Interfaces;

import com.example.inclass08_simplified.Models.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public interface IconnectToActivity {
    void populateMainFragment(FirebaseUser mUser);
    void populateRegisterFragment();
    void registerDone(FirebaseUser mUser, User user);
    void logoutPressed();
    void newMessageButtonPressed(ArrayList<User> users);

    void onFriendSelectedFromSelectFriend(User user);
}
