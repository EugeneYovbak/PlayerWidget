package com.example.perspikyliator.playerwidget.app.di;

import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.di.PlayerWidgetComponent;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.manager.di.PlayerManagerComponent;

public class DependencyGraph {

    private AppComponent mAppComponent;
    private PlayerWidgetComponent mPlayerWidgetComponent;
    private PlayerManagerComponent mPlayerManagerComponent;

    public DependencyGraph(PlayerWidgetApp playerWidgetApp) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(playerWidgetApp))
                .build();
    }

    public PlayerWidgetComponent initPlayerWidgetComponent() {
        mPlayerWidgetComponent = mAppComponent.plusPlayerWidgetComponent();
        return mPlayerWidgetComponent;
    }

    public void releasePlayerWidgetComponent() {
        mPlayerWidgetComponent = null;
    }

    public PlayerManagerComponent initPlayerManagerComponent() {
        mPlayerManagerComponent = mPlayerWidgetComponent.plusPlayerManagerComponent();
        return mPlayerManagerComponent;
    }

    public void releasePlayerManagerComponent() {
        mPlayerManagerComponent = null;
    }
}
