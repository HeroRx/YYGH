package com.gzucm.yygh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gzucm.yygh.R;
import com.gzucm.yygh.activity.FirstOrReturn.NewlyDiagnosedActivity;
import com.gzucm.yygh.activity.FirstOrReturn.ReturnvisitActivity;

/**
 * 选择就诊界面
 * Created by Administrator on 2017/10/28 0028.
 */

public class SelectActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView first_watch;
    private TextView not_first_watch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initView();
    }

    private void initView() {
        first_watch = (TextView)findViewById(R.id.tv);
        not_first_watch = (TextView)findViewById(R.id.tv02);
        first_watch.setOnClickListener(this);
        not_first_watch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv:
                Intent intent = new Intent(SelectActivity.this,NewlyDiagnosedActivity.class);
                startActivity(intent);
                break;
            case R.id.tv02:
//                直接进入就诊方式选择
                Intent intent2 = new Intent(SelectActivity.this,ReturnvisitActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
