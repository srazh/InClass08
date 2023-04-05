package com.example.inclass08_simplified;

import com.example.inclass08_simplified.Models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Utils {
    public static String generateUniqueID(ArrayList<String> chatEmails) {
//        Using Java UUID (Unique User ID) generator utility to create the IDs from the list of user emails......
        Collections.sort(chatEmails);
        String uuid = UUID.nameUUIDFromBytes(
                chatEmails.toString().getBytes())
                .toString().substring(0,16);
        return uuid;
    }
    public static String generateChatName(ArrayList<User> users){
        String name = "";
        for(int i=0; i< users.size(); i++){
            if(i==users.size()-1){
                name+= users.get(i).getFirstname();
            }else{
                name+= users.get(i).getFirstname()+", ";
            }
        }
        return name;
    }
}
