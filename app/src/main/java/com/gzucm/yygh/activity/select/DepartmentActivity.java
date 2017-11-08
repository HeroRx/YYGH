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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gzucm.yygh.R;
import com.gzucm.yygh.adapter.DDAdapter;
import com.gzucm.yygh.bean.Department;
import com.gzucm.yygh.bean.Doctor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etDepartment;
    private TextView tvDepartment;
    private ListView mListView;
    private String DepartmentId;
    private Handler mHandler =  new Handler();
    private LinearLayout none;

    //实时线程
    private static final int msgKey1 = 1;
    private TextView time01;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<Department> list= (List<Department>) msg.obj;
                    for (Department department : list) {
                        //获得数据的objectId信息
                        DepartmentId = department.getObjectId();
                        Log.i("我的科室ID。。。。。",""+DepartmentId);

                        //在这里查医生
                        BmobQuery<Doctor> query = new BmobQuery<Doctor>();
                        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                        department.setObjectId(DepartmentId);
                        query.addWhereEqualTo("d_dcode", new BmobPointer(department));
                        query.include("d_dcode");
                        query.findObjects(new FindListener<Doctor>() {

                            @Override
                            public void done(final List<Doctor> objects, BmobException e) {
                                Log.i("医生",""+objects);
                                final DDAdapter ddAdapter = new DDAdapter(DepartmentActivity.this, objects);
                                ddAdapter.notifyDataSetChanged();
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mListView.setAdapter(ddAdapter);
                                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String pid = objects.get(position).getObjectId();
                                                String name = objects.get(position).getD_name();
                                                String specialty = objects.get(position).getD_specialty();
                                                String titles = objects.get(position).getD_titles();
                                                Department department = objects.get(position).getD_dcode();
                                                String departmentname = department.getD_departmentname();
                                                String d_num = objects.get(position).getD_num();
                                                int d_acount = objects.get(position).getD_acount();
                                                int d_mcount = objects.get(position).getD_mcount();
                                                int d_nowacount = objects.get(position).getD_nowacount();
                                                int d_nowmcount = objects.get(position).getD_nowmcount();
                                                String url = objects.get(position).getD_photo().getFileUrl();
                                                SharedPreferences sp =  getSharedPreferences("patient",MODE_PRIVATE);
                                                sp.edit().putString("pdid",pid).commit();
                                                Intent intent = new Intent(DepartmentActivity.this,DoctorDetailActivity.class);

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
                                        });
                                    }
                                });
                            }
                        });
                    }
                    break;
                case msgKey1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 \nHH时mm分ss秒 \nEEE");
                    time01.setText(format.format(date));
                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        initView();
        new TimeThread().start();
    }


    private void initView() {

        etDepartment = (EditText)findViewById(R.id.search_et_department);
        tvDepartment = (TextView) findViewById(R.id.search_department);
        mListView = (ListView) findViewById(R.id.lv);
        none = (LinearLayout)findViewById(R.id.department_none);
        none.setVisibility(View.VISIBLE);
        tvDepartment.setOnClickListener(this);
        time01 = (TextView)findViewById(R.id.time01);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_department:
                String dDepartment = etDepartment.getText().toString();
                loaddoctorslist(dDepartment);
                none.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 加载list列表
     */
    private void loaddoctorslist(String d_departmentname) {

        //这里拿到objectId传给全局变量，使用handler
        BmobQuery<Department> query01=new BmobQuery<Department>();
        query01.addWhereEqualTo("d_departmentname", d_departmentname);
//        query01.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query01.findObjects(new FindListener<Department>() {
            @Override
            public void done(List<Department> list, BmobException e) {
                if (e == null) {
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    handler.sendMessage(message);

                } else {
                    Log.e("bmob", ""+e);
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
                    msg.what = msgKey1;
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
}
