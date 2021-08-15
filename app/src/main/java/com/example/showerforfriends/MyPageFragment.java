package com.example.showerforfriends;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment {

    ViewGroup viewGroup;
    Button changeButton;
    ImageButton bookmarkButton;
    Button logoutButton;
    TextView user_name, input_hairlength, input_weight, input_tall;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_my_page, container, false);

        changeButton = (Button) viewGroup.findViewById(R.id.changebtn);
        bookmarkButton = (ImageButton) viewGroup.findViewById(R.id.like_btn);
        logoutButton = (Button) viewGroup.findViewById(R.id.logout_btn);
        user_name = (TextView) viewGroup.findViewById(R.id.user_name);
        input_hairlength = (TextView) viewGroup.findViewById(R.id.input_hairlength);
        input_weight = (TextView) viewGroup.findViewById(R.id.input_weight);
        input_tall = (TextView) viewGroup.findViewById(R.id.input_tall);

        user_name.setText(AWSMobileClient.getInstance().getUsername());


        /*String userinfo="{"+
                "\"user_id\":"+"\""+Amplify.Auth.getCurrentUser().getUserId()+"\""
                +"}";
        RestOptions options = RestOptions.builder()
                .addPath("/mydisplay")
                .addBody(userinfo.getBytes())
                .build();
        Amplify.API.post(options,
                response -> {
                    Log.i("MyAmplifyApp", "POST succeeded: " + response.getData().asString());
                    String allDataString = response.getData().asString();
                    String hair = "user_hair";
                    int hair_num =allDataString.indexOf(hair);
                    input_hairlength.setText(allDataString.substring(hair_num, (allDataString.substring(hair_num).indexOf(",") + hair_num)));

                },
                error -> Log.e("MyAmplifyApp", "POST failed.", error)
        );*/

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AWSMobileClient.getInstance().initialize(getContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        // 로그아웃 후 로그인 창으로 이동
                        AWSMobileClient.getInstance().signOut();
                        Intent i = new Intent(getContext(), LoginActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookmarkActivity.class);
                startActivity(intent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddInfoActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return viewGroup;
    }
}