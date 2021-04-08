package com.example.proyecto_final_by_jordi.ui.Gestion;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.proyecto_final_by_jordi.BD.Datasource;
import com.example.proyecto_final_by_jordi.Filtro;
import com.example.proyecto_final_by_jordi.MainActivity;
import com.example.proyecto_final_by_jordi.R;
import com.example.proyecto_final_by_jordi.ui.GMaps.GoogleFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;

public class GestionFragment extends Fragment {

    private static int TASK_ADD = 1;
    private static int TASK_UPDATE = 2;


    private Datasource bd;
    private long idActual;
    private adapterTodoIcon scTasks;

    private Filtro FiltreAplicat;

    ListView lv;


    private static String[] from = new String[]{
            Datasource.NOM,
            Datasource.ADREÇA,
            Datasource.CP,
            Datasource.POBLACIO,
            Datasource.TELEFON,
            Datasource.GMAIL,
            Datasource.NUMEROSERIE,
            Datasource.DATA,
            Datasource.TIPO,
            Datasource.ZONA,};

    private static int[] to = new int[]{
            R.id.Maquina_Nom_Client,
            R.id.Maquina_Adreça,
            R.id.Maquina_Codi_Postal,
            R.id.Maquina_Població,
            R.id.Maquina_Telefono,
            R.id.Maquina_Gmail,
            R.id.Maquina_Numero_Serie,
            R.id.Maquina_DATA,
            R.id.Maquina_Tipus_Maquina,
            R.id.Maquina_Zona,};

    Button btn_Ir_Crear_Maquina;


    //Metodo que hace para poder editar y crear depende de lo que nos retorne. el reulstcode y el requestcode.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TASK_ADD) {
            if (resultCode == RESULT_OK) {
                // Carreguem tots els productes
                ActualizarProductesMaquina();
            }

        }
        if (requestCode == TASK_UPDATE) {
            if (resultCode == RESULT_OK) {
                ActualizarProductesMaquina();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Todo:
                FiltreAplicat = Filtro.TOT;
                ActualizarProductesMaquina();
                return true;
            case R.id.OrdenarPorNombre:
                FiltreAplicat = Filtro.NOM;
                ActualizarProductesMaquina();
                return true;
            case R.id.OrdenarPorZona:
                FiltreAplicat = Filtro.ZONA;
                ActualizarProductesMaquina();
                return true;
            case R.id.OrdenarPorPoblacion:
                FiltreAplicat = Filtro.POBLACION;
                ActualizarProductesMaquina();
                return true;
            case R.id.OrdenarPorAdreça:
                FiltreAplicat = Filtro.ADREÇA;
                ActualizarProductesMaquina();
                return true;
            case R.id.OrdenarPorData:
                FiltreAplicat = Filtro.DATA;
                ActualizarProductesMaquina();
                return true;
            case R.id.OrdenarMix:
                FiltreAplicat = Filtro.MIX;
                ActualizarProductesMaquina();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem Buscador = menu.findItem(R.id.Filtrar_Serie);
        SearchView searchView = (SearchView) Buscador.getActionView();
        searchView.setQueryHint("Numero de serie");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String Aux;
                Aux = newText;
                Cursor CursorFilt = bd.FiltrarNumSerie(Aux);

                scTasks = new adapterTodoIcon(getContext(),
                        R.layout.row_maquina,
                        CursorFilt,
                        from,
                        to,
                        1, GestionFragment.this);

                lv.setAdapter(scTasks);


                return false;
            }
        });


    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gestion, container, false);

        FiltreAplicat = Filtro.TOT;

        setHasOptionsMenu(true);


        //Pedir permisos para poder llamar.
        final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CALL_PHONE)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
            }
        }


        btn_Ir_Crear_Maquina = v.findViewById(R.id.btn_Gestion);

        bd = new Datasource(getContext());

        btn_Ir_Crear_Maquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMaquina();

            }
        });
        loadTasks(v);


        return v;
    }


    private void loadTasks(View v) {
        // Demanem totes les tasques
        Cursor cursorTasks = bd.Todo_Maquina();

        // Now create a simple cursor adapter and set it to display
        scTasks = new adapterTodoIcon(getContext(),
                R.layout.row_maquina,
                cursorTasks,
                from,
                to,
                1, GestionFragment.this);

        lv = (ListView) v.findViewById(R.id.list1);
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

    //Añade una nueva Maquina mitjançant un activity
    private void AddMaquina() {
        Bundle bundle = new Bundle();
        idActual = -1;
        bundle.putLong("id", idActual);

        Intent intent = new Intent(getActivity(), CrearEditarMaquina.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, TASK_ADD);

    }

    //Actualiza  el listview de maquina.
    private void ActualizarProductesMaquina() {
        // Demanem totes les tasques
        Cursor cursorTasks = bd.Todo_Maquina();

        switch (FiltreAplicat) {
            case TOT:
                cursorTasks = bd.Todo_Maquina();
                break;
            case NOM:
                cursorTasks = bd.OrdenarNombres();
                break;
            case ZONA:
                cursorTasks = bd.OrdenarZona();
                break;
            case POBLACION:
                cursorTasks = bd.OrdenarPoblacion();
                break;
            case ADREÇA:
                cursorTasks = bd.OrdenarAdreça();
                break;
            case DATA:
                cursorTasks = bd.OrdenarData();
                break;
            case MIX:
                cursorTasks = bd.OrdenarMix();
                break;

        }

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();
    }

    //Elimina una maquina
    private void DeleteMaquina(int Id) {

        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("¿Desitja eliminar la tasca?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.Borrar(Id);
                ActualizarProductesMaquina();

            }
        });

        builder.setNegativeButton("No", null);

        builder.show();


    }

    //Edita una maquina que ja esta creada.
    private void updateTask(long id) {

        Bundle bundle = new Bundle();
        bundle.putLong("id", id);

        idActual = id;


        Intent i = new Intent(getActivity(), CrearEditarMaquina.class);
        i.putExtras(bundle);
        startActivityForResult(i, TASK_UPDATE);

    }

    //Lama al numero del producto
    public void LlamarNumero(long id) {

        // Demanem totes les tasques
        Cursor Cursor_Maquina = bd.EditarMaquinaId(id);

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(Cursor_Maquina);
        scTasks.notifyDataSetChanged();

        String Numero = Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.TELEFON));

        if (!TextUtils.isEmpty(Numero)) {
            String dial = "tel:" + Numero;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        } else {
            Toast.makeText(getContext(), "No hi ha un numero seleccionat", Toast.LENGTH_SHORT).show();
        }
        //final String EtTelefono = Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.TELEFON));

        Cursor Cursor_Maquina2 = bd.Todo_Maquina();

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(Cursor_Maquina2);
        scTasks.notifyDataSetChanged();

    }

    //Envia un gmail al email del producto.
    public void EnviarGmail(long id) {

        Cursor Cursor_Maquina = bd.EditarMaquinaId(id);

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(Cursor_Maquina);
        scTasks.notifyDataSetChanged();

        String Gmail = Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.GMAIL));
        String Text = "Propera revisió maquina nº ";
        String Numero_Serie = Cursor_Maquina.getString(Cursor_Maquina.getColumnIndex(Datasource.NUMEROSERIE));

        // Defino mi Intent y hago uso del objeto ACTION_SEND
        Intent intent = new Intent(Intent.ACTION_SEND);

        // Defino los Strings Email, Asunto y Mensaje con la función putExtra
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{Gmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, Text + Numero_Serie);


        // Establezco el tipo de Intent
        intent.setType("message/rfc822");

        // Lanzo el selector de cliente de Correo
        startActivity(
                Intent
                        .createChooser(intent,
                                "Elije un cliente de Correo:"));


        Cursor Cursor_Maquina2 = bd.Todo_Maquina();

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(Cursor_Maquina2);
        scTasks.notifyDataSetChanged();

    }

    public void IrGmaps(String  Nom) {

        Bundle bundle = new Bundle();
        bundle.putString("id", Nom);

        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_navigation_Gestion_Maquines_to_GoogleFragment, bundle);


    }


    class adapterTodoIcon extends android.widget.SimpleCursorAdapter {

        private static final String colorTaskPending = "#d78290";
        private static final String colorTaskCompleted = "#d7d7d7";

        private GestionFragment oTodoListIcon;

        public adapterTodoIcon(Context context, int layout, Cursor c, String[] from, int[] to, int flags, GestionFragment GestiFra) {
            super(context, layout, c, from, to, flags);
            oTodoListIcon = GestiFra;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);

            // Agafem l'objecte de la view que es una LINEA DEL CURSOR
            Cursor linia = (Cursor) getItem(position);


            //Imagen para eliminar el stock, se redirige al metodo llamado deletemaquina, pasandole el id del cual el usuario ha pulsado.
            ImageView BtnEliminar = (ImageView) view.findViewById(R.id.Basura_Maquina);
            BtnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Carrego la linia del cursor de la posició.
                    Cursor linia = (Cursor) getItem(position);

                    oTodoListIcon.DeleteMaquina(linia.getInt(linia.getColumnIndexOrThrow(Datasource.IDGENERAL)));
                }
            });


            //Imagen para filtrar por fechas, se redirige al metodo alertidalog3, pasandole el id del que el usuario ha pulsado.
            ImageView btnGmaps = (ImageView) view.findViewById(R.id.IrGmaps);
            btnGmaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Carrego la linia del cursor de la posició.
                    Cursor linia = (Cursor) getItem(position);

                    oTodoListIcon.IrGmaps(linia.getString(linia.getColumnIndexOrThrow(Datasource.NOMZONA)));
                }
            });


            //Imagen para llamar por eltelefono del producto
            ImageView btnTelef = (ImageView) view.findViewById(R.id.Llamar_Maquina);
            btnTelef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Carrego la linia del cursor de la posició.
                    Cursor linia = (Cursor) getItem(position);


                    oTodoListIcon.LlamarNumero(linia.getInt(linia.getColumnIndexOrThrow(Datasource.IDGENERAL)));
                }
            });


            //Imagen para enviar un gmail.
            ImageView btnGmail = (ImageView) view.findViewById(R.id.Gmail_Maquina);
            btnGmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Carrego la linia del cursor de la posició.
                    Cursor linia = (Cursor) getItem(position);


                    oTodoListIcon.EnviarGmail(linia.getInt(linia.getColumnIndexOrThrow(Datasource.IDGENERAL)));
                }
            });


            return view;
        }
    }
}

