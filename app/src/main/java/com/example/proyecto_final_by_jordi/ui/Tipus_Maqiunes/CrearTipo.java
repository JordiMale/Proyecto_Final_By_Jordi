package com.example.proyecto_final_by_jordi.ui.Tipus_Maqiunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.ui.Zones.CrearZona;

import java.util.zip.Inflater;

public class CrearTipo extends AppCompatActivity {

    private Datasource bd;
    private long idTask;

    EditText EtNom_Tipo;
    //EditText EtColor_Tipo;

    RadioButton RadioRojo;
    RadioButton RadioAzul;
    RadioButton RadioAmarillo;
    RadioButton RadioNaranja;
    RadioButton RadioRosa;
    RadioButton RadioLila;
    RadioButton RadioVerde;
    RadioButton RadioTurquesa;


    Button BtnCancelar_Tipo;
    Button BtnAcceptar_Tipo;
    int NumColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tipo);

        bd = new Datasource(CrearTipo.this);
        idTask = this.getIntent().getExtras().getLong("id");

        EtNom_Tipo = findViewById(R.id.Edt_Nom_Tipo);

       // EtColor_Tipo = findViewById(R.id.Edt_ColorTipo);

        RadioRojo = (RadioButton)findViewById(R.id.RadioRojo);
        RadioAzul = (RadioButton)findViewById(R.id.RadioAzul);
        RadioAmarillo = (RadioButton)findViewById(R.id.RadioAmarillo);
        RadioNaranja = (RadioButton)findViewById(R.id.RadioNaranja);
        RadioRosa = (RadioButton)findViewById(R.id.RadioRosa);
        RadioLila = (RadioButton)findViewById(R.id.RadioLila);
        RadioVerde = (RadioButton)findViewById(R.id.RadioVerde);
        RadioTurquesa = (RadioButton)findViewById(R.id.RadioTurquesa);


        BtnCancelar_Tipo = findViewById(R.id.btn_Cancelar_Tipo);
        BtnAcceptar_Tipo = findViewById(R.id.btn_Acceptar_Tipo);


        BtnAcceptar_Tipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptrcambios();

            }
        });

        BtnCancelar_Tipo.setOnClickListener(new View.OnClickListener() {
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

    private void acceptrcambios() {

        String GuardarRadio = "";
        if (RadioRojo.isChecked()){
            GuardarRadio = "Rojo";
        }else{
            if(RadioAzul.isChecked()){
                GuardarRadio = "Azul";
            }else{
                if(RadioAmarillo.isChecked()){
                    GuardarRadio = "Amarillo";
                }else{
                    if(RadioNaranja.isChecked()){
                        GuardarRadio = "Naranja";
                    }else{
                        if(RadioRosa.isChecked()){
                            GuardarRadio = "Rosa";
                        }else{
                            if(RadioLila.isChecked()){
                                GuardarRadio = "Lila";
                            }else{
                                if(RadioVerde.isChecked()){
                                    GuardarRadio = "Verde";
                                }else{
                                    if(RadioTurquesa.isChecked()){
                                        GuardarRadio = "Turquesa";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

/*
        String Color_Tipo = EtColor_Tipo.getText().toString();
        NumColor = 0;
        if(Color_Tipo.equalsIgnoreCase("ROJO")){
            NumColor = 1;
        }if(Color_Tipo.equalsIgnoreCase("AZUL")){
            NumColor = 2;
        }if(Color_Tipo.equalsIgnoreCase("AMARILLO")){
            NumColor = 3;
        }if(Color_Tipo.equalsIgnoreCase("NARANJA")){
            NumColor = 4;
        }if(Color_Tipo.equalsIgnoreCase("ROSA")){
            NumColor = 5;
        }if(Color_Tipo.equalsIgnoreCase("LILA")){
            NumColor = 6;
        }if(Color_Tipo.equalsIgnoreCase("VERDE")){
            NumColor = 7;
        }if(Color_Tipo.equalsIgnoreCase("TURQUESA")){
            NumColor = 8;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

 */
/*
        if(Color_Tipo.equalsIgnoreCase("ROJO")){
            NumColor = 1;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Color_Tipo.equalsIgnoreCase("AZUL")){
            NumColor = 2;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Color_Tipo.equalsIgnoreCase("AMARILLO")){
            NumColor = 3;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Color_Tipo.equalsIgnoreCase("NARANJA")){
            NumColor = 4;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Color_Tipo.equalsIgnoreCase("ROSA")){
            NumColor = 5;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Color_Tipo.equalsIgnoreCase("LILA")){
            NumColor = 6;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Color_Tipo.equalsIgnoreCase("VERDE")){
            NumColor = 7;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Color_Tipo.equalsIgnoreCase("TURQUESA")){
            NumColor = 8;
        }else{
            Toast.makeText(CrearTipo.this,"No has introduït un color correcte.",Toast.LENGTH_SHORT).show();
            return;
        }

 */

        String Nom_Tipo = EtNom_Tipo.getText().toString();
        if(Nom_Tipo.equals("")){
            Toast.makeText(CrearTipo.this,"No has introduït un nom",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean Cursor = bd.PoderCrearTipo(Nom_Tipo);
        if (idTask == -1) {
            if(Cursor != true){
                Toast.makeText(CrearTipo.this,"No pots crear un tipus que ja existeix.",Toast.LENGTH_SHORT).show();
                return;
            }else{
                idTask = bd.Crear_Tipo(Nom_Tipo,GuardarRadio);
            }
        }
        else {
            bd.Update_Tipo(idTask,Nom_Tipo,GuardarRadio);
        }

        Intent intent = new Intent();
        intent.putExtra("id", idTask);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void cancelarcambios() {
        Intent intent = new Intent();
        intent.putExtra("id", idTask);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void cargarDatos() {
        Cursor Cursor_Maquina = bd.EditarTipoId(idTask);
        Cursor_Maquina.moveToFirst();

        final EditText EtNom_Maquina = findViewById(R.id.Edt_Nom_Tipo);
        EtNom_Maquina.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.NOMMAQUINA)));

        String Auxiliar = (Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.COLOR_TIPUS)));
        if(Auxiliar.equalsIgnoreCase("Rojo")){
            RadioRojo.setChecked(true);
        }else{
            if(Auxiliar.equalsIgnoreCase("Azul")){
                RadioAzul.setChecked(true);
            }else{
                if(Auxiliar.equalsIgnoreCase("Amarillo")){
                    RadioAmarillo.setChecked(true);
                }else{
                    if(Auxiliar.equalsIgnoreCase("Naranja")){
                        RadioNaranja.setChecked(true);
                    }else{
                        if(Auxiliar.equalsIgnoreCase("Rosa")){
                            RadioRosa.setChecked(true);
                        }else{
                            if(Auxiliar.equalsIgnoreCase("Lila")){
                                RadioLila.setChecked(true);
                            }else{
                                if(Auxiliar.equalsIgnoreCase("Verde")){
                                    RadioVerde.setChecked(true);
                                }else{
                                    if(Auxiliar.equalsIgnoreCase("Turquesa")){
                                        RadioTurquesa.setChecked(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /*
        final EditText EtColorMaquina = findViewById(R.id.Edt_ColorTipo);
        String ColorNUm = Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.COLOR_TIPUS));
        if(ColorNUm.equals("1")){
            EtColorMaquina.setText("Rojo");
        }else{
            if(ColorNUm.equals("2")){
                EtColorMaquina.setText("Azul");
            }else{
                if(ColorNUm.equals("3")){
                    EtColorMaquina.setText("Amarillo");
                }else{
                    if(ColorNUm.equals("4")){
                        EtColorMaquina.setText("Naranja");
                    }else{
                        if(ColorNUm.equals("5")){
                            EtColorMaquina.setText("Rosa");
                        }else{
                            if(ColorNUm.equals("6")){
                                EtColorMaquina.setText("Lila");
                            }else{
                                if(ColorNUm.equals("7")){
                                    EtColorMaquina.setText("Verde");
                                }else{
                                    if(ColorNUm.equals("8")){
                                        EtColorMaquina.setText("Turquesa");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

         */
        //EtColorMaquin.setText(Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.COLOR_TIPUS)));
    }
}