package com.example.chatapp.process_restart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.chatapp.MainService;

public class BroadcastReceive extends BroadcastReceiver {
    public BroadcastReceive() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        intent = new Intent( context, MainService.class );
        context.startService(intent);

    }
}
