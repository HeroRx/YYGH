package com.gzucm.yygh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gzucm.yygh.R;

import java.io.File;

import cn.bmob.v3.Bmob;

/**
 * 模拟系统管理员登录admin,密码为123456
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etUser;
    private EditText etPsw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "ea87f738cba8c5002153e245856b2c95");
        initView();
    }

    private void initView() {
        etUser = (EditText)findViewById(R.id.et);
        etPsw = (EditText)findViewById(R.id.et02);
        btnLogin = (Button)findViewById(R.id.btn);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                String account = etUser.getText().toString();
                String password = etPsw.getText().toString();
                // 如果账号是admin且密码是123456，就认为登录成功
                if(account == null){
                    Toast.makeText(LoginActivity.this, "管理员账号为空", Toast.LENGTH_SHORT).show();
                }else if(password == null){
                    Toast.makeText(LoginActivity.this, "管理员密码为空", Toast.LENGTH_SHORT).show();
                }else if (account.equals("admin") && password.equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, SelectActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或者密码不匹配,请重新输入", Toast.LENGTH_SHORT).show();
                }
                //删除文件
                File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","patient.xml");
                if(file.exists())
                {
                    file.delete();
                    Toast.makeText(LoginActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                }



        }

    }


}
