package com.example.showerforfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;

public class SplashGifActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 5000;

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
        if (AWSMobileClient.getInstance().getConfiguration() == null) {
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
                    switch (userStateDetails.getUserState()) {
                        case SIGNED_IN:
                            // Open Main Activity
                            Intent intent = new Intent(SplashGifActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case SIGNED_OUT:
                            //Log.d(TAG, "Do nothing yet");
                            Intent intent2 = new Intent(SplashGifActivity.this, LoginActivity.class);
                            startActivity(intent2);
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
                    switch (userStateDetails.getUserState()) {
                        case SIGNED_IN:
                            // Open Main Activity
                            Intent intent = new Intent(SplashGifActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case SIGNED_OUT:
                            //Log.d(TAG, "Do nothing yet");
                            Intent intent2 = new Intent(SplashGifActivity.this, LoginActivity.class);
                            startActivity(intent2);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_gif);

        ImageView splash_gif_img = (ImageView) findViewById(R.id.splash_gif_img);
        Glide.with(this).load(R.drawable.splash_gif).into(splash_gif_img);

        Handler handler = new Handler();
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                _initCognito();
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}