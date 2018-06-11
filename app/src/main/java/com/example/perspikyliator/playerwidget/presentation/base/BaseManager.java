package com.example.perspikyliator.playerwidget.presentation.base;

public class BaseManager<T extends BaseCallback> {

    protected T mCallback;

    public void onAttach(T callback) {
        mCallback = callback;
    }

    public void onDetach() {
        mCallback = null;
    }
}