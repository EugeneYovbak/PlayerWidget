package com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.di;

import com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.PlayerWidgetManager;

import dagger.Subcomponent;

@Subcomponent(modules = PlayerManagerModule.class)
@PlayerManagerScope
public interface PlayerManagerComponent {
    void inject(PlayerWidgetManager playerWidgetManager);
}
