package com.gzucm.yygh.activity.select;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gzucm.yygh.R;
import com.gzucm.yygh.activity.SelectActivity;
import com.gzucm.yygh.bean.Department;
import com.gzucm.yygh.bean.Doctor;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class FinishActivity extends AppCompatActivity implements View.OnClickListener{

    Handler mhanler = new Handler();
    private TextView fn;
    private TextView ft;
    private TextView fd;
    private TextView fm;
    private TextView fa;
    private Button finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        initView();
        findDoctor();
    }

    private void initView() {
        fn = (TextView)findViewById(R.id.finish_name);
        ft = (TextView)findViewById(R.id.finish_titles);
        fd = (TextView)findViewById(R.id.finish_keshi);
        fm = (TextView)findViewById(R.id.finish_morning_hao);
        fa = (TextView)findViewById(R.id.finish_afternoon_hao);
        finish = (Button)findViewById(R.id.finish);
        finish.setOnClickListener(this);
    }


    public void findDoctor() {
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        String pdid= pref.getString("pdid","");

        BmobQuery<Doctor> query = new BmobQuery<Doctor>();
        query.include("d_dcode");
        //执行查询方法
        query.getObject(pdid,new QueryListener<Doctor>() {
            @Override
            public void done(Doctor doctor, BmobException e) {

                if (e == null) {

                    final String dname = doctor.getD_name();
                    final int dnm = doctor.getD_nowmcount();
                    final int dna = doctor.getD_nowacount();
                    final String dtitles = doctor.getD_titles();
                    Department department = doctor.getD_dcode();
                    final String ddepartment = department.getD_departmentname();
                    mhanler.post(new Runnable() {
                        @Override
                        public void run() {
                            fn.setText(dname);
                            ft.setText(dtitles);
                            fd.setText(ddepartment);
                            fa.setText(dna + "");
                            fm.setText(dnm + "");
                        }
                    });


                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
    public void clear(){
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        if(pref!=null){
            pref.edit().clear().commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish:
                clear();
                Intent intent = new Intent(FinishActivity.this,SelectActivity.class);
                startActivity(intent);
                break;
        }
    }
}
