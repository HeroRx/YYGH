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
import com.gzucm.yygh.bean.Appointment;
import com.gzucm.yygh.bean.Department;
import com.gzucm.yygh.bean.Doctor;
import com.gzucm.yygh.bean.Patient;
import com.gzucm.yygh.utils.DateUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.gzucm.yygh.R.id.nowcount;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class AppointActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView aq;
    private TextView an;
    private TextView as;
    private TextView ah;
    private TextView ad;
    private TextView ak;
    private TextView ac;
    private TextView aa;
    private TextView a;
    private TextView name;
    private Button yes;

    private String type;
    private int queue;
    private String create;
    private String myname;
    private boolean mysex;
    private String dname;
    private String droom;
    private String ddepartment;
    private String myallergy;
    Handler mhanler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);
        initView();

        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        Patient patient = (Patient)intent.getSerializableExtra("patient");
        Doctor doctor = (Doctor)intent.getSerializableExtra("doctor");

        myname = patient.getP_name();
        mysex = patient.isP_sex();
        myallergy = patient.getP_allergy();
        dname = doctor.getD_name();
        Department department = doctor.getD_dcode();
        ddepartment = department.getD_departmentname();
        droom = doctor.getD_room();
        //只能显示上午的挂号排队号情况,应该加两个字段证明
        // 你是挂上午的号还是挂下午的
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        boolean ism= pref.getBoolean("pam",true);
        if(ism){
            int nowcount = doctor.getD_nowmcount() + 1;
            final String now = String.format("%02d",nowcount);
            a.setText(now);
            name.setText("排队号(上午)：");
        }else{
            int nowcount = doctor.getD_acount() + 1;
            final String now = String.format("%02d",nowcount);
            a.setText(now);
            name.setText("排队号(下午)：");
        }


        an.setText(myname);
        if(mysex){
            as.setText("男");
        }else{
            as.setText("女");
        }

        ad.setText(dname);
        ak.setText(ddepartment);
        ac.setText(droom);
        aa.setText(myallergy);

        BmobQuery<Appointment> query = new BmobQuery<Appointment>();
        query.addWhereEqualTo("a_name",patient);
        //执行查询方法
        query.findObjects(new FindListener<Appointment>() {
            @Override
            public void done(List<Appointment> object, BmobException e) {
                if(e==null){

                    for (Appointment appointment : object) {

                        type = appointment.getA_type();
                        queue = appointment.getA_queueno();
                        final String myqueue = String.format("%04d",queue);
                        create = appointment.getCreatedAt();
                        final String date = DateUtil.getWantDate(create, "yyyyMMdd");

                        mhanler.post(new Runnable() {
                        @Override
                        public void run() {
                            aq.setText(myqueue+date);
                            ah.setText(type);
                        }
                    });
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void initView() {

        aq = (TextView)findViewById(R.id.a_queue);
        an = (TextView)findViewById(R.id.a_name);
        as = (TextView)findViewById(R.id.a_sex);
        ah = (TextView)findViewById(R.id.a_haobie);
        ad = (TextView)findViewById(R.id.a_yisheng);
        ak = (TextView)findViewById(R.id.a_keshi);
        ac = (TextView)findViewById(R.id.a_keshihao);
        aa = (TextView)findViewById(R.id.a_guomin);
        a = (TextView)findViewById(nowcount);
        name = (TextView)findViewById(R.id.nowcountname);
        yes = (Button)findViewById(R.id.yes);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (R.id.yes){
            case R.id.yes:
                Intent intent = new Intent(AppointActivity.this,FinishActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void clear(){
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        if(pref!=null){
            pref.edit().clear().commit();
        }
    }


}
