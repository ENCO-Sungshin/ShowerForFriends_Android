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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Amplify;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringJoiner;

public class AddInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Button store_btn;
    RadioGroup radioGroup;
    RadioButton hair_1, hair_2, hair_3, hair_4, hair_5;
    EditText weight_input, tall_input;
    public int select_hair, select_weight, select_tall;
    String hair_data, height_data, weight_data, name_data;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        hair_data = bundle.getString("hair");
        height_data = bundle.getString("height");
        weight_data = bundle.getString("weight");

        if(Integer.parseInt(hair_data) == 1 || Integer.parseInt(hair_data) == 2 || Integer.parseInt(hair_data) == 3 || Integer.parseInt(hair_data) == 4 || Integer.parseInt(hair_data) == 5)
        {
            switch (hair_data) {
                case "1":
                    hair_1.setChecked(true);
                    break;

                case "2":
                    hair_2.setChecked(true);
                    break;

                case "3":
                    hair_3.setChecked(true);
                    break;

                case "4":
                    hair_4.setChecked(true);
                    break;

                case "5":
                    hair_5.setChecked(true);
                    break;
            }
        }
        if(height_data.equals("0")) tall_input.setText("");
        else tall_input.setText(height_data);

        if(weight_data.equals("0")) weight_input.setText("");
        else weight_input.setText(weight_data);

        /*// get database
        String loadInfo = "{" +
                "\"user_id\" : " + 0 +
                "\"user_name\" : " + "\"\", " +
                "\"user_height\" : " + "\"\", " +
                "\"user_weight\" : " + "\"\", " +
                "\"user_hair\" : " + "\"\"" + "}";

        Toast.makeText(AddInfoActivity.this, loadInfo, Toast.LENGTH_SHORT).show();
        RestOptions options = RestOptions.builder()
                .addHeader("Accept","application/hal+json")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addPath("/test/")
                .addBody(loadInfo.getBytes())
                .build();
        Amplify.API.post(options,
                response -> {
                    Log.i("MyAmplifyApp", "GET succeeded: " + response);

                    //RestResponse res = response;
                    System.out.println("String : " + response.getData().asString());

                    String allData = response.getData().asString();
                    Integer hair_index = allData.indexOf("\"user_hair\"");
                    hair_data = allData.substring(hair_index, allData.substring(hair_index).indexOf(",") + hair_index);
                    System.out.println("Hair : " + hair_data);

                    Integer height_index = allData.indexOf("\"user_height\"");
                    height_data = allData.substring(height_index, allData.substring(height_index).indexOf(",") + height_index);
                    System.out.println("Height : " + height_data);

                    Integer weight_index = allData.indexOf("\"user_weight\"");
                    weight_data = allData.substring(weight_index, allData.substring(weight_index).indexOf(",") + weight_index);
                    System.out.println("Weight : " + weight_data);

                    Integer name_index = allData.indexOf("\"user_name\"");
                    name_data = allData.substring(name_index, allData.substring(name_index).indexOf("}") + name_index);
                    System.out.println("Name : " + name_data);
                    //Toast.makeText(AddInfoActivity.this, res.toString(), Toast.LENGTH_SHORT).show();

                },
                error -> Log.e("MyAmplifyApp", "GET failed: ", error));*/


        /*if(hair_data.equals("0") == false) {
            switch (hair_data) {
                case "1":
                    hair_1.setChecked(true);
                    break;

                case "2":
                    hair_2.setChecked(true);
                    break;

                case "3":
                    hair_3.setChecked(true);
                    break;

                case "4":
                    hair_4.setChecked(true);
                    break;

                case "5":
                    hair_5.setChecked(true);
                    break;
            }
        }*//*

        if(height_data.equals("0") == false) tall_input.setText(height_data);
        if(weight_data.equals("0") == false) weight_input.setText(weight_data);*/


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.hair_1 :
                        select_hair = 1;
                        //Toast.makeText(AddInfoActivity.this, "머리 길이 : 짧은 머리 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.hair_2:
                        select_hair = 2;
                        //Toast.makeText(AddInfoActivity.this, "머리 길이 : 귀 위 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.hair_3:
                        select_hair = 3;
                        //Toast.makeText(AddInfoActivity.this, "머리 길이 : 귀 ~ 어깨 사이 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.hair_4:
                        select_hair = 4;
                        //Toast.makeText(AddInfoActivity.this, "머리 길이 : 어깨 아래 ~ 가슴 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        break;
                        
                    case R.id.hair_5:
                        select_hair = 5;
                        //Toast.makeText(AddInfoActivity.this, "머리 길이 : 가슴 아래 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        /*if(weight_input.getText().toString().equals("0") == false) {
            select_weight = Integer.parseInt(weight_input.getText().toString());
        }

        if(tall_input.getText().toString().equals("0") == false) {
            select_tall = Integer.parseInt(tall_input.getText().toString());
        }*/


        store_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((hair_1.isChecked() == true || hair_2.isChecked() == true || hair_3.isChecked() == true || hair_4.isChecked() == true || hair_5.isChecked() == true)
                        && weight_input.getText().toString().equals("") == false && tall_input.getText().toString().equals("") == false)
                {
                    select_weight = Integer.parseInt(weight_input.getText().toString());
                    select_tall = Integer.parseInt(tall_input.getText().toString());

                    if(select_weight > 0 && select_tall > 0) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(AddInfoActivity.this);
                        dlg.setTitle("정보 수정");
                        dlg.setMessage("정보 설정을 완료하시겠습니까?");
                        //dlg.setIcon(R.drawable.face);
                        dlg.setCancelable(false); //
                        dlg.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // update database(user info)
                                String inputInfo = "{" +
                                        "\"user_id\" : " + 0 + ", " +
                                        "\"user_hair\" : " + select_hair + ", " +
                                        "\"user_height\" : " + select_tall + ", " +
                                        "\"user_weight\" : " + select_weight + "}";

                                //Toast.makeText(AddInfoActivity.this, inputInfo, Toast.LENGTH_SHORT).show();
                                RestOptions options = RestOptions.builder()
                                        .addHeader("Accept", "application/hal+json")
                                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                                        .addPath("/users/")
                                        .addBody(inputInfo.getBytes())
                                        .build();
                                Amplify.API.put(options,
                                        response -> Log.i("MyAmplifyApp", "PUT succeeded: " + response),
                                        error -> Log.e("MyAmplifyApp", "PUT failed: ", error));

                                //finishActivity(1);
                                finish();
                                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        });
                        dlg.setNegativeButton("아니요", null);
                        dlg.show();
                    }
                    else
                    {
                        Toast.makeText(AddInfoActivity.this, "키와 몸무게 값을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(AddInfoActivity.this, "정보를 모두 작성해주세요.", Toast.LENGTH_SHORT).show();
                }
                /*Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);*/
            }
        });

        // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(AddInfoActivity.this, "선택하셨습니다", Toast.LENGTH_SHORT).show();
        }
    };
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