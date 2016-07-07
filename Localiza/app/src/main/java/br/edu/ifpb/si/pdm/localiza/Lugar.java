package br.edu.ifpb.si.pdm.localiza;

import android.graphics.Bitmap;

/**
 * Created by admin on 14/06/16.
 */
public class Lugar {
    private String nome;
    private String descricao;
    private Bitmap foto;
    private  String local;

    public Lugar(String nome, String descricao, Bitmap foto, String local){
        this.nome=nome;
        this.descricao=descricao;
        this.foto=foto;
        this.local=local;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public String toString(){
        return "Nome: "+this.nome+"\n Descrição: "+this.descricao+"\nLocal: "+ this.local;
    }

    public Bitmap getFoto() {
        return this.foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }


}
