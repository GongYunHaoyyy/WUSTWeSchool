package com.gongyunhaoyyy.wustweschool.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.gongyunhaoyyy.wustweschool.adapter.KYKCAdapter;
import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.KYkecheng;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ChooseLessonActivity2 extends BaseActivity {
    private String xklbmx,xkdetaildata;//选课列表明细
    private List<KYkecheng> mKYkechengmx=new ArrayList<>(  );
    private StaggeredGridLayoutManager mlayoutManager;
    private RecyclerView xklbmxrecycler;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        initData();
        setContentView( R.layout.activity_choose_lesson2 );
        initViews();
        dialog.show();
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    Thread.sleep( 500 );
                    Ksoap2 ksoap2=new Ksoap2();
                    xklbmx=ksoap2.getKxkc( xkdetaildata.split( "," )[0],xkdetaildata.split( "," )[1],xkdetaildata.split( "," )[2],null,null,null );
                    Gson gson=new Gson();
                    List<KYkecheng> xkjdlist=gson.fromJson( xklbmx,new TypeToken<List<KYkecheng>>(){}.getType());
                    mKYkechengmx.addAll( xkjdlist );
                    //回到主线程更新UI
                    runOnUiThread( new Runnable( ) {
                        @Override
                        public void run() {
                            KYKCAdapter myKYKCadapter=new KYKCAdapter( mKYkechengmx );
                            xklbmxrecycler.setLayoutManager( mlayoutManager );
                            xklbmxrecycler.setAdapter( myKYKCadapter );
                            dialog.dismiss();
                        }
                    } );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ).start();

    }

    @Override
    public void onClick(View v) {
        super.onClick( v );
    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {
        xklbmxrecycler=(RecyclerView)findViewById( R.id.recycler_xklbmx );
        dialog=loadingDialog( "拼命加载中...",false );
        mlayoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        SharedPreferences myxkdata=getSharedPreferences( "xzxkjd",MODE_PRIVATE );
        xkdetaildata=myxkdata.getString( "getxzxkjd","" );
        if (xkdetaildata.isEmpty()){
            showToast( "获取选课阶段信息出错!" );
            finish();
        }
    }
}
