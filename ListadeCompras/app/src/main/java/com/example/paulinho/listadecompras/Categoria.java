package com.example.paulinho.listadecompras;

/**
 * Created by Paulinho on 10/07/2016.
 */
public enum Categoria {
    GRAOS("Grãos"),
    FRIOS("Frios"),
    BEBIDAS("Bebidas"),
    CARNES("Carnes e Aves"),
    CONDIMENTOS("Temperos"),
    FRUTAS("Frutas e Verduras"),
    HIGIENE("Higiene Pessoas"),
    LIMPEZA("Limpeza"),
    OUTROS("Outros");

    public final String CATEGORIA;

    private Categoria(String categoria){
        this.CATEGORIA=categoria;
    }

    public String getCategoria(){
        return CATEGORIA;
    }
}
