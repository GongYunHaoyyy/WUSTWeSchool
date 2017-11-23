package com.gongyunhaoyyy.wustweschool.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by GongYunHao on 2017/10/9.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] Titles = new String[]{"首页", "图书馆", "课表","新闻","我的"};
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
        return Titles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }


//    @Override
//    public Object instantiateItem(ViewGroup container, int position){
//        return super.instantiateItem(container, position);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object){
//        super.destroyItem(container, position, object);
//    }
}
