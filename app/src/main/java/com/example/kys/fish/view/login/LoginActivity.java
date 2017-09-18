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
        TextView register_tv = (TextView) findViewById(R.id.register_tv);
        TextView forgetpassword_tv = (TextView) findViewById(R.id.forgetpassword_tv);
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @OnClick(R.id.login_login)
    public void onViewClicked() {
        String nickName = loginNickName.getText().toString();
        String passWord = loginPassWord.getText().toString();
        loginPresenter.login(nickName, passWord);
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
