package com.example.inclass08_simplified.Fragments;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inclass08_simplified.Interfaces.IconnectToActivity;
import com.example.inclass08_simplified.Models.User;
import com.example.inclass08_simplified.R;
import com.example.inclass08_simplified.Tags;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private TextView textViewUserName;
    private ImageButton buttonLogout;
    private FloatingActionButton button_AddMessage;
    private IconnectToActivity mListener;

    private ArrayList<User> users;

    public FragmentMain() {
        // Required empty public constructor
    }

    public static FragmentMain newInstance() {
        FragmentMain fragment = new FragmentMain();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        users = new ArrayList<>();
//      track the users...
        getUsersRealTime();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IconnectToActivity){
            mListener = (IconnectToActivity) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IaddButtonAction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        textViewUserName = rootView.findViewById(R.id.textView_Username);
        textViewUserName.setText(mUser.getDisplayName());
        buttonLogout = rootView.findViewById(R.id.imageButtonLogout);
        buttonLogout.setOnClickListener(this::onLogoutPressed);

        button_AddMessage = rootView.findViewById(R.id.floatingButton_AddMessage);
        button_AddMessage.setOnClickListener(this::onNewMessageButtonPressed);

        return rootView;
    }

    private void getUsersRealTime() {
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot doc: value.getDocuments()) {
                            users.add(doc.toObject(User.class));
                        }
                    }
                });
    }

    private void onNewMessageButtonPressed(View view) {
        mListener.newMessageButtonPressed(users);
    }

    private void onLogoutPressed(View view) {
        mListener.logoutPressed();
    }
}