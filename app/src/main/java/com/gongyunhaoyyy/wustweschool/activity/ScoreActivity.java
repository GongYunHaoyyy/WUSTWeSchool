package com.gongyunhaoyyy.wustweschool.activity;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.gongyunhaoyyy.wustweschool.adapter.ViewPagerAdapter;
import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.bean.Term;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.Score;
import com.gongyunhaoyyy.wustweschool.fragment.fragment_score_all;
import com.gongyunhaoyyy.wustweschool.fragment.fragment_score_now;
import com.gongyunhaoyyy.wustweschool.util.SharePreferenceHelper;
import com.gongyunhaoyyy.wustweschool.util.ThreadPoolManager;
import com.gongyunhaoyyy.wustweschool.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends BaseActivity {
    String xh,score;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter vpAdapter;
    private List<Score> mScorelist_all=new ArrayList<>();
    private List<Score> mScorelist_now=new ArrayList<>();
    private List<String> mTitles=new ArrayList<>();
    private List<Fragment> list_fragment=new ArrayList<>();
    private List<Term> mTerms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        SharedPreferences ud=getSharedPreferences( SharePreferenceHelper.USER_DATE, MODE_PRIVATE );
        xh=ud.getString(SharePreferenceHelper.STUDENT_NUMBER, "");
        mTitles.add( "全部成绩" );
        mTitles.add( "本学期成绩" );
        initViews();
        ToastUtil.loadingDialog("拼命加载中...",false);

        ThreadPoolManager.getInstance().addExecuteTask( new Runnable( ) {
            @Override
            public void run() {
                try {
                    score=Ksoap2.getScoreInfo( xh );
                    Gson gson=new Gson();
                    List<Score> slist=gson.fromJson( score,new TypeToken<List<Score>>(){}.getType());
                    mScorelist_all.addAll( slist );
                    for (int i=0;i<slist.size();i++){
                        if (slist.get( i ).getKkxq().equals( "2017-2018-2" )) {
                            mScorelist_now.add( slist.get( i ) );
                        }
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
                            ToastUtil.cancel();
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
