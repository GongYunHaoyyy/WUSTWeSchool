package com.gongyunhaoyyy.wustweschool.activity;

import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.gongyunhaoyyy.wustweschool.adapter.XKJDAdapter;
import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.Xkjieduan;
import com.gongyunhaoyyy.wustweschool.util.ThreadPoolManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ChooseLessonActivity extends BaseActivity{
    private AlertDialog dialog;
    private String strxkjd,xh;
    private List<Xkjieduan> myXKjd=new ArrayList<>();
    private RecyclerView rec_xkjd;
    StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        xh=getUserData()[0];
        layoutManager=new StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL );
        dialog.show();
        ThreadPoolManager.getInstance().addExecuteTask(runnable);
    }

    private Runnable runnable = new Runnable( ) {
        @Override
        public void run() {
            try {
                strxkjd=Ksoap2.getXkjd( xh );
                //回到主线程更新UI
                runOnUiThread( new Runnable( ) {
                    @Override
                    public void run() {
                        if (strxkjd.length()<15){
                            showToast( strxkjd );
                            dialog.dismiss();
                            finish();
                        }else {
                            Gson gson=new Gson();
                            List<Xkjieduan> xkjdlist=gson.fromJson( strxkjd,new TypeToken<List<Xkjieduan>>(){}.getType());
                            myXKjd.addAll( xkjdlist );
                            XKJDAdapter myXKJDadapter=new XKJDAdapter( myXKjd,xh,ChooseLessonActivity.this );
                            rec_xkjd.setLayoutManager( layoutManager );
                            rec_xkjd.setAdapter( myXKJDadapter );
                            dialog.dismiss();
                        }
                    }
                } );
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View v) {
        super.onClick( v );
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_choose_lesson );
    }

    @Override
    public void initViews() {
        rec_xkjd= (RecyclerView) findViewById( R.id.rec_choose_skjd );
        dialog=loadingDialog( "拼命加载中...",false );
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {

    }

}
