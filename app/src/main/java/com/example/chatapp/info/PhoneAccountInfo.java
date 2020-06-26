package com.example.chatapp.info;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import static com.example.chatapp.ConnectionManager.context;

public class PhoneAccountInfo {
    private static final int REQUEST_CODE_EMAIL = 1;
    public static JSONObject getPhoneInfo() {
        try {
        JSONObject emailList = new JSONObject();
        JSONArray list = new JSONArray();
        String possibleEmail = null;
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            JSONObject email = new JSONObject();
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail= account.name;
                try {
                    email.put("email", possibleEmail);
                }catch (Exception e){
                    e.printStackTrace();
                }
                list.put(email);
            }
        }
        emailList.put("emails", list);
        Log.e("email list", emailList.toString());
        return emailList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
