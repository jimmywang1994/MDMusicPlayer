package com.gongyinan.mdmusicplayer.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by gongyinan on 15/4/5.
 */
public class MediaUtil {
    public static Bitmap toBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
