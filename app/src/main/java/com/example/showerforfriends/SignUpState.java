package com.example.showerforfriends;

import android.app.Application;

public class SignUpState extends Application {

    private String useremail;
    private boolean state;

    @Override
    public void onCreate() {
        //전역 변수 초기화
        state = false;
        useremail=null;
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setState(boolean state){
        this.state = state;
    }
    public void setEmail(String email){
        this.useremail=email;
    }

    public boolean getState(){
        return state;
    }

    public String getEmail(){
        return useremail;
    }
}