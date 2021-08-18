package com.example.showerforfriends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankFragment extends Fragment {
    HomeActivity homeActivity;
    ViewGroup viewGroup;
    ImageView user_char;
    TextView user_name_rank, user_useTime_rank,user_time_rank;
    Button list_btn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankFragment() {
        // Required empty public constructor
    }

    @Override // 추가 부분
    public void onAttach(Context context)
    {
        super.onAttach(context);
        homeActivity = (HomeActivity) getActivity();
    }

    @Override // 추가 부분
    public void onDetach() {
        super.onDetach();
        homeActivity = null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankFragment newInstance(String param1, String param2) {
        RankFragment fragment = new RankFragment();
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

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_rank, container, false);

        list_btn = (Button) viewGroup.findViewById(R.id.list_btn);
        user_name_rank = (TextView) viewGroup.findViewById(R.id.user_name_rank);
        user_time_rank = (TextView) viewGroup.findViewById(R.id.user_time_rank);
        user_useTime_rank = (TextView) viewGroup.findViewById(R.id.user_useTime_rank);
        user_char = (ImageView) viewGroup.findViewById(R.id.user_char);
        user_name_rank.setText(Amplify.Auth.getCurrentUser().getUsername());

        String userinfo = "{" +
                "\"user_id\":\"" + Amplify.Auth.getCurrentUser().getUserId() + "\""
                + "}";
        RestOptions options = RestOptions.builder()
                .addPath("/usage")
                .addBody(userinfo.getBytes())
                .build();

        Amplify.API.post(options,
                response -> {
                    Log.i("RankingFragment", "POST succeeded: " + response);
                    //JsonParser.parseString(response.getData().asString()).getAsJsonObject() : get Items:[JSONArray]
                    JsonArray user_usage_arr= JsonParser.parseString(response.getData().asString()).getAsJsonObject().getAsJsonArray("Items");
                    JsonObject user_recent_usage = (JsonObject) user_usage_arr.get(0);
                    Log.i("RankingFragment", "POST succeed: " + JsonParser.parseString(response.getData().asString()).getAsJsonObject().getAsJsonArray("Items"));
                    Log.i("RankingFragment", "POST succeed: " + user_recent_usage.get("totalAmount"));
                    Log.i("RankingFragment", "POST succeed: " + new Calculator().getGrade(user_recent_usage.get("totalAmount").getAsInt()));
                    int amount = user_recent_usage.get("totalAmount").getAsInt();
                    user_time_rank.setText(amount*0.001+"");
                    String char_name = (String)(new Calculator().getGrade(amount));
                    user_useTime_rank.setText(char_name);
                    String s=new Calculator().getGrade(user_recent_usage.get("totalAmount").getAsInt());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch(s){
                                case "고래":
                                    user_char.setImageResource(R.drawable.whale);
                                    break;
                                case "거북이":
                                    user_char.setImageResource(R.drawable.turtle);
                                    break;
                                case "열대어":
                                    user_char.setImageResource(R.drawable.tropicalfish);
                                    break;
                                case " 불가사리":
                                    user_char.setImageResource(R.drawable.starfish);
                                    break;
                            }
                        }
                    });

                    // String to Json
                    //JSONObject json = null;
                    //json = new JSONObject(response.getData().toString());
                    //System.out.println("JsonObject 결과 값 :: " + obj);
                },
                error ->{
                    Log.i("MyAmplifyApp", "POST failed: " + error.getMessage());
                });
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FriendListActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return viewGroup;
    }
}