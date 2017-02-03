package com.yunxian.immersemode.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yunxian.immerse.ImmerseConfiguration;
import com.yunxian.immerse.ImmerseHelper;
import com.yunxian.immerse.enumeration.ImmerseConfigType;
import com.yunxian.immerse.enumeration.StatusBarImmerseType;

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

        ImmerseConfiguration.Builder builder = new ImmerseConfiguration.Builder();

        Bundle bundle = getIntent().getExtras();
        String paras = bundle.getString("Mode");
        if ("NSB_NNB".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.NORMAL).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.NORMAL).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(false);
        } else if ("TLSB_NNB".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(false);
        } else if ("TLSB_TLNB".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInL(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInL(false);
        } else if ("TPSB_NNB".equals(paras)) {
            // Kitkat不支持全透状态栏+普通导航栏，故而降级到半透状态栏+普通导航栏
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSPARENT).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(false);
        } else if ("TPSB_TLNB".equals(paras)) {
            // Kitkat不支持全透状态栏+半透导航栏，故而降级到半透状态栏+半透导航栏
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSPARENT).setNavigationBarModeInL(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInL(false);
        } else {
            // 补偿逻辑
            builder.setStatusBarModeInKK(ImmerseConfigType.NORMAL).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.NORMAL).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(false);
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
