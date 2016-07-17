package com.example.paulinho.listadecompras;

/**
 * Created by Paulinho on 10/07/2016.
 */
public class Supermercado {


    private int id;
    private String nome;
    private String longitude;
    private String latitude;


    public Supermercado(String nome) {
        this.nome = nome;
    }

    public Supermercado(int id,String nome, String longitude, String latitude) {
        this.id=id;
        this.nome = nome;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }



}
