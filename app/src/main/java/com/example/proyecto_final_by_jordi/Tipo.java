package com.example.proyecto_final_by_jordi;

import android.widget.SearchView;

public class Tipo {

    private int Id;
    private String Nom_Tipo;

    public Tipo(int id, String nom_tipo){
        this.Id = id;
        this.Nom_Tipo = nom_tipo;
    }


    public void setId(int id) {
        Id = id;
    }
    public int getId() {
        return Id;
    }

    public void setNom_Tipo(String nom_Tipo) {
        Nom_Tipo = nom_Tipo;
    }


    public String getNom_Tipo() {
        return Nom_Tipo;
    }


}
