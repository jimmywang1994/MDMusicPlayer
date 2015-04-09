package com.gongyinan.mdmusicplayer.Utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

/**
 * Created by gongyinan on 15/4/6.
 */
public class AnimationUtil {

    private static TextView getTitleTextView(Toolbar toolbar){
        TextView textView = null;
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if (toolbar.getChildAt(i).getClass() == TextView.class) {
                textView = (TextView)toolbar.getChildAt(i);
            }
        }
        return textView;
    }

    private static TranslateAnimation getTranslateX100To0Animation(View view, long duration){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,1,Animation.RELATIVE_TO_SELF,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);
        animation.setDuration(duration);
        return  animation;
    }

    private static TranslateAnimation getTranslateX0To100Animation(View view, long duration){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);
        animation.setDuration(duration);
        return  animation;
    }


    public static void StartEditTextSearchAnimation(final EditText mEditTextSearch){
        Toolbar toolbar = (Toolbar)mEditTextSearch.getParent();
        final TextView textView = getTitleTextView(toolbar);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(300);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.GONE);
                mEditTextSearch.setVisibility(View.VISIBLE);
                TranslateAnimation translateAnimation = getTranslateX100To0Animation(mEditTextSearch, 300);
                mEditTextSearch.startAnimation(translateAnimation);
                mEditTextSearch.requestFocus();
                //TODO显示软键盘
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(alphaAnimation);
    }


    public static void EndEditTextSearchAnimation(final EditText mEditTextSearch){
        //TODO关闭软键盘
        Toolbar toolbar = (Toolbar)mEditTextSearch.getParent();
        final TextView textView = getTitleTextView(toolbar);
        Animation animation =  getTranslateX0To100Animation(mEditTextSearch, 300);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mEditTextSearch.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
                alphaAnimation.setDuration(300);
                textView.startAnimation(alphaAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mEditTextSearch.startAnimation(animation);
    }




}