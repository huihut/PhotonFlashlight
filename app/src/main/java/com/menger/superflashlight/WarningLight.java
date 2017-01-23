package com.menger.superflashlight;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

/**
 * Created by lfcha on 2016/2/21.
 */
public class WarningLight extends Flashlight {
    protected boolean mWarningLightFlicker;  //  true: 闪烁   false：停止闪烁
    protected boolean mWarningLightState;    //  true:  on-on   false: on-on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWarningLightFlicker=true;

    }
    class WarningLightThread extends Thread {
        public void run(){
            mWarningLightFlicker=true;
            while(mWarningLightFlicker){
                try{
                    Thread.sleep(200);
                    mWarningHandler.sendEmptyMessage(0);
                }
                catch (Exception e){

                }
            }
        }
    }
    private Handler mWarningHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mWarningLightState) {
                mImageViewWarningLight1.setImageResource(R.drawable.warning_light_on);
                mImageViewWarningLight2.setImageResource(R.drawable.warning_light_off);
                mWarningLightState = false;
            } else {
                mImageViewWarningLight1.setImageResource(R.drawable.warning_light_off);
                mImageViewWarningLight2.setImageResource(R.drawable.warning_light_on);
                mWarningLightState = true;
            }
        }
    };

}
