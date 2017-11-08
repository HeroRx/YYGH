package com.gzucm.yygh.activity.select;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gzucm.yygh.R;
import com.gzucm.yygh.bean.Appointment;
import com.gzucm.yygh.bean.Doctor;
import com.gzucm.yygh.bean.Patient;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class Pay2Activity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup rg01;
    private RadioGroup rg02;
    private RadioGroup rg03;
    private RadioGroup rg04;
    private RadioGroup rg05;
    private Button button;

    private double money;
    private TextView tvmoney;
    private EditText etmoney;
    private TextView getmoney;
    private TextView tv_pname;
    private TextView rmoney;
    private Button dayin;

    Patient patient=new Patient();
    Doctor doctor=new Doctor();

    //要传递的数据，传递对象也可以，不过得序列化对象


    Handler mhanler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case 1:
                    patient = (Patient)msg.obj;

                    break;
                case 2:
                    doctor = (Doctor) msg.obj;
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        pay();
    }

    private void initView() {

        rg01 = (RadioGroup)findViewById(R.id.rg_01);
        rg02 = (RadioGroup)findViewById(R.id.rg_02);
        rg03 = (RadioGroup)findViewById(R.id.rg_03);
        rg04 = (RadioGroup)findViewById(R.id.rg_04);
        rg05 = (RadioGroup)findViewById(R.id.rg_05);
        tvmoney = (TextView)findViewById(R.id.money);
        rmoney = (TextView)findViewById(R.id.returnmoney);
        getmoney = (TextView)findViewById(R.id.getmoney);
        tv_pname = (TextView)findViewById(R.id.tv_pname);
        getmoney.setOnClickListener(this);
        button = (Button)findViewById(R.id.btn_yes);
        dayin = (Button)findViewById(R.id.btn_dayin);
        dayin.setOnClickListener(this);
        etmoney = (EditText)findViewById(R.id.et_money);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_yes:
                money = pay() + pay02() + pay03();
                tvmoney.setText(money+"");
                findPatient();
                findDoctor();
                break;
            case R.id.getmoney:
                double getM = Double.parseDouble(etmoney.getText().toString());
                String rm = (getM - money) + "";
                rmoney.setText(rm);
                break;
            case R.id.btn_dayin:
//                findAppoint();
                addAppoint();
                updateAfternoon();
                Intent intent = new Intent(Pay2Activity.this,AppointActivity.class);
                intent.putExtra("patient", patient);
                intent.putExtra("doctor", doctor);
                startActivity(intent);
        }
    }

    public double pay(){
        RadioButton rb21 = (RadioButton) rg02.getChildAt(0);
        RadioButton rb22 = (RadioButton) rg02.getChildAt(1);
        RadioButton rb23 = (RadioButton) rg02.getChildAt(2);
        RadioButton rb24 = (RadioButton) rg02.getChildAt(3);
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        boolean pisp= pref.getBoolean("pisp",false);
        if(pisp){
            rb23.setChecked(true);
            rb21.setEnabled(false);
            rb22.setEnabled(false);
            rb24.setEnabled(false);
        }
        if (rb21.isChecked()) {
            return 3.0;
        }

        if (rb22.isChecked()) {
            return 5.0;
        }


        if (rb23.isChecked()) {
            return 5.0;
        }

        if (rb24.isChecked()) {
            return 5.0;
        }
        return 3.0;
    }

    public double pay02(){
        RadioButton rb31 = (RadioButton) rg03.getChildAt(0);
        if (rb31.isChecked()) {
            return 1.0;
        }
        RadioButton rb32 = (RadioButton) rg03.getChildAt(1);
        if (rb32.isChecked()) {
            return 0.0;
        }
        return 1.0;
    }
    public double pay03(){
        RadioButton rb51 = (RadioButton) rg05.getChildAt(0);
        if (rb51.isChecked()) {
            return 2.0;
        }
        RadioButton rb52 = (RadioButton) rg05.getChildAt(1);
        if (rb52.isChecked()) {
            return 3.0;
        }
        RadioButton rb53 = (RadioButton) rg05.getChildAt(2);
        if (rb53.isChecked()) {
            return 10.0;
        }
        return 2.0;
    }
    public String selectHaobie(){
        //设置患者的性别
        RadioButton rb21 = (RadioButton) rg02.getChildAt(0);
        if (rb21.isChecked()) {
            return "普通";
        }
        RadioButton rb22 = (RadioButton) rg02.getChildAt(1);
        if (rb22.isChecked()) {
            return "主任医师";
        }

        RadioButton rb23 = (RadioButton) rg02.getChildAt(2);
        if (rb23.isChecked()) {
            return "特诊";
        }
        RadioButton rb24 = (RadioButton) rg02.getChildAt(3);
        if (rb23.isChecked()) {
            return "副教授";
        }
        return "普通";
    }
    public void findPatient() {

        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        String pid= pref.getString("pid","");
        BmobQuery<Patient> query = new BmobQuery<Patient>();
        //执行查询方法
        query.getObject(pid,new QueryListener<Patient>() {
            @Override
            public void done(Patient patient, BmobException e) {

                if (e == null) {
                    final String name = patient.getP_name();
                    //添加挂号类别
                    Message message = mhanler.obtainMessage();
                    message.what = 1;
                    //以消息为载体
                    message.obj = patient;
                    //向handler发送消息
                    mhanler.sendMessage(message);

                    mhanler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_pname.setText(name);

                        }
                    });

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
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

                    Message message = mhanler.obtainMessage();
                    message.what = 2;
                    //以消息为载体
                    message.obj = doctor;
                    //向handler发送消息
                    mhanler.sendMessage(message);



                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void addAppoint(){
        Appointment appointment = new Appointment();
        appointment.setA_name(patient);
        String haobie = selectHaobie();
        appointment.setA_type(haobie);
        appointment.setA_doctor(doctor);
        appointment.save(new SaveListener<String>() {

               @Override
               public void done(String objectId, BmobException e) {
               if(e==null){
                     Toast.makeText(Pay2Activity.this, "挂号成功", Toast.LENGTH_SHORT).show();
               }else{
                      Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
               }
            }
        });
    }
    private void updateAfternoon() {
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        String pdid= pref.getString("pdid","");
        Doctor doctor = new Doctor();
        doctor.increment("d_acount",-1); // 分数递增1
        doctor.increment("d_nowacount",1); // 分数递增1
        doctor.update(pdid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob更新挂号","更新成功");
                }else{
                    Log.i("bmob更新挂号","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }




}
