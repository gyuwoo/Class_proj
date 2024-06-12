package com.example.class_proj;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private final HashMap<Marker, LaundryInfo> markerInfoMap;

    public CustomInfoWindowAdapter(LayoutInflater inflater, HashMap<Marker, LaundryInfo> markerInfoMap) {
        mWindow = inflater.inflate(R.layout.info, null);
        this.markerInfoMap = markerInfoMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView titleTextView = mWindow.findViewById(R.id.title);
        TextView snippetTextView = mWindow.findViewById(R.id.snippet);

        // LaundryInfo 가져오기
        LaundryInfo info = markerInfoMap.get(marker);

        if (info != null) {
            titleTextView.setText(info.name);
            snippetTextView.setText("주소: " + info.address + "\n운영시간: " + info.openHours + "\n전화번호: " + info.tel);
        } else {
            titleTextView.setText(marker.getTitle());
            snippetTextView.setText(marker.getSnippet());
        }

        return mWindow;
    }
}