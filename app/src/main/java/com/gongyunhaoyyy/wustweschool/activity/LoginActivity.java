package com.gongyunhaoyyy.wustweschool.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.ui.DrawView;
import com.gongyunhaoyyy.wustweschool.util.SharePreferenceHelper;
import com.gongyunhaoyyy.wustweschool.util.ThreadPoolManager;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    private EditText et_username,et_password;
    private Button login;
    private String login_result,user,pass,xm;
    private String[] reslut2;
    private AlertDialog dialog;
    private SharedPreferences.Editor nameEditor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        SharedPreferences ud=getSharedPreferences( SharePreferenceHelper.USER_DATE, MODE_PRIVATE );
        String uddt=ud.getString(SharePreferenceHelper.IS_LOGIN,"" );
        if (!uddt.isEmpty()){
            startActivity(MainActivity.newIntent(LoginActivity.this));
            finish();
        }else {
            setContentView( R.layout.activity_login );
            nameEditor = ud.edit( );
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            initViews();
            dialog=loadingDialog( "小园登陆中...",false );
            login.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    user=et_username.getText().toString();
                    pass=et_password.getText().toString();
                    if (user.length()!=12||pass.length()<4){
                        showToast( "输入有误" );
                    } else {
                        if (!isNetworkAvailable( LoginActivity.this )){
                            showToast( R.string.nointernet );
                        }else {
                            dialog.show();
                            ThreadPoolManager.getInstance().addExecuteTask( new Runnable( ) {
                                @Override
                                public void run() {
                                    try {
                                        //这样看起来比较流畅
                                        Thread.sleep( 600 );
                                        login_result= Ksoap2.getLoginInfo( user,pass );
                                        reslut2=login_result.split( "," );
                                        //回到主线程更新UI
                                        runOnUiThread( new Runnable( ) {
                                            @Override
                                            public void run() {
                                                if (reslut2[0].equals( "0" )){// 登录失败
                                                    showToast( reslut2[1] );
                                                    dialog.dismiss();
                                                }else if (reslut2[0].equals( "1" )){// 登录成功
                                                    parseJSONwithJSONObject(login_result.substring( 3,login_result.length()-1 ));
                                                    showToast( xm+"，欢迎你~" );
                                                    dialog.dismiss();
                                                    startIntent( MainActivity.class );
                                                    finish();
                                                }else {
                                                    showToast( R.string.nointernet );
                                                    dialog.dismiss();
                                                }
                                            }
                                        } );

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            } );
                        }
                    }
                }
            } );

        }
    }

    @Override
    public void setContentView() {}

    @Override
    public void initViews() {
        et_username = findViewById( R.id.et_username );
        et_password = findViewById( R.id.et_password );
        login= findViewById( R.id.img_login );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    public boolean isNetworkAvailable(AppCompatActivity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void parseJSONwithJSONObject(String jsonData){
        String parseData = null;
        try {
            JSONObject jsonObject=new JSONObject( jsonData );
            xm=jsonObject.getString( "xm" );
            String xszp=jsonObject.getString( "xszp" );
            String xb=jsonObject.getString( "xb" );
            String sf=jsonObject.getString( "sf" );
            nameEditor.putString(SharePreferenceHelper.STUDENT_NAME, xm);
            nameEditor.putString(SharePreferenceHelper.PICTURE, xszp);
            nameEditor.putString(SharePreferenceHelper.STUDENT_GENDER, xb);
            nameEditor.putString(SharePreferenceHelper.STUDENT_STATUS, sf);
            nameEditor.putString(SharePreferenceHelper.STUDENT_NUMBER, user);
            nameEditor.putString(SharePreferenceHelper.PASSWORD, pass);
            nameEditor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public Bitmap getBitmap(String path) throws IOException {
//
//        URL imgUrl = null;
//        Bitmap bitmap = null;
//        try {
//            imgUrl = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) imgUrl
//                    .openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }

}
