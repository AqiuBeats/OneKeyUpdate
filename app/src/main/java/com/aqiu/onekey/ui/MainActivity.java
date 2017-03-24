package com.aqiu.onekey.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aqiu.onekey.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bt_1)
    Button bt1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void reDatas() {

    }

    @OnClick(R.id.bt_1)
    public void onClick() {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));

    }
}
