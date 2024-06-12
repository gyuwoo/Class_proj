package com.example.class_proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnZoomIn, btnZoomOut;
    private CustomInfoWindowAdapter adapter;
    private HashMap<Marker, LaundryInfo> markerInfoMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    // 현재 화면이 Home이므로 아무 작업도 하지 않습니다.
                    return true;
                } else if (itemId == R.id.nav_timer) {
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("notification")) {
            String notification = intent.getStringExtra("notification");
            Toast.makeText(this, notification, Toast.LENGTH_LONG).show();
        }


        btnZoomIn = findViewById(R.id.btn_zoom_in);
        btnZoomOut = findViewById(R.id.btn_zoom_out);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        markerInfoMap = new HashMap<>();

        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                }
            }
        });

        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.animateCamera(CameraUpdateFactory.zoomOut());
                }
            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng[] locations = {
                new LatLng(35.148087, 129.010509), // 더런드리 부산 주례점
                new LatLng(35.146923, 129.009193), // Print it 복사 빨쿡 셀프빨래방 동서대점
                new LatLng(35.147007, 129.000648), // 더런드리 셀프 빨래방 주례럭키점
                new LatLng(35.151202, 129.020452), // 코런24시 셀프빨래방
                new LatLng(35.158767, 129.053285), // 워시엔조이 셀프 빨래방 서면부전점
                new LatLng(35.159003, 129.054571), // 더런드리 셀프 빨래방 서면부전점
                new LatLng(35.159308, 129.063939), // 워시엔조이 셀프 빨래방 부산서면점
                new LatLng(35.152044, 129.066283), // 워시엔조이 셀프 빨래방 서면전포역점
                new LatLng(35.169618, 128.978244), // 워시엔조이 셀프 빨래방 부산사상점
                new LatLng(35.201854, 129.004537), // 워시엔조이 셀프 빨래방 부산구포점
                new LatLng(35.125312, 129.043385), // 워시엔조이 셀프 빨래방 부산수정점
                new LatLng(35.103123, 129.033450), // 크린토피아 코인워시 365 부산대청코모도에스테이트점
                new LatLng(35.100753, 129.022381), // 워시엔조이 셀프 빨래방 부산서구청점
                new LatLng(35.111236, 129.110043), // 크린업24 셀프 빨래방 용호점
                new LatLng(35.137393, 129.089122), // 워시엔조이 셀프 빨래방 대연못골점
                new LatLng(35.059090, 128.970868), // 워시프렌즈 24시 무인빨래방 다대점
                new LatLng(35.134111, 129.097759), // 워시엔조이 셀프 빨래방 부산대연점
                new LatLng(35.175314, 129.070646), // 부산 셀프빨래방 워시라운지
                new LatLng(35.177432, 129.070958), // 워시엔조이 셀프 빨래방 부산시청점
                new LatLng(35.175062, 129.078233), // 워시엔조이 셀프 빨래방 부산연제구청점
                new LatLng(35.174063, 129.100765), // 워시엔조이 셀프 빨래방 부산망미점
                new LatLng(35.200114, 129.088971), // 워시엔조이 셀프 빨래방 부산낙민역
                new LatLng(35.214555, 129.077430), // 부산 셀프 빨래방 워시라운지 동래점
                new LatLng(35.160197, 129.152520), // 런드리파크 해운대점
                new LatLng(35.105762, 128.962562), // 코인워시365
                new LatLng(35.080932, 128.971281), // 워시쿱셀프 빨래방 장림점
                new LatLng(35.186078, 129.204425), // 워시팡팡 셀프 빨래방 부산송정점
                new LatLng(35.237794, 129.210670), // 워시프렌즈 셀프 빨래방 부산 기장대라점
                new LatLng(35.175113, 129.104315), // 크린토피아 코인워시 셀프빨래방 수영망미중앙시장점
                new LatLng(35.202715, 129.116366), // 셀프빨래방 반여 센텀 크린업24
                new LatLng(35.184828, 129.122597), // 24시 셀프코인 빨래방 센텀재송점
        };

        // 각 위치의 이름 리스트
        String[] locationNames = {
                "더런드리 부산 주례점",
                "Print it 복사 빨쿡 셀프빨래방 동서대점",
                "더런드리 셀프 빨래방 주례럭키점",
                "코런24시 셀프빨래방",
                "워시엔조이 셀프 빨래방 서면부전점",
                "더런드리 셀프 빨래방 서면부전점",
                "워시엔조이 셀프 빨래방 부산서면점",
                "워시엔조이 셀프 빨래방 서면전포역점",
                "워시엔조이 셀프 빨래방 부산사상점",
                "워시엔조이 셀프 빨래방 부산구포점",
                "워시엔조이 셀프 빨래방 부산수정점",
                "크린토피아 코인워시 365 부산대청코모도에스테이트점",
                "워시엔조이 셀프 빨래방 부산서구청점",
                "크린업24 셀프 빨래방 용호점",
                "워시엔조이 셀프 빨래방 대연못골점",
                "워시프렌즈 24시 무인빨래방 다대점",
                "워시엔조이 셀프 빨래방 부산대연점",
                "부산 셀프빨래방 워시라운지",
                "워시엔조이 셀프 빨래방 부산시청점",
                "워시엔조이 셀프 빨래방 부산연제구청점",
                "워시엔조이 셀프 빨래방 부산망미점",
                "워시엔조이 셀프 빨래방 부산낙민역",
                "부산 셀프 빨래방 워시라운지 동래점",
                "런드리파크 해운대점",
                "코인워시365",
                "워시쿱셀프 빨래방 장림점",
                "워시팡팡 셀프 빨래방 부산송정점",
                "워시프렌즈 셀프 빨래방 부산 기장대라점",
                "크린토피아 코인워시 셀프빨래방 수영망미중앙시장점",
                "셀프빨래방 반여 센텀 크린업24",
                "24시 셀프코인 빨래방 센텀재송점",
        };

        LaundryInfo[] laundryInfos = {

                new LaundryInfo("더런드리 부산 주례점", "부산광역시 사상구 주례동 166-167 1 층 더런 드리 셀프 빨래방", "매일 오전 7:00~오전 12:00", "전화번호가 등록되어 있지 않습니다."),
                new LaundryInfo("Print it 복사 빨쿡 셀프빨래방 동서대점", "부산광역시 사상구 가야대로330번길 84 1충", "24시간 운영", "전화번호가 등록되어 있지 않습니다."),
                new LaundryInfo("더런드리 셀프 빨래방 주례럭키점", "부산광역시 사상구 동주로 15 셀프빨래방", "24시간 운영", "전화번호가 등록되어 있지 않습니다."),
                new LaundryInfo("코런24시 셀프빨래방", "부산광역시 부산진구 개금제1동 523-71", "24시간 운영", "010-2563-3603"),
                new LaundryInfo("워시엔조이 셀프 빨래방 서면부전점", "부산광역시 부산진구 부전로95번길 28", "24시간 운영", "010-8543-8425"),
                new LaundryInfo("더런드리 셀프빨래방 서면부전점", "부산광역시 부산진구 전포대로255번길 25", "24시간 운영", "010-9288-8252"),
                new LaundryInfo("워시엔조이 셀프 빨래방 부산서면점", "부산광역시 부산진구 전포대로255번길 25", "24시간 운영", "010-3438-6651"),
                new LaundryInfo("워시엔조이 셀프 빨래방 서면전포역점", "부산광역시 부산진구 서전로58번길 127", "24시간 운영", "010-3498-6651"),
                new LaundryInfo("워시엔조이 셀프 빨래방 부산사상점", "부산광역시 사상구 삼락동 운산로 11", "24시간 운영", "010-4002-0317"),
                new LaundryInfo("워시엔조이 셀프 빨래방 부산구포점", "부산광역시 북구 구포동 1212-3", "24시간 운영", "051-728-9699"),
                new LaundryInfo("워시엔조이 셀프빨래방 부산수정점", "부산광역시 동구 수정제1동 1007-3", "24시간 운영", "010-8506-0005"),
                new LaundryInfo("크린토피아 코인워시 365 부산대청코모도에스테이트점", "부산광역시 중구 대청로 121-1", "24시간 운영", "010-5488-6709"),
                new LaundryInfo("워시엔조이 셀프 빨래방 부산서구청점", "부산광역시 서구 충무동 까치고개로239번길 26", "24시간 운영", "010-7311-4782"),
                new LaundryInfo("크린업24 셀프 빨래방 용호점", "부산광역시 남구 용호로216번가길 40 예가프라자 1층 104호", "24시간 운영", "010-9030-3915"),
                new LaundryInfo("워시엔조이 셀프 빨래방 대연못골점", "부산광역시 남구 대연동 못골번영로 26", "24시간 운영", "051-926-1564"),
                new LaundryInfo("워시프렌즈 24시 무인빨래방 다대점", "부산광역시 사하구 다대동 윤공단로14번길 99", "24시간 운영", "010-3565-7900"),
                new LaundryInfo("워시엔조이 셀프빨래방 부산대연점", "부산광역시 남구 대연동 357-1", "24시간 운영", "010-4025-1969"),
                new LaundryInfo("부산 셀프빨래방 워시라운지", "부산광역시 부산진구 동평로405번길 40", "24시간 운영", "010-6525-1125"),
                new LaundryInfo("워시엔조이 셀프빨래방 부산시청점", "부산광역시 연제구 거제대로108번길 47", "24시간 운영", "010-9491-6052"),
                new LaundryInfo("워시엔조이 셀프 " +
                        "빨래방 부산연제구청점", "부산광역시 부산진구 양정제2동 양연로6번길 12", "24시간 운영", "010-8228-6052"),
                new LaundryInfo("워시엔조이 셀프 빨래방 부산망미점", "부산광역시 수영구 연수로275번길 19", "24시간 운영", "010-3117-4624"),
                new LaundryInfo("워시엔조이 셀프 빨래방 부산낙민역", "부산광역시 동래구 낙민동 222-11", "24시간 운영", "010-2894-7925"),
                new LaundryInfo("부산 셀프 빨래방 워시라운지 동래점", "부산광역시 동래구 금강로62번길 8", "24시간 운영", "010-6525-1125"),
                new LaundryInfo("런드리파크 해운대점", "부산광역시 해운대구 해운대로 554", "24시간 운영", "1588-5942"),
                new LaundryInfo("코인워시365", "부산광역시 사하구 하단동 596-22", "24시간 운영", "전화번호가 등록되어 있지 않습니다."),
                new LaundryInfo("워시쿱셀프 빨래방 장림점", "부산광역시 사하구 장림동 321-28번지 나산리버빌 117호", "24시간 운영", "전화번호가 등록되어 있지 않습니다."),
                new LaundryInfo("워시팡팡 셀프 빨래방 부산송정점", "부산광역시 해운대구 송정중앙로15번길 68 천일아파트 1층", "24시간 운영", "010-6212-7079"),
                new LaundryInfo("워시프렌즈 셀프 빨래방 부산 기장대라점", "부산광역시 기장군 기장읍 대청로 6", "24시간 운영", "010-8254-7887"),
                new LaundryInfo("크린토피아 코인워시 셀프빨래방 수영망미중앙시장점 ", "부산광역시 수영구 망미배산로36번길 18", "24시간 운영", "051-744-4560"),
                new LaundryInfo("셀프빨래방 반여 센텀 크린업24", "부산광역시 해운대구 삼어로 61 우방신세계상가 102호", "24시간 운영", "010-4894-2488"),
                new LaundryInfo("24시 셀프코인 빨래방 센텀재송점", "부산광역시 해운대구", "24시간 운영", "010-7345-6784"),
        };

        // 각 위치에 마커를 추가하고 이름을 설정.
        for (int i = 0; i < locations.length; i++) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(locations[i])
                    .title(locationNames[i]));

            // 마커와 세탁소 정보를 HashMap에 매핑
            markerInfoMap.put(marker, laundryInfos[i]);
        }

        adapter = new CustomInfoWindowAdapter(getLayoutInflater(), markerInfoMap);
        mMap.setInfoWindowAdapter(adapter);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 사용자 정의 정보 창 띄우기
                mMap.setInfoWindowAdapter(adapter);
                marker.showInfoWindow();
                return true;
            }
        });

        // 부산 중심으로 카메라 이동 및 줌 레벨 설정
        LatLng busanCenter = new LatLng(35.179554, 129.075641); // 부산 중심 좌표
        float zoomLevel = 12.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(busanCenter, zoomLevel));

    }

}
