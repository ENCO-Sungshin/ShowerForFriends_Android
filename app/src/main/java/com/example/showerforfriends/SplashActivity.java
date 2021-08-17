package com.example.showerforfriends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.plugin.Plugin;

public class SplashActivity extends AppCompatActivity {

    /*public static AWSApiPlugin awsApiPlugin;
    public static AWSCognitoAuthPlugin awsCognitoAuthPlugin;*/
    Intent intent;
    private void _initCognito() {

       /* Amplify.Auth.fetchAuthSession(
                result -> {
                    AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
                    switch(cognitoAuthSession.getIdentityId().getType()) {
                        case SUCCESS:
                            Log.i("AuthQuickStart", "IdentityId: " + cognitoAuthSession.getIdentityId().getValue());
                            // Open Main Activity
                            intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case FAILURE:
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Log.i("AuthQuickStart", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
                            break;
                    }
                },
                error -> Log.e("AuthQuickStart", error.toString())
        );*/
        // Add code here
        if (AWSMobileClient.getInstance().getConfiguration() == null){
            try {
                Amplify.addPlugin(new AWSApiPlugin());
                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.configure(getApplicationContext());
            } catch (AmplifyException e) {
                e.printStackTrace();
            }

            // Initialize user
            AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    switch (userStateDetails.getUserState()){
                        case SIGNED_IN:
                            // Open Main Activity
                            intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case SIGNED_OUT:
                            //Log.d(TAG, "Do nothing yet");
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            AWSMobileClient.getInstance().signOut();
                            break;
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", e.toString());
                }
            });

        } else {
            // Initialize user
            AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    switch (userStateDetails.getUserState()){
                        case SIGNED_IN:
                            // Open Main Activity
                            intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case SIGNED_OUT:
                            //Log.d(TAG, "Do nothing yet");
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            AWSMobileClient.getInstance().signOut();
                            break;
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", e.toString());
                }
            });
        }

        /*else if (AWSMobileClient.getInstance().isSignedIn()){

            // Logined user
            intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // Logouted user
            intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _initCognito();

       /* awsApiPlugin = new AWSApiPlugin();
        awsCognitoAuthPlugin = new AWSCognitoAuthPlugin();
        //..
        try {
            //Amplify.addPlugin(awsApiPlugin);
            Amplify.addPlugin(awsCognitoAuthPlugin);
            Amplify.configure(getApplicationContext());

            Amplify.Auth.fetchAuthSession(
                    result -> {
                        AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
                        switch(cognitoAuthSession.getIdentityId().getType()) {
                            case SUCCESS:
                                Log.i("AuthQuickStart", "IdentityId: " + cognitoAuthSession.getIdentityId().getValue());
                                // Open Main Activity
                                intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;
                            case FAILURE:
                                Log.i("AuthQuickStart", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
                                break;
                        }
                    },
                    error -> Log.e("AuthQuickStart", error.toString())
            );
            //_initCognito();
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }*/
    }
}
