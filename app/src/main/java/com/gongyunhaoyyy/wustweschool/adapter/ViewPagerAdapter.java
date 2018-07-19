package com.gongyunhaoyyy.wustweschool.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by GongYunHao on 2017/10/9.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> list_fragment;                         //fragment列表

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
        super(manager);
        this.list_fragment = fragments;
        this.mTitles = titles;
    }
    //getCount()返回List<View>的size:
    @Override
    public int getCount() {
        return list_fragment.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get( position );
    }

    /**
     * 这个方法一定要注意了！！！！！！！！！！可把我坑惨了
     * 必须手动添加这个方法，否则下方的Tablayout的字不显示
     * 就算我在MainActivity的init方法中添加也没用哦
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get( position );
    }

}
