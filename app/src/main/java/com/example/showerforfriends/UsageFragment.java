package com.example.showerforfriends;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsageFragment extends Fragment {

    private LineChart lineChart1,lineChart2;
    ViewGroup viewGroup;
    Integer time_count_index = 0, timeStamp_index = 2, totalAmount_index = 4;
    Integer timeCount;
    Float totalAmount;
    String timeStamp;
    Usage usage_array[];


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsageFragment newInstance(String param1, String param2) {
        UsageFragment fragment = new UsageFragment();
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

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_usage, container, false);

        lineChart1 = (LineChart) viewGroup.findViewById(R.id.chart1);
        lineChart2 = (LineChart) viewGroup.findViewById(R.id.chart2);

        System.out.println("userid : " + Amplify.Auth.getCurrentUser().getUserId());


        List<Entry> entries = new ArrayList<Entry>();
        List<Entry> entries2 = new ArrayList<>();
        String loadInfo = "{" +
                "\"user_id\" : \"" +  Amplify.Auth.getCurrentUser().getUserId() + "\"}";

        RestOptions options = RestOptions.builder()
                .addHeader("Accept","application/hal+json")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addPath("/usage")
                .addBody(loadInfo.getBytes())
                .build();

        Amplify.API.post(options,
                response -> {
                    Log.i("MyAmplifyApp", "GET succeeded: " + response);

                    System.out.println("String : " + response.getData().asString());
                    String res = response.getData().asString();


                    String counts_string = res.substring(res.indexOf("]") + 2);
                    String usage_data = res.substring(0, res.indexOf("]"));
                    System.out.println("usage data : " + usage_data);

                    Integer count_index = counts_string.indexOf("\"Count\"");
                    String count_data = counts_string.substring(count_index, counts_string.substring(count_index).indexOf(",") + count_index);
                    Integer count_value = Integer.parseInt(count_data.substring(count_data.indexOf(":") + 1));
                    System.out.println("Count : " + count_value);
                    usage_data = usage_data.substring(10);

                    String usageData[] = usage_data.split(",");

                    //friendItem = new Friend[count_value];
                    usage_array = new Usage[count_value];
                    int count = 0;
                    for(int i=0; i<usageData.length; i++)
                    {
                        System.out.println( usageData[i] + " / ");
                        usageData[i] = usageData[i].substring(usageData[i].indexOf(":") + 1);
                        if(count < count_value) {

                            if(i == count * 5 + time_count_index) {
                                timeCount = Integer.parseInt(usageData[i]);
                            }
                            else if(i == count * 5 + timeStamp_index) {
                                timeStamp = usageData[i];
                            }
                            else if(i == count * 5 + totalAmount_index) {
                                usageData[i] = usageData[i].substring(0, usageData[i].indexOf("}") - 1);
                                Float totalAmount_L = Float.parseFloat(usageData[i]) / 1000;
                                totalAmount = totalAmount_L;
                            }


                            /*if (i == count * 7 + user_id_index) {
                               name = userData[i];
                            }
                            else*/ /*if (i == count * 7 + user_name_index) {
                                name = userData[i].substring(1, userData[i].indexOf("}")-1);
                                System.out.println("name : " + name);
                            }*/
                            /*else if (i == count * 7 + user_display_index) {
                                showData[index] = userData[i];
                            }*/
                        }
                        if(i % 5 == 4)
                        {
                            Usage usage = new Usage(timeCount, totalAmount, timeStamp);
                            entries.add(new Entry(count + 1, usage.getTotal_amount()));
                            entries2.add(new Entry(count + 1, usage.getTime_count()));

                            count++;
                            /*Friend item = new Friend(name, 0, R.drawable.person1);
                            friendArrayList.add(item);*/
                            /*addGroupItem(name, 0, 1);*/
                            /*Friend item = new Friend(name, 0, 0);
                            friendArrayList.add(item);*/
                        }

                        /*System.out.println( userData[i] + " / ");
                        //recyclerView.setAdapter(friendItemAdapter);
                        friendItemAdapter = new FriendItemAdapter(context, friendArrayList);*/

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LineDataSet lineDataSet1 = new LineDataSet(entries, "물 (L)");
                            lineDataSet1.setLineWidth(2);
                            lineDataSet1.setCircleRadius(6);
                            lineDataSet1.setCircleColor(Color.parseColor("#88BADF"));
                            lineDataSet1.setCircleColorHole(Color.parseColor("#E1E4E9"));
                            lineDataSet1.setColor(Color.parseColor("#88BADF"));
                            lineDataSet1.setDrawCircleHole(true);
                            lineDataSet1.setDrawCircles(true);
                            lineDataSet1.setDrawHorizontalHighlightIndicator(false);
                            lineDataSet1.setDrawHighlightIndicators(false);
                            lineDataSet1.setDrawValues(true);

                            LineData lineData1 = new LineData(lineDataSet1);
                            lineChart1.setData(lineData1);

                            XAxis xAxis = lineChart1.getXAxis();
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setTextColor(Color.BLACK);
                            xAxis.setAxisMinimum(1);
                            xAxis.setLabelCount(count_value, true);
                            xAxis.setAxisMaximum(count_value);
                            xAxis.enableGridDashedLine(count_value, 24, 0);

                            YAxis yLAxis = lineChart1.getAxisLeft();
                            yLAxis.setTextColor(Color.BLACK);

                            YAxis yRAxis = lineChart1.getAxisRight();
                            yRAxis.setDrawLabels(false);
                            yRAxis.setDrawAxisLine(false);
                            yRAxis.setDrawGridLines(false);
                            yLAxis.setTextSize(10);

                            Description description = new Description();
                            description.setText("=");

                            lineChart1.setDoubleTapToZoomEnabled(false);
                            lineChart1.setDrawGridBackground(false);
                            lineChart1.setDescription(description);
                            lineChart1.animateY(3000, Easing.EasingOption.EaseInCubic);
                            lineChart1.invalidate();

        /*entries2.add(new Entry(1, 327));
        entries2.add(new Entry(2, 510));
        entries2.add(new Entry(3, 402));
        entries2.add(new Entry(4, 469));
        entries2.add(new Entry(5, 302));
        entries2.add(new Entry(6, 523));
        entries2.add(new Entry(7, 500));
        entries2.add(new Entry(8, 502));
        entries2.add(new Entry(9, 501));
        entries2.add(new Entry(10, 504));
        entries2.add(new Entry(11, 304));
        entries2.add(new Entry(12, 402));
        entries2.add(new Entry(13, 303));
        entries2.add(new Entry(14, 315));
        entries2.add(new Entry(15, 444));
        entries2.add(new Entry(16, 455));
        entries2.add(new Entry(17, 422));
        entries2.add(new Entry(18, 344));
        entries2.add(new Entry(19, 355));*/



                            LineDataSet lineDataSet2 = new LineDataSet(entries2, "시간 (분)");
                            lineDataSet2.setLineWidth(2);
                            lineDataSet2.setCircleRadius(6);
                            lineDataSet2.setCircleColor(Color.parseColor("#88BADF"));
                            lineDataSet2.setCircleColorHole(Color.parseColor("#E1E4E9"));
                            lineDataSet2.setColor(Color.parseColor("#88BADF"));
                            lineDataSet2.setDrawCircleHole(true);
                            lineDataSet2.setDrawCircles(true);
                            lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                            lineDataSet2.setDrawHighlightIndicators(false);
                            lineDataSet2.setDrawValues(true);

                            LineData lineData2 = new LineData(lineDataSet2);
                            lineChart2.setData(lineData2);
                            XAxis xAxis2 = lineChart2.getXAxis();
                            xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis2.setTextColor(Color.BLACK);
                            xAxis2.setAxisMinimum(1);
                            xAxis2.setLabelCount(count_value, true);
                            xAxis2.setAxisMaximum(count_value);
                            xAxis2.enableGridDashedLine(count_value, 24, 0);

                            YAxis yLAxis2 = lineChart2.getAxisLeft();
                            yLAxis2.setTextColor(Color.BLACK);

                            YAxis yRAxis2 = lineChart2.getAxisRight();
                            yRAxis2.setDrawLabels(false);
                            yRAxis2.setDrawAxisLine(false);
                            yRAxis2.setDrawGridLines(false);

                            Description description2 = new Description();
                            description2.setText("");

                            lineChart2.setDoubleTapToZoomEnabled(false);
                            lineChart2.setDrawGridBackground(false);
                            lineChart2.setDescription(description);
                            lineChart2.animateY(3000, Easing.EasingOption.EaseInCubic);
                            lineChart2.invalidate();
                        }
                    });

                },
                error -> {
                    Log.e("MyAmplifyApp", "POST failed: ", error);
                });

        /*entries.add(new Entry(1, 327));
        entries.add(new Entry(2, 510));
        entries.add(new Entry(3, 402));
        entries.add(new Entry(4, 469));
        entries.add(new Entry(5, 302));
        entries.add(new Entry(6, 523));
        entries.add(new Entry(7, 500));*/




        // Inflate the layout for this fragment
        return viewGroup;
    }
}