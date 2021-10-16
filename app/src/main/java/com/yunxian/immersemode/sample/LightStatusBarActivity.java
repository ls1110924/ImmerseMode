package com.yunxian.immersemode.sample;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import com.yunxian.immerse.ImmerseConfiguration;
import com.yunxian.immerse.ImmerseHelper;

/**
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2021/10/16 13:24
 */
public class LightStatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(getResources().getDimension(R.dimen.immerse_toolbar_elevation));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(Color.WHITE);

        ImmerseConfiguration.Builder builder = new ImmerseConfiguration.Builder()
                .setStatusBarModeInKK(ImmerseConfiguration.TRANSLUCENT)
                .setFullScreenInKK(true)
                .setStatusBarModeInL(ImmerseConfiguration.TRANSPARENT)
                .setFullScreenInL(true)
                .setLightStatusBar(true)
                .setCoverCompatMask(true);
        ImmerseHelper immerseHelper = new ImmerseHelper(this, builder.build());
        immerseHelper.setStatusColor(Color.WHITE);

        Rect insetsRect = immerseHelper.getInsetsPadding();
        if (insetsRect.top > 0) {
            ViewGroup.LayoutParams params = toolbar.getLayoutParams();
            params.height += insetsRect.top;
            toolbar.setLayoutParams(params);
            toolbar.setPadding(0, insetsRect.top, 0, 0);
        }
        toolbar.setTitleTextColor(Color.BLACK);
        Drawable naviDrawable = toolbar.getNavigationIcon();
        if (naviDrawable != null) {
            Drawable wrapDrawable = DrawableCompat.wrap(naviDrawable);
            DrawableCompat.setTint(wrapDrawable, Color.BLACK);
        }

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
