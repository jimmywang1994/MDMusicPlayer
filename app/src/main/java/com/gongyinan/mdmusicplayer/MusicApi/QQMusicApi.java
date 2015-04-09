package com.gongyinan.mdmusicplayer.MusicApi;

import android.util.Log;

import com.gongyinan.mdmusicplayer.Model.Music;
import com.gongyinan.mdmusicplayer.Model.SearchResult;
import com.gongyinan.mdmusicplayer.Utils.Base64Util;
import com.gongyinan.mdmusicplayer.Utils.JsonUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gongyinan on 15/4/5.
 */
public class QQMusicApi {
    private static String key=null;
    private static Date keyDate=new Date();
    private  static HttpResponse getResponse(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        Log.e("url", url);
        request.setHeader("Referer", "http://y.qq.com/");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        return httpClient.execute(request);
    }

    private static JSONObject getJsonpResponse(String url) throws IOException, JSONException {
        return new JSONObject(JsonUtil.TrimCallBackString(getWebString(url)));
    }

    private  static  String getWebString (String url) throws IOException {
        String response = EntityUtils.toString(getResponse(url).getEntity());
        Log.e("response",response);
        return response;
    }

    private static byte[] getWebBinaryData(String url) throws IOException{
        HttpResponse response = getResponse(url);
        return EntityUtils.toByteArray(response.getEntity());
    }

    private static  String getKey(String QQ) throws IOException, JSONException {
        int determine = keyDate.compareTo(new Date());
        Log.e("determine",String.valueOf(determine));
        if (key==null){
            String url = "http://base.music.qq.com/fcgi-bin/fcg_musicexpress.fcg?json=3&guid=3255913222&g_tk=938407465&loginUin="+ QQ +"&hostUin=0&format=jsonp&inCharset=GB2312&outCharset=GB2312&notice=0&platform=yqq&jsonpCallback=jsonCallback&needNewCode=0";
            JSONObject jsonObject = getJsonpResponse(url);
            key = jsonObject.getString("key");
            keyDate = new Date();
        }
        return key;
    }

    public static SearchResult search(String keyWord,int num,int page,String QQ) throws JSONException, IOException {
        String url  = "http://soso.music.qq.com/fcgi-bin/music_json.fcg?mid=1&catZhida=1&lossless=0&json=1&w="+ URLEncoder.encode(keyWord, "utf-8")+"&num="+String.valueOf(num)+"&t=0&p="+String.valueOf(page)+"&utf8=1&searchid=208754149574784872&remoteplace=sizer.yqqlist.song&g_tk=668634677&loginUin="+ QQ+ "&hostUin=0&format=jsonp&inCharset=GB2312&outCharset=gb2312&notice=0&platform=yqq&jsonpCallback=searchJsonCallback&needNewCode=0";
        JSONObject jsonObject = getJsonpResponse(url);
        SearchResult searchResult = new SearchResult();
        searchResult.List = new ArrayList<>();
        searchResult.KeyWord = jsonObject.getString("keyword");
        searchResult.CurrentCount = jsonObject.getInt("curnum");
        searchResult.TotalCount = jsonObject.getInt("totalnum");
        searchResult.CurrentPage = jsonObject.getInt("curpage");
        for (int i = 0; i < searchResult.CurrentCount; i++) {
            Music music = new Music();
            JSONObject obj = jsonObject.getJSONArray("list").getJSONObject(i);
            String f =obj.getString("f");
            if (f.split("\\|").length<22){
                Log.e("f",f);
                music.IsQQMusicResource = false;
                //TODO 错误结果
                music.MusicUrl = f.split("\\@@")[10];
            }
            else {
                music.ID = f.split("\\|")[20];
                music.AlbumPictureID =  f.split("\\|")[22];
            }
            music.Singer = obj.getString("fsinger");
            music.Name = obj.getString("fsong");
            music.AlbumName = obj.getString("salbumName");
            searchResult.List.add(music);
        }
        return searchResult;
    }

    public static  byte[] getAlbumPicture(Music music) throws IOException {
        String id = music.AlbumPictureID;
        String url =  "http://imgcache.qq.com/music/photo/mid_album_150/"+ id.charAt(id.length()-2)+"/"+id.charAt(id.length()-1)+"/" +id +".jpg";
        return  getWebBinaryData(url);
    }

    public static  String getMusicFileUrl(Music music) throws IOException, JSONException {
        if (music.IsQQMusicResource){
            String id = music.ID;
            music.MusicUrl ="http://cc.stream.qqmusic.qq.com/C200"+id+".m4a?vkey="+getKey("200804632")+"&guid=3255913222&fromtag=0";
        }
        return music.MusicUrl;
    }

    public static byte[] getMusicFile(Music music) throws IOException, JSONException {
        return getWebBinaryData(getMusicFileUrl(music));
    }

    public static String getLyc(String id) throws IOException, JSONException {
        String url = "http://portalcgi.music.qq.com/fcgi-bin/fcg_query_lyric.fcg?pcachetime=1427962245382&songmid="+ id+"&g_tk=938407465&loginUin=0&hostUin=0&format=jsonp&inCharset=GB2312&outCharset=utf-8&notice=0&platform=yqq&jsonpCallback=MusicJsonCallback&needNewCode=0";
        JSONObject jsonObject = getJsonpResponse(url);
        String base64 = jsonObject.getString("lyric");
        byte[] buffer = Base64Util.decode(base64);
        return new String(buffer);
    }
}