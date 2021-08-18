package com.example.showerforfriends;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    String display_id = null;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_timer, container, false);
        b = (Button) viewGroup.findViewById(R.id.button);

        String userInfo = "{" +
                "\"user_id\" : \"" + Amplify.Auth.getCurrentUser().getUserId() + "\"}";

        RestOptions options1 = RestOptions.builder()
                .addPath("/info")
                .addBody(userInfo.getBytes())
                .build();

        /*String display_id = null;*/
        Amplify.API.post(options1,
                response -> {
                    String res = response.getData().asString();
                    System.out.println(res);

                    Integer display_index = res.indexOf("\"display_id\"");
                    String display_data = res.substring(display_index, res.substring(display_index).indexOf(",") + display_index);
                    display_id = display_data.substring(display_data.indexOf(":") + 1);
                    System.out.println(display_id);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            display_id=display_value;
//                        }
//                    });
                },
                error -> {
                    Log.e("MyAmplifyApp", "POST failed: ", error);
                });



        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isClicked == false){
                    if (display_id.equals("null")) {
                        Toast.makeText(getContext(), "등록된 타이머가 없습니다. ", Toast.LENGTH_SHORT).show();
                    }else{
                        isClicked = true;
                        String loadInfo = "{" +
                                "\"display_id\" : " + display_id + ","
                                +"\"power\" : true ,"
                                +"\"user_id\" : \"" +Amplify.Auth.getCurrentUser().getUserId()+ "\""
                                +"}";

                        RestOptions options2 = RestOptions.builder()
                                .addPath("/display")
                                .addBody(loadInfo.getBytes())
                                .build();

                        /*String display_id = null;*/
                        Amplify.API.post(options2,
                                response -> Log.i("MyAmplifyApp", "POST succeeded: " + response.getData().asString()+response.getCode()),
                                error -> Log.e("MyAmplifyApp", "POST failed.", error));

                        b.setText("샤워 중 입니다");
                    }
                    //ShowerThread st = new ShowerThread();
                    //st.start();

                }
                else if(isClicked == true){
                    isClicked = false;
                    String loadInfo = "{" +
                            "\"display_id\" : " + display_id + ","
                            +"\"power\" : false ,"
                            +"\"user_id\" : \"" +Amplify.Auth.getCurrentUser().getUserId()+ "\""
                            +"}";

                    RestOptions options2 = RestOptions.builder()
                            .addPath("/display")
                            .addBody(loadInfo.getBytes())
                            .build();
                    /*String display_id = null;*/
                    Amplify.API.post(options2,
                            response -> Log.i("MyAmplifyApp", "POST succeeded: " + response.getData().asString()+response.getCode()),
                            error -> Log.e("MyAmplifyApp", "POST failed.", error));

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