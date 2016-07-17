package com.example.paulinho.listadecompras;

import android.graphics.Bitmap;

/**
 * Created by Paulinho on 10/07/2016.
 */
public class Produto {


    private String id;
    private String nome;
    private int quantidade;
    private String categoria;

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    private boolean comprado;

    public Produto(){

    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private double preco;

    public String toString(){
        String txt="";
        txt="Produto: "+ this.nome;
        txt+="\n Preço: "+this.preco+ " quantidade: "+this.quantidade;
        return txt;
    }




}
