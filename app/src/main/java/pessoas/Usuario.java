package pessoas;


@SuppressWarnings( "ALL" )
public class Usuario {
    private String usuario = "";
    private String login = "";
    private String senha = "";
    private String tipo = "";  //Administrador; usuario avançado ou apenas usuário normal com acesso a sua acella apenas;
    private String igreja = "";
    private String bloquado = "não";
    private String tag = "0";
    private String status = "1";

    public Usuario() {  }  //Construtor vazio para realizar update;

    public Usuario(String usuario, String login, String senha, String tipo, String bloquado, String tag, String status) {
        this.usuario = usuario;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
        this.bloquado = bloquado;
        this.tag = tag;
        this.status = status;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getBloquado() {
        return bloquado;
    }

    public void setBloquado(String bloquado) {
        this.bloquado = bloquado;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
