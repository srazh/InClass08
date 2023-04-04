package com.example.inclass08_simplified.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inclass08_simplified.Models.User;
import com.example.inclass08_simplified.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNewChatSelectFriend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNewChatSelectFriend extends Fragment {

    private static final String ARG_USERS = "users";

    // TODO: Rename and change types of parameters
    private ArrayList<User> users;
    private RecyclerView recyclerViewFriends;
    private RecyclerView.LayoutManager recyclerViewFriendsLayoutManager;

    public FragmentNewChatSelectFriend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param users       User ArrayList.
     * @param currentUser
     * @return A new instance of fragment FragmentNewChat.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNewChatSelectFriend newInstance(ArrayList<User> users, FirebaseUser currentUser) {
        FragmentNewChatSelectFriend fragment = new FragmentNewChatSelectFriend();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERS,users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            users = (ArrayList<User>) getArguments().getSerializable(ARG_USERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_chat, container, false);

        return rootView;
    }
}