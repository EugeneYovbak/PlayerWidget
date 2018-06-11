package com.example.perspikyliator.playerwidget.presentation.service.player_widget.di;

import com.example.perspikyliator.playerwidget.presentation.service.player_widget.PlayerWidgetService;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.di.PlayerManagerComponent;

import dagger.Subcomponent;

@Subcomponent(modules = PlayerWidgetModule.class)
@PlayerWidgetScope
public interface PlayerWidgetComponent {
    void inject(PlayerWidgetService playerWidgetService);

    PlayerManagerComponent plusPlayerManagerComponent();
}
