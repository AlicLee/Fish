package com.example.kys.fish.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;
import com.example.kys.fish.presenter.LoginPresenter;
import com.example.kys.fish.presenter.impl.LoginImpl;
import com.example.kys.fish.view.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Lee on 2017/9/6.
 */

public class LoginActivity extends BaseActivity implements LoginImpl.View {
    @InjectView(R.id.login_nickName)
    EditText loginNickName;
    @InjectView(R.id.login_passWord)
    EditText loginPassWord;
    @InjectView(R.id.login_login)
    Button loginLogin;
    LoginPresenter loginPresenter;
    private TextView register_tv;
    private TextView forgetpassword_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginPresenter = new LoginPresenter(this);
        TextView register_tv=(TextView)findViewById(R.id.register_tv);
        TextView forgetpassword_tv=(TextView)findViewById(R.id.forgetpassword_tv);
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventHandler eventHandler = new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        if (data instanceof Throwable) {
                            Throwable throwable = (Throwable) data;
                            String msg = throwable.getMessage();
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } else {

                            if (event ==SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                // 处理你自己的逻辑
                            }
                        }
                    }
                };

                // 注册监听器
                SMSSDK.registerEventHandler(eventHandler);


    }
        });
    }

        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，
        //并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        // SMSSDK.setAskPermisionOnReadContact(boolShowInDialog);
        // 创建EventHandler对象




    @OnClick(R.id.login_login)
    public void onViewClicked() {
        String nickName = loginNickName.getText().toString();
        String passWord = loginPassWord.getText().toString();
        loginPresenter.login(nickName, passWord);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void setPresenter(Object presenter) {
        this.loginPresenter=(LoginPresenter) presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
//        if(active){
//        }
    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(getApplicationContext(), "登陆成功,即将跳转", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError() {
        Toast.makeText(getApplicationContext(), "登陆失败，处理问题", Toast.LENGTH_SHORT).show();
    }
}
