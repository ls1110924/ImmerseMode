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

        findViewById(R.id.nsb_nnb).setOnClickListener(mCommonListener);
        findViewById(R.id.tlsb_nnb).setOnClickListener(mCommonListener);
        findViewById(R.id.tlsb_nnb_fc).setOnClickListener(mCommonListener);
        findViewById(R.id.tlsb_tlnb).setOnClickListener(mCommonListener);
        findViewById(R.id.tlsb_tlnb_fc).setOnClickListener(mCommonListener);
        findViewById(R.id.tpsb_nnb).setOnClickListener(mCommonListener);
        findViewById(R.id.tpsb_nnb_fc).setOnClickListener(mCommonListener);
        findViewById(R.id.tpsb_tlnb).setOnClickListener(mCommonListener);
        findViewById(R.id.tpsb_tlnb_fc).setOnClickListener(mCommonListener);
        findViewById(R.id.full_input_mode).setOnClickListener(mCommonListener);
    }


    private class CommonCallbackListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nsb_nnb: {
                    Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "NSB_NNB");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tlsb_nnb: {
                    Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TLSB_NNB");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tlsb_nnb_fc: {
                    Intent intent = new Intent(MainActivity.this, FullActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TLSB_NNB_FC");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tlsb_tlnb: {
                    Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TLSB_TLNB");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tlsb_tlnb_fc: {
                    Intent intent = new Intent(MainActivity.this, FullActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TLSB_TLNB_FC");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tpsb_nnb: {
                    Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TPSB_NNB");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tpsb_nnb_fc: {
                    Intent intent = new Intent(MainActivity.this, FullActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TPSB_NNB_FC");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tpsb_tlnb: {
                    Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TPSB_TLNB");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tpsb_tlnb_fc: {
                    Intent intent = new Intent(MainActivity.this, FullActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TPSB_TLNB_FC");
                    intent.putExtras(bundle);
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
