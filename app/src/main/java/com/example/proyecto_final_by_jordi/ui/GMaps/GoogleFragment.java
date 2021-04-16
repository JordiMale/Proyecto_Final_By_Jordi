package com.example.proyecto_final_by_jordi.ui.GMaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.GetSetGoogleMaps;
import com.example.proyecto_final_by_jordi.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleFragment extends Fragment {

    String[] ArrayPoblacion ;
    private FusedLocationProviderClient fusedLocationClient;

    public Datasource bd;
    String Poblacion = "";
    String NumSerie = "";
    String Colorr = "";
    String Tipo = "";

    List<Address> Dire = null;
    int Resultado = 1;
    String DeZona = null;
    long Aux = 0;
    ArrayList<GetSetGoogleMaps> Marca = new ArrayList<GetSetGoogleMaps>();



    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            Geocoder Geoco = new Geocoder(getContext());
            //Cursor Cursor_Mirar_Maquina = bd.GoogleMapsmaquina(Poblacion);

            if (ArrayPoblacion != null) {
                Poblacion = ArrayPoblacion[0];
                Tipo = ArrayPoblacion[1];
                NumSerie = ArrayPoblacion[2];
                Colorr = ArrayPoblacion[3];

                try {
                    Dire = Geoco.getFromLocationName(Poblacion, Resultado);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                LatLng Posicion = new LatLng(Dire.get(0).getLatitude(), Dire.get(0).getLongitude());
                googleMap.addMarker(new MarkerOptions().position(Posicion).icon(getMarkerIcon(Colorr)).title("Tipo maquina: " + Tipo + ", Numero de serie: " + NumSerie));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Posicion, 10));

            } else {
                if (DeZona != null) {
                    Aux = Long.parseLong(DeZona);
                    Cursor GoogleZona = bd.GoogleMapsmaquina(Aux);


                    GetSetGoogleMaps Marcas;

                    while (GoogleZona.moveToNext()) {

                        Poblacion = GoogleZona.getString(GoogleZona.getColumnIndexOrThrow(Datasource.POBLACIO));
                        NumSerie = GoogleZona.getString(GoogleZona.getColumnIndexOrThrow(Datasource.NUMEROSERIE));
                        Tipo = GoogleZona.getString(GoogleZona.getColumnIndexOrThrow(Datasource.NOMMAQUINA));
                        Colorr = GoogleZona.getString(GoogleZona.getColumnIndexOrThrow(Datasource.COLOR_TIPUS));

                        Marcas = new GetSetGoogleMaps(Poblacion, NumSerie, Tipo, Colorr);

                        Marca.add(Marcas);

                    }
                    
                        for(int i = 0; i < Marca.size(); i++){

                            try {
                                Dire = Geoco.getFromLocationName(Marca.get(i).getPoblacio(), Resultado);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            LatLng Posicion = new LatLng(Dire.get(0).getLatitude(), Dire.get(0).getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(Posicion).icon(getMarkerIcon(Marca.get(i).getColor())).title("Tipo maquina: " + Marca.get(i).getNom_Tipo() + ", Numero de serie: " + Marca.get(i).getNum_Serie()));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Posicion, 10));
                        }
                }
            }


            /*
                try {
                    Dire = Geoco.getFromLocationName(Poblacion, Resultado);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LatLng Posicion = new LatLng(Dire.get(0).getLatitude(), Dire.get(0).getLongitude());
                //googleMap.addMarker(new MarkerOptions().position(Posicion).title("Poblaicón: " + Poblacion));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(Posicion));
            */
        }








    };

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bd = new Datasource(getContext());
        return inflater.inflate(R.layout.fragment_google, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (getArguments() != null) {
            ArrayPoblacion= new String[4];
            ArrayPoblacion = getArguments().getStringArray("id");
            DeZona = getArguments().getString("idZ");
        }else{
            Poblacion = "Madrid";
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


}
