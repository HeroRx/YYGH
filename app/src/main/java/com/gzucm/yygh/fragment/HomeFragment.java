//package com.gzucm.yygh.fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.gzucm.yygh.R;
//
///**
// * Created by Administrator on 2017/10/28 0028.
// */
//
//public class HomeFragment extends Fragment implements View.OnClickListener{
//
//    //提供上下文,否则无法findViewId
//    private Context mContext;
//    //View 声明全局才能拿到findViewId
//    private View mRootView;
//    //handle
//    private Handler mHandle = new Handler();
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mRootView = inflater.inflate(R.layout.,null);
////        View view = inflater.inflate(R.layout.,null);
//        this.mContext = getActivity();//很多都用这种方式
//        return mRootView;
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//    @Override
//    public void onClick(View v) {
//
//    }
//}
