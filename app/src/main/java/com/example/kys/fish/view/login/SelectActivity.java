package com.example.kys.fish.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;
import com.example.kys.fish.util.DensityUtil;

import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Lee on 2017/9/15.
 */

public class SelectActivity extends BaseActivity {
    @InjectView(R.id.surfaceView)
    SurfaceView surfaceView;
    @InjectView(R.id.select_register_btn)
    Button selectRegisterBtn;
    @InjectView(R.id.select_login_login)
    Button selectLoginLogin;
    SharedPreferences preference;
    private MediaPlayer mediaPlayer;//全局变量，避免每次点击都创建新对象
    private SurfaceHolder holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.inject(this);
        judgeIsFirstRun();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playVideo();
    }

    /**
     * 播放视频
     */
    private void playVideo() {
        //设置成全屏沉浸式.
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        //设置视频播放
        holder = surfaceView.getHolder();
        surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(DensityUtil.initScreenWidth(this), DensityUtil.initScreenHeight(this)));
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mediaPlayer = MediaPlayer.create(SelectActivity.this, R.raw.video);
                mediaPlayer.setLooping(true);
                mediaPlayer.setDisplay(holder);//设置在surfaceView上播放
                mediaPlayer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                holder.removeCallback(this);
//                mediaPlayer.stop();
                mediaPlayer.release();
//                mediaPlayer = null;
            }
        });

    }

    /**
     * 判断是否是第一次运行
     */
    private void judgeIsFirstRun() {
        preference = this.getSharedPreferences("Fish", MODE_PRIVATE);
        boolean isFirst = preference.getBoolean("isFirstRun", true);
        if (isFirst) {
//            readExcelToDataBase();
        }
        SharedPreferences.Editor editor = this.getSharedPreferences("Fish", MODE_PRIVATE).edit();
        editor.putBoolean("isFirstRun", false);
        editor.apply();
        editor.commit();
    }

    private void readExcelToDataBase() {
        // 1、构造excel文件输入流对象
        InputStream is = getResources().openRawResource(R.raw.xx);
        // 2、声明工作簿对象
        Workbook rwb = null;
        try {
            rwb = Workbook.getWorkbook(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3、获得工作簿的个数,对应于一个excel中的工作表个数
        rwb.getNumberOfSheets();
        Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称
//        System.out.println("工作表名称：" + oFirstSheet.getName());
        int rows = oFirstSheet.getRows();//获取工作表中的总行数
        int columns = oFirstSheet.getColumns();//获取工作表中的总列数
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell oCell = oFirstSheet.getCell(j, i);//需要注意的是这里的getCell方法的参数，第一个是指定第几列，第二个参数才是指定第几行
                System.out.println(oCell.getContents() + "\r\n");
            }
        }
//        ScienceDBHelper dbHelper=new ScienceDBHelper(this,);
    }

    @OnClick({R.id.select_register_btn, R.id.select_login_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_register_btn:
                startActivity(new Intent(SelectActivity.this, RegisterActivity.class));
                break;
            case R.id.select_login_login:
                startActivity(new Intent(SelectActivity.this, LoginActivity.class));
                break;
        }
    }
}
