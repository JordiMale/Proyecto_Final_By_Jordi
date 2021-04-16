package com.example.proyecto_final_by_jordi.ui.Gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.Tipo;
import com.example.proyecto_final_by_jordi.Zona;
import com.example.proyecto_final_by_jordi.ui.Tipus_Maqiunes.CrearTipo;
import com.example.proyecto_final_by_jordi.ui.Zones.CrearZona;

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

    ArrayList<Tipo> TipoList;
    ArrayList<String> ListaTipo;
    ArrayAdapter<String> AdaperTipo;
    ArrayList<Integer> IntAuxTipo;
    ArrayList<String>ListaTipoFinalEditar;

    ArrayList<Zona> ZonaList;
    ArrayList<String> ListaZona;
    ArrayAdapter<String> AdaperZona;
    ArrayList<Integer> IntAuxZona;
    ArrayList<String>ListaZonaFinalEditar;

    Button BtnCancelar;
    Button BtnAcceptar;
    ImageView IrTipo;
    ImageView IrZona;


    public void MostrarSpinner() {

        Cursor CursorSpinnerT = bd.Todo_Tipo();
        Cursor CursorSpinnerZ = bd.Todo_Zona();
        Cursor CursorCojerMaquina = bd.EditarMaquinaId(idTask);
        CursorCojerMaquina.moveToFirst();

        Tipo TipoObj;

        Zona ZonaObj;

        TipoList = new ArrayList<Tipo>();
        ListaTipo = new ArrayList<String>();
        IntAuxTipo = new ArrayList<Integer>();
        ListaTipoFinalEditar = new ArrayList<String>();

        ZonaList = new ArrayList<Zona>();
        ListaZona = new ArrayList<String>();
        IntAuxZona = new ArrayList<Integer>();
        ListaZonaFinalEditar = new ArrayList<String>();


        while (CursorSpinnerT.moveToNext()) {
            int Id = CursorSpinnerT.getInt(CursorSpinnerT.getColumnIndex(Datasource.IDGENERAL));
            String NomTipus = CursorSpinnerT.getString(CursorSpinnerT.getColumnIndex(Datasource.NOMMAQUINA));
            TipoObj = new Tipo(Id, NomTipus);
            TipoList.add(TipoObj);
        }

        while (CursorSpinnerZ.moveToNext()) {
            int Id = CursorSpinnerZ.getInt(CursorSpinnerZ.getColumnIndex(Datasource.IDGENERAL));
            String NomZona = CursorSpinnerZ.getString(CursorSpinnerZ.getColumnIndex(Datasource.NOMZONA));
            ZonaObj = new Zona(Id, NomZona);
            ZonaList.add(ZonaObj);
        }
        CursorSpinnerT.close();
        CursorSpinnerZ.close();

        //Esto es creando una maquina el spinner
        if(idTask == -1){
            for (int i = 0; i < TipoList.size(); i++) {

            ListaTipo.add(TipoList.get(i).getId() + "- " + TipoList.get(i).getNom_Tipo());

        }

            for (int i = 0; i < ZonaList.size(); i++) {

                ListaZona.add(ZonaList.get(i).getId() + "- " + ZonaList.get(i).getNom_Zona());

            }

            AdaperTipo = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ListaTipo);
            AdaperZona = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ListaZona);

        }else{
            //Esto es editando una maquina el spinner
            if(idTask != -1){
                //Estos dos fors son para cuando edito me salga el que habia escojido primero(Tipo)
                for(int i = 0; i < TipoList.size(); i ++){
                    if(TipoList.get(i).getId() == CursorCojerMaquina.getInt(CursorCojerMaquina.getColumnIndex(Datasource.TIPO))){
                        ListaTipoFinalEditar.add(String.valueOf(TipoList.get(i).getId() + "- " + TipoList.get(i).getNom_Tipo()));

                    }else{
                        ListaTipo.add(String.valueOf(TipoList.get(i).getId() + "- " + TipoList.get(i).getNom_Tipo()));

                    }
                }

                for(int j = 0; j < ListaTipo.size(); j++){
                    ListaTipoFinalEditar.add(ListaTipo.get(j));
                }

                //Estos dos fors son para cuando edito me salga el que habia escojido primero(Zona)
                for(int x = 0; x < ZonaList.size(); x++){
                    if(ZonaList.get(x).getId() == CursorCojerMaquina.getInt(CursorCojerMaquina.getColumnIndex(Datasource.ZONA))){
                        ListaZonaFinalEditar.add(String.valueOf(ZonaList.get(x).getId() + "- " + ZonaList.get(x).getNom_Zona()));
                    }else{
                        ListaZona.add(String.valueOf(ZonaList.get(x).getId() + "- " + ZonaList.get(x).getNom_Zona()));
                    }
                }

                for(int y = 0; y < ListaZona.size(); y++){
                    ListaZonaFinalEditar.add(ListaZona.get(y));
                }

                AdaperTipo = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ListaTipoFinalEditar);
                AdaperZona = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ListaZonaFinalEditar);
            }
        }





        STipo.setAdapter(AdaperTipo);

        STipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Optri = STipo.getSelectedItem().toString();
                String Aux = "";
                int contameesapelotudo = 0;

                    if (Optri != "") {
                        while(Optri.charAt(contameesapelotudo) != '-'){
                            Aux = Aux + "" + Optri.charAt(contameesapelotudo);
                            contameesapelotudo++;
                        }
                        IdTipoFinal = Integer.parseInt(Aux);
                        NotNulll = false;
                    } else {

                        NotNulll = true;
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
                String Aux2 = "";
                int contameesapelotudo2 = 0;


                    if (Zotri != "") {
                        while(Zotri.charAt(contameesapelotudo2) != '-'){
                            Aux2 = Aux2 + "" + Zotri.charAt(contameesapelotudo2);
                            contameesapelotudo2++;
                        }
                        IdZonaFinal = Integer.parseInt(Aux2);
                        NotNullll = false;
                    } else {

                        NotNullll = true;
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
        IrTipo = findViewById(R.id.Añadir_Tipo_En_Crear_Maquina);
        IrZona = findViewById(R.id.Añadir_Zona_En_Crear_Maquina);

        MostrarSpinner();


        IrTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearTipo();
                MostrarSpinner();

            }
        });

        IrZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearZona();

            }
        });



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


    //Para crear un tipo dentro de una maquina
    private void CrearTipo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearEditarMaquina.this);

        LayoutInflater layout = this.getLayoutInflater();

        View vista = layout.inflate(R.layout.activity_crear_tipo_alert, null);



       // AlertDialog dialog = builder.create();


        //Button BtnCancelar_Tipo = findViewById(R.id.btn_Cancelar_Tipo);
        //Button BtnAcceptar_Tipo = findViewById(R.id.btn_Acceptar_Tipo);

        //builder.setPositiveButton()
        builder.setView(vista).setPositiveButton("Si", new DialogInterface.OnClickListener()  {
            public void onClick(DialogInterface dialog, int id) {
                acceptrcambiosTipo(vista);
                MostrarSpinner();

            }
        });

        builder.setView(vista).setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        MostrarSpinner();



    }

    private void acceptrcambiosTipo(View v) {

            EditText EtNom_Tipo_Alert = v.findViewById(R.id.Edt_Nom_Tipo_Alert);

            RadioButton RadioRojo_Alert = (RadioButton)v.findViewById(R.id.RadioRojo_Alert);
            RadioButton RadioAzul_Alert = (RadioButton)v.findViewById(R.id.RadioAzul_Alert);
            RadioButton RadioAmarillo_Alert = (RadioButton)v.findViewById(R.id.RadioAmarillo_Alert);
            RadioButton RadioNaranja_Alert = (RadioButton)v.findViewById(R.id.RadioNaranja_Alert);
            RadioButton RadioRosa_Alert = (RadioButton)v.findViewById(R.id.RadioRosa_Alert);
            RadioButton RadioLila_Alert = (RadioButton)v.findViewById(R.id.RadioLila_Alert);
            RadioButton RadioVerde_Alert = (RadioButton)v.findViewById(R.id.RadioVerde_Alert);
            RadioButton RadioTurquesa_Alert = (RadioButton)v.findViewById(R.id.RadioTurquesa_Alert);

            String GuardarRadio_Alert = "";
        if (RadioRojo_Alert.isChecked()){
            GuardarRadio_Alert = "#F9433B";
        }else{
            if(RadioAzul_Alert.isChecked()){
                GuardarRadio_Alert = "#00B4FF";
            }else{
                if(RadioAmarillo_Alert.isChecked()){
                    GuardarRadio_Alert = "#F0FB35";
                }else{
                    if(RadioNaranja_Alert.isChecked()){
                        GuardarRadio_Alert = "#FF9F4F";
                    }else{
                        if(RadioRosa_Alert.isChecked()){
                            GuardarRadio_Alert = "#FB3DE9";
                        }else{
                            if(RadioLila_Alert.isChecked()){
                                GuardarRadio_Alert = "#C74CFF";
                            }else{
                                if(RadioVerde_Alert.isChecked()){
                                    GuardarRadio_Alert = "#70FF4C";
                                }else{
                                    if(RadioTurquesa_Alert.isChecked()){
                                        GuardarRadio_Alert = "#4CFFDA";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


            String Nom_Tipo_Alert = EtNom_Tipo_Alert.getText().toString();
            if(Nom_Tipo_Alert.equals("")){
                Toast.makeText(this,"No has introduït un nom",Toast.LENGTH_SHORT).show();
                return;
            }


            bd.Crear_Tipo(Nom_Tipo_Alert,GuardarRadio_Alert);

        }


    //Para crear zonas dentro de maquinas
    private void CrearZona() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CrearEditarMaquina.this);

        LayoutInflater layout = this.getLayoutInflater();

        View vista = layout.inflate(R.layout.activity_crear_zona_alert, null);



        // AlertDialog dialog = builder.create();


        //Button BtnCancelar_Tipo = findViewById(R.id.btn_Cancelar_Tipo);
        //Button BtnAcceptar_Tipo = findViewById(R.id.btn_Acceptar_Tipo);

        //builder.setPositiveButton()
        builder.setView(vista).setPositiveButton("Si", new DialogInterface.OnClickListener()  {
            public void onClick(DialogInterface dialog, int id) {
                acceptrcambiosZona(vista);
                MostrarSpinner();


            }
        });

        builder.setView(vista).setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        MostrarSpinner();

    }

    private void acceptrcambiosZona(View v) {

        EditText EtNom_Zona_Alert;
        EtNom_Zona_Alert = v.findViewById(R.id.Edt_Nom_Zona_Alert);

        String Nom_Zona_Alert = EtNom_Zona_Alert.getText().toString();
        if(Nom_Zona_Alert.equals("")){
            Toast.makeText(this,"No has introduït un nom",Toast.LENGTH_SHORT).show();
            return;
        }

        bd.Crear_Zona(Nom_Zona_Alert);

    }


    //Para la maquina
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

        boolean CursorMirar2 = bd.Update(idTask, Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, IdTipoFinal, IdZonaFinal);
        if (CursorMirar2 == true) {
            Toast.makeText(CrearEditarMaquina.this, "No pots posar una maquina amb el mateix numero de serie.", Toast.LENGTH_SHORT).show();
            return;
        }


        if (NotNulll == true) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït un tipus de maqiuna", Toast.LENGTH_SHORT).show();
            return;
        }


        if (NotNullll == true) {
            Toast.makeText(CrearEditarMaquina.this, "No has introduït una zona", Toast.LENGTH_SHORT).show();
            return;

        }

        boolean Cursor = bd.PoderCrear(Numero_Serie);

        if (idTask == -1) {
            if(Cursor != true ){
                Toast.makeText(CrearEditarMaquina.this, "No pots crear una maquina amb el mateix numero de serie", Toast.LENGTH_SHORT).show();
                return;
            }else{
                idTask = bd.Crear(Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, IdTipoFinal, IdZonaFinal);
            }
        } else {
            bd.Update(idTask, Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, IdTipoFinal, IdZonaFinal);
        }

        //bd.Crear(Nom_Maquina, Adreça_Maquina, CPINT, Poblacion, Telefono, Gmail, Numero_Serie, Data, Tipo, Zona);

        Intent intent = new Intent();
        intent.putExtra("id", idTask);
        setResult(RESULT_OK, intent);
        finish();


    }


}