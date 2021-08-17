package com.example.showerforfriends;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterHWActivity extends AppCompatActivity {

    EditText input_hw_id;
    Button register_btn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_h_w);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        input_hw_id = (EditText) findViewById(R.id.input_hw_id);
        register_btn = (Button) findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_hw_id.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "기기의 ID를 확인해주세요.", Toast.LENGTH_SHORT).show();
                else {

                }
            }
        });

        // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                //toast = Toast.makeText(this, "회원가입이 취소됩니다.", Toast.LENGTH_SHORT);
                androidx.appcompat.app.AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterHWActivity.this);
                dlg.setTitle("기기등록 취소");
                dlg.setMessage("기기등록을 취소하겠습니까?");
                //dlg.setIcon(R.drawable.face);
                dlg.setCancelable(false); //
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        finish();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 핸드폰 자체 뒤로가기
    private final long FINISH_INTERVAL_TIME = 1000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        androidx.appcompat.app.AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterHWActivity.this);
        dlg.setTitle("기기등록 취소");
        dlg.setMessage("기기등록을 취소하겠습니까?");
        //dlg.setIcon(R.drawable.face);
        dlg.setCancelable(false); //
        dlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                finish();
            }
        });
        dlg.setNegativeButton("아니요", null);
        dlg.show();
    }
}