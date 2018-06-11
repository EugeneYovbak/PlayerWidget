package com.example.perspikyliator.playerwidget.presentation.service.downloader;

import android.os.Handler;

import com.example.perspikyliator.playerwidget.presentation.base.BaseManager;

public class DownloadManager extends BaseManager<DownloaderCallback> {

    //TODO add real downloader

    public void downloadFile() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallback.fileDownloadSuccess();
            }
        }, 4000);
    }

    public void deleteFile() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallback.fileDeleteSuccess();
            }
        }, 1000);
    }
}
