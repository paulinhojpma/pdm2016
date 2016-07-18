package com.example.paulinho.listadecompras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Paulinho on 10/07/2016.
 */
public class Lista {
    private String nome;
    private String periodo;
    private Double total=0.0;

    private boolean aberta;

    private List<Produto> produtos;


    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void calcularTotal(Produto p){
        total+=p.getPreco()*p.getQuantidade();

    }

    public void diminuirTotal(Produto p,int qt){
        total-=p.getPreco()*qt;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAberta() {
        return aberta;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
    public void adcionarProdutos(Produto p){
        this.produtos.add(p);
    }

    public void removerProduto(Produto p){
        this.produtos.remove(p);
    }
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public boolean getAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }



    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String toString(){
        String txt="";
        txt+="Lista: "+nome;
        txt+="\nNome: "+ periodo;
        txt+="\nTotal: "+ total;
        return txt;
    }



    public Lista(){
        this.produtos=new ArrayList<Produto>();
    }

}
