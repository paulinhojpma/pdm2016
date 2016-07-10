package com.example.paulinho.listadecompras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Paulinho on 10/07/2016.
 */
public class Lista {

    private String periodo;
    private Double total;
    private List<Produto> produtos=new ArrayList<Produto>();
    private Map<String, Double> totalCategoria=new HashMap<String, Double>();

    public Lista(String periodo) {
        this.periodo = periodo;
    }

    public Lista(String periodo, List<Produto> produtos) {
        this.periodo = periodo;
        this.produtos = produtos;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Map<String, Double> getTotalCategoria() {
        return totalCategoria;
    }

    public void setTotalCategoria(Map<String, Double> totalCategoria) {
        this.totalCategoria = totalCategoria;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void calculaTotal(){
        double soma=0;
        if(produtos.size()>0){
            for (Produto p:produtos ) {
                soma+=p.getPreco();
            }

        }
       total=soma;


    }

    public void calcularTotalCategoria(){
        for (Produto p: produtos) {
            totalCategoria.put(p.getCategoria().getCategoria(), totalCategoria.get(p.getCategoria().getCategoria())+p.getPreco());

        }


    }

    private void carregaCategorias(){

        totalCategoria.put(Categoria.GRAOS.getCategoria(), 0.0);
        totalCategoria.put(Categoria.FRIOS.getCategoria(), 0.0);
        totalCategoria.put(Categoria.BEBIDAS.getCategoria(), 0.0);
        totalCategoria.put(Categoria.FRUTAS.getCategoria(), 0.0);
        totalCategoria.put(Categoria.CARNES.getCategoria(), 0.0);
        totalCategoria.put(Categoria.CONDIMENTOS.getCategoria(), 0.0);
        totalCategoria.put(Categoria.HIGIENE.getCategoria(), 0.0);
        totalCategoria.put(Categoria.LIMPEZA.getCategoria(), 0.0);
        totalCategoria.put(Categoria.OUTROS.getCategoria(), 0.0);

    }
}
