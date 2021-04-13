package com.example.proyecto_final_by_jordi.ui.GMaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_final_by_jordi.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleFragment extends Fragment {

    String Poblacion = "";


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            Geocoder Geoco = new Geocoder(getContext());
            List<Address> Dire = null;
            int Resultado = 1;
            if (Poblacion == null) {
                Poblacion = "Toledo";
            }
            try {
                Dire = Geoco.getFromLocationName(Poblacion, Resultado);
            } catch (IOException e) {
                e.printStackTrace();
            }

            LatLng Posicion = new LatLng(Dire.get(0).getLatitude(), Dire.get(0).getLongitude());
            googleMap.addMarker(new MarkerOptions().position(Posicion).title("Poblaicón: " + Poblacion));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(Posicion));
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Poblacion = getArguments().getString("id");
        } else {
            Poblacion = "Toledo";
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


}