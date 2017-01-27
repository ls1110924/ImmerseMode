package com.yunxian.immersemode.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yunxian.immerse.ImmerseHelper;
import com.yunxian.immerse.enumeration.StatusBarImmerseType;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/27 下午8:56
 */
public class TranslucentFullActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_translucent_full);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        ImmerseHelper immerseHelper = new ImmerseHelper(this, StatusBarImmerseType.TRANSLUCENT,
                StatusBarImmerseType.TRANSLUCENT, true);
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

}
