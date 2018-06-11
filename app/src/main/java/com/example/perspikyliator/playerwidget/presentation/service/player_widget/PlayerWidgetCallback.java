package com.example.perspikyliator.playerwidget.presentation.service.player_widget;

import com.example.perspikyliator.playerwidget.presentation.base.BaseCallback;

public interface PlayerWidgetCallback extends BaseCallback {
    void playerIsLoading();
    void playerIsPlaying();
    void playerIsStopped();
}
