package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gongyunhaoyyy.wustweschool.Adapter.ViewPagerAdapter;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerAboutUs;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerCourse;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerLibrary;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerMain;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerNews;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private List<String> list_title;
    private TabLayout tab_gyh;
    private int[] tabIcons = {
            R.drawable.selector_tab_main,
            R.drawable.selector_tab_library,
            R.drawable.selector_tab_course,
            R.drawable.selector_tab_news,
            R.drawable.selector_tab_us
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        init();
    }

    private void init() {
        tab_gyh=(TabLayout)findViewById( R.id.tabs );
        //按名称加载tab名字列表.
        list_title = new ArrayList<>();
        list_title.add("首页");
        list_title.add("图书馆");
        list_title.add("课表");
        list_title.add("新闻");
        list_title.add("我的");
        //设置TabLayout的模式
        tab_gyh.setTabMode(TabLayout.MODE_FIXED);
        //List<view>放在适配器ViewPagerAdapter中
        List<Fragment> list_fragment=new ArrayList<>();
        list_fragment.add(new PagerMain());
        list_fragment.add(new PagerLibrary());
        list_fragment.add(new PagerCourse());
        list_fragment.add(new PagerNews());
        list_fragment.add(new PagerAboutUs());

        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list_fragment, list_title);
        //获取ViewPage,设置适配器;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(vpAdapter);
        //让刚打开app时，页面显示在第0个pager.
        viewPager.setCurrentItem(0);
        //预加载2个pager（除当前pager外）
        viewPager.setOffscreenPageLimit( 2 );
        //将TabLayout和ViewPager关联起来.
        tab_gyh.setupWithViewPager(viewPager);
        setupTabIcons();
    }
    private void setupTabIcons() {
        tab_gyh.getTabAt(0).setCustomView(getTabView(0));
        tab_gyh.getTabAt(1).setCustomView(getTabView(1));
        tab_gyh.getTabAt(2).setCustomView(getTabView(2));
        tab_gyh.getTabAt(3).setCustomView(getTabView(3));
        tab_gyh.getTabAt(4).setCustomView(getTabView(4));
    }
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_tab);
        txt_title.setText(list_title.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_tab);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }
}
