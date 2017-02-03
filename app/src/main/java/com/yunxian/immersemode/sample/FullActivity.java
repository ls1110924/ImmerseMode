package com.yunxian.immersemode.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.yunxian.immerse.ImmerseConfiguration;
import com.yunxian.immerse.ImmerseHelper;
import com.yunxian.immerse.enumeration.ImmerseConfigType;
import com.yunxian.immerse.enumeration.StatusBarImmerseType;
import com.yunxian.immersemode.sample.widget.ExScrollView;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/27 下午8:56
 */
public class FullActivity extends AppCompatActivity {

    private CommonCallbackListener mCommonListener = new CommonCallbackListener();

    private Toolbar mToolbar;
    private ImageView mImgHeader;
    private ImmerseHelper mImmerseHelper;

    private int mRed;
    private int mGreen;
    private int mBlue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRed = 0xFF;
        mGreen = 0x40;
        mBlue = 0x81;

        setContentView(R.layout.activity_full);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);

        mImgHeader = (ImageView) findViewById(R.id.img_header);

        int navigationBarColor;

        ImmerseConfiguration.Builder builder = new ImmerseConfiguration.Builder();

        Bundle bundle = getIntent().getExtras();
        String paras = bundle.getString("Mode");
        if ("NSB_NNB".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.NORMAL).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.NORMAL).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(false);
            navigationBarColor = ContextCompat.getColor(this, R.color.colorAccent);
        } else if ("TLSB_NNB_FC".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(true);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(true);
            navigationBarColor = ContextCompat.getColor(this, R.color.colorAccent);
        } else if ("TLSB_TLNB_FC".equals(paras)) {
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInKK(true);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInL(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInL(true);
            navigationBarColor = Color.TRANSPARENT;
        } else if ("TPSB_NNB_FC".equals(paras)) {
            // Kitkat不支持全透状态栏+普通导航栏，故而降级到半透状态栏+普通导航栏
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(true);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSPARENT).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(true);
            navigationBarColor = ContextCompat.getColor(this, R.color.colorAccent);
        } else if ("TPSB_TLNB_FC".equals(paras)) {
            // Kitkat不支持全透状态栏+半透导航栏，故而降级到半透状态栏+半透导航栏
            builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT).setNavigationBarModeInKK(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInKK(true);
            builder.setStatusBarModeInL(ImmerseConfigType.TRANSPARENT).setNavigationBarModeInL(ImmerseConfigType.TRANSLUCENT)
                    .setFullScreenInL(true);
            navigationBarColor = Color.TRANSPARENT;
        } else {
            // 补偿逻辑
            builder.setStatusBarModeInKK(ImmerseConfigType.NORMAL).setNavigationBarModeInKK(ImmerseConfigType.NORMAL)
                    .setFullScreenInKK(false);
            builder.setStatusBarModeInL(ImmerseConfigType.NORMAL).setNavigationBarModeInL(ImmerseConfigType.NORMAL)
                    .setFullScreenInL(false);
            navigationBarColor = ContextCompat.getColor(this, R.color.colorAccent);
        }

        mImmerseHelper = new ImmerseHelper(this, builder.build());
        mImmerseHelper.setStatusColor(Color.TRANSPARENT);
        mImmerseHelper.setNavigationColor(navigationBarColor);

        ExScrollView scrollView = (ExScrollView) findViewById(R.id.scroll_view);
        scrollView.setOnScrollChangeListener(mCommonListener);
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

    private int imgHeadHeight = 0;

    private class CommonCallbackListener implements ExScrollView.OnScrollChangeListener {
        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            if (imgHeadHeight == 0) {
                imgHeadHeight = mImgHeader.getHeight();
            }
            if (imgHeadHeight == 0) {
                return;
            }
            if (t < 0) {
                t = 0;
            }
            if (t > imgHeadHeight) {
                t = imgHeadHeight;
            }
            float alphaF = (float) t / imgHeadHeight * 255;
            int color = Color.argb((int) alphaF, mRed, mGreen, mBlue);
            mImmerseHelper.setStatusColor(color);
            mToolbar.setBackgroundColor(color);
        }
    }

}
