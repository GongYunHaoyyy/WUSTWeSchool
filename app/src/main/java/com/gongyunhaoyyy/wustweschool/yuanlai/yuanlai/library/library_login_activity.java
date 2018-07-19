package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.ui.DrawView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class library_login_activity extends BaseActivity {

    private static final int CheckPic = 1;
    private static final int RESULT = 2;
    private ImageView imageView;
    private EditText editTextName;
    private EditText editTextCheckNum;
    private EditText editTextPassWord;
    private Button loginButton;
    private Map<String,String> cookies = null;
    private Connection.Response  response1 = null;
    private Connection connection1;
    private Connection.Response response;
    private Map<String,String> datas = new HashMap<String,String>();
    private String userName;
    private String passWord;
    private String checkNum;
    private boolean isLoginLibrary;
    private AlertDialog dialog;

     @SuppressLint("HandlerLeak")
     Handler handler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case CheckPic:
                    byte[] temp = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(temp,0,temp.length);
                    imageView.setImageBitmap(bitmap);
                    break;
                case RESULT:

                    if (msg.obj.equals("http://opac.lib.wust.edu.cn:8080/reader/redr_verify.php")){
                        dialog.dismiss();
                        Toast.makeText(library_login_activity.this,"登录失败:(",Toast.LENGTH_SHORT).show();

                    }
                    if (msg.obj.equals("http://opac.lib.wust.edu.cn:8080/reader/redr_info.php")){
                        dialog.dismiss();
                        Toast.makeText(library_login_activity.this, "登陆成功:)", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("cookie",MODE_PRIVATE).edit();
                        editor.putString("PHPSESSID",response.cookie("PHPSESSID"));
                        Log.d("PHPSESSID",response.cookie("PHPSESSID"));
                        editor.putBoolean("isLoginLibrary",true);
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
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews(){
        imageView = (ImageView)findViewById(R.id.check_num);
        editTextName = (EditText)findViewById(R.id.et_username);
        editTextPassWord = (EditText)findViewById(R.id.et_password);
        editTextCheckNum = (EditText)findViewById(R.id.et_check_num);
        loginButton = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_library_login );
    }


    private void drawBackGround(){
        FrameLayout ll=(FrameLayout) findViewById(R.id.login_frame);
        final DrawView view=new DrawView(library_login_activity.this);
        view.setMinimumHeight(100);
        view.setMinimumWidth(400);
        //通知view组件重绘
        view.invalidate();
        ll.addView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViews();
        drawBackGround();

        SharedPreferences sharedPreferences = getSharedPreferences("cookie",MODE_PRIVATE);
        isLoginLibrary = sharedPreferences.getBoolean("isLoginLibrary",false);
        if (isLoginLibrary){
            userName = sharedPreferences.getString("userName","");
            passWord = sharedPreferences.getString("passWord","");
            editTextName.setText(userName);
            editTextPassWord.setText(passWord);
        }

        dialog = loadingDialog( "正在登陆图书馆...",false );
        getCaptcha();
        loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userName = editTextName.getText().toString();
                            passWord = editTextPassWord.getText().toString();
                            checkNum = editTextCheckNum.getText().toString();
                            if (userName.isEmpty() || passWord.isEmpty() || checkNum.isEmpty()){
                                showToast("输入有误");
                            }else{
                                dialog.show();
                                datas.put("number",userName);
                                datas.put("passwd",passWord);
                                datas.put("captcha",checkNum);
                                datas.put("select","bar_no");
                                datas.put("returnUrl","");
                                connection1 = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/reader/redr_verify.php");
                                connection1.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try{
                                            response1 = connection1
                                                    .data(datas)
                                                    .cookies(cookies)
                                                    .method(Connection.Method.POST)
                                                    .execute();
                                            Message message = new Message();
                                            message.what = RESULT;
                                            message.obj = response1.url().toString();
                                            handler.sendMessage(message);
                                            if (response1.url().toString().equals("http://opac.lib.wust.edu.cn:8080/reader/redr_verify.php")){
                                                getCaptcha();
                                            }
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();


                            }
                        }
                    });



    }

    public void getCaptcha(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    response = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/reader/captcha.php")
                            .ignoreContentType(true)
                            .execute();
                    byte[] data = response.bodyAsBytes();
                    cookies = response.cookies();
                    Message message = new Message();
                    message.what = CheckPic;
                    message.obj = data;
                    handler.sendMessage(message);


                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



}
