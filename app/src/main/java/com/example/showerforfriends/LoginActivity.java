package com.example.showerforfriends;

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
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();
    TextView enrollButton_login;
    Button home_btn, loginButton;
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
                getSignIn();
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        //getHashKey();
    }

    // 로그인 함수
    private void getSignIn() {
        EditText login_id = findViewById(R.id.login_id);
        EditText login_pw = findViewById(R.id.login_password);
        String username = login_id.getText().toString();
        String password = login_pw.getText().toString();

        Amplify.Auth.signIn(
                username,
                password,
                result -> {
                    Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                },
                error -> Log.e("AuthQuickstart", error.toString())
        );

//        Amplify.Auth.fetchUserAttributes(
//                attributes -> Log.i("AuthDemo", "User attributes = " + Amplify.Auth.getCurrentUser().getUserId()),
//                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
//        );
//        Amplify.Auth.fetchAuthSession(
//                result -> {
//                    AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
//                    switch(cognitoAuthSession.getIdentityId().getType()) {
//                        case SUCCESS:
//                            Log.i("AuthQuickStart", "IdentityId: " + cognitoAuthSession.getIdentityId().getValue());
//                            Log.i("AuthQuickStart", "Credentials: " + cognitoAuthSession.getAWSCredentials().getValue().toString());
//                            Log.i("AuthQuickStart", "IdentityId toString: " +cognitoAuthSession.getIdentityId().getValue().toString());
//                            break;
//                        case FAILURE:
//                            Log.i("AuthQuickStart", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
//                    }
//                },
//                error -> Log.e("AuthQuickStart", error.toString())
//        );

        Toast.makeText(getApplicationContext(), "Sign-in done.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
        finish();

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

    /*private void getHashKey()
    {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }*/

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
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }*/
}
