package com.transport.chemicalplatform.activity;

import com.housekeeper.mylibrary.base.BaseActivity;
import com.housekeeper.mylibrary.interf.OnBottomTabReselectListener;
import com.transport.chemicalplatform.R;
import com.transport.chemicalplatform.fragment.NavigationFragment;
import com.transport.chemicalplatform.widget.NavigationButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Description: 主页
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class MainActivity extends BaseActivity implements
        NavigationFragment.OnNavigationReselectListener {

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavigationFragment ft_bottom =
                (NavigationFragment) supportFragmentManager.findFragmentById(R.id.fg_navigation);
        ft_bottom.setup(supportFragmentManager, R.id.fl_content, this);
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {
        Fragment fragment = navigationButton.getFragment();
        if (fragment != null
                && fragment instanceof OnBottomTabReselectListener) {
            OnBottomTabReselectListener listener = (OnBottomTabReselectListener) fragment;
            listener.onTabReselect();
        }
    }
}
