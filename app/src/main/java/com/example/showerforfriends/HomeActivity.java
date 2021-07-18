package com.example.showerforfriends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.FrameMetricsAggregator;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment timerFragment, bookmarkFragment, friendListFragment, rankFragment, mypageFragment, usageFragment;

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
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, bookmarkFragment).commitAllowingStateLoss();
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
        bookmarkFragment = new BookmarkFragment();
        //friendListFragment = new FriendListFragment();
        rankFragment = new RankFragment();
        mypageFragment = new MyPageFragment();
        usageFragment = new UsageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, timerFragment).commit();
    }

     /*public void replaceFragment(int index) {
        if(index == 0)
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, friendListFragment).commit();
        else if(index == 1)
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, rankFragment).commit();
    }*/
}