package com.menger.superflashlight;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lfcha on 2016/2/21.
 *
 *   . . . - - -   . ..
 *
 */
public class Morse extends WarningLight {

    private final int DOT_TIME = 200;//点停留的时间，；单位：毫秒
    private final int LINE_TIME = DOT_TIME * 3;    //线停留的时间
    private final int DOT_LINE_TIME = DOT_TIME;  //点到线的时间间隔

    private final int CHAR_CHAR_TIME = DOT_TIME * 3;  //字符之间时间间隔

    private final int WORD_WORD_TIME = DOT_TIME * 7;  //单词之间时间间隔

    private String mMorseCode;

    private boolean stopMorse;

    private Map<Character, String> mMorseCodeMap = new HashMap<Character, String>();

//    private static final int OPEN = 1;
//    private static final int CLOSE = 0;
    private static final int TOASTSEND = 2;
    private static final int TOASTNOMORSE = 3;
    private static final int TOASTINPUTERROR = 4;
    private static final int TOASTSTOPSEND = 5;

    private int ToastStopSendTime = 0;   //Toast停止发送的次数
    private boolean MorseSending = false;  //检测Morse是否正在发送

//    Message msgOpen = new Message();
//    Message msgClose = new Message();
//    Message.obtain();

    Message msgToastSend = new Message();
    Message msgToastNoMorse = new Message();
    Message msgToastStopSend = new Message();
    Message msgToastInputError = new Message();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMorseCodeMap.put('a', ".-");
        mMorseCodeMap.put('b', "-...");
        mMorseCodeMap.put('c', "-.-.");
        mMorseCodeMap.put('d', "-..");
        mMorseCodeMap.put('e', ".");
        mMorseCodeMap.put('f', "..-.");
        mMorseCodeMap.put('g', "--.");
        mMorseCodeMap.put('h', "....");
        mMorseCodeMap.put('i', "..");
        mMorseCodeMap.put('j', ".---");
        mMorseCodeMap.put('k', "-.-");
        mMorseCodeMap.put('l', ".-..");
        mMorseCodeMap.put('m', "--");
        mMorseCodeMap.put('n', "-.");
        mMorseCodeMap.put('o', "---");
        mMorseCodeMap.put('p', ".--.");
        mMorseCodeMap.put('q', "--.-");
        mMorseCodeMap.put('r', ".-.");
        mMorseCodeMap.put('s', "...");
        mMorseCodeMap.put('t', "-");
        mMorseCodeMap.put('u', "..-");
        mMorseCodeMap.put('v', "...-");
        mMorseCodeMap.put('w', ".--");
        mMorseCodeMap.put('x', "-..-");
        mMorseCodeMap.put('y', "-.--");
        mMorseCodeMap.put('z', "--..");

        mMorseCodeMap.put('0', "-----");
        mMorseCodeMap.put('1', ".----");
        mMorseCodeMap.put('2', "..---");
        mMorseCodeMap.put('3', "...--");
        mMorseCodeMap.put('4', "....-");
        mMorseCodeMap.put('5', ".....");
        mMorseCodeMap.put('6', "-....");
        mMorseCodeMap.put('7', "--...");
        mMorseCodeMap.put('8', "---..");
        mMorseCodeMap.put('9', "----.");
    }


    public void onClick_SendMorseCode(View view) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "∑( ° △ °|||)︴你的爱机木有闪光灯呐！", Toast.LENGTH_LONG).show();
            return;
        }
        else if(!MorseSending) {
            MorseSending = true;
            if (verifyMorseCode()) {

                //开线程，保证发送Morse电码的时候能进行其他操作
                new Thread() {
                    public void run() {
                        stopMorse = false;
                        sendSentense(mMorseCode);
                        MorseSending =false;
                    }
                }.start();
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case OPEN:
//                    openFlashlight();
//                    break;
//                case CLOSE:
//                    closeFlashlight();
//                    break;
                case TOASTSEND:
                    Toast.makeText(Morse.this, "(￣▽￣)~摩尔斯电码发送完咯！", Toast.LENGTH_LONG).show();
                    break;
                case TOASTNOMORSE:
                    Toast.makeText(Morse.this, "(๑• . •๑)~你还没有输入摩尔斯电码喔！", Toast.LENGTH_SHORT).show();
                    break;
                case TOASTINPUTERROR:
                    Toast.makeText(Morse.this, "（O__O）~~摩尔斯电码只能是字母和数字呀！", Toast.LENGTH_LONG).show();
                    break;
                case TOASTSTOPSEND:
                    Toast.makeText(Morse.this, "(oﾟvﾟ)ノ摩尔斯电码已经停止发送！", Toast.LENGTH_LONG).show();
                default:
                    break;
            }
//            if (msg.what == COMPLETED) {
//                //UI响应操作
//                openFlashlight();
//            }
        }
    };

    public void openFlashlightAsync(){
        openFlashlight();
//        Message msgOpenFlashlight =new Message();
//        msgOpenFlashlight.what = OPEN;
//        handler.sendMessage(msgOpenFlashlight);

    }
    protected void closeFlashlightAsync(){
        closeFlashlight();
//        Message msgCloseFlashlight =new Message();
//        msgCloseFlashlight.what = CLOSE;
//        handler.sendMessage(msgCloseFlashlight);
    }

    public void sleepExt( final long t) {

        try {

            Thread.sleep(t);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void onClick_StopMorseCode(View view) {
        stopMorse = true;
        ToastStopSendTime = 0;
    }


    //发送点
    private void sendDot() {
        if (!stopMorse) {

            openFlashlight();
            sleepExt(DOT_TIME);
            closeFlashlight();
        }
        else if(ToastStopSendTime == 0){

            msgToastStopSend.what = 5;
            handler.sendMessage(msgToastStopSend);
            ToastStopSendTime ++;
        }
    }

    //发送线
    private void sendLine() {
        if (!stopMorse) {

            openFlashlight();
            sleepExt(LINE_TIME);
            closeFlashlight();
        }
        else if(ToastStopSendTime == 0){

            msgToastStopSend.what = 5;
            handler.sendMessage(msgToastStopSend);
            ToastStopSendTime ++;
        }
    }

    //发送字符
    private void sendChar(char c) {
        String morseCode = mMorseCodeMap.get(c);
        if (morseCode != null) {
            char lastChar = ' ';
            for (int i = 0; i < morseCode.length(); i++) {
                char dotLine = morseCode.charAt(i);
                if (dotLine == '.') {
                    sendDot();
                } else if (dotLine == '-') {
                    sendLine();
                }
                if (i > 0 && i < morseCode.length() - 1) {
                    if (lastChar == '.' && dotLine == '-') {
                        sleepExt(DOT_LINE_TIME);
                    }
                }
                lastChar = dotLine;
            }
        }
    }

    //发送字词
    private void sendWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sendChar(c);
            if (i < s.length() - 1) {
                sleepExt(CHAR_CHAR_TIME);
            }
        }
    }

    //发送句
    private void sendSentense(String s) {
        String[] words = s.split(" +");
        for (int i = 0; i < words.length; i++) {
            sendWord(words[i]);
            if (i < words.length - 1) {
                sleepExt(WORD_WORD_TIME);
            }
        }
        if(ToastStopSendTime == 0) {
            msgToastSend.what = 2;
            handler.sendMessage(msgToastSend);
            Toast.makeText(this,"(￣▽￣)~摩尔斯电码发送完咯！",Toast.LENGTH_LONG).show();
        }
    }

    private boolean verifyMorseCode() {
        mMorseCode = mEditTwxtMorseCode.getText().toString().toLowerCase();
        if ("".equals(mMorseCode)) {
            msgToastNoMorse.what = 3;
            handler.sendMessage(msgToastNoMorse);
             Toast.makeText(this, "(๑• . •๑)~你还没有输入摩尔斯电码喔！", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (int i = 0; i < mMorseCode.length(); i++) {
            char c = mMorseCode.charAt(i);
            if (!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && c != ' ') {
                msgToastInputError.what = 4;
                handler.sendMessage(msgToastInputError);
                Toast.makeText(this, "（O__O）~~摩尔斯电码只能是字母和数字呀！", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}
