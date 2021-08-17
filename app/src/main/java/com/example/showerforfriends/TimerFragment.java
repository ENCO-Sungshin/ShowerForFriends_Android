package com.example.showerforfriends;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;

import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    ViewGroup viewGroup;
    boolean isClicked = false;
    int showerT = 0; // 샤워 시간 측정
    int waterT = 0; // 물 사용 시간 계산
    Button b;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*String userinfo="{"+
                "\"user_id\":"+"\""+Amplify.Auth.getCurrentUser().getUserId()+"\""
                +"}";
        RestOptions options = RestOptions.builder()
                .addPath("/mydisplay")
                .addBody(userinfo.getBytes())
                .build();
        Amplify.API.post(options,
                response -> Log.i("MyAmplifyApp", "POST succeeded: " + response.getData().asString()),
                error -> Log.e("MyAmplifyApp", "POST failed.", error)
        );

//        String userinfo2="{"+
//                "\"user_id\":"+"\""+1+"\""
//                +"}";
        RestOptions options2 = RestOptions.builder()
                .addPath("/user/2")
                .build();

        Amplify.API.get(options2,
                restResponse -> Log.i("MyAmplifyApp", "GET succeeded: " + restResponse.getCode()+restResponse.getData().asString()),
                apiFailure -> Log.e("MyAmplifyApp", "GET failed.", apiFailure)
        );
        Log.i("CurrentUser",Amplify.Auth.getCurrentUser().getUsername());
        Log.i("CurrentUser",Amplify.Auth.getCurrentUser().getUserId());*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_timer, container, false);
        b = (Button) viewGroup.findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isClicked == false){
                    isClicked = true;
                    //ShowerThread st = new ShowerThread();
                    //st.start();
                    b.setText("샤워 중 입니다");
                    //Toast.makeText(TimerActivity.this, "친환경적인 샤워를 시작해보아요!", Toast.LENGTH_SHORT).show();
                }
                else if(isClicked == true){
                    isClicked = false;
                    b.setText("샤워 시작하기");
                    //Toast.makeText(TimerActivity.this, "깨끗하게, 맑게, 자신있게!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return viewGroup;
    }

    private class ShowerThread extends Thread { // 샤워 시간 측정

        public ShowerThread(){
            showerT = 0;
        }

        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 스레드에게 수행시킬 동작들 구현
                    while(isClicked == true)
                    {
                        try {
                            showerT++;
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}