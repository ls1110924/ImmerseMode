package com.yunxian.immersemode.sample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/27 下午10:01
 */
public class ExScrollView extends ScrollView {

    private OnScrollChangeListener mOnScrollChangeListener;

    public ExScrollView(Context context) {
        super(context);
    }

    public ExScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

}
