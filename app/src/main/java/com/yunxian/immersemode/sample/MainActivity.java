package com.yunxian.immersemode.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private CommonCallbackListener mCommonListener = new CommonCallbackListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        findViewById(R.id.tlsb_nnb_fc_ar).setOnClickListener(mCommonListener);
        findViewById(R.id.tpsb_nnb_fc_ar).setOnClickListener(mCommonListener);
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
                case R.id.tlsb_nnb_fc_ar: {
                    Intent intent = new Intent(MainActivity.this, FullInputActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TLSB_NNB_FC_AR");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.tpsb_nnb_fc_ar: {
                    Intent intent = new Intent(MainActivity.this, FullInputActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode", "TPSB_NNB_FC_AR");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                default:
                    break;
            }
        }
    }

}
