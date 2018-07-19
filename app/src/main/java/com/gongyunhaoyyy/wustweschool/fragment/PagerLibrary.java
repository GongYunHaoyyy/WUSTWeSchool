package com.gongyunhaoyyy.wustweschool.fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library.MyFragmentPagerAdapter;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library.Search_activity;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library.library_login_activity;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.card.card_login_activity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerLibrary extends Fragment implements View.OnClickListener{

    public static final int ISLOGINLIBRARY = 1;
    public static final int ISLOGINCARD = 2;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView imageViewUser;
    private boolean isCreate;
    private boolean isHasLaodOnce;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private Button search;
    private TextView textViewUserName;
    private TextView textViewStudentNumber;
    private String userName;
    private String studentNumber;
    private String userImageUrl;
    private LinearLayout linearLayoutDetail;
    private String cookie;
    private boolean isLoginLibrary;
    private boolean isLoginCard;
    private Document document;
    private String card_cookie;
    private byte[] imageByte;



    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case ISLOGINLIBRARY:
                    Map<String,String> temp = (Map<String, String>) msg.obj;
                    textViewUserName.setText(temp.get("userName"));
                    textViewStudentNumber.setText(temp.get("studentNumber").replaceAll("条码号","学号"));
                    break;
                case ISLOGINCARD:
                    byte[] tempByte = (byte[])msg.obj;
                    Glide.with(getActivity()).load(tempByte).apply( RequestOptions.bitmapTransform( new CenterCrop() ) ).into(imageViewUser);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        isCreate=true;

    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences preferences = getContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);
        cookie = preferences.getString("PHPSESSID","");
        isLoginLibrary = preferences.getBoolean("isLoginLibrary",false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Card",Context.MODE_PRIVATE);
        userImageUrl = sharedPreferences.getString("userImageUrl","");
        isLoginCard = sharedPreferences.getBoolean("isLoginCard",false);
        card_cookie = sharedPreferences.getString("ASP.NET_SessionId","");


        if (isLoginLibrary == true){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection.Response response = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/reader/redr_info.php")
                                .cookie("PHPSESSID", cookie)
                                .execute();
                        if (response.url().toString().equals("http://opac.lib.wust.edu.cn:8080/reader/login.php")){
                            Intent intent = new Intent(getActivity(),library_login_activity.class);
                            startActivity(intent);
                        }else{
                            document = response.parse();
                            Map<String,String> data = new HashMap<String, String>();
                            userName = document.getElementById("mylib_info")
                                    .select("tbody")
                                    .select("tr")
                                    .get(0)
                                    .select("td")
                                    .get(1)
                                    .text();
                            data.put("userName",userName);
                            studentNumber = document.getElementById("mylib_info")
                                    .select("tbody")
                                    .select("tr")
                                    .get(0)
                                    .select("td")
                                    .get(3)
                                    .text();
                            data.put("studentNumber",studentNumber);
                            Message message = new Message();
                            message.what=ISLOGINLIBRARY;
                            message.obj=data;
                            handler.sendMessage(message);
                        }


                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();


        }

        if (isLoginCard == true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Connection.Response response = Jsoup.connect(userImageUrl)
                                .ignoreContentType(true)
                                .cookie("ASP.NET_SessionId",card_cookie)
                                .execute();
                        if (response.url().toString().equals("http://card.wust.edu.cn/error.aspx?t=1")){
                            Intent intent = new Intent(getActivity(),card_login_activity.class);
                            startActivity(intent);
                        }else {
                            imageByte = response.bodyAsBytes();
                            Message message = new Message();
                            message.what = ISLOGINCARD;
                            message.obj = imageByte;
                            handler.sendMessage(message);
                        }


                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_detail:
                if (isLoginLibrary == false){
                    Intent intent = new Intent(getActivity().getApplicationContext(),library_login_activity.class);
                    startActivity(intent);
                }
                break;
            case R.id.search:
                Intent intent = new Intent(getActivity().getApplicationContext(),Search_activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private void setOnClickListener(){
        linearLayoutDetail.setOnClickListener(this);
        search.setOnClickListener(this);
    }


    @Override
    public void setUserVisibleHint(boolean isVisbleToUser){
        super.setUserVisibleHint(isVisbleToUser);

        load();
    }

    private void load() {
        if (isCreate && getUserVisibleHint() && !isHasLaodOnce){
            isCreate = false;
            isHasLaodOnce = true;
        }
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load();
    }

    private void initViews(View view){
        search = (Button) view.findViewById(R.id.search);
        textViewStudentNumber = (TextView) view.findViewById(R.id.student_number);
        textViewUserName = (TextView) view.findViewById(R.id.user_name);
        linearLayoutDetail = (LinearLayout) view.findViewById(R.id.btn_detail);
        imageViewUser = (ImageView) view.findViewById(R.id.imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_library, container, false);
        initViews(view);
        setOnClickListener();
        mViewPager= (ViewPager) view.findViewById(R.id.viewPager);
        //mViewPager.setOffscreenPageLimit(3);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        return view;
    }


}
