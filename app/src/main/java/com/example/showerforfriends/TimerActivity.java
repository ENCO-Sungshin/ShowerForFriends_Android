package com.example.showerforfriends;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    boolean isClicked = false;
    int showerT = 0; // 샤워 시간 측정
    int waterT = 0; // 물 사용 시간 계산
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        b = (Button) findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isClicked == false){
                    isClicked = true;
                   /* ShowerThread st = new ShowerThread();
                    st.start();*/
                    b.setText("샤워 중 입니다");
                    //Toast.makeText(TimerActivity.this, "친환경적인 샤워를 시작해보아요!", Toast.LENGTH_SHORT).show();
                }
                else if(isClicked == true){
                    isClicked = false;
                    b.setText("샤워 시작하기");
                    //Toast.makeText(TimerActivity.this, "깨끗하게, 맑게, 자신있게!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ShowerThread extends Thread { // 샤워 시간 측정

        public ShowerThread(){
            showerT = 0;
        }

        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 스레드에게 수행시킬 동작들 구현
                    while(isClicked == true)
                    {
                        try {
                            showerT++;
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}