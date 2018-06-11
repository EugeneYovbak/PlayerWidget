package com.example.perspikyliator.playerwidget.app.di;

import android.content.Context;

import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private PlayerWidgetApp mPlayerWidgetApp;

    public AppModule(PlayerWidgetApp playerWidgetApp) {
        mPlayerWidgetApp = playerWidgetApp;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mPlayerWidgetApp.getApplicationContext();
    }
}
