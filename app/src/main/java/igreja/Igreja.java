package igreja;

public final class Igreja {
    private String nome = "";
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String estado = "";
    private String pais_ = "";
    private String cep = "";

    public Igreja() {
    }

    public Igreja(String nome, String endereco, String bairro, String cidade, String estado, String pais_, String cep) {
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais_ = pais_;
        this.cep = cep;
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

}
