package com.gongyunhaoyyy.wustweschool.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.util.ThreadPoolManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class TeachingAssessmentActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_teaching_assessment);
        final TextView textView=findViewById( R.id.hhhhhhasdf );

        ThreadPoolManager.getInstance().addExecuteTask( new Runnable( ) {
            @Override
            public void run() {
                String aosdh = null;
                try {
//                    aosdh = Ksoap2.getCourseInfo( "201613137110","2018-2019-1" );
                    aosdh = Ksoap2.getTerm();
                } catch (IOException e) {
                    e.printStackTrace( );
                } catch (XmlPullParserException e) {
                    e.printStackTrace( );
                }
                final String finalAosdh = aosdh;
                runOnUiThread( new Runnable( ) {
                    @Override
                    public void run() {
                        textView.setText( finalAosdh );
                    }
                } );
            }
        } );
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
