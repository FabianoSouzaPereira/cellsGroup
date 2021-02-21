package br.com.cellsgroup.models.relatorios;

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
    private String userId;

    public Relatorio() {
    }

    public Relatorio(String uid,String celula, String rede, String supervisor, String lider, String viceLider, String anfitriao, String secretario, String colaborador, String dia, String hora, String  baseCelula, String  membrosIEQ, String  convidados, String  criancas, String  total, String estudo, String quebragelo, String lanche, String aceitacao, String reconciliacao, String testemunho, String status, String datahora,String userId ) {
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
        this.userId  = userId ;

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
   
   public String  getBaseCelula() {
        return baseCelula;
    }
   
   public String  getMembrosIEQ() {
        return membrosIEQ;
    }
   
   public String  getConvidados() {
        return convidados;
    }
   
   public String  getCriancas() {
        return criancas;
    }
   
   public String  getTotal() {
        return total;
    }
   
   public String getEstudo() {
        return estudo;
    }
   
   public String getQuebragelo() {
        return quebragelo;
    }
   
   public String getLanche() {
        return lanche;
    }
   
   public String getAceitacao() {
        return aceitacao;
    }
   
   public String getReconciliacao() {
        return reconciliacao;
    }
   
   public String getTestemunho() {
        return testemunho;
    }
   
   public String getDatahora() {
        return datahora;
    }
   
}
