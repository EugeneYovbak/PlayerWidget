package com.example.perspikyliator.playerwidget.presentation.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.perspikyliator.playerwidget.R;
import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;
import com.example.perspikyliator.playerwidget.presentation.manager.PlayerManager;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerWidgetService extends Service implements PlayerWidgetCallback {

    @BindView(R.id.root_view) RelativeLayout mRootView;
    @BindView(R.id.iv_player) ImageView mPlayerImageView;
    @BindView(R.id.pb_player) ProgressBar mPlayerProgressBar;

    @Inject
    PlayerManager mPlayerManager;

    private WindowManager mWindowManager;
    private View mFloatingView;

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.widget_player, null);
        ButterKnife.bind(this, mFloatingView);
        PlayerWidgetApp.getDependencyGraph().initPlayerWidgetComponent().inject(this);
        mPlayerManager.onAttach(this);
        setFloatingView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFloatingView() {
        final WindowManager.LayoutParams params;
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        mRootView.setOnTouchListener(new View.OnTouchListener() {
            private static final int MAX_CLICK_DURATION = 200;
            private long startClickTime;

            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startClickTime = Calendar.getInstance().getTimeInMillis();

                        initialX = params.x;
                        initialY = params.y;

                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if (clickDuration < MAX_CLICK_DURATION) {
                            mPlayerManager.handleActionPlay();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void playerIsFetching() {
        mPlayerImageView.setImageResource(R.drawable.ic_action_play);
        mPlayerImageView.setVisibility(View.GONE);
        mPlayerProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void playerIsPlaying() {
        mPlayerImageView.setImageResource(R.drawable.ic_action_pause);
        mPlayerImageView.setVisibility(View.VISIBLE);
        mPlayerProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void playerIsStopped() {
        mPlayerImageView.setImageResource(R.drawable.ic_action_play);
        mPlayerImageView.setVisibility(View.VISIBLE);
        mPlayerProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PlayerWidgetApp.getDependencyGraph().releasePlayerWidgetComponent();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}