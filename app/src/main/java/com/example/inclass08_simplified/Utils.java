package com.example.inclass08_simplified;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Utils {
    public static String generateUniqueID(ArrayList<String> chatEmails) {
//        Using Java UUID (Unique User ID) generator utility to create the IDs from the list of user emails......
        Collections.sort(chatEmails);
        String uuid = UUID.nameUUIDFromBytes(chatEmails.toString().getBytes()).toString().substring(0,16);
        return uuid;
    }
}
