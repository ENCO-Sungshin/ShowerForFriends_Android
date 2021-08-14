package com.example.showerforfriends;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class ConfirmSignUpActivity extends AppCompatActivity {

    String TAG = ConfirmSignUpActivity.class.getSimpleName();
    String username, email, password;
    /*String username, userPassword;*/
    EditText confirmTxt;
    Button confirm_btn;
    TextView confirm_email_error_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);

        //confirmTxt = (EditText) findViewById(R.id.confirmTxt);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        confirm_email_error_txt = (TextView) findViewById(R.id.confirm_email_error_txt);

        // EnrollActivity 에서 사용된 username 정보를 가져와 TextView에 넣는다.
        TextView TextView = findViewById(R.id.signUpUsername2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("username");
        email = bundle.getString("email");
        password = bundle.getString("password");
        TextView.setText(username);


        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* EditText*/
                confirmTxt = findViewById(R.id.confirmTxt);
                String code = confirmTxt.getText().toString();

                if(confirmTxt.getText().toString().equals(""))
                {
                    confirm_email_error_txt.setText("인증번호가 입력되지 않았습니다.");
                }
                else {
                    Amplify.Auth.confirmSignUp(//이메일 인증이 완료된다면 username으로 회원가입
                            username,
                            code,
                            result ->
                            {
                                Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                                if (result.isSignUpComplete() == true) {
                                    /*androidx.appcompat.app.AlertDialog.Builder dlg = new AlertDialog.Builder(ConfirmSignUpActivity.this);
                                    dlg.setTitle("환영합니다!");
                                    dlg.setMessage("회원가입이 성공적으로 완료되었습니다! 환영합니다!");
                                    //dlg.setIcon(R.drawable.face);
                                    dlg.setCancelable(false); //
                                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(ConfirmSignUpActivity.this, LoginActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                                    dlg.show();*/
                                    //confirm_email_error_txt.setVisibility(View.INVISIBLE);
                                    confirm_email_error_txt.setText("");
                                    //Toast.makeText(getApplicationContext(),"성공적으로 회원가입 되셨습니다..", Toast.LENGTH_SHORT).show();

                                    //put user data
                                    String userinfo = "{" +
                                            "\"user_id\":" + "\"" + "0" + "\","
                                            + "\"user_name\":" + "\"" + username + "\","
                                            + "\"user_email\":" + "\"" + email + "\""
                                            + "}";
                                    RestOptions options = RestOptions.builder()
                                            .addPath("/users")
                                            .addBody(userinfo.getBytes())
                                            .build();
                                    Amplify.API.post(options,
                                            response -> Log.i("MyAmplifyApp", "POST succeeded: " + response),
                                            error -> Log.e("MyAmplifyApp", "POST failed.", error)
                                    );

                                    Intent i = new Intent(ConfirmSignUpActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            },
                            error -> {
                                //confirm_email_error_txt.setText("* 인증번호가 다르거나 이미 가입된 이메일입니다. *");
                                //confirm_email_error_txt.setVisibility(View.VISIBLE);

                                    /*androidx.appcompat.app.AlertDialog.Builder dlg = new AlertDialog.Builder(ConfirmSignUpActivity.this);
                                    dlg.setTitle("회원가입 오류");
                                    dlg.setMessage("\t- 이미 가입된 이메일\n\t- 잘못된 인증번호 입력\n\n위 두가지 이유 중 하나로 회원가입할 수 없습니다.");
                                    //dlg.setIcon(R.drawable.face);
                                    dlg.setCancelable(false); //
                                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            confirm_email_error_txt.setText("* 인증번호가 다르거나 이미 가입된 이메일입니다. *");
                                            confirm_email_error_txt.setVisibility(View.VISIBLE);
                                            //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        }
                                    });
                                    dlg.show();*/

                                Log.e("AuthQuickstartE", error.toString());
                                confirm_email_error_txt.setText("* 인증번호가 다르거나 이미 가입된 이메일입니다. *");
                            }
                    );

                    confirmTxt.setText("");
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

    // 뒤로가기 2번 눌러야 종료
    private final long FINISH_INTERVAL_TIME = 1000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        androidx.appcompat.app.AlertDialog.Builder dlg = new AlertDialog.Builder(ConfirmSignUpActivity.this);
        dlg.setTitle("회원가입 취소");
        dlg.setMessage("회원가입을 취소하겠습니까?");
        //dlg.setIcon(R.drawable.face);
        dlg.setCancelable(false); //
        dlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        dlg.setNegativeButton("아니요", null);
        dlg.show();
        // 뒤로 가기 할 경우 SignActivity 화면으로 이동
        /*if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            Intent i = new Intent(ConfirmSignUpActivity.this, EnrollActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }*/
    }
}