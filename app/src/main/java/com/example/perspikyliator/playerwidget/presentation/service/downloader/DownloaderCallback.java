package com.example.perspikyliator.playerwidget.presentation.service.downloader;

import com.example.perspikyliator.playerwidget.presentation.base.BaseCallback;

public interface DownloaderCallback extends BaseCallback {
    void fileDownloadSuccess();
    void fileDownloadError();
    void fileDeleteSuccess();
    void fileDeleteError();
}
