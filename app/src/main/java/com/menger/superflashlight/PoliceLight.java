package com.menger.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.menger.superflashlight.widget.HideTextView;

/**
 * Created by lfcha on 2016/2/21.
 */
public class PoliceLight extends ColorLight{
    protected boolean mPoliceState;
    protected HideTextView mHideTextViewPoliceLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextViewPoliceLight = (HideTextView)findViewById(R.id.textview_hide_police_light);

    }

    class PoliceThread extends Thread{
        public void run(){
            mPoliceState = true;
            while (mPoliceState){
                mHandler.sendEmptyMessage(Color.BLUE);
                sleepExt(100);
                mHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(100);
                mHandler.sendEmptyMessage(Color.RED);
                sleepExt(100);
                mHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(100);
            }
        }
    }

    private Handler mHandler = new Handler(){
      @Override
    public void handleMessage(Message message){
          int color = message.what;
          mUIPoliceLight.setBackgroundColor(color);
      }
    };
}
