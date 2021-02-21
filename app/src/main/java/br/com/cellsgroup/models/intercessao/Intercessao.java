package br.com.cellsgroup.models.intercessao;

public final class Intercessao {
    private String uid = "";
    private String nome = "";
    private String motivo = "";
    private String data = "";
   
   public Intercessao() {
    }

    public Intercessao(String uid, String nome, String motivo, String data) {
        this.uid = uid;
        this.nome = nome;
        this.motivo = motivo;
        this.data = data;
    }

    public String getUid() {
        return uid;
    }
   
   public String getNome() {
        return nome;
    }
   
   public String getMotivo() {
        return motivo;
    }
   
   public String getData() {
        return data;
    }
   
}
