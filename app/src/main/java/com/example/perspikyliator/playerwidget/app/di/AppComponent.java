package com.example.perspikyliator.playerwidget.app.di;

import com.example.perspikyliator.playerwidget.presentation.service.player_widget.di.PlayerWidgetComponent;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    PlayerWidgetComponent plusPlayerWidgetComponent();
}
