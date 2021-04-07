package com.example.proyecto_final_by_jordi.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {


        // database version
        private static final int database_VERSION = 2;

        // database name
        private static final String database_NAME = "DataBaseBudiem";

        public Helper(Context context) {
            super(context, database_NAME, null, database_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_MAQUINES =
                    "CREATE TABLE Maquines ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "Nom_Client TEXT NOT NULL," +
                            "Adreça TEXT NOT NULL," +
                            "CP INTEGER NOT NULL," +
                            "Poblacio TEXT NOT NULL," +
                            "Telefon TEXT," +
                            "Gmail TEXT," +
                            "NumeSerie TEXT UNIQUE NOT NULL," +
                            "Data TEXT," +
                            "Tipo INTEGER NOT NULL," +
                            "Zones INTEGER NOT NULL," +
                            "FOREIGN KEY(Tipo) REFERENCES Tipo_Maquines(_id),"+
                            "FOREIGN KEY(Zones) REFERENCES ZonesM(_id)"+ ")";

            String CREATE_ZONES =
                    "CREATE TABLE ZonesM (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NomZona TEXT UNIQUE NOT NULL)";

            String CREATE_TIPO =
                    "CREATE TABLE Tipo_Maquines (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NomMaquina TEXT UNIQUE NOT NULL," +
                    "Color TEXT )";

            db.execSQL(CREATE_MAQUINES);
            db.execSQL(CREATE_ZONES);
            db.execSQL(CREATE_TIPO);

        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
