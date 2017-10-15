package com.example.kys.fish.view.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys.fish.R;
import com.example.kys.fish.model.MyApplication;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.materialdialog.MaterialDialog;


public class PersonFragment extends Fragment {
    View mView;
    @InjectView(R.id.personalHead_img)
    CircleImageView personalHeadImg;
    @InjectView(R.id.personalHead_tv)
    TextView personalHeadTv;
    @InjectView(R.id.personData_img)
    ImageView personDataImg;
    @InjectView(R.id.personData_tv)
    TextView personDataTv;
    @InjectView(R.id.harvestAddress_img)
    ImageView harvestAddressImg;
    @InjectView(R.id.harvestAddress_tv)
    TextView harvestAddressTv;
    @InjectView(R.id.logout_btn)
    TextView logoutBtn;
    private int PHOTO_REQUEST_GALLERY = 0;
    private int PHOTO_REQUEST_CUT = 1;
    private static final int REQUEST_TO_CAMERA = 3;
    private static final String fileName = "temp.jpg";
    private File tempFile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.inject(this, mView);
        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.personalHead_img, R.id.personalHead_tv, R.id.personData_img, R.id.personData_tv, R.id.harvestAddress_img, R.id.harvestAddress_tv, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personalHead_img:
                showListDialog();
                break;
            case R.id.personalHead_tv:
                break;
            case R.id.personData_img:
                break;
            case R.id.personData_tv:
                startActivity(new Intent(getActivity(), PersonalInformation_Activity.class));
                break;
            case R.id.harvestAddress_img:
                break;
            case R.id.harvestAddress_tv:
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
            case R.id.logout_btn:
                final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                materialDialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyApplication.getInstance().exit();
                        materialDialog.dismiss();

                    }
                });
                materialDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialDialog.dismiss();
                    }
                });
                materialDialog.show();
                break;

        }


    }
    private void showListDialog() {
        final String[] items = { "选取相册","相机拍照","取        消"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(getActivity());
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        choseHeadImageFromGallery();
                        dialog.dismiss();
                        break;
                    case 1:
                        cameraHeadImage();
                        dialog.dismiss();
                        break;
                    case 2:
                        dialog.dismiss();


                }
            }
        });
        listDialog.show();
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


    /**
     * Intent请求回调函数
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                personalHeadImg.setBackground(null);//覆盖头像
                personalHeadTv.setText(null);//覆盖“点击上传”这几个字
                personalHeadImg.setImageBitmap(bitmap);//将图片显示在imageView上面


            } else {
                Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();

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
                personalHeadImg.setBackground(null);//覆盖头像
                personalHeadTv.setText(null);//覆盖“点击上传”这几个字
                personalHeadImg.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
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


}
