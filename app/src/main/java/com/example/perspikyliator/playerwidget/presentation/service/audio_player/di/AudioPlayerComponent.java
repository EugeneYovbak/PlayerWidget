package com.example.perspikyliator.playerwidget.presentation.service.audio_player.di;

import com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.PlayerWidgetManager;

import dagger.Subcomponent;

@Subcomponent(modules = AudioPlayerModule.class)
@AudioPlayerScope
public interface AudioPlayerComponent {
    void inject(PlayerWidgetManager playerWidgetManager);
}
