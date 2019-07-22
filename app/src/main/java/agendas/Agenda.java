package agendas;

@SuppressWarnings( "ALL" )
public final class Agenda {
    private String uid;
    private String data;
    private String hora;
    private String evento;
    private String local;
    private String descricao;
    private String status = "1";
    private String userId = "";

    public Agenda() {
    }

    public Agenda(String uid, String data, String hora, String evento, String local, String descricao, String status, String userId) {
        this.uid = uid;
        this.data = data;
        this.hora = hora;
        this.evento = evento;
        this.local = local;
        this.descricao = descricao;
        this.status = status;
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData() {
        if (data.isEmpty()){
            data.valueOf( "01/01/2019" );

        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
}
