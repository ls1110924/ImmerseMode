package com.yunxian.immersemode.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.yunxian.immerse.ImmerseHelper;
import com.yunxian.immerse.enumeration.StatusBarImmerseType;
import com.yunxian.immersemode.sample.widget.ExScrollView;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/27 下午8:56
 */
public class TranslucentFullActivity extends AppCompatActivity {

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

        setContentView(R.layout.activity_translucent_full);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);

        mImgHeader = (ImageView) findViewById(R.id.img_header);

        mImmerseHelper = new ImmerseHelper(this, StatusBarImmerseType.TRANSLUCENT,
                StatusBarImmerseType.TRANSLUCENT, true);
        mImmerseHelper.setStatusColor(Color.TRANSPARENT);

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
