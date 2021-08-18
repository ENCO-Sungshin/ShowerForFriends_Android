package com.example.showerforfriends;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;

public class RegisterHWActivity extends AppCompatActivity {

    EditText input_hw_id;
    Button register_btn;
    private Toolbar toolbar;
    TextView display_id_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_h_w);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        input_hw_id = (EditText) findViewById(R.id.input_hw_id);
        register_btn = (Button) findViewById(R.id.register_btn);
        display_id_check = (TextView) findViewById(R.id.display_id_check);

        //값이 없을 때 예외 처리하기..
        String loadInfo = "{" +
                "\"user_id\" : \"" + Amplify.Auth.getCurrentUser().getUserId() + "\"}";

        RestOptions options = RestOptions.builder()
                .addHeader("Accept","application/hal+json")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addPath("/info")
                .addBody(loadInfo.getBytes())
                .build();

        Amplify.API.post(options,
                response -> {
                    String res = response.getData().asString();
                    Integer display_index = res.indexOf("\"display_id\"");
                    String display_data = res.substring(display_index, res.substring(display_index).indexOf(",") + display_index);
                    String display_value = display_data.substring(display_data.indexOf(":") + 1);
                    System.out.println(display_value);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            display_id_check.setText(display_value);
                        }
                    });

                    /*if (display_value != 0) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterHWActivity.this);
                        dlg.setTitle("기기 등록");
                        dlg.setMessage("이미 등록된 기기가 있습니다. 새로 등록하시겠습니까?");
                        //dlg.setIcon(R.drawable.face);
                        dlg.setCancelable(false); //
                        dlg.setPositiveButton("예", null);
                        dlg.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        dlg.show();
                    }*/
                },
                error -> {
                    Log.e("MyAmplifyApp", "POST failed: ", error);
                });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_hw_id.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "기기의 ID를 작성했는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    String display_num = display_id_check.getText().toString();
                    System.out.println(display_num);
                    if(display_num.equals("null")) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterHWActivity.this);
                        dlg.setTitle("기기 등록");
                        dlg.setMessage("기기를 등록합니다.");
                        //dlg.setIcon(R.drawable.face);
                        dlg.setCancelable(false); //
                        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // update database(user info)
                                String inputInfo = "{" +
                                        "\"user_id\" : \"" + Amplify.Auth.getCurrentUser().getUserId() + "\", " +
                                        "\"display_id\" : \"" + input_hw_id.getText().toString() + "\"}";

                                RestOptions options = RestOptions.builder()
                                        .addHeader("Accept", "application/hal+json")
                                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                                        .addPath("/info")
                                        .addBody(inputInfo.getBytes())
                                        .build();

                                Amplify.API.put(options,
                                        response -> {
                                            Log.i("MyAmplifyApp", "POST succeeded: " + response);
                                        },
                                        error -> {
                                            Log.e("MyAmplifyApp", "POST failed: ", error);
                                        });
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        });
                        dlg.setNegativeButton("아니요", null);
                        dlg.show();
                    }
                    else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterHWActivity.this);
                        dlg.setTitle("기기 등록");
                        dlg.setMessage("이미 등록된 기기가 있습니다. 새로 등록하시겠습니까?");
                        //dlg.setIcon(R.drawable.face);
                        dlg.setCancelable(false); //
                        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // update database(user info)
                                String inputInfo = "{" +
                                        "\"user_id\" : \"" + Amplify.Auth.getCurrentUser().getUserId() + "\", " +
                                        "\"display_id\" : \"" + input_hw_id.getText().toString() + "\"}";

                                RestOptions options = RestOptions.builder()
                                        .addHeader("Accept", "application/hal+json")
                                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                                        .addPath("/info")
                                        .addBody(inputInfo.getBytes())
                                        .build();

                                Amplify.API.put(options,
                                        response -> {
                                            Log.i("MyAmplifyApp", "POST succeeded: " + response);
                                        },
                                        error -> {
                                            Log.e("MyAmplifyApp", "POST failed: ", error);
                                        });

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        });
                        dlg.setNegativeButton("아니요", null);
                        dlg.show();
                    }
                    /*String loadInfo = "{" +
                            "\"user_id\" : " + 0 + "}";

                    RestOptions options = RestOptions.builder()
                            .addHeader("Accept","application/hal+json")
                            .addHeader("Content-Type","application/json;charset=UTF-8")
                            .addPath("/info")
                            .addBody(loadInfo.getBytes())
                            .build();

                    Amplify.API.put(options,
                            response -> {
                        String res = response.getData().asString();
                        Integer display_index = res.indexOf("\"display_id\"");
                        String display_data = res.substring(display_index, res.substring(display_index).indexOf(",") + display_index);
                        Integer display_value = Integer.parseInt(display_data.substring(display_data.indexOf(":") + 1));
                        if(display_value != 0)
                        {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterHWActivity.this);
                            dlg.setTitle("기기 등록");
                            dlg.setMessage("기기를 등록합니다.");
                            //dlg.setIcon(R.drawable.face);
                            dlg.setCancelable(false); //
                            dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }););
                            dlg.setNegativeButton("아니요", null);
                            dlg.show();
                        }
                        Log.i("MyAmplifyApp", "POST succeeded: " + response);
                        },
                            error -> {
                                Log.e("MyAmplifyApp", "POST failed: ", error);
                            });*/
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