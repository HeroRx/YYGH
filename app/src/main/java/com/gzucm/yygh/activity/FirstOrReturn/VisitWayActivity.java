package com.gzucm.yygh.activity.FirstOrReturn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzucm.yygh.R;
import com.gzucm.yygh.activity.select.DateSelectedActivity;
import com.gzucm.yygh.activity.select.DepartmentActivity;
import com.gzucm.yygh.activity.select.DoctorActivity;
import com.gzucm.yygh.activity.select.EmergencyActivity;
import com.gzucm.yygh.activity.select.ProfessorActivity;
import com.gzucm.yygh.activity.select.RestActivity;
import com.gzucm.yygh.bean.Patient;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 就诊方式选择
 * Created by Administrator on 2017/10/28 0028.
 */

public class VisitWayActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_card;
    private TextView tv_name;
    Handler mHandler = new Handler();

    private LinearLayout lljz;
    private LinearLayout llks;
    private LinearLayout lljs;
    private LinearLayout llys;
    private LinearLayout llrq;
    private LinearLayout lltj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_visit);
        initView();
        setCard();
    }

    private void initView() {
        tv_card = (TextView)findViewById(R.id.tv_card_first);
        tv_name = (TextView)findViewById(R.id.tv_name_first);
        lljz = (LinearLayout)findViewById(R.id.ll01_first);
        lljz.setOnClickListener(this);
        llks = (LinearLayout)findViewById(R.id.ll02_first);
        llks.setOnClickListener(this);
        lljs = (LinearLayout)findViewById(R.id.ll03_first);
        lljs.setOnClickListener(this);
        llys = (LinearLayout)findViewById(R.id.ll04_first);
        llys.setOnClickListener(this);
        llrq = (LinearLayout)findViewById(R.id.ll05_first);
        llrq.setOnClickListener(this);
        lltj = (LinearLayout)findViewById(R.id.ll06_first);
        lltj.setOnClickListener(this);
    }

    /**
     * 设置ID,从后台获取
     */
    public void setCard(){
        Intent intent = getIntent();
        final String name = intent.getStringExtra("pname");
        String idcard = intent.getStringExtra("pidcard");
        Log.i("www",name + idcard +"");
        BmobQuery<Patient> query1 = new BmobQuery<Patient>();
        query1.addWhereEqualTo("p_name",name);
        BmobQuery<Patient> query2 = new BmobQuery<Patient>();
        query2.addWhereEqualTo("p_idcard", idcard);
        List<BmobQuery<Patient>> queries = new ArrayList<BmobQuery<Patient>>();
        queries.add(query1);
        queries.add(query2);
        BmobQuery<Patient> mainQuery = new BmobQuery<Patient>();
        mainQuery.and(queries);
        mainQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);

        //执行查询方法
        mainQuery.findObjects(new FindListener<Patient>() {
            @Override
            public void done(List<Patient> object, BmobException e) {
                if(e==null){
                    List<String> cardlist = null;
                    for (Patient patient : object) {
                        //获得playerName的信息
                        int mycard = patient.getP_card();
                        final String card = String.format("%010d",mycard);
                        cardlist = new ArrayList<String>();
                        cardlist.add(card);
                        //获得数据的objectId信息
                        String pid = patient.getObjectId();
                        SharedPreferences sp =  getSharedPreferences("patient",MODE_PRIVATE);
                        sp.edit().putString("pid",pid).commit();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        patient.getCreatedAt();
                        Log.i("sdadas",""+mycard + "dsad" + card);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_card.setText(card);
                                tv_name.setText(name);
                            }
                        });
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll01_first:
                Intent intent = new Intent(VisitWayActivity.this,EmergencyActivity.class);
                startActivity(intent);
                break;
            case R.id.ll02_first:
                Intent intent02 = new Intent(VisitWayActivity.this,DepartmentActivity.class);
                startActivity(intent02);
                break;
            case R.id.ll03_first:
                Intent intent03 = new Intent(VisitWayActivity.this,ProfessorActivity.class);
                startActivity(intent03);
                break;
            case R.id.ll04_first:
                //先写这个界面
                Intent intent04 = new Intent(VisitWayActivity.this,DoctorActivity.class);
                startActivity(intent04);
                break;
            case R.id.ll05_first:
                Intent intent05 = new Intent(VisitWayActivity.this,DateSelectedActivity.class);
                startActivity(intent05);
                break;
            case R.id.ll06_first:
                Intent intent06 = new Intent(VisitWayActivity.this,RestActivity.class);
                startActivity(intent06);
                break;
        }
    }
}
