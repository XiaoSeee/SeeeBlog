package com.yiketang.android.view;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by Xiao.Se on 2016/5/19.
 * ..
 */
public class MyCountDownTimer extends CountDownTimer {
    private Button countdownButton;
    private String buttonText;

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * @param millisInFuture    总的时间（毫秒）
     * @param countDownInterval 间隔时间（毫秒）
     * @param button            倒计时的按钮
     * @param buttonText        倒计时按钮的初始文字
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval, Button button, String buttonText) {
        this(millisInFuture, countDownInterval);
        this.countdownButton = button;
        this.buttonText = buttonText;
    }

    public void stop() {
        cancel();
        onFinish();
    }

    @Override
    public void onFinish() {
        countdownButton.setEnabled(true);
        countdownButton.setText(buttonText);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        countdownButton.setEnabled(false);
        countdownButton.setText("等待" + millisUntilFinished / 1000 + "秒");
    }
}
