package com.example.testapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.amplifyframework.core.Amplify;

public class ConfirmSignUpActivity extends AppCompatActivity {
    String username, email;
    EditText confirmTxt;
    Button confirm_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //confirmTxt = (EditText) findViewById(R.id.confirmTxt);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);

        // EnrollActivity 에서 사용된 username 정보를 가져와 TextView에 넣는다.
        TextView TextView = findViewById(R.id.signUpUsername2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("username");
        email = bundle.getString("email");
        TextView.setText(username);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* EditText*/
                confirmTxt = findViewById(R.id.confirmTxt);
                String code = confirmTxt.getText().toString();

                Amplify.Auth.confirmSignUp(
                        username,
                        code,
                        result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );


            }
        });

    }
}