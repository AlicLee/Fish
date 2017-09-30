package com.example.kys.fish.view.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys.fish.R;
import com.example.kys.fish.adapter.AddTalkAdapter;
import com.example.kys.fish.model.Add;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TalkActivity extends AppCompatActivity {
    private int PHOTO_REQUEST_GALLERY = 0;
    private int PHOTO_REQUEST_CUT = 1;
    private static final int REQUEST_TO_CAMERA = 3;
    private static final String fileName = "temp.jpg";
    private File tempFile;
    private TextView talk_tv;
    private EditText add_edit;
    private ImageView addTalk_img;
    private RecyclerView add_recycleView;
    private List<Add> addList = new ArrayList<>();
    private AddTalkAdapter addTalkAdapter;
    private ImageView adapter_img;
    private final int i = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        talk_tv = (TextView) findViewById(R.id.talk_tv);
        add_edit = (EditText) findViewById(R.id.add_edit);
        addTalk_img = (ImageView) findViewById(R.id.addTalk_img);
        add_recycleView = (RecyclerView) findViewById(R.id.add_recycleView);
        adapter_img = (ImageView) LayoutInflater.from(TalkActivity.this).inflate(R.layout.adapter_add, null).findViewById(R.id.adapter_img);
        List<String> datas = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            datas.add("item:" + (i + 1));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TalkActivity.this, 2);//getActivity().getApplicationContext()解决了兼容性问题，进行强制转换
        add_recycleView.setLayoutManager(gridLayoutManager);
        addTalkAdapter = new AddTalkAdapter(addList);
        add_recycleView.setAdapter(addTalkAdapter);
        addTalk_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopuwindows();

            }
        });
        talk_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TalkActivity.this,HomeFragment.class);
                startActivity(intent);
            }
        });
    }


    private void showPopuwindows() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindows, null);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 在底部显示
        window.showAtLocation(adapter_img,
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        final Button photo_btn = (Button) view.findViewById(R.id.photo_btn);
        Button camera_btn = (Button) view.findViewById(R.id.camera_btn);
        Button cancel_btn = (Button) view.findViewById(R.id.cancel_btn);
        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("One", "选择相册");
                choseHeadImageFromGallery();//选择相册
                window.dismiss();//销毁弹出框
            }
        });
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Two", "选择相机");
                cameraHeadImage();//选择相机
                window.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Three", "取消");
                window.dismiss();
            }

        });
    }

    /**
     * Intent请求回调函数
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);

            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
               addTalk_img.setImageBitmap(bitmap);//将图片显示在imageView上面


            } else {
                Toast.makeText(getApplicationContext(), "网络不给力", Toast.LENGTH_SHORT).show();

            }
        }

        //相机数据
        if (requestCode == REQUEST_TO_CAMERA) {
            Log.i("TAG", "程序运行" + requestCode);
            if (data == null) {
                tempFile = new File(Environment.getExternalStorageDirectory(), fileName);
                Log.i("TAG", "运行程序" + tempFile);
                crop(Uri.fromFile(tempFile));
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {

            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                addTalk_img.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(), "网络不给力", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    /**
     * 从相册选取照片
     **/
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 启动系统相机
     */
    private void cameraHeadImage() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName)));
        startActivityForResult(intent, REQUEST_TO_CAMERA);

    }
}





