package com.example.proyecto_final_by_jordi.ui.Zones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.Filtro;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.ui.Gestion.CrearEditarMaquina;
import com.example.proyecto_final_by_jordi.ui.Gestion.GestionFragment;

import static android.app.Activity.RESULT_OK;

public class ZonesFragment extends Fragment {

    private static int TASK_ADD = 1;
    private static int TASK_UPDATE = 2;

    private Datasource bd;
    private long idActual;
    private adapterTodoIcon scTasks;

    private static String[] from = new String[]{
           // Datasource.IDGENERAL,
            Datasource.NOMZONA,};

    private static int[] to = new int[]{
            R.id.Nom_Zona,};


    Button btn_Ir_Crear_Zona;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TASK_ADD) {
            if (resultCode == RESULT_OK) {
                // Carreguem tots els productes
                ActualizarZona();
            }

        }
        if (requestCode == TASK_UPDATE) {
            if (resultCode == RESULT_OK) {
                ActualizarZona();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.CrearZona:
                AddZones();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.mainnn, menu);

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_zones, container, false);
        bd = new Datasource(getContext());

        setHasOptionsMenu(true);


        /*
        btn_Ir_Crear_Zona = v.findViewById(R.id.btn_Zona);

        btn_Ir_Crear_Zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddZones();
            }
        });


         */
        loadTasks(v);
        return v;
    }


    private void loadTasks(View v) {
        // Demanem totes les tasques
        Cursor cursorTasks = bd.Todo_Zona();

        // Now create a simple cursor adapter and set it to display
        scTasks = new adapterTodoIcon(getContext(),
                R.layout.row_zona,
                cursorTasks,
                from,
                to,
                1, ZonesFragment.this);

        ListView lv = (ListView) v.findViewById(R.id.list1);
        lv.setAdapter(scTasks);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        // modifiquem el id
                        updateTask(id);
                    }
                }
        );
    }

    private void AddZones() {
        Bundle bundle = new Bundle();
        idActual = -1;
        bundle.putLong("id", idActual);

        Intent intent = new Intent(getActivity(), CrearZona.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, TASK_ADD);
    }


    private void updateTask(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);

        idActual = id;


        Intent i = new Intent(getActivity(), CrearZona.class);
        i.putExtras(bundle);
        startActivityForResult(i, TASK_UPDATE);

    }

    private void ActualizarZona() {
        Cursor cursorTasks = bd.Todo_Zona();

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();
    }

    //Elimina una maquina
    private void DeleteZona(int Id){

        Cursor CursorDeleteSiPuedo = bd.PoderEliminarZona(Id);
        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("¿Desitja eliminar la tasca?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if(CursorDeleteSiPuedo.moveToFirst()){
                    Toast.makeText(getContext(),"No pots eliminar ja que esta assignada a una maquina",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    bd.BorrarZona(Id);
                    ActualizarZona();
                }


            }
        });

        builder.setNegativeButton("No", null);

        builder.show();


    }

    private void GmapsZona(long id){

        String  IDZonas = String.valueOf((int) id);
        Bundle bundle = new Bundle();
        bundle.putString("idZ", IDZonas);

        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_navigation_Zones_to_GoogleFragment, bundle);
    }



    class adapterTodoIcon extends android.widget.SimpleCursorAdapter {

        private static final String colorTaskPending = "#d78290";
        private static final String colorTaskCompleted = "#d7d7d7";

        private ZonesFragment oTodoListIcon;

        public adapterTodoIcon(Context context, int layout, Cursor c, String[] from, int[] to, int flags, ZonesFragment ZonaFra) {
            super(context, layout, c, from, to, flags);
            oTodoListIcon = ZonaFra;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);

            // Agafem l'objecte de la view que es una LINEA DEL CURSOR
            Cursor linia = (Cursor) getItem(position);


            //Imagen para eliminar la zona.
            ImageView BtnEliminar = (ImageView) view.findViewById(R.id.Basura_Zona_Maquina);
            BtnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Carrego la linia del cursor de la posició.
                    Cursor linia = (Cursor) getItem(position);

                    oTodoListIcon.DeleteZona(linia.getInt(linia.getColumnIndexOrThrow(Datasource.IDGENERAL)));
                }
            });

            //Imagen para ir a gmaps la zona.
            ImageView BtnGmaps = (ImageView) view.findViewById(R.id.Gmaps_Zona_Maquina);
            BtnGmaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Carrego la linia del cursor de la posició.
                    Cursor linia = (Cursor) getItem(position);

                    oTodoListIcon.GmapsZona(linia.getInt(linia.getColumnIndexOrThrow(Datasource.IDGENERAL)));
                }
            });


            return view;
        }
    }
}
