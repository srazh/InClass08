package com.example.inclass08_simplified.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inclass08_simplified.Models.User;
import com.example.inclass08_simplified.R;
import com.example.inclass08_simplified.Tags;
import com.google.firebase.firestore.DocumentReference;

public class FragmentChat extends Fragment {

    private User currentUser;
    private DocumentReference documentReference;

    public FragmentChat(User currentLocalUser, DocumentReference documentReference){
        this.currentUser = currentLocalUser;
        this.documentReference = documentReference;
    };

    public FragmentChat() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        Log.d(Tags.TAG, "onCreateView: "+documentReference.getId());
        return rootView;
    }
}