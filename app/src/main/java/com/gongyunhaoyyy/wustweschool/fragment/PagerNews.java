package com.gongyunhaoyyy.wustweschool.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news.NewsFragmentPagerAdapter;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerNews extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NewsFragmentPagerAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private  TabLayout.Tab four;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_news, container, false);

        mViewPager= (ViewPager) view.findViewById(R.id.news_viewpager_1);
        myFragmentPagerAdapter = new NewsFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) view.findViewById(R.id.news_tablayout_1);
        mTabLayout.setupWithViewPager(mViewPager);
        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);

        return view;
    }
}
