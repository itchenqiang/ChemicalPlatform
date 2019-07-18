package com.transport.chemicalplatform.fragment;

import android.view.View;

import com.housekeeper.mylibrary.base.BaseFragment;
import com.housekeeper.mylibrary.interf.OnBottomTabReselectListener;
import com.housekeeper.mylibrary.util.LogUtil;
import com.transport.chemicalplatform.R;

/**
 * Description: 更多
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class MoreFragment extends BaseFragment implements OnBottomTabReselectListener {

    @Override
    protected int getContentView() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initTitleBar(View view) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onTabReselect() {
        LogUtil.d("再次点击更多");
    }
}
