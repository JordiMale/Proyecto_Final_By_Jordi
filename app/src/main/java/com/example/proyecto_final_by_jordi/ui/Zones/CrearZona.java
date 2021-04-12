package com.example.proyecto_final_by_jordi.ui.Zones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.ui.Gestion.CrearEditarMaquina;

public class CrearZona extends AppCompatActivity {

    private Datasource bd;
    private long idTask;

    EditText EtNom_Zona;

    Button BtnCancelar_Zona;
    Button BtnAcceptar_Zona;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_zona);


        bd = new Datasource(CrearZona.this);
        idTask = this.getIntent().getExtras().getLong("id");



        EtNom_Zona = findViewById(R.id.Edt_Nom_Zona);
        BtnCancelar_Zona = findViewById(R.id.btn_Cancelar_Zona);
        BtnAcceptar_Zona = findViewById(R.id.btn_Acceptar_Zona);

        BtnAcceptar_Zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptrcambios();

            }
        });

        BtnCancelar_Zona.setOnClickListener(new View.OnClickListener() {
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
        Cursor Cursor_Maquina = bd.EditarZonaId(idTask);
        Cursor_Maquina.moveToFirst();

        final EditText EtNom_Maquina = findViewById(R.id.Edt_Nom_Zona);
        EtNom_Maquina.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.NOMZONA)));
    }

    private void cancelarcambios() {
        Intent intent = new Intent();
        intent.putExtra("id", idTask);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void acceptrcambios() {


        String Nom_Zona = EtNom_Zona.getText().toString();
        if(Nom_Zona.equals("")){
            Toast.makeText(CrearZona.this,"No has introduït un nom",Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        boolean CursorMirarZona = bd.Update_Zona(idTask, Nom_Zona);
        if(CursorMirarZona == true){
            Toast.makeText(this, "Ja hi ha una zona amb el mateix nom!!!.", Toast.LENGTH_SHORT).show();
            return;
        }

         */

        boolean Cursor = bd.PoderCrearZona(Nom_Zona);
        if (idTask == -1) {
            if(Cursor != true){
                Toast.makeText(this, "No pots crear una zonaque ja existeix.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                idTask = bd.Crear_Zona(Nom_Zona);
            }
        }
        else {
            bd.Update_Zona(idTask,Nom_Zona);

        }


        Intent intent = new Intent();
        intent.putExtra("id", idTask);
        setResult(RESULT_OK, intent);
        finish();

    }
}