package com.example.showerforfriends;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();
    TextView enrollButton_login, login_error_txt;
    Button home_btn, loginButton;
    EditText login_id, login_pw;
    /*String userName;
    String userEmail;
    String userPassword;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enrollButton_login = (TextView) findViewById(R.id.enrollButton_login);
        home_btn = (Button) findViewById(R.id.home_btn);
        loginButton = (Button) findViewById(R.id.loginButton);
        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_password);
        login_error_txt = findViewById(R.id.login_error_txt);

        //자동 로그인
        /*AWSMobileClient.getInstance().initialize(LoginActivity.this, new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(TAG, userStateDetails.getUserState().toString());
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onError(Exception e) {
                Log.e(TAG, e.toString());
            }
        });*/

        enrollButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnrollActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login_id.getText().toString().equals("") || login_pw.getText().toString().equals(""))
                {
                    login_error_txt.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "로그인 정보를 입력해주세요.", Toast.LENGTH_SHORT).show(); }
                else {
                    getSignIn();
                }
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    // 로그인 함수
    private void getSignIn() {
        String username = login_id.getText().toString();
        String password = login_pw.getText().toString();
        Amplify.Auth.signIn(
                username,
                password,

                result -> {Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                    if(result.isSignInComplete() == true) {
                        login_error_txt.setVisibility(View.INVISIBLE);
                        /*Toast.makeText(getApplicationContext(), "Sign-in done.", Toast.LENGTH_SHORT).show();*/
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();}
                },
                error -> {Log.e("AuthQuickstart", error.toString());
                    //Toast.makeText(this, "로그인 정보가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                    login_error_txt.setVisibility(View.VISIBLE);
                }
        );

       /* AWSMobileClient.getInstance().signIn(username, password, null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                        switch (signInResult.getSignInState()) {
                            case DONE:
                                Toast.makeText(getApplicationContext(), "Sign-in done.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                                break;
                            case SMS_MFA:
                                Toast.makeText(getApplicationContext(), "Please confirm sign-in with SMS.", Toast.LENGTH_SHORT).show();
                                break;
                            case NEW_PASSWORD_REQUIRED:
                                Toast.makeText(getApplicationContext(), "Please confirm sign-in with new password.", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Unsupported sign-in confirmation: " + signInResult.getSignInState(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-in error", e);
            }
        });*/
    }

    // 앱 종료할 때 사용
    private long time = 0;
    Toast toast;

    // 핸드폰 자체 뒤로가기 버튼 눌러 앱 종료
    @Override
    public void onBackPressed()
    {
        if(System.currentTimeMillis() - time > 2000)
        {
            time = System.currentTimeMillis();
            toast = Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(System.currentTimeMillis() - time <= 2000)
        {
            finishAffinity();
            toast.cancel();
        }
    }

}
