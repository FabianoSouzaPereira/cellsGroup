package br.com.cellsgroup.models.celulas;

public final class Celula {
    private String uid;
    private String celula;
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

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCelula() {
        return celula;
    }

    public void setCelula(String celula) {
        this.celula = celula;
    }

    public String getRede() {
        return rede;
    }

    public void setRede(String rede) {
        this.rede = rede;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public String getViceLider() {
        return viceLider;
    }

    public void setViceLider(String viceLider) {
        this.viceLider = viceLider;
    }

    public String getAnfitriao() {
        return anfitriao;
    }

    public void setAnfitriao(String anfitriao) {
        this.anfitriao = anfitriao;
    }

    public String getSecretario() {
        return secretario;
    }

    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIgreja() {
        return igreja;
    }

    public void setIgreja(String igreja) {
        this.igreja = igreja;
    }

    /*    @Override
    public String toString() {  return this.celula;  }*/
}
