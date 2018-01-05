package cn.likole.test2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import static cn.likole.test2.JwxtTool.jwxt;

/**
 * Created by likole on 1/5/18.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private EditText et_captcha;
    private ImageView iv_captcha;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        setCaptcha();
        iv_captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCaptcha();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindView(){
        et_username= (EditText) findViewById(R.id.et_username);
        et_password= (EditText) findViewById(R.id.et_password);
        et_captcha= (EditText) findViewById(R.id.et_captcha);
        iv_captcha= (ImageView) findViewById(R.id.iv_captcha);
        btn_login= (Button) findViewById(R.id.btn_login);
    }

    private void login() throws IOException {
        final String username=et_username.getText().toString();
        final String password=et_password.getText().toString();
        final String captcha=et_captcha.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(jwxt.login(username,password,captcha)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setCaptcha();
                                Toast.makeText(getApplicationContext(),"用户名,密码或验证码错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setCaptcha(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap= BitmapFactory.decodeStream(jwxt.getCaptureStream());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_captcha.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
