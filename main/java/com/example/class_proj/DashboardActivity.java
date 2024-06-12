package com.example.class_proj;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity implements TimerService.TimerListener {
    private TimerService timerService;
    private boolean isBound = false;
    private Button startButton, dryButton, spinButton;
    private String currentButton = "";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.LocalBinder binder = (TimerService.LocalBinder) service;
            timerService = binder.getService();
            timerService.setTimerListener(DashboardActivity.this);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.nav_timer) {
                    return true;
                }
                return false;
            }
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_timer);

        startButton = findViewById(R.id.btn_washing);
        dryButton = findViewById(R.id.btn_drying);
        spinButton = findViewById(R.id.btn_dehydration);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound && timerService.isTimerRunning()) {
                    Toast.makeText(DashboardActivity.this, "이미 작동 중입니다", Toast.LENGTH_SHORT).show();
                } else if (isBound) {
                    currentButton = "start";
                    timerService.startTimer(10000, "세탁이 완료되었습니다.", DashboardActivity.this);
                }
            }
        });

        dryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound && timerService.isTimerRunning()) {
                    Toast.makeText(DashboardActivity.this, "이미 작동 중입니다", Toast.LENGTH_SHORT).show();
                } else if (isBound) {
                    currentButton = "dry";
                    timerService.startTimer(10000, "건조가 완료되었습니다.", DashboardActivity.this);
                }
            }
        });

        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound && timerService.isTimerRunning()) {
                    Toast.makeText(DashboardActivity.this, "이미 작동 중입니다", Toast.LENGTH_SHORT).show();
                } else if (isBound) {
                    currentButton = "spin";
                    timerService.startTimer(10000, "탈수가 완료되었습니다.", DashboardActivity.this);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    @Override
    public void onTimerTick(long millisUntilFinished) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String timeLeft = "타이머 작동 중... " + millisUntilFinished / 1000 + "초 남음";
                if (currentButton.equals("start")) {
                    startButton.setText(timeLeft);
                } else if (currentButton.equals("dry")) {
                    dryButton.setText(timeLeft);
                } else if (currentButton.equals("spin")) {
                    spinButton.setText(timeLeft);
                }
            }
        });
    }

    @Override
    public void onTimerFinish(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (currentButton.equals("start")) {
                    startButton.setText("세탁 시작");
                } else if (currentButton.equals("dry")) {
                    dryButton.setText("건조 시작");
                } else if (currentButton.equals("spin")) {
                    spinButton.setText("탈수 시작");
                }
                currentButton = "";
            }
        });
    }
}