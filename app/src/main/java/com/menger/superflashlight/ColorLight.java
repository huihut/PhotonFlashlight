package com.menger.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.menger.superflashlight.widget.HideTextView;

/**
 * Created by lfcha on 2016/2/21.
 */
public class ColorLight extends Bulb implements ColorPickerDialog.OnColorChangedListener {
    protected int mCurrentColorLight = Color.RED;
    protected HideTextView mHideTextViewColorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextViewColorLight = (HideTextView) findViewById(R.id.textview_hide_color_light);
    }
    @Override
    public void colorChanged(int color) {
        mUIColorLight.setBackgroundColor(color);
        mCurrentColorLight = color;
    }

    public void onClick_ShowColorPicker(View view) {

        new ColorPickerDialog(this, this, Color.RED).show();
    }
}


//android.view.WindowManager$InvalidDisplayException: Unable to add window android.view.ViewRootImpl$W@2e31052 -- the specified window type is not valid
