package com.example.perspikyliator.playerwidget.app.di;

import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;
import com.example.perspikyliator.playerwidget.presentation.service.di.PlayerWidgetComponent;

public class DependencyGraph {

    private AppComponent mAppComponent;

    private PlayerWidgetComponent mPlayerWidgetComponent;

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
}
