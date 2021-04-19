package com.example.proyecto_final_by_jordi.ui.GMaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.GetSetGoogleMaps;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.Tiempo.City;
import com.example.proyecto_final_by_jordi.Tiempo.clouds;
import com.example.proyecto_final_by_jordi.Tiempo.coord;
import com.example.proyecto_final_by_jordi.Tiempo.main;
import com.example.proyecto_final_by_jordi.Tiempo.sys;
import com.example.proyecto_final_by_jordi.Tiempo.weather;
import com.example.proyecto_final_by_jordi.Tiempo.wind;
import com.example.proyecto_final_by_jordi.ui.Gestion.CrearEditarMaquina;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    //Tiempo

    String Poner_Ubicacion = "";
    TextView Pon_Ciud;
    TextView Poner_Grados;
    TextView Poner_Tiempo;
    ImageView ImageTi;
    TextView Horario;
    TextView Sen_Termica;
    TextView Viento;
    TextView Temp_Min;
    TextView Temp_Max;
    TextView Longi;
    TextView Lat;
    TextView Presion;
    TextView Hume;
    TextView Visi;
    City Json = null;

    String key="aa3e1b446355a7684a18ca7b4f4699f5";




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
        setHasOptionsMenu(true);
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_tiempo, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Tiempo:
                Tiempoo(Poblacion);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void Tiempoo(String poblacion) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater layout = this.getLayoutInflater();

        View vista = layout.inflate(R.layout.tiempo, null);


        MostrarTiemo(vista);


        Pon_Ciud = (TextView) vista.findViewById(R.id.Poner_ciudad1);
        Poner_Grados = (TextView) vista.findViewById(R.id.Grados);
        Poner_Tiempo = (TextView) vista.findViewById(R.id.Tiempo_Hace);

        Horario = (TextView) vista.findViewById(R.id.Horario);
        Sen_Termica = (TextView) vista.findViewById(R.id.Sen_Termica);
        Viento = (TextView) vista.findViewById(R.id.Viento);
        Temp_Min = (TextView) vista.findViewById(R.id.Temp_Min);
        Temp_Max = (TextView) vista.findViewById(R.id.Temp_Max);
        //Longi = (TextView) vista.findViewById(R.id.Long);
        //Lat = (TextView) vista.findViewById(R.id.Lati);
        Presion = (TextView) vista.findViewById(R.id.Presion);
        //Hume = (TextView) vista.findViewById(R.id.Humedad);
        //Visi = (TextView) vista.findViewById(R.id.Visi);

        //Poblacion = Poner_Ubicacion;

        Poner_Ubicacion = poblacion;
        String idioma = "&lang=es";
        String api = "http://api.openweathermap.org/data/2.5/weather?q=" + Poner_Ubicacion + "&appid=" + key + idioma;


        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        client.get(getContext(), api, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONObject Ti = null;
                String STRING = new String(responseBody);

                try {
                    Ti = new JSONObject(STRING);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                coord Corden = null;
                clouds Cloudes = null;
                sys Syso = null;
                main Mainn = null;
                wind Windy = null;
                weather Weathere = null;


                try {

                    //Poniendo todas las variables de cada classe en el json.

                    //Classe Coord
                    Corden = new coord(Ti.getJSONObject("coord").getDouble("lon"),
                            Ti.getJSONObject("coord").getDouble("lat"));

                    //Classe clouds
                    Cloudes = new clouds(Ti.getJSONObject("clouds").getDouble("all"));

                    //Classe sys
                    Syso = new sys(Ti.getJSONObject("sys").getInt("type"),
                            Ti.getJSONObject("sys").getInt("id"),
                            Ti.getJSONObject("sys").getString("country"),
                            Ti.getJSONObject("sys").getInt("sunrise"),
                            Ti.getJSONObject("sys").getInt("sunset"));

                    //Classe Mainn
                    Mainn = new main(Ti.getJSONObject("main").getDouble("temp"),
                            Ti.getJSONObject("main").getDouble("feels_like"),
                            Ti.getJSONObject("main").getDouble("temp_min"),
                            Ti.getJSONObject("main").getDouble("temp_max"),
                            Ti.getJSONObject("main").getDouble("pressure"),
                            Ti.getJSONObject("main").getDouble("humidity"));

                    //Classe wind
                    Windy = new wind(Ti.getJSONObject("wind").getDouble("speed"),
                            Ti.getJSONObject("wind").getDouble("deg"));

                    //Classe wheater
                    Weathere = new weather(Ti.getJSONArray("weather").getJSONObject(0).getInt("id"),
                            Ti.getJSONArray("weather").getJSONObject(0).getString("main"),
                            Ti.getJSONArray("weather").getJSONObject(0).getString("description"),
                            Ti.getJSONArray("weather").getJSONObject(0).optString("few_clouds", ""),
                            Ti.getJSONArray("weather").getJSONObject(0).optString("icon", ""));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Json = new City(Ti.getString("base"),
                            Cloudes,
                            Ti.getInt("cod"),
                            Corden,
                            Ti.getInt("dt"),
                            Ti.getInt("id"),
                            Mainn,
                            Ti.getString("name"),
                            Syso,
                            Ti.getInt("timezone"),
                            Ti.optInt("visibility", 0),
                            Weathere,
                            Windy
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Ponemos ciudad
                String Poner_Ciud = Json.getName();
                Pon_Ciud.setText(Poner_Ciud);

                //Convertimos los kel a cel
                double Grados_final = Json.getMain().getTemp() - 273.15;
                String Grados_Finall = String.valueOf(Grados_final);
                String Gra = Grados_Finall.charAt(0) + "" + Grados_Finall.charAt(1) + "º";
                Poner_Grados.setText(Gra);

                //Ponemos el tiempo
                Poner_Tiempo.setText(Json.getWeather().getDescription());

                //Cojemos la fecha del movil
                Date d = new Date();
                SimpleDateFormat data = new SimpleDateFormat("d MMMM ");
                String DataCom = data.format(d);
                Horario.setText(DataCom);

                //Hacemos igual que los grados
                double Sen_Ter = Json.getMain().getFeels_like() - 273.15;
                String Sen_Ter_Final = String.valueOf(Sen_Ter);
                String Sen_Ter_Stegen = Sen_Ter_Final.charAt(0) + "" + Sen_Ter_Final.charAt(1) + "º";
                Sen_Termica.setText(Sen_Ter_Stegen);

                Viento.setText("S " + String.valueOf(Json.getWind().getSpeed()) + " km/h");

                double Grados_Min = Json.getMain().getTemp_min() - 273.15;
                String Grados_Min_Final = String.valueOf(Grados_Min);
                String Gra2 = Grados_Min_Final.charAt(0) + "" + Grados_Min_Final.charAt(1) + "º";
                Temp_Min.setText(Gra2);

                double Grados_Max = Json.getMain().getTemp_max() - 273.15;
                String Grados_Max_Final = String.valueOf(Grados_Max);
                String Gra3 = Grados_Max_Final.charAt(0) + "" + Grados_Max_Final.charAt(1) + "º";
                Temp_Max.setText(Gra3);

                /*
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            ImageTi = (ImageView)findViewById(R.id.imageView2);
                            URL url = new URL("https://openweathermap.org/img/w/" + Json.getWeather().getIcon() +".png");
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            ImageTi.setImageBitmap(bmp);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();


                 */


                //Longi.setText(String.valueOf(Json.getCoord().getLon()));
                //Lat.setText(String.valueOf(Json.getCoord().getLat()));
                Presion.setText(String.valueOf(Json.getMain().getPressure()) + " hPa");
                //Hume.setText(String.valueOf(Json.getMain().getHumidity()) + "%");
                //Visi.setText(String.valueOf(Json.getVivibility() + " m"));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String STRING2 = new String(error.getMessage().toString());
                String valor = "No se ha podido recuperar los datos. " + STRING2;
            }
        });

        // AlertDialog dialog = builder.create();


        //Button BtnCancelar_Tipo = findViewById(R.id.btn_Cancelar_Tipo);
        //Button BtnAcceptar_Tipo = findViewById(R.id.btn_Acceptar_Tipo);

        //builder.setPositiveButton()
        builder.setView(vista).setPositiveButton("Vale", new DialogInterface.OnClickListener()  {
            public void onClick(DialogInterface dialog, int id) {
            return;

            }
        });

        //builder.setView(vista).setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }




    private void MostrarTiemo(View vista) {



    }



}
