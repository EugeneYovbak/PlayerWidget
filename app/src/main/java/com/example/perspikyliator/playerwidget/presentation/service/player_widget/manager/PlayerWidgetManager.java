package com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager;

import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;
import com.example.perspikyliator.playerwidget.presentation.base.BaseManager;
import com.example.perspikyliator.playerwidget.presentation.service.audio_player.AudioPlayerCallback;
import com.example.perspikyliator.playerwidget.presentation.service.audio_player.AudioPlayerManager;
import com.example.perspikyliator.playerwidget.presentation.service.downloader.DownloadManager;
import com.example.perspikyliator.playerwidget.presentation.service.downloader.DownloaderCallback;
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
        switch (mPlayerState) {
            case COMPLETED:
            case FETCHING:
                //do nothing when fetching or completed
                break;
            case PLAYING:
                applyPauseState();
                break;
            case PAUSED:
                applyPlayState();
                break;
            case UNINITIALIZED:
            default:
                applyFetchingState();
                break;
        }
    }

    @Override
    public void playerIsStopped() {
        applyCompletedState();
    }

    @Override
    public void fileDownloadSuccess() {
        applyPlayState();
    }

    @Override
    public void fileDownloadError() {
        applyUninitializedState();
    }

    @Override
    public void fileDeleteSuccess() {
        applyUninitializedState();
    }

    private void applyUninitializedState() {
        setPlayerState(PlayerState.UNINITIALIZED);
        mCallback.playerIsStopped();
        mCallback.loadingFileError();
    }

    private void applyFetchingState() {
        mDownloadManager.downloadFile();
        setPlayerState(PlayerState.FETCHING);
        mCallback.playerIsLoading();
    }

    private void applyPlayState() {
        mAudioPlayerManager.playMusic();
        setPlayerState(PlayerState.PLAYING);
        mCallback.playerIsPlaying();
    }

    private void applyPauseState() {
        mAudioPlayerManager.pauseMusic();
        setPlayerState(PlayerState.PAUSED);
        mCallback.playerIsStopped();
    }

    private void applyCompletedState() {
        mDownloadManager.deleteFile();
        setPlayerState(PlayerState.COMPLETED);
        mCallback.playerIsLoading();
    }

    private void setPlayerState(PlayerState playerState) {
        mPlayerState = playerState;
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
