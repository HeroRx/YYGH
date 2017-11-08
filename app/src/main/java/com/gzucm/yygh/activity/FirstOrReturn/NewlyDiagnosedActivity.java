package com.gzucm.yygh.activity.FirstOrReturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gzucm.yygh.R;
import com.gzucm.yygh.bean.Patient;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 初诊的记录患者信息表
 * Created by Administrator on 2017/10/28 0028.
 */

public class NewlyDiagnosedActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etName;
    private EditText etIDcard;
    private EditText etPhone;
    private EditText etAddress;
    private EditText etAllergy;
    private EditText etBirth;
    private Button btn_commit;

    private RadioGroup rg_sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
    }

    private void initView() {
        etName = (EditText)findViewById(R.id.et_name);
        etIDcard = (EditText)findViewById(R.id.et_idcard);
        etPhone = (EditText)findViewById(R.id.et_phone);
        etAddress = (EditText)findViewById(R.id.et_address);
        etAllergy = (EditText)findViewById(R.id.et_allergy);
        etBirth = (EditText)findViewById(R.id.et_birth);
        btn_commit = (Button) findViewById(R.id.btn_commit);

        btn_commit.setOnClickListener(this);

        rg_sex = (RadioGroup)findViewById(R.id.radioGroup);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_commit:
                //获取键盘输入
                String name = etName.getText().toString();
                String idcard = etIDcard.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                String allergy = etAllergy.getText().toString();
                String birth = etBirth.getText().toString();
                //成功插入一条数据
                addItem(name,idcard,phone,address,allergy,birth);
                Intent intent = new Intent(NewlyDiagnosedActivity.this,VisitWayActivity.class);
                Log.i("hhh",name + idcard +"");
                intent.putExtra("pname",name);
                intent.putExtra("pidcard",idcard);
                startActivity(intent);
                break;
        }
    }

    /**
     * 添加一个病人数据的操作
     * @param etName
     * @param etIDcard
     * @param etPhone
     * @param etAddress
     * @param etAllergy
     */
    public void addItem(String etName,String etIDcard,String etPhone,String etAddress,String etAllergy,String etBirth){
        Patient patient = new Patient();
        patient.setP_name(etName);
        patient.setP_idcard(etIDcard);
        patient.setP_phone(etPhone);
        patient.setP_address(etAddress);
        patient.setP_allergy(etAllergy);
        patient.setP_birth(etBirth);

        //设置患者的性别
        RadioButton rd_male = (RadioButton) rg_sex.getChildAt(0);
        if (rd_male.isChecked()) {
            Toast.makeText(NewlyDiagnosedActivity.this, "点击提交按钮,获取你选择的是:" + rd_male.getText(), Toast.LENGTH_SHORT).show();
            patient.setP_sex(true);
        }
        RadioButton rd_female = (RadioButton) rg_sex.getChildAt(1);
        if (rd_female.isChecked()) {
            Toast.makeText(NewlyDiagnosedActivity.this, "点击提交按钮,获取你选择的是:" + rd_female.getText(), Toast.LENGTH_SHORT).show();
            patient.setP_sex(false);
        }

        patient.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(NewlyDiagnosedActivity.this, "添加到数据库成功", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
