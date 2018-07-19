package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.card;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.ui.DrawView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class card_login_activity extends BaseActivity {

    private EditText editTextUserName;
    private EditText editTextPassWord;
    private Button buttonLogin;
    private String userName;
    private String passWord;
    private boolean isLoginCard;
    private Connection connection;
    private Connection.Response response;
    private static final int RESULT = 1;
    private AlertDialog dialog;
    private  SharedPreferences.Editor editor;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case RESULT:

                    if (msg.obj.equals("http://card.wust.edu.cn/Default.aspx")){
                        dialog.dismiss();
                        Toast.makeText(card_login_activity.this,"登录失败:(",Toast.LENGTH_SHORT).show();
                    }
                    if (msg.obj.equals("http://card.wust.edu.cn/Cardholder/Cardholder.aspx")) {
                        dialog.dismiss();
                        Toast.makeText(card_login_activity.this, "登陆成功:)", Toast.LENGTH_SHORT).show();
                        editor.putString("ASP.NET_SessionId",response.cookie("ASP.NET_SessionId"));
                        Log.d("ASP.NET_SessionId",response.cookie("ASP.NET_SessionId"));
                        editor.putBoolean("isLoginCard",true);
                        editor.putString("userName",userName);
                        editor.putString("passWord",passWord);
                        editor.apply();
                        finish();
                    }

                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViews();
        drawBackGround();
        dialog=loadingDialog( "正在登陆卡务中心...",false );
        SharedPreferences sharedPreferences = getSharedPreferences("Card",MODE_PRIVATE);
        isLoginCard = sharedPreferences.getBoolean("isLoginCard",false);
        if (isLoginCard == true){
            userName = sharedPreferences.getString("userName","");
            passWord = sharedPreferences.getString("passWord","");
            editTextUserName.setText(userName);
            editTextPassWord.setText(passWord);
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try{

                        connection = Jsoup.connect("http://card.wust.edu.cn/");
                        connection.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0");
                        response = connection.execute();
                        Document document = response.parse();
                        String VIEWSTATE = document.getElementById("__VIEWSTATE").attr("value");
                        String EVENTVALIDATION = document.getElementById("__EVENTVALIDATION").attr("value");
                        Element element3 = document.getElementById("UserLogin_ImgFirst");
                        char UserLogin_ImgFirst = element3.attr("src").charAt(7);
                        Element element4 = document.getElementById("UserLogin_imgSecond");
                        char UserLogin_imgSecond = element4.attr("src").charAt(7);
                        Element element5 = document.getElementById("UserLogin_imgThird");
                        char UserLogin_imgThird = element5.attr("src").charAt(7);
                        Element element6 = document.getElementById("UserLogin_imgFour");
                        char UserLogin_imgFour = element6.attr("src").charAt(7);
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(UserLogin_ImgFirst);
                        stringBuffer.append(UserLogin_imgSecond);
                        stringBuffer.append(UserLogin_imgThird);
                        stringBuffer.append(UserLogin_imgFour);
                        Map<String,String> datas = new HashMap<String, String>();
                        datas.put("__LASTFOCUS", "");
                        datas.put("__VIEWSTATE", VIEWSTATE);
                        datas.put("__EVENTTARGET", "");
                        datas.put("__EVENTARGUMENT", "");
                        datas.put("__EVENTVALIDATION", EVENTVALIDATION);
                        datas.put("UserLogin:txtUser", userName);
                        datas.put("UserLogin:txtPwd", passWord);
                        datas.put("UserLogin:ddlPerson", "卡户");
                        datas.put("UserLogin:txtSure", stringBuffer.toString());
                        datas.put("UserLogin:ImageButton1.x", "0");
                        datas.put("UserLogin:ImageButton1.y", "0");
                        Connection connection1 = Jsoup.connect("http://card.wust.edu.cn/Default.aspx");
                        connection1.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0");
                        Connection.Response response1 = connection1
                                .data(datas)
                                .cookies(response.cookies())
                                .method(Connection.Method.POST)
                                .execute();
                        Document document1 = Jsoup
                                .connect("http://card.wust.edu.cn/Cardholder/AccInfo.aspx")
                                .cookies(response.cookies())
                                .get();
                        String imageUrl =
                                "http://card.wust.edu.cn/Cardholder/"+ document1
                                        .getElementById("Table16")
                                        .select("tr")
                                        .select("td")
                                        .get(4)
                                        .select("img")
                                        .get(0)
                                        .attr("src");
                        editor.putString("userImageUrl",imageUrl);
                        Message message = new Message();
                        message.what = RESULT;
                        message.obj = response1.url().toString();
                        handler.sendMessage(message);



                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        editor = getSharedPreferences("Card",MODE_PRIVATE).edit();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = editTextUserName.getText().toString();
                passWord = editTextPassWord.getText().toString();
                if (userName.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(card_login_activity.this,"输入错误",Toast.LENGTH_SHORT).show();
                }else {
                    dialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try{

                                connection = Jsoup.connect("http://card.wust.edu.cn/");
                                connection.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0");
                                response = connection.execute();
                                Document document = response.parse();
                                String VIEWSTATE = document.getElementById("__VIEWSTATE").attr("value");
                                String EVENTVALIDATION = document.getElementById("__EVENTVALIDATION").attr("value");
                                Element element3 = document.getElementById("UserLogin_ImgFirst");
                                char UserLogin_ImgFirst = element3.attr("src").charAt(7);
                                Element element4 = document.getElementById("UserLogin_imgSecond");
                                char UserLogin_imgSecond = element4.attr("src").charAt(7);
                                Element element5 = document.getElementById("UserLogin_imgThird");
                                char UserLogin_imgThird = element5.attr("src").charAt(7);
                                Element element6 = document.getElementById("UserLogin_imgFour");
                                char UserLogin_imgFour = element6.attr("src").charAt(7);
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append(UserLogin_ImgFirst);
                                stringBuffer.append(UserLogin_imgSecond);
                                stringBuffer.append(UserLogin_imgThird);
                                stringBuffer.append(UserLogin_imgFour);
                                Map<String,String> datas = new HashMap<String, String>();
                                datas.put("__LASTFOCUS", "");
                                datas.put("__VIEWSTATE", VIEWSTATE);
                                datas.put("__EVENTTARGET", "");
                                datas.put("__EVENTARGUMENT", "");
                                datas.put("__EVENTVALIDATION", EVENTVALIDATION);
                                datas.put("UserLogin:txtUser", userName);
                                datas.put("UserLogin:txtPwd", passWord);
                                datas.put("UserLogin:ddlPerson", "卡户");
                                datas.put("UserLogin:txtSure", stringBuffer.toString());
                                datas.put("UserLogin:ImageButton1.x", "0");
                                datas.put("UserLogin:ImageButton1.y", "0");
                                Connection connection1 = Jsoup.connect("http://card.wust.edu.cn/Default.aspx");
                                connection1.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0");
                                Connection.Response response1 = connection1
                                        .data(datas)
                                        .cookies(response.cookies())
                                        .method(Connection.Method.POST)
                                        .execute();
                                Document document1 = Jsoup
                                        .connect("http://card.wust.edu.cn/Cardholder/AccInfo.aspx")
                                        .cookies(response.cookies())
                                        .get();
                                String imageUrl =
                                        "http://card.wust.edu.cn/Cardholder/"+ document1
                                                .getElementById("Table16")
                                                .select("tr")
                                                .select("td")
                                                .get(4)
                                                .select("img")
                                                .get(0)
                                                .attr("src");
                                editor.putString("userImageUrl",imageUrl);
                                Message message = new Message();
                                message.what = RESULT;
                                message.obj = response1.url().toString();
                                handler.sendMessage(message);



                            }catch (IOException e){
                                e.printStackTrace();
                            }

                        }
                    }).start();

                }



            }
        });



    }

    public void setContentView(){ setContentView(R.layout.activity_card_login); }

    public void initViews(){
        editTextUserName = (EditText)findViewById(R.id.et_username);
        editTextPassWord = (EditText)findViewById(R.id.et_password);
        buttonLogin = (Button)findViewById(R.id.img_login);
    }

    public void initListeners(){}

    public void initData(){}


    private void drawBackGround(){
        FrameLayout ll=(FrameLayout) findViewById(R.id.login_frame);
        final DrawView view=new DrawView(card_login_activity.this);
        view.setMinimumHeight(100);
        view.setMinimumWidth(400);
        //通知view组件重绘
        view.invalidate();
        ll.addView(view);
    }
}
