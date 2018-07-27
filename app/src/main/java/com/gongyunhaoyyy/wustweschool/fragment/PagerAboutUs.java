package com.gongyunhaoyyy.wustweschool.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.gongyunhaoyyy.wustweschool.bean.Course;
import com.gongyunhaoyyy.wustweschool.util.SharePreferenceHelper;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.card.card_login_activity;
import com.gongyunhaoyyy.wustweschool.activity.LoginActivity;
import com.gongyunhaoyyy.wustweschool.base.BaseFragment;
import com.gongyunhaoyyy.wustweschool.R;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.litepal.crud.DataSupport;
import java.io.IOException;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerAboutUs extends BaseFragment implements View.OnClickListener{

    public static final int SHOWIMAGE = 1;
    private Button exit;
    private Document document;
    private TextView textViewRemainingBalance;
    private ImageView imageViewUser;
    private Context mContextUs;
    private ImageView ima_xb,ima_what;
    private TextView stuname,stusf,stuid;
    private String[] uddt_detail;
    private boolean isboy;
    private boolean isLoginCard;
    private String userImageUrl;
    private String cookie;
    private LinearLayout linearLayoutBalance;
    private LinearLayout linearLayoutSpendingHistory;
    private LinearLayout linearLayoutLostCard;
    private byte[] data;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOWIMAGE:
                    byte[] temp = (byte[])msg.obj;
                    Glide.with(getActivity()).load(temp).apply( RequestOptions.bitmapTransform( new CenterCrop() ) ).into(imageViewUser);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContextUs = context;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.pager_aboutus, container, false);
        SharedPreferences ud = mContextUs.getSharedPreferences( "userdata", MODE_PRIVATE );
        String uddt = ud.getString( "getuserdata","" );
        uddt_detail = uddt.split( "," );
        init(view);
        setOnClickListener();
        inflateData();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Card",MODE_PRIVATE);
        isLoginCard = sharedPreferences.getBoolean("isLoginCard",false);
        cookie = sharedPreferences.getString("ASP.NET_SessionId","");
        userImageUrl = sharedPreferences.getString("userImageUrl","");
        if (isLoginCard == true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{

                        Connection.Response response = Jsoup.connect(userImageUrl)
                                .ignoreContentType(true)
                                .cookie("ASP.NET_SessionId",cookie)
                                .execute();
                        if (response.url().toString().equals("http://card.wust.edu.cn/error.aspx?t=1")){
                            startIntent( card_login_activity.class );
                        }else {
                            data = response.bodyAsBytes();
                            document = Jsoup.connect("http://card.wust.edu.cn/Cardholder/AccBalance.aspx")
                                    .ignoreContentType(true)
                                    .cookie("ASP.NET_SessionId",cookie)
                                    .get();
                            Message message = new Message();
                            message.what = SHOWIMAGE;
                            message.obj = data;
                            handler.sendMessage(message);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Element element = document.getElementById("lblOne0");
                                    textViewRemainingBalance.setText(element.text());
                                }
                            });
                        }



                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor nameeditor = mContextUs.getSharedPreferences( SharePreferenceHelper.USER_DATE, MODE_PRIVATE ).edit( );
        switch (v.getId()){
            case R.id.exit_button:
                nameeditor.putString( "getuserdata","" );
                nameeditor.apply();
                startIntent( LoginActivity.class );
//                finish方法无效，fragment依赖与Activity，不能在fragment中关闭活动
//                getActivity().finish();
//                模拟按返回键，销毁当前Activity，有效
                DataSupport.deleteAll( Course.class );
                getActivity().onBackPressed();
                break;
            case R.id.balance:
                if (isLoginCard == false){
                    startIntent( card_login_activity.class );
                }
                break;
            case R.id.spending_history:
                if (isLoginCard == false){
                    startIntent( card_login_activity.class );
                }
                break;
            case R.id.lost_card:
                if (isLoginCard == false){
                    startIntent( card_login_activity.class );
                }
                break;
            default:
                break;
        }
    }

    private void setOnClickListener(){
            exit.setOnClickListener( this );
            linearLayoutBalance.setOnClickListener(this);
            linearLayoutSpendingHistory.setOnClickListener(this);
            linearLayoutLostCard.setOnClickListener(this);
    }

    /**
     * 0学号 1密码 2姓名 3学生照片 4性别 5身份(普通本科)
     */
    private void inflateData(){
        stuname.setText( uddt_detail[2] );
        stusf.setText( uddt_detail[5] );
        stuid.setText( "ID："+uddt_detail[0] );
        if (isboy){
            ima_xb.setImageResource( R.drawable.icon_boy );
        }else {
            ima_xb.setImageResource( R.drawable.icon_girl );
        }
    }

    private void init(View view){
        isboy = uddt_detail[4].equals( "男" );
        imageViewUser = view.findViewById(R.id.user_image);
        linearLayoutBalance = view.findViewById(R.id.balance);
        linearLayoutSpendingHistory = view.findViewById(R.id.spending_history);
        linearLayoutLostCard = view.findViewById(R.id.lost_card);
        exit=(Button)view.findViewById( R.id.exit_button );
        stuname=(TextView)view.findViewById( R.id.student_name );
        stusf=(TextView)view.findViewById( R.id.student_sf );
        stuid=(TextView)view.findViewById( R.id.student_id );
        ima_xb=(ImageView)view.findViewById( R.id.image_xingbie );
        ima_what=(ImageView)view.findViewById( R.id.image_user_what );
        textViewRemainingBalance = (TextView)view.findViewById(R.id.remaining_balance);
    }
}
