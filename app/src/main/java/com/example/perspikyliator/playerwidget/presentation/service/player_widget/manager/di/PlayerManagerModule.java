package com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.di;

import android.content.Context;

import com.example.perspikyliator.playerwidget.presentation.service.audio_player.AudioPlayerManager;
import com.example.perspikyliator.playerwidget.presentation.service.downloader.DownloadManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerManagerModule {

    @Provides
    @PlayerManagerScope
    DownloadManager provideDownloadManager() {
        return new DownloadManager();
    }

    @Provides
    @PlayerManagerScope
    AudioPlayerManager provideAudioPlayerManager(Context context) {
        return new AudioPlayerManager(context);
    }
}
