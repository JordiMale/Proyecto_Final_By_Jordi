package com.example.proyecto_final_by_jordi.ui.Gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.Tipo;
import com.example.proyecto_final_by_jordi.Zona;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class CrearEditarMaquina extends AppCompatActivity {


    private Datasource bd;
    private long idTask;

    EditText EtNom_Maquina;
    EditText EtAdreça_Maquina;
    EditText EtCP;
    EditText EtPoblacion;
    EditText EtTelefono;
    EditText EtGmail;
    EditText EtNumero_Serie;
    EditText EtData;
    ImageView BtnData;

    Spinner STipo;
    Spinner SZona;

    String Optri;
    boolean NotNulll;
    int IdTipoFinal;

    String Zotri;
    boolean NotNullll;
    int IdZonaFinal;

    ArrayList<Tipo>TipoList;
    ArrayList<String>ListaTipo;
    ArrayAdapter<String> AdaperTipo;
    ArrayList<Integer>IntAuxTipo;

    ArrayList<Zona>ZonaList;
    ArrayList<String>ListaZona;
    ArrayAdapter<String> AdaperZona;
    ArrayList<Integer>IntAuxZona;

    Button BtnCancelar;
    Button BtnAcceptar;


    public void MostrarSpinner(){

        Cursor CursorSpinnerT = bd.Todo_Tipo();
        Cursor CursorSpinnerZ = bd.Todo_Zona();

        Tipo TipoObj;

        Zona ZonaObj;

        TipoList = new ArrayList<Tipo>();
        ListaTipo = new ArrayList<String>();
        IntAuxTipo = new ArrayList<Integer>();

        ZonaList = new ArrayList<Zona>();
        ListaZona = new ArrayList<String>();
        IntAuxZona = new ArrayList<Integer>();



        while(CursorSpinnerT.moveToNext()){
            int Id =  CursorSpinnerT.getInt(CursorSpinnerT.getColumnIndex(Datasource.IDGENERAL));
            String NomTipus = CursorSpinnerT.getString(CursorSpinnerT.getColumnIndex(Datasource.NOMMAQUINA));
            TipoObj = new Tipo(Id, NomTipus);
            TipoList.add(TipoObj);
        }

        while(CursorSpinnerZ.moveToNext()){
            int Id =  CursorSpinnerZ.getInt(CursorSpinnerZ.getColumnIndex(Datasource.IDGENERAL));
            String NomZona = CursorSpinnerZ.getString(CursorSpinnerZ.getColumnIndex(Datasource.NOMZONA));
            ZonaObj = new Zona(Id, NomZona);
            ZonaList.add(ZonaObj);
        }
        CursorSpinnerT.close();
        CursorSpinnerZ.close();

        for(int i = 0; i < TipoList.size();i++){

            ListaTipo.add(TipoList.get(i).getNom_Tipo());
            IntAuxTipo.add(TipoList.get(i).getId());
        }

        for(int i = 0; i < ZonaList.size();i++){

            ListaZona.add(ZonaList.get(i).getNom_Zona());
            IntAuxZona.add(ZonaList.get(i).getId());
        }



        AdaperTipo = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ListaTipo);
        AdaperZona = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ListaZona);

        STipo.setAdapter(AdaperTipo);

        STipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Optri = STipo.getSelectedItem().toString();

                if(Optri!="")
                {
                    for(int j = 0; j < IntAuxTipo.size(); j++){
                        if(Optri.equalsIgnoreCase(ListaTipo.get(j))){
                            IdTipoFinal = IntAuxTipo.get(j);
                        }
                    }
                    NotNulll=false;
                }
                else{

                    NotNulll=true;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SZona.setAdapter(AdaperZona);

        SZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Zotri = SZona.getSelectedItem().toString();

                if(Zotri!="")
                {
                    for(int j = 0; j < IntAuxZona.size(); j++){
                        if(Zotri.equalsIgnoreCase(ListaZona.get(j))){
                            IdZonaFinal = IntAuxZona.get(j);
                        }
                    }
                    NotNullll=false;
                }
                else{

                    NotNullll=true;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_maquina);

            bd = new Datasource(CrearEditarMaquina.this);
            idTask = this.getIntent().getExtras().getLong("id");


        EtNom_Maquina = findViewById(R.id.Edit_text_Nombre_Cliente);
        EtAdreça_Maquina = findViewById(R.id.Edit_Text_Adreça);
        EtCP = findViewById(R.id.Edit_Text_CP);
        EtPoblacion = findViewById(R.id.Edit_Text_Poblacio);
        EtTelefono = findViewById(R.id.Edit_Text_Telefono);
        EtGmail = findViewById(R.id.Edit_Text_Gmail);
        EtNumero_Serie = findViewById(R.id.Edit_Text_Numero_Serie);
        EtData = findViewById(R.id.Edit_Text_Data);

        BtnData = findViewById(R.id.BuscarData);

        STipo = findViewById(R.id.Spinner_Tipo);
        SZona = findViewById(R.id.Spinner_Zona);

        BtnCancelar = findViewById(R.id.btn_Cancelar);
        BtnAcceptar = findViewById(R.id.btn_Acceptar);

        MostrarSpinner();


        BtnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int any = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(CrearEditarMaquina.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String Halooo;
                        int monthaux = month + 1;
                        Halooo = dayOfMonth + "-" + monthaux + "-" + year;
                        EtData.setText(Halooo);

                    }
                }, any, mes, dia);
                dpd.show();

            }

        });

        BtnAcceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptrcambios();

            }
        });

        BtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarcambios();

            }
        });

        // Busquem el id que estem modificant
        // si el el id es -1 vol dir que s'està creant
        idTask = this.getIntent().getExtras().getLong("id");

        if (idTask != -1) {
            // Si estem modificant carreguem les dades en pantalla
            cargarDatos();
        }

    }


    private void cargarDatos() {
        Cursor Cursor_Maquina = bd.EditarMaquinaId(idTask);
        Cursor_Maquina.moveToFirst();


        final EditText EtNom_Maquina = findViewById(R.id.Edit_text_Nombre_Cliente);
        EtNom_Maquina.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.NOM)));

        final EditText EtAdreça_Maquina = findViewById(R.id.Edit_Text_Adreça);
        EtAdreça_Maquina.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.ADREÇA)));

        final EditText EtCP = findViewById(R.id.Edit_Text_CP);
        EtCP.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.CP)));

        final EditText EtPoblacion = findViewById(R.id.Edit_Text_Poblacio);
        EtPoblacion.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.POBLACIO)));

        final EditText EtTelefono = findViewById(R.id.Edit_Text_Telefono);
        EtTelefono.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.TELEFON)));

        final EditText EtGmail = findViewById(R.id.Edit_Text_Gmail);
        EtGmail.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.GMAIL)));

        final EditText EtNumero_Serie = findViewById(R.id.Edit_Text_Numero_Serie);
        EtNumero_Serie.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.NUMEROSERIE)));

        final TextView TXData = findViewById(R.id.Edit_Text_Data);
        TXData.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.DATA)));

    }

    private void cancelarcambios() {
        Intent intent = new Intent();
        intent.putExtra("id", idTask);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void acceptrcambios() {
        int CPINT = 0;

        String Nom_Maquina = EtNom_Maquina.getText().toString();
        if (Nom_Maquina.equals("")) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït un nom", Toast.LENGTH_SHORT).show();
            return;

        }

        String Adreça_Maquina = EtAdreça_Maquina.getText().toString();
        if (Adreça_Maquina.equals("")) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït una adreça", Toast.LENGTH_SHORT).show();
            return;
        }

        String CP = EtCP.getText().toString();


        if (CP.equals("")) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït un Codi postal", Toast.LENGTH_SHORT).show();
            return;

        } else {
            CPINT = Integer.parseInt(CP);
        }

        String Poblacion = EtPoblacion.getText().toString();
        if (Poblacion.equals("")) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït una població", Toast.LENGTH_SHORT).show();
            return;

        }

        String Telefono = EtTelefono.getText().toString();

        String Gmail = EtGmail.getText().toString();
        if (!Gmail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït un gmail correcte.", Toast.LENGTH_SHORT).show();
            return;
        }


        String Numero_Serie = EtNumero_Serie.getText().toString();
        //Cursor CursorMirar = bd.MirarNumSerie(Numero_Serie);

        if (Numero_Serie.equals("")) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït un numero de serie", Toast.LENGTH_SHORT).show();
            return;
        }
        String Data = EtData.getText().toString();

            boolean CursorMirar2 = bd.Update(idTask,Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, IdTipoFinal, IdZonaFinal);
            if(CursorMirar2 == true){
                Toast.makeText(CrearEditarMaquina.this, "No pots pots una maquina amb el mateix numero de serie.", Toast.LENGTH_SHORT).show();
                return;
            }


        if(NotNulll == true){
            Toast.makeText(CrearEditarMaquina.this,"No has introduït un tipus de maqiuna",Toast.LENGTH_SHORT).show();
            return;
        }


        if(NotNullll == true){
            Toast.makeText(CrearEditarMaquina.this,"No has introduït una zona",Toast.LENGTH_SHORT).show();
            return;

        }




        if (idTask == -1) {
            idTask = bd.Crear(Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, IdTipoFinal, IdZonaFinal);
        }
        else {
            bd.Update(idTask,Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, IdTipoFinal, IdZonaFinal);


        }

        //bd.Crear(Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, Tipo, Zona);

        Intent intent = new Intent();
        intent.putExtra("id", idTask);
        setResult(RESULT_OK, intent);
        finish();


    }



}