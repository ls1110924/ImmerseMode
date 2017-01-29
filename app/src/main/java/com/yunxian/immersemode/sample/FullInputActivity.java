package com.yunxian.immersemode.sample;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.yunxian.immerse.ImmerseHelper;
import com.yunxian.immerse.enumeration.StatusBarImmerseType;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/1/29 14:07
 */
public class FullInputActivity extends AppCompatActivity {

    private FrameLayout mToolbarPanel;

    private final CommonCallbackListener mCommonListener = new CommonCallbackListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_input);

        mToolbarPanel = (FrameLayout) findViewById(R.id.toolbar_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        ImmerseHelper immerseHelper = new ImmerseHelper(this, StatusBarImmerseType.TRANSLUCENT,
                StatusBarImmerseType.TRANSPARENT, true, true);
        immerseHelper.setStatusColor(Color.TRANSPARENT);
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

    private class CommonCallbackListener implements ConsumeInsetsFrameLayout.OnInsetsChangeListener {

        @Override
        public void onInsetsChanged(Rect insets) {

            mToolbarPanel.setPadding(0, insets.top, 0, 0);

        }
    }
}
