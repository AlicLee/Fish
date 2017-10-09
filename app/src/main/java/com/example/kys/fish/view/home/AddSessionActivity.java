package com.example.kys.fish.view.home;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;
import com.example.kys.fish.adapter.ImagePickerAdapter;
import com.example.kys.fish.customwidget.SelectDialog;
import com.example.kys.fish.presenter.AddSessionPresenter;
import com.example.kys.fish.presenter.OnRecyclerItemClickListener;
import com.example.kys.fish.presenter.RecyclerItemDeleteListener;
import com.example.kys.fish.presenter.impl.AddSessionImpl;
import com.example.kys.fish.util.DateUtil;
import com.example.kys.fish.util.GlideImageLoader;
import com.example.kys.fish.util.StringUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Lee on 2017/10/1.
 */

public class AddSessionActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener,
        RecyclerItemDeleteListener.OnDragListener, AddSessionImpl.View {
    @InjectView(R.id.session_leftback)
    ImageView sessionLeftback;
    @InjectView(R.id.session_input_edit)
    EditText sessionInputEdit;
    @InjectView(R.id.publish_session)
    ImageView publishSession;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.delete_img_tv)
    TextView deleteImgTv;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    ItemTouchHelper itemTouchHelper;
    AddSessionPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsession);
        ButterKnife.inject(this);
        selImageList = new ArrayList<>();
        initView();
        init();
    }

    private void init() {
        presenter = new AddSessionPresenter(this);
        sessionInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtil.isEmpty(charSequence.toString()) && selImageList.size() <= 1) {
                    publishSession.setEnabled(false);
                } else {
                    publishSession.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        publishSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.AddSession(sessionInputEdit.getText().toString(), DateUtil.getCurrentTime(), selImageList);
            }
        });
    }

    static {
        //加载图片选择器
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount, manager);
        adapter.setOnItemClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        itemTouchHelper = new ItemTouchHelper(new RecyclerItemDeleteListener(this, adapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                RecyclerItemDeleteListener.deletePosition = vh.getLayoutPosition();
                if (vh.getLayoutPosition() != selImageList.size()) {
                    itemTouchHelper.startDrag(vh);
                    Vibrator vib = (Vibrator) AddSessionActivity.this.getSystemService(Service.VIBRATOR_SERVICE);
                    vib.vibrate(40);
                }
            }
        });
    }

    @OnClick({R.id.session_leftback, R.id.publish_session})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.session_leftback:
                this.finish();
                break;
        }
    }

    //recyclerView的点击事件
    @Override
    public void onItemClick(View view, final int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(AddSessionActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(AddSessionActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }
                    }
                }, names);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                    publishSession.setEnabled(true);
                } else {
                    if (sessionInputEdit.getText().toString().trim().length() == 0) {
                        publishSession.setEnabled(false);
                    }
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                    publishSession.setEnabled(true);
                } else {
                    if (sessionInputEdit.getText().toString().trim().length() == 0) {
                        publishSession.setEnabled(false);
                    }
                }
            }
        }
    }

    @Override
    public void onFinishDrag() {

    }

    @Override
    public void deleteState(boolean delete) {
        if (delete) {
            deleteImgTv.setBackgroundResource(R.color.holo_red_dark);
            deleteImgTv.setText("放开手指,进行删除");
        } else {
            deleteImgTv.setText("拖动此处进行删除");
            deleteImgTv.setBackgroundResource(R.color.holo_red_light);
        }

    }

    @Override
    public void dragState(boolean start) {
        if (start) {
            deleteImgTv.setVisibility(View.VISIBLE);
        } else {
            deleteImgTv.setVisibility(View.GONE);
        }

//        selImageList.sort();
    }

    @Override
    public void isDelete(int position) {
        Log.e("tag", "确认删除：" + position);
        if (position >= 0) {
            selImageList.remove(position);
            initView();
        }
    }

    @Override
    public void setPresenter(Object presenter) {
        this.presenter = (AddSessionPresenter) presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showAddSessionSuccess() {

    }

    @Override
    public void showAddSessionError() {

    }
}
