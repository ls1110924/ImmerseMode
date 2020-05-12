package com.yunxian.immersemode.sample;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.yunxian.immerse.ImmerseConfiguration;
import com.yunxian.immerse.ImmerseHelper;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/26 下午11:34
 */
public class NormalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_normal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        TextView helloworldTxt = (TextView) findViewById(R.id.helloworld);

        ImmerseConfiguration.Builder builder = new ImmerseConfiguration.Builder();

        Bundle bundle = getIntent().getExtras();
        String paras = bundle.getString("Mode");
        if ("NSB_NNB".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfiguration.NORMAL).setNavigationBarModeInKK(ImmerseConfiguration.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfiguration.NORMAL).setNavigationBarModeInL(ImmerseConfiguration.NORMAL)
                    .setFullScreenInL(false);

            helloworldTxt.setText("普通状态栏+普通导航栏");
        } else if ("TLSB_NNB".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfiguration.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfiguration.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfiguration.TRANSLUCENT).setNavigationBarModeInL(ImmerseConfiguration.NORMAL)
                    .setFullScreenInL(false);

            helloworldTxt.setText("半透明状态栏+普通导航栏");
        } else if ("TLSB_TLNB".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfiguration.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfiguration.TRANSLUCENT)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfiguration.TRANSLUCENT).setNavigationBarModeInL(ImmerseConfiguration.TRANSLUCENT)
                    .setFullScreenInL(false);

            helloworldTxt.setText("半透明状态栏+半透明导航栏");
        } else if ("TPSB_NNB".equals(paras)) {
            // Kitkat不支持全透状态栏+普通导航栏，故而降级到半透状态栏+普通导航栏
            builder.setStatusBarModeInKK(ImmerseConfiguration.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfiguration.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfiguration.TRANSPARENT).setNavigationBarModeInL(ImmerseConfiguration.NORMAL)
                    .setFullScreenInL(false);

            helloworldTxt.setText("全透明状态栏+普通导航栏；4.4降级到半透明状态栏+普通导航栏");
        } else if ("TPSB_TLNB".equals(paras)) {
            // Kitkat不支持全透状态栏+半透导航栏，故而降级到半透状态栏+半透导航栏
            builder.setStatusBarModeInKK(ImmerseConfiguration.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfiguration.TRANSLUCENT)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfiguration.TRANSPARENT).setNavigationBarModeInL(ImmerseConfiguration.TRANSLUCENT)
                    .setFullScreenInL(false);

            helloworldTxt.setText("全透明状态栏+半透明导航栏；4.4降级到半透明状态栏+半透明导航栏");
        } else {
            // 补偿逻辑
            builder.setStatusBarModeInKK(ImmerseConfiguration.NORMAL).setNavigationBarModeInKK(ImmerseConfiguration.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfiguration.NORMAL).setNavigationBarModeInL(ImmerseConfiguration.NORMAL)
                    .setFullScreenInL(false);

            helloworldTxt.setText("普通状态栏+普通导航栏");
        }

        ImmerseHelper immerseHelper = new ImmerseHelper(this, builder.build());
        immerseHelper.setStatusColorRes(R.color.colorAccent);
        immerseHelper.setNavigationColorRes(R.color.colorAccent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
