package com.gzucm.yygh.activity;
//
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.RadioButton;
//
//import com.gzucm.yygh.R;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
//
//    private ViewPager mViewPager;
//    private RadioButton mHomeRb,mDanRb,mMyRb;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initView();
//        initData();
//    }
//    /**
//     * 初始化界面
//     */
//    private void initView() {
//        mViewPager = (ViewPager) findViewById(R.id.view_pager);
//        mHomeRb = (RadioButton) findViewById(R.id.home_rb);
//        mDanRb = (RadioButton) findViewById(R.id.dan_rb);
//        mMyRb = (RadioButton) findViewById(R.id.my_rb);
//        mHomeRb.setOnClickListener(this);
//        mDanRb.setOnClickListener(this);
//        mMyRb.setOnClickListener(this);
//    }
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//        //Fragment的集合
//        ArrayList<Fragment> fragments = new ArrayList<>();//1.7里面的写法
//        //在集合添加Fragment
//        fragments.add(new HomeFragment());
//        fragments.add(new DanFragment());
//        fragments.add(new MyFragment());
//        //自己多传入一个fragments集合
//        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager(),fragments);
//        mViewPager.setAdapter(adapter);
//    }
//    @Override
//    public void onClick(View view) {
//        switch(view.getId()){
//            case R.id.home_rb:
//                //把他切换到第一页,false为不要切换效果
//                mViewPager.setCurrentItem(0,false);
//                break;
//            case R.id.dan_rb:
//                mViewPager.setCurrentItem(1,false);
//                break;
//            case R.id.my_rb:
//                mViewPager.setCurrentItem(2,false);
//                break;
//        }
//    }
//    //监听滑动的回调方法
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//    }
//    @Override
//    public void onPageSelected(int position) {
//        //切换到相应的页面图标点亮
//        switch (position){
//            case 0:
//                mHomeRb.setChecked(true);
//                break;
//            case 1:
//                mDanRb.setChecked(true);
//                break;
//            case 2:
//                mMyRb.setChecked(true);
//                break;
//        }
//    }
//    @Override
//    public void onPageScrollStateChanged(int state) {
//    }
//}