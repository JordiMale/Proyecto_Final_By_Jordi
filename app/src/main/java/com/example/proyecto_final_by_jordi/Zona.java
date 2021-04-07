package com.example.proyecto_final_by_jordi;

public class Zona {

    private int Id;
    private String Nom_Zona;

    public Zona(int id, String nom_zona){
        this.Id = id;
        this.Nom_Zona = nom_zona;
    }



    public void setId(int id) {
        Id = id;
    }
    public int getId() {
        return Id;
    }

    public void setNom_Zona(String nom_zona) {
        Nom_Zona = nom_zona;
    }


    public String getNom_Zona() {
        return Nom_Zona;
    }
}
