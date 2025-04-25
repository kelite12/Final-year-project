package com.ztkj.victe.ui.yin;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ztkj.victe.R;
import com.ztkj.victe.ui.base.BaseActivity;

public class YinActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin);
        TongJi();
        getTongjiHeadText().setText("列表");
    }
}