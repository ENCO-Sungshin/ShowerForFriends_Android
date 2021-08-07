package com.example.showerforfriends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.HashMap;
import java.util.Map;

public class EnrollActivity extends AppCompatActivity {

    private Toolbar toolbar;
    final String TAG = EnrollActivity.class.getSimpleName();

    String userName;
    String userEmail;
    String userPassword;
    EditText putName_enroll, putEmailID_enroll, putPassword_enroll;
    Button enroll_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        putName_enroll = (EditText) findViewById(R.id.putName_enroll);
        putEmailID_enroll = (EditText) findViewById(R.id.putEmailID_enroll);
        putPassword_enroll = (EditText) findViewById(R.id.putPassword_enroll);

        enroll_Btn = (Button) findViewById(R.id.enroll_Btn);

        enroll_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), "확인", Toast.LENGTH_SHORT).show();
                signUp(putName_enroll.getText().toString(), putPassword_enroll.getText().toString(), putEmailID_enroll.getText().toString());
                //signUp(putEmailID_enroll.getText().toString(), putPassword_enroll.getText().toString());
            }
        });

        /*enroll_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(), "확인", Toast.LENGTH_SHORT).show();
                signUp(putName_enroll.getText().toString(), putPassword_enroll.getText().toString(), putEmailID_enroll.getText().toString());
                //signUp(putEmailID_enroll.getText().toString(), putPassword_enroll.getText().toString());
            }
        });*/

        // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*public void signUp(String username, String password, String email) {

        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);
        attributes.put("gender", "female");
        attributes.put("locale", "seoul");
        attributes.put("nickname", username);

        AWSMobileClient.getInstance().signUp(username, password, attributes, null, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                        if (!signUpResult.getConfirmationState()) {
                            final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                            Toast.makeText(getApplicationContext(), "인증 메일을 보냈습니다.: " + details.getDestination(), Toast.LENGTH_SHORT).show();

                            // 이메일에 문제가 없으면 인증 코드 창으로 이동
                            Intent i = new Intent(EnrollActivity.this, ConfirmSignUpActivity.class);
                            i.putExtra("email", email*//*username*//*); // username을 인증 코드 창에서 사용하기 위해
                            startActivity(i);
                            finish();
                        } else {
                            //makeToast("Sign-up done.");
                            Log.i(TAG, "balls2");
                        }
                    }
                });
            }


            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-up error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException)
                        Toast.makeText(EnrollActivity.this, ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    //System.out.println(((AmazonServiceException) e).getErrorMessage());
                });
            }
        });
    }*/

    public void signUp(String username, String password, String email) {
        Intent intent;
        //Register a user
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();
        Amplify.Auth.signUp(username, password, options,
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );

        Toast.makeText(getApplicationContext(), "인증 메일을 보냈습니다." , Toast.LENGTH_SHORT).show();

        // 이메일에 문제가 없으면 인증 코드 창으로 이동
        Intent i = new Intent(EnrollActivity.this, ConfirmSignUpActivity.class);
        i.putExtra("username", username); // username을 인증 코드 창에서 사용하기 위해
        i.putExtra("email",email);
        startActivity(i);
        finish();
    }

    // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*// 뒤로가기 2번 눌러야 종료
    private final long FINISH_INTERVAL_TIME = 1000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        // 뒤로 가기 할 경우 SignActivity 화면으로 이동
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            *//*Intent i = new Intent(EnrollActivity.this, EnrollActivity.class);
            startActivity(i);*//*
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }*/
}
