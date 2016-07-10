package com.example.paulinho.listadecompras;

import android.graphics.Bitmap;

/**
 * Created by Paulinho on 10/07/2016.
 */
public class Produto {


    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private String marca;
    private Bitmap foto;



    private Categoria categoria;

    public Produto(int id, String nome, double preco, String marca, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.marca = marca;
        this.categoria=categoria;
    }

    public Produto(int id, String nome, String descricao, double preco, String marca, Bitmap foto, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.marca = marca;
        this.foto = foto;
        this.categoria=categoria;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
