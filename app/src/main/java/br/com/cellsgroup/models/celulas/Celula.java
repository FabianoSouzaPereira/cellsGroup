package br.com.cellsgroup.models.celulas;

public final class Celula {
    private String uid;
    private String celula = "";
    private String rede = "";
    private String supervisor = "";
    private String lider = "";
    private String viceLider = "";
    private String anfitriao = "";
    private String secretario = "";
    private String colaborador = "";
    private String dia = "";
    private String hora = "";
    private String datainicio = "";
    private String datahora = "";
    private String status = "1";
    private String userId = "";
    private String igreja = "";


    public Celula() { }

    public Celula(String uid, String celula, String rede, String supervisor, String lider, String viceLider, String anfitriao, String secretario, String colaborador, String dia, String hora, String datainicio, String datahora, String status, String userId, String igreja) {
        this.uid = uid;
        this.celula = celula;
        this.rede = rede;
        this.supervisor = supervisor;
        this.lider = lider;
        this.viceLider = viceLider;
        this.anfitriao = anfitriao;
        this.secretario = secretario;
        this.colaborador = colaborador;
        this.dia = dia;
        this.hora = hora;
        this.datainicio = datainicio;
        this.datahora = datahora;
        this.status = status;
        this.userId = userId;
        this.igreja = igreja;
    }

    public String getUid() {
        return uid;
    }
   
   public String getCelula() {
        return celula;
    }
   
   public String getRede() {
        return rede;
    }
   
   public String getSupervisor() {
        return supervisor;
    }
   
   public String getLider() {
        return lider;
    }
   
   public String getViceLider() {
        return viceLider;
    }
   
   public String getAnfitriao() {
        return anfitriao;
    }
   
   public String getSecretario() {
        return secretario;
    }
   
   public String getColaborador() {
        return colaborador;
    }
   
   public String getDia() {
        return dia;
    }
   
   public String getHora() {
        return hora;
    }
   
   public String getDatainicio() {
        return datainicio;
    }
   
   public String getStatus() {
        return status;
    }
   
   public String getUserId() {
        return userId;
    }

    /*    @Override
    public String toString() {  return this.celula;  }*/
}
