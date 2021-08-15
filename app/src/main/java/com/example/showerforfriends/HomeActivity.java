package com.example.showerforfriends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.FrameMetricsAggregator;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment timerFragment, bookmarkFragment, friendListFragment, rankFragment, mypageFragment, usageFragment, storelistFragment;

    // navigation bar 연결
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId())
            {
                case R.id.action_timer:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, timerFragment).commitAllowingStateLoss();
                    return true;

                case R.id.action_usage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, usageFragment).commitAllowingStateLoss();
                    return true;

                case R.id.action_store:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, storelistFragment).commitAllowingStateLoss();
                    return true;

                case R.id.action_rank:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, rankFragment).commitAllowingStateLoss();
                    return true;

                case R.id.action_my:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mypageFragment).commitAllowingStateLoss();
                    return true;

                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, timerFragment).commitAllowingStateLoss();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // bottom navigation view 부분
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        timerFragment = new TimerFragment();
        //bookmarkFragment = new BookmarkFragment();
        storelistFragment = new StoreListFragment();
        //friendListFragment = new FriendListFragment();
        rankFragment = new RankFragment();
        mypageFragment = new MyPageFragment();
        usageFragment = new UsageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, timerFragment).commit();
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
     /*public void replaceFragment(int index) {
        if(index == 0)
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, friendListFragment).commit();
        else if(index == 1)
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, rankFragment).commit();
    }*/
}