package com.example.showerforfriends;

public class Calculator {
    String getGrade(int wUsage){
        double wUsageL = wUsage*0.001;
        String s="";
        if (wUsageL>=0&&wUsageL<26.5){
            s+="고래";
        }else if(wUsageL>=26.5&&wUsageL<51){
            s+="거북이";
        }else if(wUsageL>=51&&wUsageL<76.5){
            s+="열대어";
        }else if(wUsageL>=76.5){
            s+="불가사리";
        }
        return s;
    }

}
