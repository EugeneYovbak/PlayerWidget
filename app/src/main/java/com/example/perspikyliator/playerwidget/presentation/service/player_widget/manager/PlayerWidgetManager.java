package com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager;

import android.os.Handler;

import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;
import com.example.perspikyliator.playerwidget.presentation.base.BaseManager;
import com.example.perspikyliator.playerwidget.presentation.service.audio_player.AudioPlayerCallback;
import com.example.perspikyliator.playerwidget.presentation.service.audio_player.AudioPlayerManager;
import com.example.perspikyliator.playerwidget.presentation.service.downloader.DownloaderCallback;
import com.example.perspikyliator.playerwidget.presentation.service.downloader.DownloadManager;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.PlayerWidgetCallback;

import javax.inject.Inject;

public class PlayerWidgetManager extends BaseManager<PlayerWidgetCallback> implements AudioPlayerCallback, DownloaderCallback {
    private PlayerState mPlayerState = PlayerState.UNINITIALIZED;

    @Inject
    AudioPlayerManager mAudioPlayerManager;

    @Inject
    DownloadManager mDownloadManager;

    @Override
    public void onAttach(PlayerWidgetCallback callback) {
        super.onAttach(callback);
        PlayerWidgetApp.getDependencyGraph().initPlayerManagerComponent().inject(this);
        mAudioPlayerManager.onAttach(this);
        mDownloadManager.onAttach(this);
    }

    public void handleActionPlay() {
        //TODO fix this shit
        switch (mPlayerState) {
            case COMPLETED:
            case FETCHING:
                //do nothing when fetching or completed
                break;
            case PLAYING:
                mAudioPlayerManager.pauseMusic();
                setPlayerState(PlayerState.PAUSED);
                mCallback.playerIsStopped();
                break;
            case PAUSED:
                mAudioPlayerManager.playMusic();
                setPlayerState(PlayerState.PLAYING);
                mCallback.playerIsPlaying();
                break;
            case UNINITIALIZED:
            default:
                setPlayerState(PlayerState.FETCHING);
                mCallback.playerIsLoading();
                //TODO try to get file
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAudioPlayerManager.playMusic();
                        setPlayerState(PlayerState.PLAYING);
                        mCallback.playerIsPlaying();
                    }
                }, 4000);
                break;
        }
    }

    private void setPlayerState(PlayerState playerState) {
        mPlayerState = playerState;
    }

    @Override
    public void playerIsStopped() {
        setPlayerState(PlayerState.COMPLETED);
        mCallback.playerIsLoading();
        //TODO try to delete file
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setPlayerState(PlayerState.UNINITIALIZED);
                mCallback.playerIsStopped();
            }
        }, 1000);
    }

    @Override
    public void onDetach() {
        mAudioPlayerManager.onDetach();
        mDownloadManager.onDetach();
        PlayerWidgetApp.getDependencyGraph().releasePlayerManagerComponent();
        super.onDetach();
    }
}

enum PlayerState {
    UNINITIALIZED,
    FETCHING,
    PLAYING,
    PAUSED,
    COMPLETED
}
