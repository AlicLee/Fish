package com.example.kys.fish.view.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;
import com.example.kys.fish.presenter.impl.RegisterImpl;
import com.example.kys.fish.presenter.RegisterPresenter;
import com.example.kys.fish.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements RegisterImpl.View {
    int i = 60; // 设置短信验证提示时间为60s
    @InjectView(R.id.register_name)
    EditText registerName;
    @InjectView(R.id.phone_edit)
    EditText phoneEdit;
    @InjectView(R.id.passWord_edit)
    EditText passWordEdit;
    @InjectView(R.id.verifyCode_edit)
    EditText verifyCodeEdit;
    @InjectView(R.id.verifyCode_btn)
    TextView verifyCodeBtn;
    @InjectView(R.id.register)
    Button register;
    //    EditText []mEditTexts;
//    Button[]mButtons;
    RegisterPresenter registerPresenter;
    Handler smsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initView();
        initSDK();//短信初始化
        registerPresenter = new RegisterPresenter(this);
    }

    private void initView() {
//        mEditTexts = new EditText[]{registerName, phoneEdit, verifyCodeEdit,passWordEdit};
//        mButtons = new Button[]{ register};
    }

    //启动短信验证
    private void initSDK() {
        smsHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == -9) {
                    verifyCodeBtn.setText("重新发送(" + i + ")");
                } else if (msg.what == -8) {
                    verifyCodeBtn.setText("获取验证码");
                    verifyCodeBtn.setClickable(true); // 设置可点击
                    i = 30;
                } else {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //  短信注册成功后，返回MainActivity,然后提示
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                            Toast.makeText(getApplicationContext(), "提交验证码成功",
                                    Toast.LENGTH_SHORT).show();
                            // 验证成功后跳转主界面
                            //此处可注释掉
                            String nickName = phoneEdit.getText().toString();
                            String name = registerName.getText().toString();
                            String passWord = passWordEdit.getText().toString();
                            registerPresenter.Register(nickName, passWord, name);
//                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            Log.e("注册", "成功！");
//                            finish();// 成功跳转之后销毁当前页面
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            Toast.makeText(getApplicationContext(), "验证码已经发送",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Throwable throwable = (Throwable) data;
                            Log.e("Register", "短信验证错误:" + throwable.getMessage());
                        }
                    }
                }
            }
        };
        final EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {//短信SDK操作回调
                Message message = new Message();
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                smsHandler.sendMessage(message);
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }


    @OnClick({R.id.register_name, R.id.phone_edit, R.id.verifyCode_edit, R.id.verifyCode_btn, R.id.register, R.id.passWord_edit})
    public void onViewClicked(View view) {
        String phoneNumbers;
        switch (view.getId()) {
            case R.id.register_name:
                break;
            case R.id.phone_edit:
                break;
            case R.id.verifyCode_edit:
                break;
            case R.id.verifyCode_btn:
                phoneNumbers = phoneEdit.getText().toString();
                SMSSDK.getVerificationCode("86", phoneNumbers);
                verifyCodeBtn.setClickable(false);// 设置按钮不可点击 显示倒计时
                verifyCodeBtn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            smsHandler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);// 线程休眠实现读秒功能
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        smsHandler.sendEmptyMessage(-8);// 在30秒后重新显示为获取验证码
                    }
                }).start();
                break;
            case R.id.register:
                phoneNumbers = phoneEdit.getText().toString();
                if (!isRightPhone(phoneNumbers)) {
                    Toast.makeText(this, "手机格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(registerName.getText().toString().trim())) {
                    Toast.makeText(this, "名字不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passWordEdit.getText().toString().trim().length() <= 6) {
                    Toast.makeText(this, "密码长度不能小于6", Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", phoneNumbers, verifyCodeEdit.getText().toString());
                createProgressBar();
                break;
        }
    }


    @Override
    public void setPresenter(Object presenter) {
        this.registerPresenter = (RegisterPresenter) presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
    }

    @Override
    public void showRegisterSuccess() {
        Toast.makeText(getApplicationContext(), "注册成功,即将跳转", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void showRegisterError() {
        Toast.makeText(getApplicationContext(), "注册失败，处理问题", Toast.LENGTH_SHORT).show();
    }

    public static boolean isRightPhone(String phoneNumber) {
        /**
         * 国内手机号码验证规则：
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、（1349卫通）
         * 新增的4G手机号段：
         * 中国电信分到新号段170,177,联通分到了176,移动分到了178号段.
         */
        String regExp = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }

}
