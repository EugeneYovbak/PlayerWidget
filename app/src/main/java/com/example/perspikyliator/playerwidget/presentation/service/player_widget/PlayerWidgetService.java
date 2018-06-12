package com.example.perspikyliator.playerwidget.presentation.service.player_widget;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.perspikyliator.playerwidget.R;
import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.PlayerWidgetManager;

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
    PlayerWidgetManager mPlayerWidgetManager;

    private WindowManager mWindowManager;
    private View mFloatingView;

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.widget_player, null);
        ButterKnife.bind(this, mFloatingView);
        PlayerWidgetApp.getDependencyGraph().initPlayerWidgetComponent().inject(this);
        mPlayerWidgetManager.onAttach(this);
        setFloatingView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFloatingView() {
        final WindowManager.LayoutParams params;
        params = new WindowManager.LayoutParams(
                (int)getResources().getDimension(R.dimen.player_view_size),
                (int)getResources().getDimension(R.dimen.player_view_size),
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        mRootView.setOnTouchListener(new View.OnTouchListener() {
            //not the best code, but works correct)
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
                            mPlayerWidgetManager.handleActionPlay();
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
    public void playerIsLoading() {
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
    public void loadingFileError() {
        Toast.makeText(this, getString(R.string.error_loading_file), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv_close)
    void onCloseClick() {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerWidgetManager.onDetach();
        PlayerWidgetApp.getDependencyGraph().releasePlayerWidgetComponent();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}