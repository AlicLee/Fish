package com.example.kys.fish.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;
import com.example.kys.fish.presenter.LoginPresenter;
import com.example.kys.fish.presenter.impl.LoginImpl;
import com.example.kys.fish.util.StringUtil;
import com.example.kys.fish.view.main.ForgetPasswordActivity;
import com.example.kys.fish.view.main.MainActivity;
import com.example.kys.fish.view.main.PhoneVerifyActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
    @InjectView(R.id.login_left_back)
    ImageView left_img;
    LoginPresenter loginPresenter;
    private TextView register_tv;
    private TextView forgetpassword_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginPresenter = new LoginPresenter(this);
        TextView register_tv = (TextView) findViewById(R.id.register_tv);
        TextView forgetpassword_tv = (TextView) findViewById(R.id.forgetpassword_tv);
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        forgetpassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PhoneVerifyActivity.class));

            }
        });
    }

    @OnClick({R.id.login_login, R.id.login_left_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_login:
                String nickName = loginNickName.getText().toString();
                String passWord = loginPassWord.getText().toString();
                if (StringUtil.isEmpty(nickName)) {
                    Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(passWord)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginPresenter.login(nickName, passWord);
                break;
            case R.id.login_left_back:
                this.finish();
                break;
        }
//        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    @Override
    public void setPresenter(Object presenter) {
        this.loginPresenter = (LoginPresenter) presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(getApplicationContext(), "登陆成功,即将跳转", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    public void showLoginError() {
        Toast.makeText(getApplicationContext(), "登陆失败，处理问题", Toast.LENGTH_SHORT).show();
    }
}
