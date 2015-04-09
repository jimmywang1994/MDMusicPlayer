package com.gongyinan.mdmusicplayer.Utils;

/**
 * Created by gongyinan on 15/4/5.
 */
public class JsonUtil {
    public static String TrimCallBackString(String jsonp){
        return jsonp.substring(jsonp.indexOf("(")+1,jsonp.length()-1);
    }
}

