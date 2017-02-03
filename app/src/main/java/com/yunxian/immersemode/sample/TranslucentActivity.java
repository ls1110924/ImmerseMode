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
 * @date 2017/1/27 17:27
 */
public class TranslucentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_translucent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        ImmerseConfiguration.Builder builder = new ImmerseConfiguration.Builder();
        builder.setStatusBarModeInKK(ImmerseConfigType.TRANSLUCENT);
        builder.setStatusBarModeInL(ImmerseConfigType.TRANSLUCENT);
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
