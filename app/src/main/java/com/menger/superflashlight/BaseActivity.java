package com.menger.superflashlight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mobads.BaiduManager;

/**
 * Created by lfcha on 2016/2/20.
 */
public class BaseActivity extends Activity {
    protected enum UIType {
        UI_TYPE_MAIN,UI_TYPE_FLASHLIGHT,UI_TYPE_WARNINGLIGHT,UI_TYPE_MORSE,UI_TYPE_BLUB,UI_TYPE_COLOR,UI_TYPE_POLICE,UI_TYPE_About;
    }

    protected ImageView mImageViewFlashlight;
    protected ImageView mImageViewFlashlightController;
    protected ImageView mImageViewWarningLight1;
    protected ImageView mImageViewWarningLight2;
    protected EditText mEditTwxtMorseCode;
    protected ImageView mImageViewBulb;
    protected Button mButtonAboutExit;

    protected Camera mCamera;
    protected Parameters mParameters;

    protected FrameLayout mUIFlashlight;
    protected LinearLayout mUIMain;
    protected LinearLayout mUIWarningLight;
    protected LinearLayout mUIMorse;
    protected FrameLayout mUIBulb;
    protected FrameLayout mUIColorLight;
    protected FrameLayout mUIPoliceLight;
    protected LinearLayout mAbout;

    protected UIType mCurrentUIType =UIType.UI_TYPE_FLASHLIGHT;
    protected UIType mLastUIType =UIType.UI_TYPE_FLASHLIGHT;

    protected int mDefaultScreenBrightness;

    protected  int mFinishCount = 0;

    protected SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 广告展现前先调用sdk初始化方法，可以有效缩短广告第一次展现所需时间
        BaiduManager.init(this);

        mUIFlashlight=(FrameLayout)findViewById(R.id.framelayout_flashlight);
        mUIMain=(LinearLayout)findViewById(R.id.linearlayout_main);
        mUIWarningLight=(LinearLayout)findViewById(R.id.linearlayout_warning_light);
        mUIMorse=(LinearLayout)findViewById(R.id.linearlayout_morse);
        mUIBulb = (FrameLayout)findViewById(R.id .framelayout_bulb);
        mUIColorLight = (FrameLayout)findViewById(R.id.framelayout_color_light);
        mUIPoliceLight= (FrameLayout)findViewById(R.id.framelayout_police_light);
        mAbout = (LinearLayout)findViewById(R.id.linearlayout_about);

        mImageViewFlashlight = (ImageView)findViewById(R.id.imageview_flashlight);
        mImageViewFlashlightController = (ImageView)findViewById(R.id.imageview_flashlight_controller);
        mImageViewWarningLight1= (ImageView) findViewById(R.id.imageview_warning_light1);
        mImageViewWarningLight2= (ImageView) findViewById(R.id.imageview_warning_light2);
        mEditTwxtMorseCode= (EditText)findViewById(R.id.editext_morse_code);
        mImageViewBulb = (ImageView)findViewById(R.id.imageview_bulb);
        mButtonAboutExit = (Button)findViewById(R.id.onClick_About_Exit);

        mSharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        mDefaultScreenBrightness = getScreenBrightness();

    }
    protected void hideALLUI(){
        mUIMain.setVisibility(View.GONE);
        mUIFlashlight.setVisibility(View.GONE);
        mUIWarningLight.setVisibility(View.GONE);
        mUIMorse.setVisibility(View.GONE);
        mUIBulb.setVisibility(View.GONE);
        mUIColorLight.setVisibility(View.GONE);
        mUIPoliceLight.setVisibility(View.GONE);
        mAbout.setVisibility(View.GONE);
    }

    protected void screenBrightness(float value){
        try {
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness=value;
            getWindow().setAttributes(layout);
        }
        catch (Exception e){

        }
    }

    protected int getScreenBrightness(){
        int value = 0;
        try{
            value = android.provider.Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Exception e){
        }
        return value;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        mFinishCount = 0;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish(){
        mFinishCount ++;
        if(mFinishCount == 1){
            Toast.makeText(this,"( •̥́ ˍ •̀ू )~~别按啦！再按我就要退出啦！",Toast.LENGTH_LONG).show();
        }
        else if(mFinishCount == 2){
            super.finish();
        }
    }
    public void onClick_AboutExit(View view) {
        super.finish();
    }

}

