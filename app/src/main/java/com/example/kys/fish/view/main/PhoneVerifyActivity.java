package com.example.kys.fish.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys.fish.R;
import com.example.kys.fish.view.login.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.example.kys.fish.R.id.forgetPassword_verifyCode_tv;

public class PhoneVerifyActivity extends AppCompatActivity {
    @InjectView(R.id.forgetPassword_verifyCode_edit)
    EditText forgetPasswordVerifyCodeEdit;
    @InjectView(forgetPassword_verifyCode_tv)
    TextView forgetPasswordVerifyCodeTv;
    @InjectView(R.id.submit_btn)
    Button submitBtn;
    @InjectView(R.id.forgetPassWord_phoneEdit)
    EditText forgetPassWordPhoneEdit;
    Handler smsHandler;
    int i = 60; // 设置短信验证提示时间为60s
    @InjectView(R.id.newPassword_edit)
    EditText newPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        ButterKnife.inject(this);
        /**设置密码只能输入字母，数字*/
        newPasswordEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        String digists = "1234567890qwertyuiopasdfghjklzxcvbnm";
        /**设置密码不可见*/
        newPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


    }

    @OnClick({R.id.forgetPassword_verifyCode_edit, forgetPassword_verifyCode_tv, R.id.submit_btn, R.id.forgetPassWord_phoneEdit, R.id.newPassword_edit})
    public void onViewClicked(View view) {
        String phoneNumbers;
        switch (view.getId()) {
            case R.id.forgetPassword_verifyCode_edit:
                break;
            case forgetPassword_verifyCode_tv:
                if (!forgetPassWordPhoneEdit.getText().toString().equals("")) {
                    initSDK();
                    phoneNumbers = forgetPassWordPhoneEdit.getText().toString();
                    SMSSDK.getVerificationCode("86", phoneNumbers);
                    forgetPasswordVerifyCodeTv.setClickable(false);// 设置按钮不可点击 显示倒计时
                    forgetPasswordVerifyCodeTv.setText("重新发送(" + i + ")");
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
                }
                break;
            case R.id.submit_btn:
                phoneNumbers = forgetPassWordPhoneEdit.getText().toString();
                if (!isRightPhone(phoneNumbers)) {
                    Toast.makeText(this, "手机格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPasswordEdit.getText().toString().trim().length() <= 6) {
                    Toast.makeText(this, "密码长度不能小于6", Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", phoneNumbers, forgetPasswordVerifyCodeEdit.getText().toString());
                createProgressBar();
                break;
            case R.id.forgetPassWord_phoneEdit:
                break;
            case R.id.newPassword_edit:
                break;
        }
    }

    //启动短信验证
    private void initSDK() {
        smsHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == -9) {
                    forgetPasswordVerifyCodeTv.setText("重新发送(" + i + ")");
                } else if (msg.what == -8) {
                    forgetPasswordVerifyCodeTv.setText("获取验证码");
                    forgetPasswordVerifyCodeTv.setClickable(true); // 设置可点击
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
                            startActivity(new Intent(PhoneVerifyActivity.this, LoginActivity.class));
                            // 验证成功后跳转主界面
//                            //此处可注释掉
//                            String nickName = phoneEdit.getText().toString();
//                            String name = registerName.getText().toString();
//                            String passWord = passWordEdit.getText().toString();
//                            registerPresenter.Register(nickName, passWord, name);
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
