package com.example.egemathematicsapp;

import android.app.Application;

import java.util.Objects;

public class MyApplication extends Application {

    private String userName;
    private String token;//сами переменные

    public String getSomeVariable(String variable) { //метод для получения
        if(Objects.equals(variable, "userName")){
            return userName;
        }
        else if(Objects.equals(variable, "token")){
            return token;
        }
        return null;
    }

    public void setSomeVariable(String key ,String someVariable) { //метод для изменения
        if(Objects.equals(key, "userName")){
            this.userName = someVariable;
        }
        if(Objects.equals(key, "token")){
            this.token = someVariable;
        }
    }

}