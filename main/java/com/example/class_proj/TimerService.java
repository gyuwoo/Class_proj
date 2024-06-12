package com.example.class_proj;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

public class TimerService extends Service {

    private final IBinder binder = new LocalBinder();
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis;
    private String notificationMessage;
    private TimerListener timerListener;

    public interface TimerListener {
        void onTimerTick(long millisUntilFinished);
        void onTimerFinish(String message);
    }

    public class LocalBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void startTimer(long durationInMillis, String message, TimerListener listener) {
        if (isTimerRunning) {
            return;
        }

        notificationMessage = message;
        isTimerRunning = true;
        timerListener = listener;
        countDownTimer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                if (timerListener != null) {
                    timerListener.onTimerTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                if (timerListener != null) {
                    timerListener.onTimerFinish(notificationMessage);
                }
                Intent intent = new Intent(TimerService.this, MainActivity.class);
                intent.putExtra("notification", notificationMessage);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }.start();
    }

    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    public long getTimeLeftInMillis() {
        return timeLeftInMillis;
    }

    public void setTimerListener(TimerListener listener) {
        timerListener = listener;
    }

    @Override
    public void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
}