package com.example.kys.fish.config;

import com.example.kys.fish.model.Login;
import com.example.kys.fish.model.Person;

/**
 * Created by Lee on 2017/9/10.
 */

public class AppConfig {
    public static String TOKEN = "";
    public static int SUCCESS_CODE = 1;
    public static int FAILURE_CODE = 0;
    public static int TOKEN_EXPRIED = 40001;
    public static int CACHE_MAXSIZE = 1024 * 1024 * 10;
    public static Login login = new Login();
    public static Person person=new Person();
}
