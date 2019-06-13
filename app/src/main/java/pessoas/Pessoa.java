package pessoas;

@SuppressWarnings( "ALL" )
public class Pessoa {
    private String nome = "";
    private String idade = "";
    private String sexo = "";
    private String dataNascimento = "";
    private String dataBastismo = "";
    private String nomepai = "";
    private String nomemae = "";
    private String estadocivil = "";
    private String telefone = "";
    private String email = "";
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String pais = "";
    private String cep = "";
    private String cargoIgreja = "";

    public Pessoa() { }  //Construtor vazio para realizar update;

    public Pessoa(String nome, String idade, String sexo, String dataNascimento, String dataBastismo, String nomepai, String nomemae, String estadocivil, String telefone, String email, String endereco, String bairro, String cidade, String pais, String cep, String cargoIgreja) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.dataBastismo = dataBastismo;
        this.nomepai = nomepai;
        this.nomemae = nomemae;
        this.estadocivil = estadocivil;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.pais = pais;
        this.cep = cep;
        this.cargoIgreja = cargoIgreja;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataBastismo() {
        return dataBastismo;
    }

    public void setDataBastismo(String dataBastismo) {
        this.dataBastismo = dataBastismo;
    }

    public String getNomepai() {
        return nomepai;
    }

    public void setNomepai(String nomepai) {
        this.nomepai = nomepai;
    }

    public String getNomemae() {
        return nomemae;
    }

    public void setNomemae(String nomemae) {
        this.nomemae = nomemae;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCargoIgreja() {
        return cargoIgreja;
    }

    public void setCargoIgreja(String cargoIgreja) {
        this.cargoIgreja = cargoIgreja;
    }
}
