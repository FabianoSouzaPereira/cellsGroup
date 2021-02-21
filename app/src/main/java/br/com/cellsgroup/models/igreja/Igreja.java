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
    private String phone_fixo = "";
    private String datahora = "";
    private String user;
    private String status = "1";
    private String group="";
    private String igrejaID = "";
    private String members = "";


    public Igreja ( ) {
    }

    public Igreja ( String uid , String nome , String endereco , String bairro , String cidade , String estado , String pais_ , String cep , String ddi , String phone , String phone_fixo, String datahora , String user , String status , String group , String igrejaID , String members ) {
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
        this.phone_fixo = phone_fixo;
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
   
   public String getNome ( ) {
        return nome;
    }
   
   public String getEndereco ( ) {
        return endereco;
    }
   
   public String getBairro ( ) {
        return bairro;
    }
   
   public String getCidade ( ) {
        return cidade;
    }
   
   public String getEstado ( ) {
        return estado;
    }
   
   public String getPais_ ( ) {
        return pais_;
    }
   
   public String getCep ( ) {
        return cep;
    }
   
   public String getDdi ( ) {
        return ddi;
    }
   
   public String getPhone ( ) {
        return phone;
    }
   
   public String getPhone_fixo ( ) {
        return phone_fixo;
    }
   
   public String getUser ( ) {
        return user;
    }
   
   public String getGroup ( ) {
        return group;
    }
   
   public String getIgrejaID ( ) {
        return igrejaID;
    }
   
   public String getMembers ( ) {
        return members;
    }
   
}
