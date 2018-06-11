package com.example.perspikyliator.playerwidget.app.di;

import com.example.perspikyliator.playerwidget.app.PlayerWidgetApp;
import com.example.perspikyliator.playerwidget.presentation.service.audio_player.di.AudioPlayerComponent;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.di.PlayerWidgetComponent;

public class DependencyGraph {

    private AppComponent mAppComponent;

    private PlayerWidgetComponent mPlayerWidgetComponent;

    private AudioPlayerComponent mAudioPlayerComponent;

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

    public AudioPlayerComponent initAudioPlayerComponent() {
        mAudioPlayerComponent = mPlayerWidgetComponent.plusAudioPlayerComponent();
        return mAudioPlayerComponent;
    }

    public void releaseAudioPlayerComponent() {
        mAudioPlayerComponent = null;
    }
}
