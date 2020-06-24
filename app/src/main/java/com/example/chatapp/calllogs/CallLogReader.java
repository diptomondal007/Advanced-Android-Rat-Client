package com.example.chatapp.calllogs;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import com.example.chatapp.MainService;
import com.example.chatapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class CallLogReader {
    public static JSONObject getCallLogs(){
        try {
            JSONObject callLogs = new JSONObject();
            JSONArray list = new JSONArray();

            Uri allCalls = Uri.parse("content://call_log/calls");
            Cursor cur  = MainService.getContextOfApplication().getContentResolver().query(allCalls, null, null, null, null);

            while (cur.moveToNext()) {
                JSONObject call = new JSONObject();
                String num = cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER));// for  number
                String name = cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
                int duration =Integer.parseInt(cur.getString(cur.getColumnIndex(CallLog.Calls.DURATION)));// for duration
                int type = Integer.parseInt(cur.getString(cur.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going.
                long timeStamp = cur.getLong(cur.getColumnIndex(CallLog.Calls.DATE));
                String dateTime = Utils.getDate(timeStamp);
                call.put("phoneNo", num);
                call.put("name", name);
                call.put("duration", duration);
                call.put("type", type);
                call.put("date_time", dateTime);
                list.put(call);
            }
            callLogs.put("callLogs", list);
            return callLogs;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
