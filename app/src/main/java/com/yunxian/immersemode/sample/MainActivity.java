package com.yunxian.immersemode.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CommonCallbackListener mCommonListener = new CommonCallbackListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.normal_mode).setOnClickListener(mCommonListener);
        findViewById(R.id.translucent_mode).setOnClickListener(mCommonListener);
        findViewById(R.id.translucent_full_mode).setOnClickListener(mCommonListener);
        findViewById(R.id.transparent_mode).setOnClickListener(mCommonListener);
        findViewById(R.id.transparent_full_mode).setOnClickListener(mCommonListener);
        findViewById(R.id.full_input_mode).setOnClickListener(mCommonListener);
    }


    private class CommonCallbackListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.normal_mode: {
                    Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.translucent_mode: {
                    Intent intent = new Intent(MainActivity.this, TranslucentActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.translucent_full_mode: {
                    Intent intent = new Intent(MainActivity.this, TranslucentFullActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.transparent_mode: {
                    Intent intent = new Intent(MainActivity.this, TransparentActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.transparent_full_mode: {
                    Intent intent = new Intent(MainActivity.this, TransparentFullActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.full_input_mode: {
                    Intent intent = new Intent(MainActivity.this, FullInputActivity.class);
                    startActivity(intent);
                    break;
                }
                default:
                    break;
            }
        }
    }

}
