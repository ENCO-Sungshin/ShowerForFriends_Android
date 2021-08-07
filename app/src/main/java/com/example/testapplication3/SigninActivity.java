package com.example.testapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SigninActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        EditText passwordEditText = findViewById(R.id.login_password);

        EditText usernameEditText = findViewById(R.id.login_id);

        Button loginbtn = findViewById(R.id.loginButton);



        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                String email = usernameEditText.getText().toString();

                String password = passwordEditText.getText().toString();


                Amplify.Auth.signIn(

                        email,

                        password,

                        result -> {

                            if ( result.isSignInComplete() ){

                                RestOptions options = RestOptions.builder()
                                        .addPath("/profile")
                                        .addBody(("{\"name\":\""+email+"\"}").getBytes())
                                        .build();

                                Amplify.API.post(options,
                                        response -> Log.i("MyAmplifyApp", "POST succeeded: " + response),
                                        error -> Log.e("MyAmplifyApp", "POST failed.", error)
                                );


                                Intent intent = new Intent(SigninActivity.this, HomeActivity.class);

                                startActivity(intent);

                            } else {

                                Log.d("Amplify-Login", "User Sign In is not complete.");

                            }

                        },

                        error -> {

                            Log.e("Amplify-Login", error.toString());

                        }

                );



            }

        });


    }

}
