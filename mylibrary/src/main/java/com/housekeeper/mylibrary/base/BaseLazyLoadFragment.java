package com.housekeeper.mylibrary.base;

import android.view.View;

/**
 * Description: 懒加载基类
 *
 * @author created by Chenqiang
 * Date: 2019-6-17
 */
public abstract class BaseLazyLoadFragment extends BaseFragment {
    /**
     * 是否第一次加载
     */
    protected boolean mIsFirstLoad;

    @Override
    public void initView(View view) {
        mIsFirstLoad = true;
    }

    @Override
    public void initData() {
        super.initData();
        if (getUserVisibleHint()) {
            mIsFirstLoad = false;
            loadData();
        }
    }

    /**
     * 懒加载
     */
    protected abstract void loadData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mIsFirstLoad && isVisibleToUser) {
            mIsFirstLoad = false;
            loadData();
        }
    }
}
