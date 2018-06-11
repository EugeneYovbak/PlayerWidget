package com.example.perspikyliator.playerwidget.presentation.service.player_widget.di;

import com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.PlayerWidgetManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerWidgetModule {

    @Provides
    @PlayerWidgetScope
    PlayerWidgetManager providePlayerManager() {
        return new PlayerWidgetManager();
    }
}
