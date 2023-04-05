package com.example.inclass08_simplified;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.inclass08_simplified.Fragments.FragmentChat;
import com.example.inclass08_simplified.Fragments.FragmentLogin;
import com.example.inclass08_simplified.Fragments.FragmentMain;
import com.example.inclass08_simplified.Fragments.FragmentNewChatSelectFriend;
import com.example.inclass08_simplified.Fragments.FragmentRegister;
import com.example.inclass08_simplified.Interfaces.IconnectToActivity;
import com.example.inclass08_simplified.Models.Chat;
import com.example.inclass08_simplified.Models.ChatRecord;
import com.example.inclass08_simplified.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IconnectToActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private User currentLocalUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("In Class 08/09");
//        Initializing Firebase Authentication...
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        populateScreen();
    }

    private void populateScreen() {
        //      Check for Authenticated users ....
        if(currentUser != null){
//            The user is authenticated, fetching the details of the current user from Firebase...
            db.collection("users")
                    .document(currentUser.getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    currentLocalUser = task.getResult()
                                            .toObject(User.class);
//                                    Log.d(Tags.TAG, "Current user: "+currentLocalUser.toString());
                                    //Populating The Main Fragment....
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.containerMain, FragmentMain.newInstance(),"mainFragment")
                                            .commit();

                                }else{
                                    mAuth.signOut();
                                    currentUser = null;
                                    populateScreen();
                                }
                            }
                        });
        }else{
//            The user is not logged in, load the login Fragment....
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerMain, FragmentLogin.newInstance(),"loginFragment")
                    .commit();
        }
    }

    @Override
    public void populateMainFragment(FirebaseUser mUser) {
        this.currentUser = mUser;
        populateScreen();
    }

    @Override
    public void populateRegisterFragment() {
//            The user needs to create an account, load the register Fragment....
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, FragmentRegister.newInstance(),"registerFragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void registerDone(FirebaseUser firebaseUser, User user) {
        this.currentUser = firebaseUser;
//        Updating the Firestore structure....
        updateFirestoreWithUserDetails(user);
    }

    private void updateFirestoreWithUserDetails(User user) {
        db.collection("users")
            .document(user.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                      On success populate home screen...
                        Log.d(Tags.TAG, "onSuccess: updated data");
                        populateScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(Tags.TAG, "onFailure: "+e.getMessage());
                    }
                });
    }
    @Override
    public void logoutPressed() {
        mAuth.signOut();
        currentUser = null;
        populateScreen();
    }

    @Override
    public void newMessageButtonPressed(ArrayList<User> users) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, new FragmentNewChatSelectFriend(users, currentLocalUser),"selectFriendFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFriendSelectedFromSelectFriend(User selectedFriend) {
//        Using set for future scalability for chat among more than two persons...
        Set<String> emailsGroup = new HashSet<>();
        final ChatRecord[] theRecord = new ChatRecord[1];
        emailsGroup.add(currentLocalUser.getEmail());
        emailsGroup.add(selectedFriend.getEmail());
//        Fetch the collection of chat records from users tree...
        db.collection("users")
                .document(currentLocalUser.getEmail())
                .collection("chats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<ChatRecord> chatRecords = new ArrayList<>();

                        if(task.isSuccessful()){
                            for (DocumentSnapshot docSnap: task.getResult()) {
                                ChatRecord record = docSnap.toObject(ChatRecord.class);
                                if (record.getUser_emails().equals(emailsGroup)){
                                    theRecord[0] = record;
                                }

                            }
                            Log.d(Tags.TAG, "TheRecord: "+theRecord[0]);
                        }else{
//                            Create a new record in chats tree......
                            Log.d(Tags.TAG, "Not found in chats ");
                            Chat chat = new Chat(emailsGroup);
                            db.collection("chats")
                                    .add(chat)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
//                                            Create the chat record in users tree....
                                            String docRef = documentReference.getId();
                                            ChatRecord chatRecord = new ChatRecord(docRef, emailsGroup);
                                            db.collection("users")
                                                    .document(currentLocalUser.getEmail())
                                                    .collection("chats")
                                                    .add(chatRecord)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            populateChatFragment(documentReference);
                                                        }
                                                    });
                                        }
                                    });
                        }

                    }
                });

    }

    private void populateChatFragment(DocumentReference documentReference) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, new FragmentChat(currentLocalUser,documentReference),"newChatFragment")
                .addToBackStack(null)
                .commit();
    }


}