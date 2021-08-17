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
import android.widget.Toast;

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
    String hair_data, height_data, weight_data, name_data;
    String hair_value, height_value, weight_value, name_value;


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

        String loadInfo = "{" +
                "\"user_id\" : " + 0 + "}";

        RestOptions options = RestOptions.builder()
                .addHeader("Accept","application/hal+json")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addPath("/test/")
                .addBody(loadInfo.getBytes())
                .build();

        Amplify.API.post(options,
                response -> {
                    Log.i("MyAmplifyApp", "POST succeeded: " + response);

                    //RestResponse res = response;
                    System.out.println("String : " + response.getData().asString());

                    String allData = response.getData().asString();
                    Integer hair_index = allData.indexOf("\"user_hair\"");
                    hair_data = allData.substring(hair_index, allData.substring(hair_index).indexOf(",") + hair_index);
                    hair_value = hair_data.substring(hair_data.indexOf(":") + 1);
                    System.out.println("Hair : " + hair_value);

                    Integer height_index = allData.indexOf("\"user_height\"");
                    height_data = allData.substring(height_index, allData.substring(height_index).indexOf(",") + height_index);
                    height_value = height_data.substring(height_data.indexOf(":") + 1);
                    System.out.println("Height : " + height_value);

                    Integer weight_index = allData.indexOf("\"user_weight\"");
                    weight_data = allData.substring(weight_index, allData.substring(weight_index).indexOf(",") + weight_index);
                    weight_value = weight_data.substring(weight_data.indexOf(":") + 1);
                    System.out.println("Weight : " + weight_value);

                    Integer name_index = allData.indexOf("\"user_name\"");
                    name_data = allData.substring(name_index, allData.substring(name_index).indexOf("}") + name_index);
                    //name_value = name_data.substring(name_data.indexOf(":") + 2, name_data.substring(name_data.indexOf(":")).indexOf("\"") + name_data.indexOf(":"));
                    System.out.println("Name : " + name_data);

                    if(height_value.equals("0")) input_tall.setText("");
                    else input_tall.setText(height_value + " cm");

                    if(weight_value.equals("0")) input_weight.setText("");
                    else input_weight.setText(weight_value + " kg");

                    if(hair_value.equals("0")) input_hairlength.setText("");
                    else {
                        switch (hair_value) {
                            case "1":
                                input_hairlength.setText("짧은 머리");
                                break;

                            case "2":
                                input_hairlength.setText("귀 위");
                                break;

                            case "3":
                                input_hairlength.setText("귀 ~ 어깨 사이");
                                break;

                            case "4":
                                input_hairlength.setText("어깨 아래 ~ 가슴");
                                break;

                            case "5":
                                input_hairlength.setText("가슴 아래");
                                break;
                        }
                    }
                },
                error -> Log.e("MyAmplifyApp", "POST failed: ", error));



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

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddInfoActivity.class);
                //intent.putExtra("username", name_value); // username을 인증 코드 창에서 사용하기 위해
                if(Integer.parseInt(hair_value) == 0) intent.putExtra("hair", "0");
                else intent.putExtra("hair", hair_value);

                if(Integer.parseInt(height_value) == 0) intent.putExtra("height", "0");
                else intent.putExtra("height", height_value);

                if(Integer.parseInt(weight_value) == 0) intent.putExtra("weight", "0");
                else intent.putExtra("weight", weight_value);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return viewGroup;
    }
}