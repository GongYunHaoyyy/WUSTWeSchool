package com.gongyunhaoyyy.wustweschool.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.adapter.AbsGridAdapter;
import com.gongyunhaoyyy.wustweschool.base.BaseFragment;
import com.gongyunhaoyyy.wustweschool.bean.Course;
import com.gongyunhaoyyy.wustweschool.bean.CoursebeanforGson;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.ui.courseSetPopwindow;
import com.gongyunhaoyyy.wustweschool.bean.Coursebean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GongYunHao on 2017/10/10.
 */

public class PagerCourse extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener {
    private RelativeLayout titleRl;
    private courseSetPopwindow mCoursePop;
    private View mLayoutPopView;//悬浮窗的布局
    private PopupWindow mPopupWindow;
    private Button set_yes;
    private TextView set_xq, set_zs,week;
    private Context mContext;
    private GridView detailCource;
    private ArrayAdapter<String> mAdapter;
    private String[][][] contents;
    private AbsGridAdapter absGridAdapter;
    private List<Coursebean> course_list = new ArrayList<>( );
    private ImageView set;
    private AlertDialog dialog;
    private int currentZc,i,j;
    private String xh, coursestr, xq;

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.pager_course, container, false );
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor zceditor = mContext.getSharedPreferences( "zhouci", MODE_PRIVATE ).edit( );
        detailCource =view.findViewById(R.id.courceDetail);
        week=view.findViewById( R.id.switchWeek );
        titleRl= (RelativeLayout) view.findViewById(R.id.title_rl);
        set=view.findViewById( R.id.course_setting );
        xh=getUserData()[0];
        List<Course> coud= DataSupport.findAll( Course.class );
//        if (coud.size()<1){
//            DeleteCourse();
//            dialog=loadingDialog( "正在获取课表...",true );
//            dialog.show();
//            linkInternetForCourse( xh,getDateForXq() );
//            ParseCourse();
//            fillStringArray(0);
//            absGridAdapter = new AbsGridAdapter(mContext);
//            absGridAdapter.setContent(contents, 6, 7);
//            detailCource.setAdapter( absGridAdapter );
//        }else {
//            ParseCourse();
//            fillStringArray(0);
//            absGridAdapter = new AbsGridAdapter(mContext);
//            absGridAdapter.setContent(contents, 6, 7);
//            detailCource.setAdapter( absGridAdapter );
//        }

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        set.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                mLayoutPopView = LayoutInflater.from(getActivity()).inflate(R.layout
                        .pop_course_setting, null);
                mCoursePop = new courseSetPopwindow(view.findViewById(R.id.pager_course), getActivity(), mLayoutPopView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                mCoursePop.setOnPopupWindowListener( new courseSetPopwindow.PopupWindowListener( ) {
                    @Override
                    public void initView() {
                        set_yes=(Button)mLayoutPopView.findViewById( R.id.course_set_yes );
                        set_xq=(TextView)mLayoutPopView.findViewById( R.id.course_xq );
                        set_zs=(TextView)mLayoutPopView.findViewById( R.id.course_zs );
                        set_xq.setText( getDateForXq() );
                        set_yes.setOnClickListener( PagerCourse.this );
                        set_xq.setOnClickListener( PagerCourse.this );
                        set_zs.setOnClickListener( PagerCourse.this );
                    }
                } );

                zceditor.putInt( "huoquzhouci",10 );
                zceditor.apply();

                mCoursePop.showView();
                Animation scaleAanimation = AnimationUtils.loadAnimation(getActivity(),R.anim.popu_up_in);
                mLayoutPopView.startAnimation(scaleAanimation);
                mCoursePop.setBackgroundAlpha(1.0f);

            }
        } );
        return view;
    }

    /**
     * 准备数据
     */
    public void ParseCourse(){
        for (int l=0;l<course_list.size();l++){
            //若为空，则返回null
            course_list.get( l ).setJsmc1( getjsmc(course_list.get( l ),1) );
            course_list.get( l ).setJsmc2( getjsmc(course_list.get( l ),2 ) );
            course_list.get( l ).setJsmc3( getjsmc(course_list.get( l ),3 ) );

            //若周次为-1，表示空;
            //若开始周次和结束周次相同且不为-1，表示只有一周;
            //若开始周次和结束周次不同，表示有多周;
            course_list.get( l ).setKkzc1s( getkkzc( course_list.get( l ),1,1 ) );
            course_list.get( l ).setKkzc1e( getkkzc( course_list.get( l ),1,2 ) );
            course_list.get( l ).setKkzc2s( getkkzc( course_list.get( l ),2,1 ) );
            course_list.get( l ).setKkzc2e( getkkzc( course_list.get( l ),2,2 ) );
            course_list.get( l ).setKkzc3s( getkkzc( course_list.get( l ),3,1 ) );
            course_list.get( l ).setKkzc3e( getkkzc( course_list.get( l ),3,2 ) );
            //开课星期，同上
            course_list.get( l ).setKcxq1( getkcxq( course_list.get( l ),1) );
            course_list.get( l ).setKcxq2( getkcxq( course_list.get( l ),2) );
            course_list.get( l ).setKcxq3( getkcxq( course_list.get( l ),3) );
            //开课节次，返回0123456。5表示早上连着上，6表示下午连着上
            course_list.get( l ).setKcjc1( getkcjc( course_list.get( l ),1) );
            course_list.get( l ).setKcjc2( getkcjc( course_list.get( l ),2) );
            course_list.get( l ).setKcjc3( getkcjc( course_list.get( l ),3) );

        }
    }

    private int getkcxq(Coursebean c,int a){
        if (c.getKcsj()==null){
            return -1;
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getKcsj().length()-1;i++) {
            String getstr=c.getKcsj().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (x==0){
            return Integer.parseInt( c.getKcsj().substring( 0,1 ) );
        }else if (x==1){
            String[] sj=c.getKcsj().split( "," );
            String sjdetail=sj[a-1];
            return Integer.parseInt( sjdetail.substring( 0,1 ) );
        }else {
            String[] sj=c.getKcsj().split( "," );
            String sjdetail=sj[a-1];
            return Integer.parseInt( sjdetail.substring( 0,1 ) );
        }
    }

    private int getkcjc(Coursebean c,int a){
        if (c.getKcsj()==null){
            return -1;
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getKcsj().length()-1;i++) {
            String getstr=c.getKcsj().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (a-x>=2){
            return -1;
        }
        if (x==0){
            String sjjc=c.getKcsj().substring( 1,c.getKcsj().length() );
            if (Objects.equals( sjjc, "0102" )){
                return 0;
            }else if (Objects.equals( sjjc, "0304" )){
                return 1;
            }else if (Objects.equals( sjjc, "0506" )){
                return 2;
            }else if (Objects.equals( sjjc, "0708" )){
                return 3;
            }else if (Objects.equals( sjjc, "0910" )){
                return 4;
            }else if (Objects.equals( sjjc, "01020304" )){
                return 5;
            }else if (Objects.equals( sjjc, "05060708" )){
                return 6;
            }
        }else if (x==1){
            String[] sj=c.getKcsj().split( "," );
            String sjdetail=sj[a-1];
            String sjjc=sjdetail.substring( 1,c.getKcsj().length() );
            if (Objects.equals( sjjc, "0102" )){
                return 0;
            }else if (Objects.equals( sjjc, "0304" )){
                return 1;
            }else if (Objects.equals( sjjc, "0506" )){
                return 2;
            }else if (Objects.equals( sjjc, "0708" )){
                return 3;
            }else if (Objects.equals( sjjc, "0910" )){
                return 4;
            }else if (Objects.equals( sjjc, "01020304" )){
                return 5;
            }else if (Objects.equals( sjjc, "05060708" )){
                return 6;
            }
        }else{
            String[] sj=c.getKcsj().split( "," );
            String sjdetail=sj[a-1];
            String sjjc=sjdetail.substring( 1,c.getKcsj().length() );
            if (Objects.equals( sjjc, "0102" )){
                return 0;
            }else if (Objects.equals( sjjc, "0304" )){
                return 1;
            }else if (Objects.equals( sjjc, "0506" )){
                return 2;
            }else if (Objects.equals( sjjc, "0708" )){
                return 3;
            }else if (Objects.equals( sjjc, "0910" )){
                return 4;
            }else if (Objects.equals( sjjc, "01020304" )||Objects.equals( sjjc, "010203" )||Objects.equals( sjjc, "020304" )){
                return 5;
            }else if (Objects.equals( sjjc, "05060708" )||Objects.equals( sjjc, "060708" )||Objects.equals( sjjc, "050607" )){
                return 6;
            }
        }
        return -1;
    }

    private String getjsmc(Coursebean c,int a){
        if (c.getJsmc()==null) {
            return null;
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getJsmc().length()-1;i++) {
            String getstr=c.getJsmc().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (x==0){
            //            String reg = "[\\u4e00-\\u9fa5]+";
            //全为汉字
            return c.getJsmc();
        }else {
            String[] sj=c.getJsmc().split( "," );
            return sj[a-1];
        }
    }

    /**
     *
     * @param c
     * @param a 获取周次的第第a个部分
     * @param b b为1表示开始周次，b为2表示结束周次
     * @return
     */
    private int getkkzc(Coursebean c,int a,int b){
        if (c.getKkzc()==null){
            return -1;
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getKkzc().length()-1;i++) {
            String getstr=c.getKkzc().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (a==1){
            if (x == 0) {
                if (c.getKkzc( ).length( ) == 1 || c.getKkzc( ).length( ) == 2) {
                    return Integer.parseInt( c.getKkzc( ) );
                }
                String[] shj = c.getKkzc( ).split( "-" );
                return Integer.parseInt( shj[b - 1] );
            } else if (x == 1) {
                String[] sj = c.getKkzc( ).split( "," );
                //开课周次只有一周的直接返回
                if (sj[0].length( ) == 1 || sj[0].length( ) == 2) {
                    return Integer.parseInt( sj[0] );
                }
                //开课周次多周的情况
                String[] sjdetail = sj[0].split( "-" );
                return Integer.parseInt( sjdetail[b - 1] );
            } else {
                String[] sj = c.getKkzc( ).split( "," );
                //开课周次只有一周的直接返回
                if (sj[0].length( ) == 1 || sj[0].length( ) == 2) {
                    return Integer.parseInt( sj[0] );
                }
                //开课周次多周的情况
                String[] sjdetail = sj[0].split( "-" );
                return Integer.parseInt( sjdetail[b - 1] );
            }
        }else if (a==2){
            if (x==0){
                return -1;
            }else if (x==1){
                String[] sj=c.getKkzc().split( "," );
                //开课周次只有一周的直接返回
                if (sj[1].length()==1||sj[1].length()==2){
                    return Integer.parseInt( sj[1] );
                }
                //开课周次多周的情况
                String[] sjdetail=sj[1].split( "-" );
                return Integer.parseInt( sjdetail[b-1] );
            }else {
                String[] sj=c.getKkzc().split( "," );
                //开课周次只有一周的直接返回
                if (sj[1].length()==1||sj[1].length()==2){
                    return Integer.parseInt( sj[1] );
                }
                //开课周次多周的情况
                String[] sjdetail=sj[1].split( "-" );
                return Integer.parseInt( sjdetail[b-1] );
            }
        }else {
            if (x==0){
                return -1;
            }else if (x==1){
                return -1;
            }else {
                String[] sj=c.getKkzc().split( "," );
                //开课周次只有一周的直接返回
                if (sj[2].length()==1||sj[2].length()==2){
                    return Integer.parseInt( sj[2] );
                }
                //开课周次多周的情况
                String[] sjdetail=sj[2].split( "-" );
                return Integer.parseInt( sjdetail[b-1] );
            }
        }
    }

    public void fillStringArray(int myZCrecevive) {
        int myZC;
        if (myZCrecevive!=0){
            myZC=myZCrecevive;
        }else {
            SharedPreferences zzcc=mContext.getSharedPreferences( "zhouci", MODE_PRIVATE );
            myZC=zzcc.getInt( "huoquzhouci",1 );
        }
        contents = new String[6][7][2];
        for (i=0;i<6;i++){
            for (j=0;j<7;j++){
                contents[i][j][0]="";
            }
        }

        for (int s=0;s<course_list.size();s++){
            int kkzc1s=course_list.get( s ).getKkzc1s();
            int kkzc1e=course_list.get( s ).getKkzc1e();
            int kkzc2s=course_list.get( s ).getKkzc2s();
            int kkzc2e=course_list.get( s ).getKkzc2e();
            int kkzc3s=course_list.get( s ).getKkzc3s();
            int kkzc3e=course_list.get( s ).getKkzc3e();
            int kcxq1=course_list.get( s ).getKcxq1();
            int kcxq2=course_list.get( s ).getKcxq2();
            int kcxq3=course_list.get( s ).getKcxq3();
            int kcjc1=course_list.get( s ).getKcjc1();
            int kcjc2=course_list.get( s ).getKcjc2();
            int kcjc3=course_list.get( s ).getKcjc3();

            for (int n=0;n<course_list.get( s ).getP_jsmc().length;n++){
                if (course_list.get( s ).getKkzc()==null){
                    continue;
                }
                if (kcjc1==-1){
                    continue;
                }
                if (kcjc2==-1){
                    if (n!=0){
                        continue;
                    }
                    if (kkzc1s<=myZC&&kkzc1e>=myZC){
                        contents[kcjc1][kcxq1-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getJsmc1();
                        contents[kcjc1][kcxq1-1][1]= String.valueOf( course_list.get( s ).getId() );
                        continue;
                    }
                }
                if (kcjc3==-1){
                    if (n!=0&&n!=1){
                        continue;
                    }
                    if (kkzc2s<=myZC&&kkzc2e>=myZC){
                        contents[kcjc2][kcxq2-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getJsmc2();
                        contents[kcjc2][kcxq2-1][1]= String.valueOf( course_list.get( s ).getId() );
                    }
                }else {
                    if (n!=0&&n!=1){
                        if (kkzc3s<=myZC&&kkzc3e>=myZC){
                            contents[kcjc3][kcxq3-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getJsmc3();
                            contents[kcjc3][kcxq3-1][1]= String.valueOf( course_list.get( s ).getId() );
                        }
                    }
                }
            }
        }

//        //用s遍历course_list
//        for (int s=0;s<course_list.size();s++){
//            int[] kkzc_start = new int[10];
//            int[] kkzc_end = new int[10];
//            //用k遍历第s个course_list的粗略周次
//            if (course_list.get( s ).getP_kkzc_c()==null||course_list.get( s ).getP_jsmc()==null){
//                continue;
//            }else {
//                for (int k=0;k<course_list.get( s ).getP_kkzc_c().length;k++){
//                    String[] p_kkzc_c2=course_list.get( s ).getP_kkzc_c();
//                    //解析开课周次，在parseCourse()中已经粗略用","分割了，现在再次按"-"分割
//                    //长度大于等于3说明有"-"，要分割
//                    if (p_kkzc_c2[k].length()>=3){
//                        kkzc_start[k]=Integer.parseInt( p_kkzc_c2[k].split( "-" )[0] );
//                        kkzc_end[k]=Integer.parseInt( p_kkzc_c2[k].split( "-" )[1] );
//                    }else {
//                        kkzc_start[k]=Integer.parseInt( p_kkzc_c2[k] );
//                        kkzc_end[k]=Integer.parseInt( p_kkzc_c2[k] );
//                    }
                    //用教室个数与时间周次对应
//                    for (int n=0;n<course_list.get( s ).getP_jsmc().length;n++){
//                        if (course_list.get( s ).getP_kcjc().length<=course_list.get( s ).getP_jsmc().length){
//                            continue;
//                        }
//                        int[] p_kcjc_d=getkcjc( course_list.get( s ).getP_kcjc() );
////                        Log.d( "Course----------->", " "+course_list.get( s ).getP_kcjc()[n]);
//                        if (course_list.get( s ).getKkzc()==null){
//                            continue;
//                        }
//
//                        Log.d( "Course----------->", " "+p_kcjc_d[n]);
//
//                        if (p_kcjc_d[0]==0){
//                            continue;
//                        }
//
//
//                        //开课节次,开课星期
//                        if (kkzc_start[n]<=myZC&&kkzc_end[n]>=myZC){
//                            //用1，2，3，4,5表示第几大节,6,7分别表示12节和34节连着上
//                            if (p_kcjc_d[n]>5){
//                                if (p_kcjc_d[n]==6){
//                                    contents[1][course_list.get( s ).getP_kcxq()[n]-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getP_jsmc()[n];
//                                    contents[2][course_list.get( s ).getP_kcxq()[n]-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getP_jsmc()[n];
//                                    contents[p_kcjc_d[n]%6][course_list.get( s ).getP_kcxq()[n]-1][1]= String.valueOf( course_list.get( s ).getId() );
//                                }else {
//                                    contents[3][course_list.get( s ).getP_kcxq()[n]-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getP_jsmc()[n];
//                                    contents[4][course_list.get( s ).getP_kcxq()[n]-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getP_jsmc()[n];
//                                    contents[p_kcjc_d[n]%6][course_list.get( s ).getP_kcxq()[n]-1][1]= String.valueOf( course_list.get( s ).getId() );
//                                }
//                            }else {
//                                contents[p_kcjc_d[n]%6][course_list.get( s ).getP_kcxq()[n]-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getP_jsmc()[n];
//                                contents[p_kcjc_d[n]%6][course_list.get( s ).getP_kcxq()[n]-1][1]= String.valueOf( course_list.get( s ).getId() );
//                            }
//                        }
//
//
//
//                    }

//                }
//            }

//        }

    }

//    private int[] getkcjc(String[] kcjc){
//        int[] mykcjc = new int[10];
//        if (kcjc[0]==null){
//            return new int[]{0};
//        }else {
//            for (int h=0;h<kcjc.length;h++){
//                if (kcjc[h].equals( "0102" )){
//                    mykcjc[h]=1;
//                }else if (kcjc[h].equals( "0304" )){
//                    mykcjc[h]=2;
//                }else if (kcjc[h].equals( "0506" )){
//                    mykcjc[h]=3;
//                }else if (kcjc[h].equals( "0708" )){
//                    mykcjc[h]=4;
//                }else if (kcjc[h].equals( "0910" )){
//                    mykcjc[h]=5;
//                }else if (kcjc[h].equals( "01020304" )){
//                    mykcjc[h]=6;
//                }else if (kcjc[h].equals( "05060708" )){
//                    mykcjc[h]=7;
//                }else {
//                    mykcjc[h]=0;
//                }
//            }
//        }
//        return mykcjc;
//    }

//    public List<Coursebean> parseCourse(){
//        List<Coursebean> myCoursebean=new ArrayList<>(  );
//        List<Course> myCourse=DataSupport.findAll( Course.class );
//        for (Course coursebean:myCourse){
//            myCoursebean.add( new Coursebean(coursebean.getId(),coursebean.getDwmc(),coursebean.getJsmc(),
//                    coursebean.getKcxzmc(),coursebean.getKcsj(),coursebean.getKtmc(),coursebean.getKcsxm(),
//                    coursebean.getJsxm(),coursebean.getXkjd(),coursebean.getZxs(),coursebean.getKkzc(),
//                    coursebean.getKcmc(),coursebean.getXf() ) );
//        }
//
//        for (int js=0;js<myCoursebean.size();js++){
//            String[] p_kkzc_cu,p_jsmc = new String[0];
//            int[] p_kcxq = new int[10];
//            String[] p_kcxq_cu = new String[10];
//            String[] p_kcjc_cu = new String[10];
//            if (myCoursebean.get( js ).getKkzc()==null){
//            }else {
//                p_kkzc_cu=myCoursebean.get( js ).getKkzc().split( "," );//开课周次粗略
//                if (myCoursebean.get( js ).getJsmc()!=null){
//                    p_jsmc=myCoursebean.get( js ).getJsmc().split( "," );//教室完成^^
//                }
//                if (myCoursebean.get( js ).getKcsj()!=null){
//                    p_kcxq_cu=myCoursebean.get( js ).getKcsj().split( "," );//粗略星期^^
//                }
//                for (int k=0;k<p_kcxq_cu.length;k++){
//                    if (p_kcxq_cu[k]!=null){
//                        p_kcxq[k]= Integer.parseInt( p_kcxq_cu[k].substring( 0,1 ) );//星期完成^^
//                        p_kcjc_cu[k]= p_kcxq_cu[k].substring( 1,p_kcxq_cu[k].length());//0304^^
//                    }
//                }
//                if (myCoursebean.get( js ).getJsmc()==null||myCoursebean.get( js ).getKkzc()==null||myCoursebean.get( js ).getKcsj()==null){
//                    continue;
//                }else {
//                    myCoursebean.get( js ).setP_kkzc_c( p_kkzc_cu );
//                    myCoursebean.get( js ).setP_kcxq( p_kcxq );
//                    myCoursebean.get( js ).setP_kcjc( p_kcjc_cu );
//                    myCoursebean.get( js ).setP_jsmc( p_jsmc );
//                }
//            }
//        }
//        return myCoursebean;
//    }

//    /**
//     *
//     * @param xh 学号
//     * @param xq 学期
//     * 联网获取课表并保存到数据库
//     */
//    public void linkInternetForCourse(final String xh, final String xq){
//        final String[] coursestr = new String[1];
//        new Thread( new Runnable( ) {
//            @Override
//            public void run() {
//                try {
//                    Ksoap2 ksoap2=new Ksoap2();
//                    coursestr[0] = ksoap2.getCourseInfo( xh,xq );
//                    Gson gson=new Gson();
//                    List<CoursebeanforGson> slist=gson.fromJson( coursestr[0],new TypeToken<List<CoursebeanforGson>>(){}.getType());
//                    for (int i=0;i<slist.size();i++){
//                        Course course=new Course();
//                        //dwmc,jsmc,kcxzmc,kcsj,ktmc,kcsxm,jsxm,xkjd,zxs,kkzc,kcmc,xf
//                        course.setDwmc( slist.get( i ).getDwmc() );
//                        course.setJsmc( slist.get( i ).getJsmc() );
//                        course.setKcxzmc( slist.get( i ).getKcxzmc() );
//                        course.setKcsj( slist.get( i ).getKcsj() );
//                        course.setKtmc( slist.get( i ).getKtmc() );
//                        course.setKcsxm( slist.get( i ).getKcsxm() );
//                        course.setJsxm( slist.get( i ).getJsxm() );
//                        course.setXkjd( slist.get( i ).getXkjd() );
//                        course.setZxs( slist.get( i ).getZxs() );
//                        course.setKkzc( slist.get( i ).getKkzc() );
//                        course.setKcmc( slist.get( i ).getKcmc() );
//                        course.setXf( slist.get( i ).getXf() );
//                        course.save();
//                    }
//                    getActivity().runOnUiThread( new Runnable( ) {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();
//                        }
//                    } );
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        } ).start();
//    }

    public void DeleteCourse(){
        DataSupport.deleteAll( Course.class );
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pw_layout, null);
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth( AppBarLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(AppBarLayout.LayoutParams.WRAP_CONTENT);
        mAdapter=new ArrayAdapter<>(getActivity(),R.layout.pw_item_layout, R.id.tv,getData());
        ListView lv=(ListView)contentView.findViewById(R.id.lv_zc);
        TextView tv2=(TextView)contentView.findViewById(R.id.tv);
        tv2.setVisibility(View.GONE);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener( (AdapterView.OnItemClickListener) PagerCourse.this );
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(titleRl, Gravity.TOP| Gravity.CENTER, 0, 160);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPopupWindow.dismiss();
        if(position!=currentZc) {
            week.setText("第" + (position + 1) + "周(非本周)");
        }else {
            week.setText("第" + (position + 1) + "周");
        }
        fillStringArray(position+1);
        absGridAdapter = new AbsGridAdapter(mContext);
        absGridAdapter.setContent(contents, 6, 7);
        detailCource.setAdapter( absGridAdapter );
    }

    private List<String> getData() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            if(i!=currentZc) {
                list.add("第" + (i + 1) + "周");
            }else {
                list.add("第" + (i + 1) + "周(本周)");
            }
        }
        return list;
    }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.course_set_yes:
                        mCoursePop.dismiss();
                        break;
                    case R.id.course_zs:
                        mCoursePop.dismiss();
                        break;
                }

            }
    }
