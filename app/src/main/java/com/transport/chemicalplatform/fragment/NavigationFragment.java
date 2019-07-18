package com.transport.chemicalplatform.fragment;

import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;

import com.housekeeper.mylibrary.base.BaseFragment;
import com.transport.chemicalplatform.R;
import com.transport.chemicalplatform.widget.BorderShape;
import com.transport.chemicalplatform.widget.NavigationButton;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Description: 底部导航栏
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class NavigationFragment extends BaseFragment implements View.OnClickListener {

    private NavigationButton mCurrentNavigationButton;
    private FragmentManager mFragmentManager;
    private int mContentId;
    private OnNavigationReselectListener mOnNavigationReselectListener;
    private NavigationButton mNbProduct;
    private NavigationButton mNbMy;
    private NavigationButton mNbMore;

    public interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_navagation;
    }

    @Override
    protected void initTitleBar(View view) {

    }

    @Override
    public void initView(View view) {
        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShape(new RectF(0, 1, 0, 0)));
        lineDrawable.getPaint().setColor(getResources().getColor(R.color.navigation_divider_color));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                new ColorDrawable(Color.WHITE),
                lineDrawable
        });
        view.setBackground(layerDrawable);
        mNbProduct = view.findViewById(R.id.nb_product);
        mNbMy = view.findViewById(R.id.nb_my);
        mNbMore = view.findViewById(R.id.nb_more);
    }

    @Override
    public void initData() {
        super.initData();
        mNbProduct.init(R.drawable.tab_icon_product,
                R.string.main_tab_name_product,
                ProductFragment.class);

        mNbMy.init(R.drawable.tab_icon_my,
                R.string.main_tab_name_my,
                MyFragment.class);

        mNbMore.init(R.drawable.tab_icon_more,
                R.string.main_tab_name_more,
                MoreFragment.class);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mNbProduct.setOnClickListener(this);
        mNbMy.setOnClickListener(this);
        mNbMore.setOnClickListener(this);
    }

    public void setup(FragmentManager fragmentManager, int contentId, OnNavigationReselectListener listener) {
        mFragmentManager = fragmentManager;
        mContentId = contentId;
        mOnNavigationReselectListener = listener;
        clearOldFragment();
        doSelect(mNbProduct);
    }

    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.isEmpty())
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view instanceof NavigationButton) {
            NavigationButton navigationButton = (NavigationButton) view;
            doSelect(navigationButton);
        }
    }

    private void doSelect(NavigationButton newNavigationButton) {
        NavigationButton oldNavigationButton = null;
        if (mCurrentNavigationButton != null) {
            oldNavigationButton = mCurrentNavigationButton;
            if (oldNavigationButton == newNavigationButton) {
                //重新刷新页面
                mOnNavigationReselectListener.onReselect(oldNavigationButton);
                return;
            }
            oldNavigationButton.setSelected(false);
        }
        newNavigationButton.setSelected(true);
        doTabChanged(oldNavigationButton, newNavigationButton);
        mCurrentNavigationButton = newNavigationButton;
    }

    private void doTabChanged(NavigationButton oldNavigationButton, NavigationButton newNavigationButton) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (oldNavigationButton != null) {
            if (oldNavigationButton.getFragment() != null) {
                fragmentTransaction.detach(oldNavigationButton.getFragment());
            }
        }
        if (newNavigationButton != null) {
            if (newNavigationButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext, newNavigationButton.getClx().getName(), null);
                fragmentTransaction.add(mContentId, fragment, newNavigationButton.getTag());
                newNavigationButton.setFragment(fragment);
            } else {
                fragmentTransaction.attach(newNavigationButton.getFragment());
            }
        }
        fragmentTransaction.commit();
    }
}
