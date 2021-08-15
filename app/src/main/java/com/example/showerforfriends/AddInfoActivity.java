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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Button store_btn;
    RadioGroup radioGroup;
    RadioButton hair_1, hair_2, hair_3, hair_4, hair_5;
    EditText weight_input, tall_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        radioGroup = (RadioGroup) findViewById(R.id.hairgroup);
        hair_1 = (RadioButton) findViewById(R.id.hair_1);
        hair_2 = (RadioButton) findViewById(R.id.hair_2);
        hair_3 = (RadioButton) findViewById(R.id.hair_3);
        hair_4 = (RadioButton) findViewById(R.id.hair_4);
        hair_5 = (RadioButton) findViewById(R.id.hair_5);
        weight_input = (EditText) findViewById(R.id.weight_input);
        tall_input = (EditText) findViewById(R.id.tall_input);

        store_btn = (Button) findViewById(R.id.store_btn);


        store_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tall_input.getText().toString().equals("") == false && weight_input.getText().toString().equals("") == false)
                    finish();
                /*Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);*/
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
                androidx.appcompat.app.AlertDialog.Builder dlg = new AlertDialog.Builder(AddInfoActivity.this);
                dlg.setTitle("정보 수정");
                dlg.setMessage("정보 설정을 그만하시겠습니까?");
                //dlg.setIcon(R.drawable.face);
                dlg.setCancelable(false); //
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        //finishActivity(1);
                        finish();
                        //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 핸드폰 자체 뒤로가기 버튼 눌러 앱 종료
    @Override
    public void onBackPressed()
    {
        androidx.appcompat.app.AlertDialog.Builder dlg = new AlertDialog.Builder(AddInfoActivity.this);
        dlg.setTitle("정보 수정");
        dlg.setMessage("정보 설정을 그만하시겠습니까?");
        //dlg.setIcon(R.drawable.face);
        dlg.setCancelable(false); //
        dlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                //finishActivity(0);
                finish();
                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
        dlg.setNegativeButton("아니요", null);
        dlg.show();

        /*if(System.currentTimeMillis() - time > 2000)
        {
            time = System.currentTimeMillis();
            toast = Toast.makeText(this, "한번 더 누르시면 회원가입이 취소됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(System.currentTimeMillis() - time <= 2000)
        {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            //finishAffinity();
            toast.cancel();
        }*/
    }
}