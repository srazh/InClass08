package com.example.inclass08_simplified.Fragments;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


import com.example.inclass08_simplified.Interfaces.IconnectToActivity;
import com.example.inclass08_simplified.Models.ChatRecord;
import com.example.inclass08_simplified.Models.User;
import com.example.inclass08_simplified.R;
import com.example.inclass08_simplified.RecyclerAdapters.RecentChatsAdapter;
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

public class FragmentMain extends Fragment{

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private User currentLocalUser;
    private TextView textViewUserName;
    private ImageButton buttonLogout;
    private FloatingActionButton button_AddMessage;
    private IconnectToActivity mListener;

    private ArrayList<User> users;
    private ArrayList<ChatRecord> recentChats;

    private RecyclerView recyclerViewRecentChats;
    private RecyclerView.LayoutManager recyclerViewRecentChatsLayoutManager;
    private RecyclerView.Adapter recyclerViewRecentChatsAdapter;

    private ImageView profilePic;
    public FragmentMain() {
        // Required empty public constructor
    }

    public FragmentMain(User currentLocalUser) {
        this.currentLocalUser = currentLocalUser;
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
        recentChats = new ArrayList<>();
//        track the recent chat uypdates...
        getRecentChats();
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

        recyclerViewRecentChats = rootView.findViewById(R.id.recyclerView_recentChats);
        recyclerViewRecentChatsLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewRecentChatsAdapter = new RecentChatsAdapter(recentChats,getContext());
        recyclerViewRecentChats.setLayoutManager(recyclerViewRecentChatsLayoutManager);
        recyclerViewRecentChats.setAdapter(recyclerViewRecentChatsAdapter);


        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Access the ImageView inside the layout
        ImageView profilePic = view.findViewById(R.id.profilePicture);

        // Set an OnClickListener on the ImageView
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                Toast.makeText(getActivity(), "Image clicked", Toast.LENGTH_SHORT).show();
                FragmentCameraController FragmentCameraController = new FragmentCameraController();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cameraContainer, FragmentCameraController);
                fragmentTransaction.addToBackStack(null); // This allows the user to go back to the previous fragment using the back button
                fragmentTransaction.commit();
            }
        });
    }



    private void getUsersRealTime() {
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        users.clear();
                        for (DocumentSnapshot doc: value.getDocuments()) {
                            users.add(doc.toObject(User.class));
                        }
                    }
                });
    }
    private void getRecentChats() {
        db.collection("users")
                .document(currentLocalUser.getEmail())
                .collection("chats")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e(Tags.TAG, "onEvent: "+error.getMessage());
                        }else{
                            recentChats.clear();
                            for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                                recentChats.add(documentSnapshot.toObject(ChatRecord.class));
                            }
                            recyclerViewRecentChatsAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }




    private void onNewMessageButtonPressed(View view) {
        mListener.newMessageButtonPressedFromMainFragment(users);
    }

    private void onLogoutPressed(View view) {
        mListener.logoutPressed();
    }



}