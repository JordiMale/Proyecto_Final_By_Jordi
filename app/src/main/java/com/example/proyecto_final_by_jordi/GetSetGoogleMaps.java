package com.example.proyecto_final_by_jordi;

public class GetSetGoogleMaps {


    private String Poblacio;
    private String Nom_Tipo;
    private String Num_Serie;
    private String Color;

    //Construcor
    public GetSetGoogleMaps(String poblacio, String nom_Tipo, String num_Serie, String color) {
        Poblacio = poblacio;
        Nom_Tipo = nom_Tipo;
        Num_Serie = num_Serie;
        Color = color;
    }

    //Getters i setters para hacer el mapa de zona
    public String getPoblacio() {
        return Poblacio;
    }

    public void setPoblacio(String poblacio) {
        Poblacio = poblacio;
    }

    public String getNom_Tipo() {
        return Nom_Tipo;
    }

    public void setNom_Tipo(String nom_Tipo) {
        Nom_Tipo = nom_Tipo;
    }

    public String getNum_Serie() {
        return Num_Serie;
    }

    public void setNum_Serie(String num_Serie) {
        Num_Serie = num_Serie;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
