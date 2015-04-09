package com.gongyinan.mdmusicplayer;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.gongyinan.mdmusicplayer.Activity.LeftMenu;
import com.gongyinan.mdmusicplayer.Model.Music;
import com.gongyinan.mdmusicplayer.Model.SearchResult;
import com.gongyinan.mdmusicplayer.MusicPlayer.Player;
import com.gongyinan.mdmusicplayer.Threads.NetWorkThreads;
import com.gongyinan.mdmusicplayer.Utils.AnimationUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/7.
 */
public class SearchHandler {
    private static SearchResult getSearchResult(Message msg){
        return (SearchResult)msg.getData().getSerializable("result");
    }

    public static void addEditTextSearchListenerAndHandler(final EditText mEditTextSearch, final ViewGroup container){
        final  Handler downloadHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                byte[] data = msg.getData().getByteArray("result");
                Music music = (Music)msg.getData().getSerializable("music");
                try {
                    Player.setMusicData(data,music)
                            .start();
                } catch (IOException e) {
                    e.printStackTrace();
                    //TODO 错误
                }
            }
        };

        final Handler searchHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                final SearchResult searchResult = getSearchResult(msg);
                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < searchResult.List.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("title", searchResult.List.get(i).Name);
                    data.add(map);
                }
                ListView listView = (ListView)container.findViewById(R.id.lv_search_result);
                SimpleAdapter adapter = new SimpleAdapter(mEditTextSearch.getContext(),data,R.layout.search_result_layout,LeftMenu.getFroms(),LeftMenu.getTos());
                listView.setAdapter(adapter);

                Animation animation = AnimationUtils.loadAnimation(mEditTextSearch.getContext(),R.anim.te_search_anim);
                animation.setDuration(1000);
                LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation,1f);
                listView.setLayoutAnimation(layoutAnimationController);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Music music = searchResult.List.get(position);
                        NetWorkThreads.downloadMusicThread(music,downloadHandler).start();
                    }
                });
            }
        };



        TextWatcher textWatcher = new TextWatcher(){
            Thread thread;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() != 0){
                    if (thread!=null){
                        thread.interrupt();
                    }
                    thread = NetWorkThreads.searchThread(s.toString().trim(),"200804632",30,1,searchHandler);
                    thread.start();
                }
            }
        };
        mEditTextSearch.addTextChangedListener(textWatcher);
    }
}
