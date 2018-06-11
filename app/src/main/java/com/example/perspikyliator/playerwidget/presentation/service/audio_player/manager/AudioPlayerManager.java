package com.example.perspikyliator.playerwidget.presentation.service.audio_player.manager;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.perspikyliator.playerwidget.R;
import com.example.perspikyliator.playerwidget.presentation.base.BaseManager;
import com.example.perspikyliator.playerwidget.presentation.service.audio_player.AudioPlayerCallback;

import javax.inject.Inject;

public class AudioPlayerManager extends BaseManager<AudioPlayerCallback> {

    private Context mContext;
    private MediaPlayer mMediaPlayer;

    @Inject public AudioPlayerManager(Context context) {
        mContext = context;
    }

    @Override
    public void onAttach(AudioPlayerCallback callback) {
        super.onAttach(callback);
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.sample);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mCallback.playerIsStopped();
            }
        });
    }

    public void playMusic() {
        mMediaPlayer.start();
    }

    public void pauseMusic() {
        mMediaPlayer.pause();
    }

    @Override
    public void onDetach() {
        mMediaPlayer.release();
        mMediaPlayer = null;
        mContext = null;
        super.onDetach();
    }
}
