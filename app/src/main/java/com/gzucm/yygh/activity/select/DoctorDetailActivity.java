package com.gzucm.yygh.activity.select;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzucm.yygh.R;
import com.gzucm.yygh.bean.Doctor;
import com.gzucm.yygh.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class DoctorDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private RoundImageView head;
    private TextView detailname;
    private TextView detailtitles;
    private TextView detailspecial;
    private TextView detaildepartment;
    private TextView dacount;
    private TextView dacountClick;
    private TextView dmcount;
    private TextView dnowacount;
    private TextView dnowmcount;
    private TextView dmcountClick;
    private ImageView mbtn;
    private ImageView abtn;
    private ImageView mman;
    private ImageView aman;
    ImageLoader imageLoader = ImageLoader.getInstance();

    String dnum;
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        initView();
        initData();
    }

    private void initData() {

        Intent intent = getIntent();
        dnum = intent.getStringExtra("dnum");
        String name = intent.getStringExtra("dname");
        String specialty = intent.getStringExtra("dspecialty");
        String titles = intent.getStringExtra("dtitles");
        String departmentname = intent.getStringExtra("ddepartmentname");
        int a = intent.getIntExtra("dacount",0);
        int m = intent.getIntExtra("dmcount",0);
        int na = intent.getIntExtra("dnowacount",0);
        int nm = intent.getIntExtra("dnowmcount",0);
        String url = intent.getStringExtra("url");

        //设置满号不可点击
        if(m == 0){
            mman.setVisibility(View.VISIBLE);
            dmcountClick.setEnabled(false);
        }
        if(a == 0){
            aman.setVisibility(View.VISIBLE);
            dacountClick.setEnabled(false);
        }

        detailname.setText(name);
        detailtitles.setText(titles);
        detailspecial.setText(specialty);
        detaildepartment.setText(departmentname);
        detaildepartment.setTextColor(Color.parseColor("#b48c5a"));
//        String path = Environment.getExternalStorageDirectory() + "/"+name +".jpg";
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        head.setImageBitmap(bitmap);
        //创建DisplayImageOptions对象并进行相关选项配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_stub)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_x)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象
        //利用ImageView加载图片
        imageLoader.displayImage(url,head,options);
        dacount.setText(a+"");
        dnowacount.setText(na+"");
        dmcount.setText(m+"");
        dnowmcount.setText(nm+"");
    }

    private void initView() {
        detailname = (TextView) findViewById(R.id.detail_name);
        detailtitles = (TextView) findViewById(R.id.detail_titles);
        detailspecial = (TextView) findViewById(R.id.detail_special);
        detaildepartment = (TextView) findViewById(R.id.detail_department);
        dacount = (TextView) findViewById(R.id.afternoon_hao);
        dnowacount = (TextView) findViewById(R.id.now_afternoon_hao);
        dacountClick = (TextView) findViewById(R.id.afternoon_config);
        dacountClick.setOnClickListener(this);
        dmcount = (TextView) findViewById(R.id.morning_hao);
        dnowmcount = (TextView) findViewById(R.id.now_morning_hao);
        dmcountClick = (TextView) findViewById(R.id.morning_config);
        dmcountClick.setOnClickListener(this);
        head = (RoundImageView) findViewById(R.id.detail_head);
        mbtn = (ImageView)findViewById(R.id.madd);
        mbtn.setOnClickListener(this);
        abtn = (ImageView)findViewById(R.id.aadd);
        abtn.setOnClickListener(this);
        mman = (ImageView)findViewById(R.id.mman);
        aman = (ImageView)findViewById(R.id.aman);
    }


    @Override
    public void onClick(View v) {
        SharedPreferences sp =  getSharedPreferences("patient",MODE_PRIVATE);
        switch (v.getId()){
            case R.id.afternoon_config:
                Intent intent = new Intent(DoctorDetailActivity.this,Pay2Activity.class);
                sp.edit().putBoolean("pam",false).commit();
                startActivity(intent);
                break;
            case R.id.morning_config:
                Intent intent2 = new Intent(DoctorDetailActivity.this,PayActivity.class);
                sp.edit().putBoolean("pam",true).commit();
                startActivity(intent2);
                break;
            case R.id.madd:
                addMorning();
                findDoctor();
                dmcountClick.setEnabled(true);
                mman.setVisibility(View.GONE);
                break;
            case R.id.aadd:
                addAfternoon();
                findDoctor();
                dacountClick.setEnabled(true);
                aman.setVisibility(View.GONE);
                break;
        }
    }

    private void addMorning() {
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        String pdid= pref.getString("pdid","");
        Doctor doctor = new Doctor();
        doctor.increment("d_mcount",1); // 分数递增1
        doctor.update(pdid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob加号","更新成功");
                }else{
                    Log.i("bmob更新挂号","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void addAfternoon() {
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        String pdid= pref.getString("pdid","");
        Doctor doctor = new Doctor();
        doctor.increment("d_acount",1); // 分数递增1
        doctor.update(pdid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob更新挂号","更新成功");
                }else{
                    Log.i("bmob加号","更新失败："+e.getMessage()+","+e.getErrorCode());
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

                    final int ma = doctor.getD_mcount();
                    final int aa = doctor.getD_acount();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            dacount.setText(aa + "");
                            dmcount.setText(ma + "");
                        }
                    });
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
