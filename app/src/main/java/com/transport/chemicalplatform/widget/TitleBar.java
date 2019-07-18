package com.transport.chemicalplatform.widget;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.transport.chemicalplatform.R;

/**
 * Description: 标题栏
 * Creator: Chenqiang
 * Date: 2017/2/14
 */

public class TitleBar extends FrameLayout {


    private ImageView mIvLeft;
    /**
     * 中间标题
     */
    private TextView mTvTitle;
    private TextView mTvRight;
    private ImageView mIvClose;

    public TitleBar(Context context) {
        super(context);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_title_bar, this, true);
        mIvLeft = findViewById(R.id.iv_left);
        mTvTitle = findViewById(R.id.tv_moment_des);
        mTvRight = findViewById(R.id.tv_right);
        mIvClose = findViewById(R.id.iv_close);
    }

    public void setCloseShow() {
        mIvClose.setVisibility(VISIBLE);
    }

    public void setCloseClickListener(OnClickListener listener) {
        mIvClose.setOnClickListener(listener);
    }

    public void setLeftBackground(@DrawableRes int id) {
        mIvLeft.setImageResource(id);
    }

    public void setLeftPadding(int left, int top, int right, int bottom) {
        mIvLeft.setPadding(left, top, right, bottom);
    }

    public void setRightBackground(@DrawableRes int id) {
        mTvRight.setBackgroundResource(id);
    }

    public void setRightText(@StringRes int id) {
        mTvRight.setText(getResources().getText(id));
    }

    public void setRightTextSize(float size) {
        mTvRight.setTextSize(size);
    }

    public void setRightVisibility(int id) {
        mTvRight.setVisibility(id);
    }

    public void setTitle(@StringRes int id) {
        mTvTitle.setText(getResources().getText(id));
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTitleSize(float size) {
        mTvTitle.setTextSize(size);
    }

    public void setTitleBackground(@DrawableRes int id) {
        mTvTitle.setBackgroundResource(id);
    }

    public void setLeftClickListener(OnClickListener listener) {
        mIvLeft.setOnClickListener(listener);
    }

    public void setLeftEnable(boolean isEnable) {
        mIvLeft.setEnabled(isEnable);
    }

    public void setRightClickListener(OnClickListener listener) {
        mTvRight.setOnClickListener(listener);
    }

    public void setRightEnable(boolean isEnable) {
        mTvRight.setEnabled(isEnable);
    }

    public void setLeftHide() {
        mIvLeft.setVisibility(GONE);
    }

    public void setLeftShow() {
        mIvLeft.setVisibility(VISIBLE);
    }

}
