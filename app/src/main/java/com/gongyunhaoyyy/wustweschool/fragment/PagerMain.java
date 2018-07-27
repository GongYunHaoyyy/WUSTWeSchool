package com.gongyunhaoyyy.wustweschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.activity.ChooseLessonActivity;
import com.gongyunhaoyyy.wustweschool.activity.ScoreActivity;
import com.gongyunhaoyyy.wustweschool.activity.TeachingAssessmentActivity;
import com.gongyunhaoyyy.wustweschool.base.BaseFragment;
import com.gongyunhaoyyy.wustweschool.util.ThreadPoolManager;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library.library_login_activity;
import com.gongyunhaoyyy.wustweschool.R;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerMain extends BaseFragment implements View.OnClickListener{

    private Context mContext;
    private Boolean isLoginLibrary;
    private String cookie;
    private TextView textViewFirstBook;
    private TextView textViewSecondBook;
    private TextView textViewThirdBook;
    private View bookFirstDot;
    private View bookSecondDot;
    private View bookThirdDot;
    private View bookFirstLine;
    private View bookSecondLine;
    private View bookThirdLine;
    private View bookFirstRow;
    private View bookSecondRow;
    private View bookThirdRow;
    private Elements elements;
    private Document document;
    private LinearLayout scoreLinear,chooseLesson,teachingAssessment;

    //界面图片相关
    public static String IMAGE_CACHE_PATH = "imageloader/Cache"; // 图片缓存路径
    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合
    private List<View> dots; // 图片标题正文的那些点
    private List<View> dotList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initViews(View view){
        textViewFirstBook = view.findViewById(R.id.book_first_name);
        textViewSecondBook = view.findViewById(R.id.book_second_name);
        textViewThirdBook = view.findViewById(R.id.book_third_name);
        bookFirstDot = view.findViewById(R.id.book_first_dot);
        bookSecondDot = view.findViewById(R.id.book_second_dot);
        bookThirdDot = view.findViewById(R.id.book_third_dot);
        bookFirstLine = view.findViewById(R.id.book_first_line);
        bookSecondLine = view.findViewById(R.id.book_second_line);
        bookThirdLine = view.findViewById(R.id.book_third_line);
        bookFirstRow = view.findViewById(R.id.book_first_row);
        bookSecondRow = view.findViewById(R.id.book_second_row);
        bookThirdRow = view.findViewById(R.id.book_third_row);
        scoreLinear = view.findViewById(R.id.ll_score);
        chooseLesson = view.findViewById(R.id.ll_choose_lesson);
        teachingAssessment = view.findViewById(R.id.ll_teaching_assessment);
    }

    private void initClickListener(){
        scoreLinear.setOnClickListener(this);
        chooseLesson.setOnClickListener(this);
        teachingAssessment.setOnClickListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cookie",Context.MODE_PRIVATE);
        cookie = sharedPreferences.getString("PHPSESSID","");
        isLoginLibrary = sharedPreferences.getBoolean("isLoginLibrary",false);
        if (isLoginLibrary){
            ThreadPoolManager.getInstance().addExecuteTask( new Runnable( ) {
                @Override
                public void run() {
                    try{
                        Connection.Response response = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/reader/book_hist.php")
                                .cookie("PHPSESSID",cookie)
                                .execute();
                        Log.d("library_url",response.url().toString());
                        if (response.url().toString().equals("http://opac.lib.wust.edu.cn:8080/reader/login.php")){
                            Log.d("PagerMain","跳");
                            Intent intent = new Intent(getActivity(),library_login_activity.class);
                            startActivity(intent);
                        }else{
                            document = response.parse();
                            elements = document
                                    .select("table.table_line")
                                    .select("tbody")
                                    .select("tr");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (elements.size() == 2){
                                        textViewFirstBook.setText(elements.get(1).select("td").select("a").text());
                                        textViewFirstBook.setVisibility(View.VISIBLE);
                                        bookFirstDot.setVisibility(View.VISIBLE);
                                        bookFirstLine.setVisibility(View.VISIBLE);
                                        bookFirstRow.setVisibility(View.VISIBLE);
                                        textViewSecondBook.setVisibility(View.GONE);
                                        bookSecondDot.setVisibility(View.GONE);
                                        bookSecondLine.setVisibility(View.GONE);
                                        bookSecondRow.setVisibility(View.GONE);
                                        textViewThirdBook.setVisibility(View.GONE);
                                        bookThirdDot.setVisibility(View.GONE);
                                        bookThirdLine.setVisibility(View.GONE);
                                        bookThirdRow.setVisibility(View.GONE);
                                    }else if(elements.size() == 3){
                                        textViewFirstBook.setText(elements.get(1).select("td").get(2).select("a").text());
                                        textViewFirstBook.setVisibility(View.VISIBLE);
                                        bookFirstDot.setVisibility(View.VISIBLE);
                                        bookFirstLine.setVisibility(View.VISIBLE);
                                        bookFirstRow.setVisibility(View.VISIBLE);
                                        textViewSecondBook.setText(elements.get(2).select("td").get(2).select("a").text());
                                        textViewSecondBook.setVisibility(View.VISIBLE);
                                        bookSecondDot.setVisibility(View.VISIBLE);
                                        bookSecondLine.setVisibility(View.VISIBLE);
                                        bookSecondRow.setVisibility(View.VISIBLE);
                                        textViewThirdBook.setVisibility(View.GONE);
                                        bookThirdDot.setVisibility(View.GONE);
                                        bookThirdLine.setVisibility(View.GONE);
                                        bookThirdRow.setVisibility(View.GONE);
                                    }else if(elements.size() == 4){
                                        textViewFirstBook.setText(elements.get(1).select("td").get(2).select("a").text());
                                        textViewFirstBook.setVisibility(View.VISIBLE);
                                        bookFirstDot.setVisibility(View.VISIBLE);
                                        bookFirstLine.setVisibility(View.VISIBLE);
                                        bookFirstRow.setVisibility(View.VISIBLE);
                                        textViewSecondBook.setText(elements.get(2).select("td").get(2).select("a").text());
                                        textViewSecondBook.setVisibility(View.VISIBLE);
                                        bookSecondDot.setVisibility(View.VISIBLE);
                                        bookSecondLine.setVisibility(View.VISIBLE);
                                        bookSecondRow.setVisibility(View.VISIBLE);
                                        textViewThirdBook.setText(elements.get(3).select("td").get(2).select("a").text());
                                        textViewThirdBook.setVisibility(View.VISIBLE);
                                        bookThirdDot.setVisibility(View.VISIBLE);
                                        bookThirdLine.setVisibility(View.VISIBLE);
                                        bookThirdRow.setVisibility(View.VISIBLE);
                                    }

                                }
                            });
                        }

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate( R.layout.pager_main, container, false);
        initViews(view);
        initClickListener();
        view.findViewById( R.id.more_thing ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

            }
        } );

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_score:
                startIntent( ScoreActivity.class );
                break;
            case R.id.ll_choose_lesson:
                startIntent( ChooseLessonActivity.class );
                break;
            case R.id.ll_teaching_assessment:
                startIntent( TeachingAssessmentActivity.class );
                break;
                default:
        }
    }
}
