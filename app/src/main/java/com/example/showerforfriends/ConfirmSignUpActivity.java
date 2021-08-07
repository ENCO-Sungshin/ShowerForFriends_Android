package com.example.showerforfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.amplifyframework.core.Amplify;

public class ConfirmSignUpActivity extends AppCompatActivity {

    String TAG = ConfirmSignUpActivity.class.getSimpleName();
    String username, email;
    /*String username, userPassword;*/
    EditText confirmTxt;
    Button confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);

        //confirmTxt = (EditText) findViewById(R.id.confirmTxt);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);

        // EnrollActivity 에서 사용된 username 정보를 가져와 TextView에 넣는다.
        TextView TextView = findViewById(R.id.signUpUsername2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("username");
        email = bundle.getString("email");
        /*username = bundle.getString("email");*/
        TextView.setText(username);

       /* confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* EditText*//* confirmTxt = findViewById(R.id.confirmTxt);
                String code = confirmTxt.getText().toString();

                AWSMobileClient.getInstance().confirmSignUp(username, code, new Callback<SignUpResult>() {
                    @Override
                    public void onResult(final SignUpResult signUpResult) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                                if (!signUpResult.getConfirmationState()) {
                                    final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();

                                    Toast.makeText(getApplicationContext(), "Confirm sign-up with: " + details.getDestination(), Toast.LENGTH_SHORT).show();


                                } else {

                                    // 회원가입이 완료되면 로그인 창으로 이동
                                    Toast.makeText(getApplicationContext(),"성공적으로 회원가입 되셨습니다..", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ConfirmSignUpActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Confirm sign-up error", e);
                    }
                });
                *//*if(confirmTxt.getText() != null)
                {
                    confirmSignUp(confirmTxt.getText().toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "인증번호를 입력해주세요.", Toast.LENGTH_SHORT);
                }*//*
            }
        });*/


        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* EditText*/
                confirmTxt = findViewById(R.id.confirmTxt);
                String code = confirmTxt.getText().toString();

                /*Amplify.Auth.confirmSignUp(
                        username,
                        code,
                        result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );*/

                if(confirmTxt.getText() != null)
                {
                    Amplify.Auth.confirmSignUp(
                            username,
                            code,
                            result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                            error -> Log.e("AuthQuickstart", error.toString())
                    );

                    Toast.makeText(getApplicationContext(),"성공적으로 회원가입 되셨습니다..", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ConfirmSignUpActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                    /*confirmSignUp(confirmTxt.getText().toString());*/
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "인증번호를 입력해주세요.", Toast.LENGTH_SHORT);
                }
            }
        });

    }
/*
    public void confirmSignUp(String code)
    {
        AWSMobileClient.getInstance().confirmSignUp(userEmail, code, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult)
            {
                runOnUiThread(() -> {
                    //Log.d(TAG, "Sign-up callcak state: " + signUpResult.getConfirmationState());
                    if(!signUpResult.getConfirmationState()) {
                        final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                        Toast.makeText(getApplicationContext(), "Confirm sign-up with : " + details.getDestination(), Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Sign-up done.", Toast.LENGTH_SHORT);
                        _signIn(userEmail, userPassword);
                    }
                });
            }
            @Override
            public void onError(Exception e)
            {
                // Log.e(TAG, "Confirm sign-up error", e);
                runOnUiThread(() -> {
                    if(e instanceof AmazonServiceException)
                        Toast.makeText(getApplicationContext(), ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void _signIn(String userEmail, String password)
    {
        AWSMobileClient.getInstance().signIn(userEmail, password, null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult)
            {
                runOnUiThread(() -> {
                    // Log.d(TAG, "Sign-in callback state : " + signInResult.getSignInState());
                    switch(signInResult.getSignInState())
                    {
                        case DONE:
                            Toast.makeText(getApplicationContext(), "Sign-ing done.", Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            break;
                        case SMS_MFA:
                            Toast.makeText(getApplicationContext(), "Please confirm sign-in with SMS.", Toast.LENGTH_SHORT);
                            break;
                        case NEW_PASSWORD_REQUIRED:
                            Toast.makeText(getApplicationContext(), "Please confirm sign-in with new password.", Toast.LENGTH_SHORT);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Unsupported sign-in confirmation: " + signInResult.getSignInState(), Toast.LENGTH_SHORT);
                            break;
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                //Log.e(TAG, "Sign-in error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException)
                        Toast.makeText(getApplicationContext(), ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT);
                });
            }
        });
    }*/

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
            Intent i = new Intent(ConfirmSignUpActivity.this, EnrollActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }*/
}