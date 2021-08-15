package com.example.showerforfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class ShowMapActivity extends AppCompatActivity {
//    public static MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);

    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        Intent intent = getIntent();
        double position1 = intent.getExtras().getDouble("position1");
        double position2 = intent.getExtras().getDouble("position2");
        String Store_name = intent.getExtras().getString("store_name");
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(position1, position2);

        //mapView.setMapViewEventListener(this);
        //mapView.setPOIItemEventListener(this);

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(position1, position2), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(0, true);
        // 중심점 변경 + 줌 레벨 변경
        //mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);
        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);

        // 커스텀 마커
        MapPOIItem customMaker = new MapPOIItem();
        customMaker.setItemName(Store_name);
        customMaker.setTag(1);
        customMaker.setMapPoint(MARKER_POINT);
        customMaker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커타입을 커스텀 마커로 지정
        //customMaker.setCustomImageResourceId(R.drawable.store); // 마커 이미지
        customMaker.setCustomImageAutoscale(false); //hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 끔
        customMaker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지 중 기준이 되는 위치(앵커포인트) 지점 - 마커 이미지 좌측 상단 기준 x(0.0f~1.0f), y(0.0f~1.0f) 값

        mapView.addPOIItem(customMaker);
    }
}