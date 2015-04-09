package com.gongyinan.mdmusicplayer.Activity;

import android.content.Context;

import com.gongyinan.mdmusicplayer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongyinan on 15/4/5.
 */
public class LeftMenu {
    private static String[] titles = new String[]{"音乐列表","设置","退出"};

    public static String[] getTitles(){
        return titles;
    }

    public static List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < titles.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title",titles[i]);
            list.add(map);
        }

        return list;
    }

    public static String[] getFroms(){
        return new String[]{"title"};
    }

    public static int[] getTos(){
        return new int[]{R.id.title};
    }
}
