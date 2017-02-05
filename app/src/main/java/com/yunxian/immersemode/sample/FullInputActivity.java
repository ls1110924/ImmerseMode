package com.yunxian.immersemode.sample;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.yunxian.immerse.ImmerseConfiguration;
import com.yunxian.immerse.ImmerseHelper;
import com.yunxian.immerse.enumeration.ImmerseConfigType;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/1/29 14:07
 */
public class FullInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        ImmerseConfiguration.Builder builder = new ImmerseConfiguration.Builder();
        Bundle bundle = getIntent().getExtras();
        String paras = bundle.getString("Mode");
        if ("TLSB_NNB_FC_AR".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(true).setAdjustResizeInKK(true);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(true).setAdjustResizeInL(true);
        } else if ("TPSB_NNB_FC_AR".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSPARENT).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(true).setAdjustResizeInKK(true);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSPARENT).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(true).setAdjustResizeInL(true);
        } else {
            // 补偿逻辑
            builder.setStatusBarModeInKK(ImmerseConfigType.NORMAL).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.NORMAL).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(false);
        }

        ImmerseHelper immerseHelper = new ImmerseHelper(this, builder.build());
        immerseHelper.setStatusColor(Color.TRANSPARENT);
        immerseHelper.setNavigationColorRes(R.color.colorAccent);

        Rect insetsRect = immerseHelper.getInsetsPadding();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        params.topMargin = insetsRect.top;
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
