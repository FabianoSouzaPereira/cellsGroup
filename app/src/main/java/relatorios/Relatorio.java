package relatorios;

public class Relatorio {
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
    private String baseCelula = "";
    private String membrosIEQ = "";
    private String convidados = "";
    private String criancas = "";
    private String total = "";
    private String estudo = "";
    private String quebragelo = "";
    private String lanche = "";
    private String aceitacao = "";
    private String reconciliacao = "";
    private String testemunho = "";
    private String datahora = "";
    private String status = "1";

    public Relatorio() {
    }

    public Relatorio(String uid, String celula, String rede, String supervisor, String lider, String viceLider, String anfitriao, String secretario, String colaborador, String dia, String hora, String  baseCelula, String  membrosIEQ, String  convidados, String  criancas, String  total, String estudo, String quebragelo, String lanche, String aceitacao, String reconciliacao, String testemunho, String status, String datahora) {
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
        this.baseCelula = baseCelula;
        this.membrosIEQ = membrosIEQ;
        this.convidados = convidados;
        this.criancas = criancas;
        this.total = total;
        this.estudo = estudo;
        this.quebragelo = quebragelo;
        this.lanche = lanche;
        this.aceitacao = aceitacao;
        this.reconciliacao = reconciliacao;
        this.testemunho = testemunho;
        this.datahora = datahora;
        this.status = status;

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

    public String  getBaseCelula() {
        return baseCelula;
    }

    public void setBaseCelula(String  baseCelula) {
        this.baseCelula = baseCelula;
    }

    public String  getMembrosIEQ() {
        return membrosIEQ;
    }

    public void setMembrosIEQ(String membrosIEQ) {
        this.membrosIEQ = membrosIEQ;
    }

    public String  getConvidados() {
        return convidados;
    }

    public void setConvidados(String  convidados) {
        this.convidados = convidados;
    }

    public String  getCriancas() {
        return criancas;
    }

    public void setCriancas(String  criancas) {
        this.criancas = criancas;
    }

    public String  getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEstudo() {
        return estudo;
    }

    public void setEstudo(String estudo) {
        this.estudo = estudo;
    }

    public String getQuebragelo() {
        return quebragelo;
    }

    public void setQuebragelo(String quebragelo) {
        this.quebragelo = quebragelo;
    }

    public String getLanche() {
        return lanche;
    }

    public void setLanche(String lanche) {
        this.lanche = lanche;
    }

    public String getAceitacao() {
        return aceitacao;
    }

    public void setAceitacao(String aceitacao) {
        this.aceitacao = aceitacao;
    }

    public String getReconciliacao() {
        return reconciliacao;
    }

    public void setReconciliacao(String reconciliacao) {
        this.reconciliacao = reconciliacao;
    }

    public String getTestemunho() {
        return testemunho;
    }

    public void setTestemunho(String testemunho) {
        this.testemunho = testemunho;
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
}
