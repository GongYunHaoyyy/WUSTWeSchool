package com.gongyunhaoyyy.wustweschool.util;

import com.gongyunhaoyyy.wustweschool.bean.Course;
import com.gongyunhaoyyy.wustweschool.bean.Coursebean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

//
//  Creste by GongYunHao on 2018/3/6
// 
public class CourseAndScoreHelper {
    private String[] p_kkzc_cu,p_jsmc;
    private int[] p_kcxq;
    private String[] p_kcxq_cu;
    private String[] p_kcjc_cu;
    private int COURSE_INTERNET_D=3;

//    public List<Coursebean> parseCourse(){
//        List<Coursebean> mCoursebean=new ArrayList<>(  );
//        List<Course> myCourse=DataSupport.findAll( Course.class );
//        for (int j=0;j<myCourse.size();j++){
//            p_kkzc_cu=myCourse.get( j ).getKkzc().split( "," );//开课周次粗略
//            p_jsmc=myCourse.get( j ).getJsmc().split( "," );//教室完成^^
//            p_kcxq_cu=myCourse.get( j ).getKcsj().split( "," );//粗略星期^^
//            for (int k=0;k<p_kcxq_cu.length;k++){
//                p_kcxq[k]= Integer.parseInt( p_kcxq_cu[k].substring( 0,1 ) );//星期完成^^
//                p_kcjc_cu[k]= p_kcxq_cu[k].substring( 1,p_kcxq_cu[k].length());//0304^^
//            }
//            mCoursebean.add( new Coursebean(myCourse.get( j ).getId(),myCourse.get( j ).getDwmc(),myCourse.get( j ).getJsmc(),
//                    myCourse.get( j ).getKcxzmc(),myCourse.get( j ).getKcsj(),myCourse.get( j ).getKtmc(),myCourse.get( j ).getKcsxm(),
//                    myCourse.get( j ).getJsxm(),myCourse.get( j ).getXkjd(),myCourse.get( j ).getZxs(),myCourse.get( j ).getKkzc(),
//                    myCourse.get( j ).getKcmc(),myCourse.get( j ).getXf(),p_kkzc_cu,p_kcxq,p_kcjc_cu,p_jsmc ) );
//
//        }
//        return mCoursebean;
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
//                    List<Coursebean> slist=gson.fromJson( coursestr[0],new TypeToken<List<Coursebean>>(){}.getType());
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
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        } ).start();
//    }

    public void DeleteCourse(){
        DataSupport.deleteAll( Course.class );
    }

}
