package com.example.showerforfriends;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsageFragment extends Fragment {

    private LineChart lineChart1,lineChart2;
    ViewGroup viewGroup;


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

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(1, 327));
        entries.add(new Entry(2, 510));
        entries.add(new Entry(3, 402));
        entries.add(new Entry(4, 469));
        entries.add(new Entry(5, 302));
        entries.add(new Entry(6, 523));
        entries.add(new Entry(7, 500));

        LineDataSet lineDataSet1 = new LineDataSet(entries, "물 사용량 (L)");
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setCircleRadius(6);
        lineDataSet1.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet1.setCircleColorHole(Color.BLUE);
        lineDataSet1.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet1.setDrawCircleHole(true);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawHorizontalHighlightIndicator(false);
        lineDataSet1.setDrawHighlightIndicators(false);
        lineDataSet1.setDrawValues(false);

        LineData lineData1 = new LineData(lineDataSet1);
        lineChart1.setData(lineData1);

        XAxis xAxis = lineChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart1.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart1.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart2.setDoubleTapToZoomEnabled(false);
        lineChart2.setDrawGridBackground(false);
        lineChart2.setDescription(description);
        lineChart2.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart2.invalidate();

        List<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(1, 327));
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
        entries2.add(new Entry(19, 355));



        LineDataSet lineDataSet2 = new LineDataSet(entries2, "물 사용량 (L)");
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setCircleRadius(6);
        lineDataSet2.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet2.setCircleColorHole(Color.BLUE);
        lineDataSet2.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet2.setDrawCircleHole(true);
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setDrawHorizontalHighlightIndicator(false);
        lineDataSet2.setDrawHighlightIndicators(false);
        lineDataSet2.setDrawValues(false);

        LineData lineData2 = new LineData(lineDataSet2);
        lineChart2.setData(lineData2);

        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setTextColor(Color.BLACK);
        xAxis2.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis2 = lineChart2.getAxisLeft();
        yLAxis2.setTextColor(Color.BLACK);

        YAxis yRAxis2 = lineChart2.getAxisRight();
        yRAxis2.setDrawLabels(false);
        yRAxis2.setDrawAxisLine(false);
        yRAxis2.setDrawGridLines(false);

        Description description2 = new Description();
        description2.setText("");

        lineChart1.setDoubleTapToZoomEnabled(false);
        lineChart1.setDrawGridBackground(false);
        lineChart1.setDescription(description);
        lineChart1.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart1.invalidate();



        // Inflate the layout for this fragment
        return viewGroup;
    }
}