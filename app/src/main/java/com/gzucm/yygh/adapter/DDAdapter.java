package com.gzucm.yygh.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzucm.yygh.R;
import com.gzucm.yygh.bean.Doctor;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class DDAdapter extends BaseAdapter{


    private List<Doctor> doctors;

    private Context context;
    private Activity activity;
    private static LayoutInflater inflater=null;
    private ImageLoader imageLoader;

    Handler handler = new Handler();


    public DDAdapter(Activity a,List<Doctor> doctors){
        this.activity = a;
        this.doctors = doctors;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=ImageLoader.getInstance();
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){

//            先去inflater一个布局加载控件
            convertView = inflater.inflate(R.layout.item_adapter, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.d_photo = (ImageView)convertView.findViewById(R.id.iv_head);
            viewHolder.d_name = (TextView)convertView.findViewById(R.id.tv_dname);
            viewHolder.d_titles = (TextView)convertView.findViewById(R.id.tv_dtitle);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Doctor doctor = doctors.get(position);
        viewHolder.d_name.setText(doctor.getD_name());
        viewHolder.d_titles.setText(doctor.getD_titles());
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
        imageLoader.displayImage(d_url,viewHolder.d_photo,options);

//        //下载医生的头像,以医生的名字命名的文件
//        BmobFile bmobfile = doctor.getD_photo();
//        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
//        final File saveFile = new File(Environment.getExternalStorageDirectory(), doctor.getD_name() +".jpg");
//        if(!saveFile.exists()){
//            bmobfile.download(saveFile, new DownloadFileListener() {
//                @Override
//                public void onStart() {
//                }
//
//                @Override
//                public void done(final String savePath, BmobException e) {
//                    if(e==null){
//                        Log.i("bmob","下载进度cg");
//                    }else{
//                        Log.i("bmob","下载进度sb");
//                    }
//                }
//                @Override
//                public void onProgress(Integer value, long newworkSpeed) {
//                    Log.i("bmob","下载进度："+value+","+newworkSpeed);
//                }
//            });
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    if(saveFile.exists()){
//                        String path = Environment.getExternalStorageDirectory() + "/"+doctor.getD_name() +".jpg";
//                        Bitmap bitmap = BitmapFactory.decodeFile(path);
//                        viewHolder.d_photo.setImageBitmap(bitmap);
//                    }
//                }
//            });
//
//        }
        return convertView;
    }

    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class ViewHolder{
        public ImageView d_photo;
        public TextView d_titles;
        public TextView d_name;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return doctors.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return doctors.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}

