package com.gongyunhaoyyy.wustweschool.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import com.gongyunhaoyyy.wustweschool.adapter.ViewPagerAdapter;
import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.score;
import com.gongyunhaoyyy.wustweschool.fragment.fragment_score_all;
import com.gongyunhaoyyy.wustweschool.fragment.fragment_score_now;
import com.gongyunhaoyyy.wustweschool.util.ThreadPoolManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends BaseActivity {
    String xh,score;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter vpAdapter;
    private List<score> mScorelist_all=new ArrayList<>();
    private List<score> mScorelist_now=new ArrayList<>();
    private List<String> mTitles=new ArrayList<>();
    private List<Fragment> list_fragment=new ArrayList<>();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        xh=getUserData()[0];
        mTitles.add( "全部成绩" );
        mTitles.add( "本学期成绩" );
        initViews();
        dialog=loadingDialog( "拼命加载中...",false );
        dialog.show();

        ThreadPoolManager.getInstance().addExecuteTask( new Runnable( ) {
            @Override
            public void run() {
                try {
                    score=Ksoap2.getScoreInfo( xh );
                    Gson gson=new Gson();
                    List<score> slist=gson.fromJson( score,new TypeToken<List<score>>(){}.getType());
                    mScorelist_all.addAll( slist );
                    for (int i=0;i<slist.size();i++){
                        if (slist.get( i ).getKkxq().equals( "2017-2018-2" ))
                            mScorelist_now.add( slist.get( i ) );
                    }
                    //回到主线程更新UI
                    runOnUiThread( new Runnable( ) {
                        @Override
                        public void run() {
                            list_fragment.add(new fragment_score_all(mScorelist_all));
                            list_fragment.add(new fragment_score_now(mScorelist_now));
                            vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list_fragment, mTitles);
                            mViewPager.setAdapter(vpAdapter);
                            mTabLayout.setupWithViewPager( mViewPager );
                            dialog.dismiss();
                        }
                    } );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } );
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_score );
    }

    @Override
    public void initViews() {
        mViewPager=findViewById( R.id.pager_score );
        mTabLayout=findViewById( R.id.tab_score );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

}
