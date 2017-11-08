package com.gzucm.yygh.activity.FirstOrReturn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class ReturnvisitActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etSearch;
    private TextView btnSearch;
    private TextView tvreturnname;
    private TextView tvCard;
    private TextView tvContent;

    private LinearLayout lljz;
    private LinearLayout llks;
    private LinearLayout lljs;
    private LinearLayout llys;
    private LinearLayout llrq;
    private LinearLayout lltj;
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returnvisit);
        initView();

    }

    private void initView() {
        etSearch = (EditText)findViewById(R.id.search_et_card);
        btnSearch = (TextView) findViewById(R.id.search_btn);
        tvreturnname = (TextView)findViewById(R.id.tv_return_name);
        tvContent = (TextView)findViewById(R.id.tv_content);
        tvCard = (TextView)findViewById(R.id.tv_card);
        btnSearch.setOnClickListener(this);

        lljz = (LinearLayout)findViewById(R.id.ll01);
        lljz.setOnClickListener(this);
        llks = (LinearLayout)findViewById(R.id.ll02);
        llks.setOnClickListener(this);
        lljs = (LinearLayout)findViewById(R.id.ll03);
        lljs.setOnClickListener(this);
        llys = (LinearLayout)findViewById(R.id.ll04);
        llys.setOnClickListener(this);
        llrq = (LinearLayout)findViewById(R.id.ll05);
        llrq.setOnClickListener(this);
        lltj = (LinearLayout)findViewById(R.id.ll06);
        lltj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                String pidcard = etSearch.getText().toString();
                //根据正则表达式获取000222 后面的222,只有这个才是写入数据库的
                String trueCardNum = getTrueCardNum(pidcard);
                int card = Integer.parseInt(trueCardNum);
//                tvCard.setText(card+"");
                tvCard.setText(pidcard);
                findPatient(card);
                break;
            case R.id.ll01:
                Intent intent = new Intent(ReturnvisitActivity.this,EmergencyActivity.class);
                startActivity(intent);
                break;
            case R.id.ll02:
                Intent intent02 = new Intent(ReturnvisitActivity.this,DepartmentActivity.class);
                startActivity(intent02);
                break;
            case R.id.ll03:
                Intent intent03 = new Intent(ReturnvisitActivity.this,ProfessorActivity.class);
                startActivity(intent03);
                break;
            case R.id.ll04:
                Intent intent04 = new Intent(ReturnvisitActivity.this,DoctorActivity.class);
                startActivity(intent04);
                break;
            case R.id.ll05:
                Intent intent05 = new Intent(ReturnvisitActivity.this,DateSelectedActivity.class);
                startActivity(intent05);
                break;
            case R.id.ll06:
                Intent intent06 = new Intent(ReturnvisitActivity.this,RestActivity.class);
                startActivity(intent06);
                break;
        }
    }

    /**
     * 根据正则表达式获取0000000222 后面的222,只有这个才是写入数据库的
     * @param pidcard
     * @return
     */
    public String getTrueCardNum(String pidcard){
        String regex = "[0]+";
        String[] str = pidcard.split(regex);

        if(!pidcard.matches("\\d*")){
            System.out.println(pidcard);
            return pidcard;
        }else{
            System.out.println(str[str.length-1]);
            return str[str.length-1];
        }

    }

    public void findPatient(int card){

        BmobQuery<Patient> query = new BmobQuery<Patient>();
        query.addWhereEqualTo("p_card",card);
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        //执行查询方法
        query.findObjects(new FindListener<Patient>() {
            @Override
            public void done(List<Patient> object, BmobException e) {
                if(e==null){

                    for (Patient patient : object) {
                        //获得playerName的信息
                        final String name = patient.getP_name();
                        final String address = patient.getP_address();
                        final String allergy = patient.getP_allergy();
                        final String birth = patient.getP_birth();
                        final String idcard = patient.getP_idcard();
                        final String phone = patient.getP_phone();
                        final String CreatedAt = patient.getCreatedAt();
                        final String laterAt = patient.getUpdatedAt();

                        //获得数据的objectId信息,保存到本地
                        String pid = patient.getObjectId();
                        SharedPreferences sp =  getSharedPreferences("patient",MODE_PRIVATE);
                        sp.edit().putString("pid",pid).commit();

                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        patient.getCreatedAt();
                        Log.i("名字名字",""+ name);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvreturnname.setText(name);
                                String content = "您现居住：" + address + "\n" +"过敏历史：" + allergy + "\n" +
                                        "出生日期：" + birth + "\n" +"身份证号码：" + idcard + "\n" +
                                        "手机号：" + phone + "\n" +"您首次就诊我院：" + CreatedAt
                                        + "\n" +"您最近一次就诊我院：" + laterAt;
                                tvContent.setText(content);

                            }
                        });
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
