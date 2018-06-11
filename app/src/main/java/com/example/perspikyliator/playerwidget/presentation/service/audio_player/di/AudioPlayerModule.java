package com.example.perspikyliator.playerwidget.presentation.service.audio_player.di;

import android.content.Context;

import com.example.perspikyliator.playerwidget.presentation.service.audio_player.manager.AudioPlayerManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AudioPlayerModule {

    @Provides
    @AudioPlayerScope
    AudioPlayerManager provideAudioPlayerManager(Context context) {
        return new AudioPlayerManager(context);
    }
}
