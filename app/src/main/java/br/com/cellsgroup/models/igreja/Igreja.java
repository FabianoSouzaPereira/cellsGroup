package br.com.cellsgroup.models.igreja;


public final class Igreja {
    private String uid = "";
    private String nome = "";
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String estado = "";
    private String pais_ = "";
    private String cep = "";
    private String ddi = "";
    private String phone = "";
    private String datahora = "";
    private String user;
    private String status = "1";
    private String group="";
    private String igrejaID = "";
    private String members = "";


    public Igreja ( ) {
    }

    public Igreja ( String uid , String nome , String endereco , String bairro , String cidade , String estado , String pais_ , String cep , String ddi , String phone , String datahora , String user , String status , String group , String igrejaID , String members ) {
        this.uid = uid;
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais_ = pais_;
        this.cep = cep;
        this.ddi = ddi;
        this.phone = phone;
        this.datahora = datahora;
        this.user = user;
        this.status = status;
        this.group = group;
        this.igrejaID = igrejaID;
        this.members = members;
    }

    public String getUid ( ) {
        return uid;
    }

    public void setUid ( String uid ) {
        this.uid = uid;
    }

    public String getNome ( ) {
        return nome;
    }

    public void setNome ( String nome ) {
        this.nome = nome;
    }

    public String getEndereco ( ) {
        return endereco;
    }

    public void setEndereco ( String endereco ) {
        this.endereco = endereco;
    }

    public String getBairro ( ) {
        return bairro;
    }

    public void setBairro ( String bairro ) {
        this.bairro = bairro;
    }

    public String getCidade ( ) {
        return cidade;
    }

    public void setCidade ( String cidade ) {
        this.cidade = cidade;
    }

    public String getEstado ( ) {
        return estado;
    }

    public void setEstado ( String estado ) {
        this.estado = estado;
    }

    public String getPais_ ( ) {
        return pais_;
    }

    public void setPais_ ( String pais_ ) {
        this.pais_ = pais_;
    }

    public String getCep ( ) {
        return cep;
    }

    public void setCep ( String cep ) {
        this.cep = cep;
    }

    public String getDdi ( ) {
        return ddi;
    }

    public void setDdi ( String ddi ) {
        this.ddi = ddi;
    }

    public String getPhone ( ) {
        return phone;
    }

    public void setPhone ( String phone ) {
        this.phone = phone;
    }

    public String getDatahora ( ) {
        return datahora;
    }

    public void setDatahora ( String datahora ) {
        this.datahora = datahora;
    }

    public String getUser ( ) {
        return user;
    }

    public void setUser ( String user ) {
        this.user = user;
    }

    public String getStatus ( ) {
        return status;
    }

    public void setStatus ( String status ) {
        this.status = status;
    }

    public String getGroup ( ) {
        return group;
    }

    public void setGroup ( String group ) {
        this.group = group;
    }

    public String getIgrejaID ( ) {
        return igrejaID;
    }

    public void setIgrejaID ( String igrejaID ) {
        this.igrejaID = igrejaID;
    }

    public String getMembers ( ) {
        return members;
    }

    public void setMembers ( String members ) {
        this.members = members;
    }

}
