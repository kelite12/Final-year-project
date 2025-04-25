package com.ztkj.victe.ui.health;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ztkj.victe.R;
import com.ztkj.victe.ui.base.BaseActivity;

public class HealthActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        TongJi();
        getTongjiHeadText().setText("health data");
    }
}
