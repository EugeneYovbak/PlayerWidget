package com.example.perspikyliator.playerwidget.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.perspikyliator.playerwidget.R;
import com.example.perspikyliator.playerwidget.presentation.service.player_widget.PlayerWidgetService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_DRAW_OVER_OTHER_APP_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_play)
    void onPlayClick() {
        if (needToCheckPermission()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            startPlayer();
        }
    }

    private boolean needToCheckPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MainActivity.this);
    }

    private void startPlayer() {
        startService(new Intent(MainActivity.this, PlayerWidgetService.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (needToCheckPermission()) {
                Toast.makeText(this, getString(R.string.error_permission_denied), Toast.LENGTH_SHORT).show();
            } else {
                startPlayer();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
