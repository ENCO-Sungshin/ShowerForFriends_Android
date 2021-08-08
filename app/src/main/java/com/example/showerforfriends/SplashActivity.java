package com.example.showerforfriends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;

public class SplashActivity extends AppCompatActivity {

    Intent intent;
    private void _initCognito() {
        // Add code here
        if (AWSMobileClient.getInstance().getConfiguration() == null){
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

        } else if (AWSMobileClient.getInstance().isSignedIn()){
            // Logined user
            intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // Logouted user
            intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        _initCognito();
    }
}
