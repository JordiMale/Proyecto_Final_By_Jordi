package com.example.proyecto_final_by_jordi.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_final_by_jordi.Zona;

public class Datasource {

    public static final String IDGENERAL = "_id";

    //Taula de Maquines
    public static final String TABLE_MAQUINES = "Maquines";
    public static final String NOM = "Nom_Client";
    public static final String ADREÇA = "Adreça";
    public static final String CP = "CP";
    public static final String POBLACIO = "Poblacio";
    public static final String TELEFON = "Telefon";
    public static final String GMAIL = "Gmail";
    public static final String NUMEROSERIE = "NumeSerie";
    public static final String DATA = "Data";
    public static final String TIPO = "Tipo";
    public static final String ZONA = "Zones";

    //Taula de zones
    public static final String TABLE_ZONA = "ZonesM";
    public static final String NOMZONA = "NomZona";

    //Taula de tipo de maquines
    public static final String TABLE_TIPO_MAQUINAS = "Tipo_Maquines";
    public static final String NOMMAQUINA = "NomMaquina";
    public static final String COLOR_TIPUS = "Color";


    private Helper dbHelper;
    private SQLiteDatabase dbW, dbR;


    public Datasource(Context ctx) {
        // En el constructor directament obro la comunicació amb la base de dades
        dbHelper = new Helper(ctx);
        // amés també construeixo dos databases un per llegir i l'altre per alterar
        open();
    }

    // DESTRUCTOR
    protected void finalize() {
        // Cerramos los databases
        dbW.close();
        dbR.close();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }


    //\\//\\//\\//\\//\\//\\Maquina//\\//\\//\\//\\//\\//\\
    //Mostrar Maquina

    /*
    public Cursor Todo_Maquina() {
        // Retorem totes les tasques

        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                null, null,
                null, null, IDGENERAL);
    }

     */

    public Cursor Todo_Maquina() {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id ORDER BY Maqui.Nom_Client";

        return dbR.rawQuery(MY_QUERY, null);

    }

    //Creem Maquina
    public long Crear(String Nom_Maquina, String Adreça_Maquina, int CPo, String Poblacion, String Telefono, String Gmail, String Numero_Serie, String Data, int Tipo, int Zona) {
        ContentValues values = new ContentValues();
        values.put(NOM, Nom_Maquina);
        values.put(ADREÇA, Adreça_Maquina);
        values.put(CP, CPo);
        values.put(POBLACIO, Poblacion);
        values.put(TELEFON, Telefono);
        values.put(GMAIL, Gmail);
        values.put(NUMEROSERIE, Numero_Serie);
        values.put(DATA, Data);
        values.put(TIPO, Tipo);
        values.put(ZONA, Zona);
        return dbW.insert(TABLE_MAQUINES, null, values);
    }

    //Mirar si hay algun numero de serie para poder crear.
    public boolean PoderCrear(String Num_Serie) {
        boolean BooleanCrear;
        Cursor MirarNum = dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                NUMEROSERIE + "=?", new String[]{String.valueOf(Num_Serie)},
                null, null, null);
        if (!MirarNum.moveToFirst()) {
            BooleanCrear = true;
        } else {
            BooleanCrear = false;
        }
        return BooleanCrear;
    }

    //Este metodo sirve tanto para editar la maquina como para hacer lo de llamar y enviar el correo.
    public Cursor EditarMaquinaId(long id) {
        // Retorna un cursor només amb el id indicat
        // Retornem les tasques que el camp DONE = 1
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                IDGENERAL + "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }

    //Aqui actualizamos los datos.
    public boolean Update(long id, String Nom_Maquina, String Adreça_Maquina, int CPo, String Poblacion, String Telefono, String Gmail, String Numero_Serie, String Data, int Tipo, int Zona) {
        ContentValues values = new ContentValues();
        boolean Return = false;
        values.put(NOM, Nom_Maquina);
        values.put(ADREÇA, Adreça_Maquina);
        values.put(CP, CPo);
        values.put(POBLACIO, Poblacion);
        values.put(TELEFON, Telefono);
        values.put(GMAIL, Gmail);
        values.put(NUMEROSERIE, Numero_Serie);
        values.put(DATA, Data);
        values.put(TIPO, Tipo);
        values.put(ZONA, Zona);
        try {
            dbW.update(TABLE_MAQUINES, values, IDGENERAL + "=?", new String[]{String.valueOf(id)});

        } catch (SQLiteConstraintException e) {
            Return = true;
        }
        return Return;
    }

    //Eliminar Maquina
    public void Borrar(long id) {
        // Eliminem la task amb clau primària "id"
        dbW.delete(TABLE_MAQUINES, IDGENERAL + " = ?", new String[]{String.valueOf(id)});
    }

    /*
    public Cursor OrdenarNombres() {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                null, null,
                null, null, NOM);
    }

     */
    //Ordenar maquina por nombres.
    public Cursor OrdenarNombres() {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id ORDER BY Maqui.Nom_Client";

        return dbR.rawQuery(MY_QUERY, null);

    }

    /*
    public Cursor OrdenarMix() {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                null, null,
                null, null, ZONA + "," + POBLACIO + "," + ADREÇA);
    }

     */

    //Ordenar maquina por nombres.
    public Cursor OrdenarMix() {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id ORDER BY ZoM.NomZona, Maqui.Poblacio, Maquin.Adreça";

        return dbR.rawQuery(MY_QUERY, null);
    }


    /*
    public Cursor OrdenarZona() {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                null, null,
                null, null, ZONA);
    }

     */

    //Ordenar maquinas por Zona.
    public Cursor OrdenarZona() {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id ORDER BY ZoM.NomZona";

        return dbR.rawQuery(MY_QUERY, null);

    }

    /*
    public Cursor OrdenarPoblacion() {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                null, null,
                null, null, POBLACIO);
    }

     */

    //Ordenar maquinas por Poblacion.
    public Cursor OrdenarPoblacion() {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id ORDER BY Maqui.Poblacio";

        return dbR.rawQuery(MY_QUERY, null);

    }

    /*
    public Cursor OrdenarAdreça() {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                null, null,
                null, null, ADREÇA);
    }

     */


    //Ordenar maquina por Direccion(Adreça).
    public Cursor OrdenarAdreça() {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id ORDER BY Maqui.Adreça";

        return dbR.rawQuery(MY_QUERY, null);

    }

    /*
    public Cursor OrdenarData() {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                null, null,
                null, null, DATA);
    }

     */

    //Ordenar maquina por Data.
    public Cursor OrdenarData() {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id ORDER BY Maqui.Data";

        return dbR.rawQuery(MY_QUERY, null);

    }


    /*
    //Filtrar por numero de serie
    public Cursor FiltrarNumSerie(String nums) {
        final String Filtrar = "SELECT * FROM Maquines WHERE NumeSerie LIKE '%" + nums + "%'";
        return dbR.rawQuery(Filtrar, null);

    }

     */

    public Cursor FiltrarNumSerie(String nums) {
        final String MY_QUERY = "SELECT Maqui._id,Nom_Client,Adreça,CP,Poblacio,Telefon,Gmail,NumeSerie,Data,TiM.NomMaquina,ZoM.NomZona " +
                "FROM Maquines AS Maqui INNER JOIN ZonesM AS ZoM ON Maqui.Zones = ZoM._id INNER JOIN Tipo_Maquines AS TiM ON Maqui.Tipo = TiM._id WHERE NumeSerie LIKE '%" + nums + "%'" + "  ORDER BY Maqui.Nom_Client";

        return dbR.rawQuery(MY_QUERY, null);

    }


    /*

    //Mirar si ya hay algun numero de serie creado.
    public Cursor MirarNumSerie(String nums) {
        final String Filtrar = "SELECT * FROM Maquines WHERE NumeSerie LIKE '%" + nums + "%'";
        return dbR.rawQuery(Filtrar, null);

    }

     */

    /*
    //Poder editar una maquina y que no se repita el numero de serie
    public boolean PoderActualizar(long id, String Num_Serie){
        boolean Return = false;
        ContentValues Val = new ContentValues();
        Val.put(NUMEROSERIE, Num_Serie);
        try{
            dbW.update(TABLE_MAQUINES, Val, IDGENERAL + "=?", new String[]{ String.valueOf(id) });

        }catch (SQLiteConstraintException e){
            Return = true;
        }
        return Return;
    }

     */


    //\\//\\//\\//\\//\\//\\Zona//\\//\\//\\//\\//\\//\\
    public Cursor Todo_Zona() {
        // Retorem totes les tasques
        return dbR.query(TABLE_ZONA, new String[]{IDGENERAL, NOMZONA},
                null, null,
                null, null, IDGENERAL);
    }

    //Creem Zona
    public long Crear_Zona(String Nom_Zona) {
        ContentValues values = new ContentValues();
        values.put(NOMZONA, Nom_Zona);
        return dbW.insert(TABLE_ZONA, null, values);
    }

    //Mirar para poder crear una zona mirando que no se repita la zona
    public boolean PoderCrearZona(String Nom_Zona) {
        boolean BooleanCrearZona;
        Cursor MirarZona = dbR.query(TABLE_ZONA, new String[]{IDGENERAL, NOMZONA},
                NOMZONA + "=?", new String[]{String.valueOf(Nom_Zona)},
                null, null, null);
        if (!MirarZona.moveToFirst()) {
            BooleanCrearZona = true;
        } else {
            BooleanCrearZona = false;
        }
        return BooleanCrearZona;
    }

    //Aqui actualizamos los datos.
    public void Update_Zona(long id, String Nom_Zona) {
        ContentValues values = new ContentValues();
        values.put(NOMZONA, Nom_Zona);
        dbW.update(TABLE_ZONA, values, IDGENERAL + " = ?", new String[]{String.valueOf(id)});
    }

    //Este metodo sirve para editar la zona, para cojer el id y poder editar el que ha escojido.
    public Cursor EditarZonaId(long id) {
        // Retorna un cursor només amb el id indicat
        // Retornem les tasques que el camp DONE = 1
        return dbR.query(TABLE_ZONA, new String[]{IDGENERAL, NOMZONA},
                IDGENERAL + "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }

    //Cursor para mirar si hay alguna zona assignada a una maquina.
    public Cursor PoderEliminarZona(long id) {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                ZONA + "=?", new String[]{String.valueOf(id)}, null,
                null, null, null);
    }

    //Borrar la zona que ha seleccionat el usuari.
    public void BorrarZona(long id) {
        // Eliminem la task amb clau primària "id"
        dbW.delete(TABLE_ZONA, IDGENERAL + " = ?", new String[]{String.valueOf(id)});
    }


    //\\//\\//\\//\\//\\//\\Tipo//\\//\\//\\//\\//\\//\\
    public Cursor Todo_Tipo() {
        // Retorem totes les tasques
        return dbR.query(TABLE_TIPO_MAQUINAS, new String[]{IDGENERAL, NOMMAQUINA, COLOR_TIPUS},
                null, null,
                null, null, IDGENERAL);
    }

    //Creem Tipo
    public long Crear_Tipo(String Nom_Tipo, String Color_Tipo) {
        ContentValues values = new ContentValues();
        values.put(NOMMAQUINA, Nom_Tipo);
        values.put(COLOR_TIPUS, Color_Tipo);
        return dbW.insert(TABLE_TIPO_MAQUINAS, null, values);
    }


    //Mirar para poder crear un tipo mirando que no se repita el tipo
    public boolean PoderCrearTipo(String Nom_Tipo) {
        boolean BooleanCrearTipo;
        Cursor MirarTipo = dbR.query(TABLE_TIPO_MAQUINAS, new String[]{IDGENERAL, NOMMAQUINA},
                NOMMAQUINA + "=?", new String[]{String.valueOf(Nom_Tipo)},
                null, null, null);
        if (!MirarTipo.moveToFirst()) {
            BooleanCrearTipo = true;
        } else {
            BooleanCrearTipo = false;
        }
        return BooleanCrearTipo;
    }

    //Aqui actualizamos los datos.
    public void Update_Tipo(long id, String Nom_Tipo, String Color_Tipo) {
        ContentValues values = new ContentValues();
        values.put(NOMMAQUINA, Nom_Tipo);
        values.put(COLOR_TIPUS, Color_Tipo);
        dbW.update(TABLE_TIPO_MAQUINAS, values, IDGENERAL + " = ?", new String[]{String.valueOf(id)});
    }

    //Este metodo sirve para editar la zona, para cojer el id y poder editar el que ha escojido.
    public Cursor EditarTipoId(long id) {
        // Retorna un cursor només amb el id indicat
        // Retornem les tasques que el camp DONE = 1
        return dbR.query(TABLE_TIPO_MAQUINAS, new String[]{IDGENERAL, NOMMAQUINA, COLOR_TIPUS},
                IDGENERAL + "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }

    //Cursor para mirar si hay algun tipo assignada a una maquina.
    public Cursor PoderEliminarTipo(long id) {
        // Retorem totes les tasques
        return dbR.query(TABLE_MAQUINES, new String[]{IDGENERAL, NOM, ADREÇA, CP, POBLACIO, TELEFON, GMAIL, NUMEROSERIE, DATA, TIPO, ZONA},
                TIPO + "=?", new String[]{String.valueOf(id)}, null,
                null, null, null);
    }

    //Borrar el tipo que ha seleccionat el usuari.
    public void BorrarTipo(long id) {
        // Eliminem la task amb clau primària "id"
        dbW.delete(TABLE_TIPO_MAQUINAS, IDGENERAL + " = ?", new String[]{String.valueOf(id)});
    }

}
