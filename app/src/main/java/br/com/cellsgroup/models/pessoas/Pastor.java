package br.com.cellsgroup.models.pessoas;

import java.util.ArrayList;

public final class Pastor extends Pessoa {

    public Pastor() {
    }

    public Pastor ( String uid , String nome , String idade , String sexo , String dataNascimento , String dataBastismo , String nomepai , String nomemae , String estadocivil , String codigoPais , String telefone , String email , String endereco , String bairro , String cidade , String pais , String cep , String cargoIgreja , String status , String datahora , String igrejaPadrao , String userId , ArrayList < String > group ) {
        super ( uid , nome , idade , sexo , dataNascimento , dataBastismo , nomepai , nomemae , estadocivil , codigoPais , telefone , email , endereco , bairro , cidade , pais , cep , cargoIgreja , status , datahora , igrejaPadrao , userId , group );
    }
}
