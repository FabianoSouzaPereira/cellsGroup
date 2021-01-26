package br.com.cellsgroup.intercessao;

public final class Intercessao {
    private String uid = "";
    private String nome = "";
    private String motivo = "";
    private String data = "";
    private int iconeRid = -1;

    public Intercessao() {
    }

    public Intercessao(String uid, String nome, String motivo, String data) {
        this.uid = uid;
        this.nome = nome;
        this.motivo = motivo;
        this.data = data;
        this.iconeRid = iconeRid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
