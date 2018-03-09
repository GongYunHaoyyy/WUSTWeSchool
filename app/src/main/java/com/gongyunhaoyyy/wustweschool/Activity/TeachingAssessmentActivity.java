package com.gongyunhaoyyy.wustweschool.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.Base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;

public class TeachingAssessmentActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_teaching_assessment);
        final TextView textView=findViewById( R.id.hhhhhhasdf );

        new Thread( new Runnable( ) {
            @Override
            public void run() {
                final String aosdh= Ksoap2.getCourseInfo( "201613137110","2017-2018-2" );
                runOnUiThread( new Runnable( ) {
                    @Override
                    public void run() {
                        textView.setText( aosdh );
                    }
                } );
            }
        } ).start();

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }


}
