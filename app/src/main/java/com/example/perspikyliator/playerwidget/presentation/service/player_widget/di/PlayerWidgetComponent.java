package com.example.perspikyliator.playerwidget.presentation.service.player_widget.di;

import com.example.perspikyliator.playerwidget.presentation.service.audio_player.di.AudioPlayerComponent;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.PlayerWidgetService;

import dagger.Subcomponent;

@Subcomponent(modules = PlayerWidgetModule.class)
@PlayerWidgetScope
public interface PlayerWidgetComponent {
    void inject(PlayerWidgetService playerWidgetService);

    AudioPlayerComponent plusAudioPlayerComponent();
}
