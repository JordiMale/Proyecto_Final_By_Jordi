package com.example.proyecto_final_by_jordi.ui.Tipus_Maqiunes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.Filtro;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.ui.Gestion.GestionFragment;
import com.example.proyecto_final_by_jordi.ui.Zones.CrearZona;
import com.example.proyecto_final_by_jordi.ui.Zones.ZonesFragment;

import static android.app.Activity.RESULT_OK;

public class TipusFragment extends Fragment {

    private static int TASK_ADD = 1;
    private static int TASK_UPDATE = 2;

    private Datasource bd;
    private long idActual;
    private SimpleCursorAdapter scTasks;

    private static String[] from = new String[]{
            //Datasource.IDGENERAL,
            Datasource.NOMMAQUINA,};

    private static int[] to = new int[]{
            R.id.Nom_Tipo_Maquina,};

    Button btn_Ir_Crear_Tipo;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TASK_ADD) {
            if (resultCode == RESULT_OK) {
                // Carreguem tots els productes
                ActualizarTipo();
            }

        }
        if (requestCode == TASK_UPDATE) {
            if (resultCode == RESULT_OK) {
                ActualizarTipo();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.CrearTipo:
                AddTipo();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.mainn, menu);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tipus, container, false);
        bd = new Datasource(getContext());

        setHasOptionsMenu(true);

        /*
        btn_Ir_Crear_Tipo = v.findViewById(R.id.btn_Tipo);

        btn_Ir_Crear_Tipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTipo();
            }
        });

         */
        loadTasks(v);
        return v;
    }



    private void loadTasks(View v) {
        // Demanem totes les tasques
        Cursor cursorTasks = bd.Todo_Tipo();

        // Now create a simple cursor adapter and set it to display
        scTasks = new adapterTodoIcon(getContext(),
                R.layout.row_tipo,
                cursorTasks,
                from,
                to,
                1,TipusFragment.this);

        ListView lv = (ListView) v.findViewById(R.id.list1);
        lv.setAdapter(scTasks);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        // modifiquem el id
                        updateTask(id);
                    }
                }
        );
    }

    private void AddTipo() {
        Bundle bundle = new Bundle();
        idActual = -1;
        bundle.putLong("id", idActual);

        Intent intent = new Intent(getActivity(), CrearTipo.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, TASK_ADD);
    }

    private void updateTask(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);

        idActual = id;


        Intent i = new Intent(getActivity(), CrearTipo.class);
        i.putExtras(bundle);
        startActivityForResult(i, TASK_UPDATE);

    }

    private void ActualizarTipo() {
        Cursor cursorTasks = bd.Todo_Tipo();

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();
    }

    //Elimina una Tipo
    private void DeleteTipo(int Id){

        Cursor CursorDeleteSiPuedo = bd.PoderEliminarTipo(Id);
        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("¿Desitja eliminar la tasca?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(CursorDeleteSiPuedo.moveToFirst()){
                    Toast.makeText(getContext(),"No pots eliminar ja que esta assignada a una maquina",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    bd.BorrarTipo(Id);
                    ActualizarTipo();
                }


            }
        });

        builder.setNegativeButton("No", null);

        builder.show();


    }



    class adapterTodoIcon extends android.widget.SimpleCursorAdapter {

        private static final String ROJO = "#F9433B";
        private static final String AZUL = "#00B4FF";
        private static final String AMARILLO = "#F0FB35";
        private static final String NARANJA = "#FF9F4F";
        private static final String ROSA = "#FB3DE9";
        private static final String LILA = "#C74CFF";
        private static final String VERDE = "#70FF4C";
        private static final String TURQUESA = "#4CFFDA";

        private TipusFragment oTodoListIcon;

        public adapterTodoIcon(Context context, int layout, Cursor c, String[] from, int[] to, int flags, TipusFragment TipoFra) {
            super(context, layout, c, from, to, flags);
            oTodoListIcon = TipoFra;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);

            // Agafem l'objecte de la view que es una LINEA DEL CURSOR

            Cursor linia = (Cursor) getItem(position);

            String done = linia.getString(linia.getColumnIndexOrThrow(Datasource.COLOR_TIPUS));

            if(done.equalsIgnoreCase(ROJO)) {
                view.setBackgroundColor(Color.parseColor(ROJO));
            }
            if(done.equalsIgnoreCase(AZUL)) {
                view.setBackgroundColor(Color.parseColor(AZUL));
            }
            if(done.equalsIgnoreCase(AMARILLO)) {
                view.setBackgroundColor(Color.parseColor(AMARILLO));
            }
            if(done.equalsIgnoreCase(NARANJA)) {
                view.setBackgroundColor(Color.parseColor(NARANJA));
            }
            if(done.equalsIgnoreCase(ROSA)) {
                view.setBackgroundColor(Color.parseColor(ROSA));
            }
            if(done.equalsIgnoreCase(LILA)) {
                view.setBackgroundColor(Color.parseColor(LILA));
            }
            if(done.equalsIgnoreCase(VERDE)) {
                view.setBackgroundColor(Color.parseColor(VERDE));
            }
            if(done.equalsIgnoreCase(TURQUESA)) {
                view.setBackgroundColor(Color.parseColor(TURQUESA));
            }
/*
            String pintar = linia.getString(linia.getColumnIndex(Datasource.COLOR_TIPUS));

            if(pintar.equalsIgnoreCase("ROJO")){
                view.setBackgroundColor(Color.parseColor(ROJO));
            }else{
                if(pintar.equalsIgnoreCase("AZUL")){
                    view.setBackgroundColor(Color.parseColor(AZUL));
                }else{
                    if(pintar.equalsIgnoreCase("AMARILLO")){
                        view.setBackgroundColor(Color.parseColor(AMARILLO));
                    }else{
                        if(pintar.equalsIgnoreCase("NARANJA")){
                            view.setBackgroundColor(Color.parseColor(NARANJA));
                        }else{
                            if(pintar.equalsIgnoreCase("ROSA")){
                                view.setBackgroundColor(Color.parseColor(ROSA));
                            }else{
                                if(pintar.equalsIgnoreCase("LILA")){
                                    view.setBackgroundColor(Color.parseColor(LILA));
                                }else{
                                    if(pintar.equalsIgnoreCase("VERDE")){
                                        view.setBackgroundColor(Color.parseColor(VERDE));
                                    }else{
                                        if(pintar.equalsIgnoreCase("TURQUESA")){
                                            view.setBackgroundColor(Color.parseColor(TURQUESA));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

 */


            //Imagen para eliminar el tipo.
            ImageView BtnEliminar = (ImageView) view.findViewById(R.id.Basura_Tipo_Maquina);
            BtnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Carrego la linia del cursor de la posició.
                    Cursor linia = (Cursor) getItem(position);

                    oTodoListIcon.DeleteTipo(linia.getInt(linia.getColumnIndexOrThrow(Datasource.IDGENERAL)));
                }
            });





            return view;
        }
    }


}