package br.com.cellsgroup.models.pessoas;

public class Pessoa {
    private String uid = "";
    private String nome = "";
    private String idade = "";
    private String sexo = "";
    private String dataNascimento = "";
    private String dataBastismo = "";
    private String nomepai = "";
    private String nomemae = "";
    private String estadocivil = "";
    private String ddi = "55";
    private String telefone = "";
    private String email = "";
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String estado = "";
    private String pais = "";
    private String cep = "";
    private String cargoIgreja = "";
    private String status = "1";
    private String datahora = "";
    private String igrejaPadrao = "";
    private String userId = "";
    private Object group;
    private String celula = "";

    public Pessoa( ) { }

    public Pessoa ( String uid , String nome , String idade , String sexo , String dataNascimento , String dataBastismo , String nomepai , String nomemae , String estadocivil , String ddi , String telefone , String email , String endereco , String bairro , String cidade ,String estado, String pais , String cep , String cargoIgreja , String status , String datahora , String igrejaPadrao , String userId , Object group, String celula ) {
        this.uid = uid;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.dataBastismo = dataBastismo;
        this.nomepai = nomepai;
        this.nomemae = nomemae;
        this.estadocivil = estadocivil;
        this.ddi = ddi;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
        this.cargoIgreja = cargoIgreja;
        this.status = status;
        this.datahora = datahora;
        this.igrejaPadrao = igrejaPadrao;
        this.userId = userId;
        this.group = group;
        this.celula = celula;
    }


    public String getUid ( ) {
        return uid;
    }
   
   public String getNome ( ) {
        return nome;
    }

    public void setNome ( String nome ) {
        this.nome = nome;
    }

    public String getIdade ( ) {
        return idade;
    }
   
   public String getSexo ( ) {
        return sexo;
    }
   
   public String getDataNascimento ( ) {
        return dataNascimento;
    }
   
   public String getDataBastismo ( ) {
        return dataBastismo;
    }
   
   public String getNomepai ( ) {
        return nomepai;
    }
   
   public String getNomemae ( ) {
        return nomemae;
    }
   
   public String getEstadocivil ( ) {
        return estadocivil;
    }
   
   public String getDdi ( ) {
        return ddi;
    }
   
   public String getTelefone ( ) {
        return telefone;
    }
   
   public String getEmail ( ) {
        return email;
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
   
   public String getPais ( ) {
        return pais;
    }
   
   public String getCep ( ) {
        return cep;
    }
   
   public String getCargoIgreja ( ) {
        return cargoIgreja;
    }
   
   public String getUserId ( ) {
        return userId;
    }
   
   public String getCelula ( ) {
        return celula;
    }
   
}
