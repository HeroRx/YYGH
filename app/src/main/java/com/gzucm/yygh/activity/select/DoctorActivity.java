package com.gzucm.yygh.activity.select;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzucm.yygh.R;
import com.gzucm.yygh.bean.Department;
import com.gzucm.yygh.bean.Doctor;
import com.gzucm.yygh.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class DoctorActivity extends AppCompatActivity implements View.OnClickListener{

    private Handler mHandler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    List<Doctor> list= (List<Doctor>) msg.obj;
                    for (Doctor doctor : list) {
                        url = doctor.getD_photo().getFileUrl();
                        name = doctor.getD_name();
                        d_num = doctor.getD_num();
                        specialty = doctor.getD_specialty();
                        titles = doctor.getD_titles();
                        Department department = doctor.getD_dcode();
                        departmentname = department.getD_departmentname();
                        d_acount = doctor.getD_acount();
                        d_mcount = doctor.getD_mcount();
                        d_nowacount = doctor.getD_nowacount();
                        d_nowmcount = doctor.getD_nowmcount();
                        Log.i("departmentname>>>>>>",departmentname+"");
                    }
                    break;
                case msgKey:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 \nHH时mm分ss秒 \nEEE");
                    time02.setText(format.format(date));
                    break;
            }

        }
    };
    String url;
    String d_num;
    String name;
    String specialty;
    String titles;
    String departmentname;
    int d_acount;
    int d_mcount;
    int d_nowacount;
    int d_nowmcount;
    private EditText etDoctor;
    private TextView tvDoctor;
    private RoundImageView head;
    private TextView tvname;
    private TextView tvtitle;
    private ImageView iv;
    private LinearLayout ll;
    ImageLoader imageLoader = ImageLoader.getInstance();

    //实时线程
    private static final int msgKey = 2;
    private TextView time02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        initView();
        new TimeThread().start();
    }

    private void initView() {
        etDoctor = (EditText)findViewById(R.id.etdoctor);
        tvDoctor = (TextView) findViewById(R.id.btndoctor);
        tvname = (TextView) findViewById(R.id.tv_d_name);
        tvtitle = (TextView) findViewById(R.id.tv_d_title);
        time02 = (TextView) findViewById(R.id.time02);
        head = (RoundImageView) findViewById(R.id.iv_d_photo);
        iv = (ImageView)findViewById(R.id.doctor_iv_detail);
        ll = (LinearLayout) findViewById(R.id.ll_doctor);
        tvDoctor.setOnClickListener(this);
        iv.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btndoctor:
                String doctor = etDoctor.getText().toString();
                findDoctor(doctor);
                break;
            case R.id.doctor_iv_detail:
                Intent intent = new Intent(DoctorActivity.this,DoctorDetailActivity.class);
                intent.putExtra("dname",name);
                intent.putExtra("dspecialty",specialty);
                intent.putExtra("dtitles",titles);
                intent.putExtra("ddepartmentname",departmentname);
                intent.putExtra("dnum",d_num);
                intent.putExtra("dacount",d_acount);
                intent.putExtra("dmcount",d_mcount);
                intent.putExtra("dnowacount",d_nowacount);
                intent.putExtra("dnowmcount",d_nowmcount);
                intent.putExtra("url",url);
                startActivity(intent);
        }

    }

    /**
     * 查询医生并更新UI界面
     * @param dname
     */
    public void findDoctor(String dname){

        BmobQuery<Doctor> query = new BmobQuery<Doctor>();
        query.addWhereEqualTo("d_name",dname);
        query.include("d_dcode");
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        //执行查询方法
        query.findObjects(new FindListener<Doctor>() {
            @Override
            public void done(List<Doctor> object, BmobException e) {
                if(e==null){

                    Message message = mHandler.obtainMessage();
                    message.what = 1;
                    //以消息为载体
                    message.obj = object;//这里的list就是查询出list
                    //向handler发送消息
                    mHandler.sendMessage(message);

                    for (final Doctor doctor : object) {
                        //获得playerName的信息
                        final String name = doctor.getD_name();
                        final String titles = doctor.getD_titles();

                        String d_url = doctor.getD_photo().getFileUrl();
                        //创建DisplayImageOptions对象并进行相关选项配置
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.drawable.icon_stub)// 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.drawable.icon_empty)// 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.drawable.icon_x)// 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                                .build();// 创建DisplayImageOptions对象
                        //利用ImageView加载图片
                        imageLoader.displayImage(d_url,head,options);

//                        //下载医生的头像,以医生的名字命名的文件
//                        BmobFile bmobfile = doctor.getD_photo();
//                        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
//                        final File saveFile = new File(Environment.getExternalStorageDirectory(), name +".jpg");
//                        if(!saveFile.exists()){
//                            bmobfile.download(saveFile, new DownloadFileListener() {
//                                @Override
//                                public void onStart() {
//                                }
//                                @Override
//                                public void done(String savePath,BmobException e) {
//                                    if(e==null){
//                                        Log.i("bmob","下载进度cg");
//                                    }else{
//                                        Log.i("bmob","下载进度sb");
//                                    }
//                                }
//                                @Override
//                                public void onProgress(Integer value, long newworkSpeed) {
//                                    Log.i("bmob","下载进度："+value+","+newworkSpeed);
//                                }
//                            });
//                        }

                        //获得数据的objectId信息
                        String pid = doctor.getObjectId();
                        SharedPreferences sp =  getSharedPreferences("patient",MODE_PRIVATE);
                        sp.edit().putString("pdid",pid).commit();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        doctor.getCreatedAt();

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvname.setText(name);
                                tvname.setTextColor(Color.RED);
                                tvtitle.setText(titles);
                                tvtitle.setTextColor(Color.RED);
                                ll.setBackgroundResource(R.color.orange02);

//                                if(saveFile.exists()){
//                                    String path = Environment.getExternalStorageDirectory() + "/"+name +".jpg";
//                                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//                                    head.setImageBitmap(bitmap);
//                                }

                            }
                        });
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 实时线程
     */
    public class TimeThread extends  Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
}
