package igreja;

public final class Igreja {
    private String uid = "";
    private String nome = "";
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String estado = "";
    private String pais_ = "";
    private String cep = "";
    private String datahora = "";
    private String user;
    private String status = "1";

    public Igreja() {
    }

    public Igreja(String uid, String nome, String endereco, String bairro, String cidade, String estado, String pais_, String cep, String datahora, String user, String status) {
        this.uid = uid;
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais_ = pais_;
        this.cep = cep;
        this.datahora = datahora;
        this.user = user;
        this.status = status;
    }

    public String getNome() {   return nome;   }

    public void setNome(String nome) {  this.nome = nome;    }

    public String getEndereco() {    return endereco;   }

    public void setEndereco(String endereco) {   this.endereco = endereco;    }

    public String getBairro() {   return bairro;   }

    public void setBairro(String bairro) {     this.bairro = bairro;    }

    public String getCidade() {     return cidade;    }

    public void setCidade(String cidade) {      this.cidade = cidade;    }

    public String getEstado() {      return estado;    }

    public void setEstado(String estado) {       this.estado = estado;    }

    public String getPais_() {       return pais_;    }

    public void setPais_(String pais_) {       this.pais_ = pais_;    }

    public String getCep() {       return cep;    }

    public void setCep(String cep) {       this.cep = cep;    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
