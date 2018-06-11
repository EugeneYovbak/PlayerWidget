package com.example.perspikyliator.playerwidget.presentation.service.di;

import com.example.perspikyliator.playerwidget.presentation.manager.PlayerManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerWidgetModule {

    @Provides
    @PlayerWidgetScope
    PlayerManager providePlayerManager() {
        return new PlayerManager();
    }
}
