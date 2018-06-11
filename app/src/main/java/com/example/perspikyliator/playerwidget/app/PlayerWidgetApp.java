package com.example.perspikyliator.playerwidget.app;

import android.app.Application;

import com.example.perspikyliator.playerwidget.app.di.DependencyGraph;

public class PlayerWidgetApp extends Application {

    private static DependencyGraph mDependencyGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mDependencyGraph = new DependencyGraph(this);
    }

    public static DependencyGraph getDependencyGraph() {
        return mDependencyGraph;
    }
}
