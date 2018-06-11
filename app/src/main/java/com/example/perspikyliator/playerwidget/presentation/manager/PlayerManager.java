package com.example.perspikyliator.playerwidget.presentation.manager;

import android.os.Handler;

import com.example.perspikyliator.playerwidget.presentation.service.PlayerWidgetCallback;

public class PlayerManager {

    private PlayerWidgetCallback mPlayerWidgetCallback;
    private PlayerState mPlayerState = PlayerState.UNINITIALIZED;

    public PlayerState getPlayerState() {
        return mPlayerState;
    }

    public void setPlayerState(PlayerState playerState) {
        mPlayerState = playerState;
    }

    public void onAttach(PlayerWidgetCallback playerWidgetCallback) {
        mPlayerWidgetCallback = playerWidgetCallback;
    }

    public void handleActionPlay() {
        switch (mPlayerState) {
            case FETCHING:
                //do nothing when fetching
                break;
            case PLAYING:
                //do pause
                setPlayerState(PlayerState.PAUSED);
                mPlayerWidgetCallback.playerIsStopped();
                break;
            case PAUSED:
                //do play
                setPlayerState(PlayerState.PLAYING);
                mPlayerWidgetCallback.playerIsPlaying();
                break;
            case COMPLETED:
                //do delete file
                break;
            case UNINITIALIZED:
            default:
                setPlayerState(PlayerState.FETCHING);
                mPlayerWidgetCallback.playerIsFetching();
                //TODO try to get file
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setPlayerState(PlayerState.PLAYING);
                        mPlayerWidgetCallback.playerIsPlaying();
                    }
                }, 4000);
                break;
        }
    }

    public void onDetach() {
        mPlayerWidgetCallback = null;
    }
}

enum PlayerState {
    UNINITIALIZED,
    FETCHING,
    PLAYING,
    PAUSED,
    COMPLETED
}
