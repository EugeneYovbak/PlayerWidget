package com.example.perspikyliator.playerwidget.presentation.service.di;

import com.example.perspikyliator.playerwidget.presentation.service.PlayerWidgetService;

import dagger.Subcomponent;

@Subcomponent(modules = PlayerWidgetModule.class)
@PlayerWidgetScope
public interface PlayerWidgetComponent {
    void inject(PlayerWidgetService playerWidgetService);
}
