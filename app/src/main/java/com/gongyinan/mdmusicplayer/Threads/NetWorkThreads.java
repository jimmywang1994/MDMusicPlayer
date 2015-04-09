package com.gongyinan.mdmusicplayer.Threads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gongyinan.mdmusicplayer.Model.Music;
import com.gongyinan.mdmusicplayer.Model.SearchResult;
import com.gongyinan.mdmusicplayer.MusicApi.QQMusicApi;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by gongyinan on 15/4/5.
 */
public class NetWorkThreads {
    public static Thread searchThread(final String keyWord, final String QQ, final int pageSize, final int pageIndex, final Handler handler){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SearchResult result = QQMusicApi.search(keyWord, pageSize, pageIndex, QQ);
                    Bundle data = new Bundle();
                    data.putSerializable("result",result);
                    Message message = new Message();
                    message.setData(data);
                    handler.sendMessage(message);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    //TODO 错误处理
                }
            }
        });
    }

    public static Thread downloadMusicThread(final Music music, final Handler handler){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] result = QQMusicApi.getMusicFile(music);
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putByteArray("result", result);
                    data.putSerializable("music",music);
                    message.setData(data);
                    handler.sendMessage(message);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    //TODO 错误处理
                }
            }
        });
    }
}