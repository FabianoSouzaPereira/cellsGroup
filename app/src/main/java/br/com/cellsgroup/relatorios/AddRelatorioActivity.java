package br.com.cellsgroup.relatorios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.leader.LeaderActivity;
import br.com.cellsgroup.models.relatorios.Relatorio;
import br.com.cellsgroup.models.celulas.Celula;

import static br.com.cellsgroup.home.HomeActivity.Logado;
import static br.com.cellsgroup.home.HomeActivity.UI;
import static br.com.cellsgroup.home.HomeActivity.typeUserAdmin;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;


@SuppressWarnings("ALL")
public final class AddRelatorioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference relatorios;
    private DatabaseReference novaRef5;

    // variáveis dos textInputs
    private TextInputLayout textInputCelula;
    private TextInputLayout textInputRede;
    private TextInputLayout textInputSupervisor;
    private TextInputLayout textInputLider;
    private TextInputLayout textInputViceLider;
    private TextInputLayout textInputAnfitriao;
    private TextInputLayout textInputSecretario;
    private TextInputLayout textInputColaborador;
    private TextInputLayout textInputDia;
    private TextInputLayout textInputHora;
    private TextInputLayout textInputBaseCelula;
    private TextInputLayout textInputMembrosIEQ;
    private TextInputLayout textInputConvidados;
    private TextInputLayout textInputCriancas;
    private TextInputLayout textInputTotal;
    private TextInputLayout textInputDataInicio;
    // Variáveis do radiogroups
    private RadioGroup radioGroupEstudo;
    private RadioGroup radioGroupQuebraGelo;
    private RadioGroup radioGroupLanche;
    private RadioGroup radioGroupAceitacao;
    private RadioGroup radioGroupReconcilhacao;
    private RadioGroup radioGroupTestemunho;
    // variaveis dos RadioButtons
    private RadioButton rbEstudoSim;
    private RadioButton rbEstudoNao;
    private RadioButton rbQuebrageloSim;
    private RadioButton rbQuebrageloNao;
    private RadioButton rbLancheSim;
    private RadioButton rbLancheNao;
    private RadioButton rbAceitacaoSim;
    private RadioButton rbAceitacaoNao;
    private RadioButton rbReconciliacaoSim;
    private RadioButton rbReconciliacaoNao;
    private RadioButton rbTestemunhoSim;
    private RadioButton rbTestemunhoNao;
    // variaveis que vão receber o valor dos Radios buttons checked.
    private String estudo;
    private String quebragelo;
    private String lanche;
    private String aceitacao;
    private String reconciliacao;
    private String testemunho;
    public String DataTime;
    public String DataT;
    public  String celulas_;
    private static boolean validate = true;
    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;
    private int total;
    private int base;
    private int membros;
    private int convidados;
    private int crianca;
    View.OnFocusChangeListener listenerBase;
    View.OnFocusChangeListener listenerMembros;
    View.OnFocusChangeListener listenerConvidados;
    View.OnFocusChangeListener listenerCrianca;
    View.OnFocusChangeListener listenerTotal;
    String mensagem1 = "";
    String mensagem2 = "";
    String mensagem3 = "";
    String mensagem4 = "";
    String mensagem5 = "";
    String mensagem6 = "";
    String mensagem7 = "";
    String mensagem8 = "";
    String mensagem9 = "";
    String mensagem10 = "";
    String mensagem11 = "";
    String mensagem12 = "";
    String mensagem13 = "";
    String mensagem14 = "";
    String mensagem15 = "";
    String mensagem16 = "";
    String mensagem17 = "";
    String mensagem18 = "";
    String mensagem19 = "";
    String mensagem20 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_relatorio );
        Toolbar toolbar = findViewById( R.id.toolbar_add_relatorio );
        setSupportActionBar( toolbar );

        inicializarFirebase();
        inicializarComponentes();
        Intent intent = getIntent();
        celulas_ = intent.getStringExtra( "Celula" );

        pegandoConteudoCelula();

    }

    private void inicializarComponentes() {
        mensagem1 =  getResources ().getString (R.string.erroCampoObrigatorio);
        mensagem2 =  getResources ().getString (R.string.criadoRelatorioSuccess);
        mensagem3 =  getResources ().getString (R.string.erroCriadoRelatorio);

        textInputCelula = findViewById(R.id.text_input_celula);
        textInputRede = findViewById(R.id.text_input_rede);
        textInputSupervisor = findViewById(R.id.text_input_supervisor);
        textInputLider = findViewById(R.id.text_input_lider);
        textInputViceLider = findViewById(R.id.text_input_vice_lider);
        textInputAnfitriao = findViewById(R.id.text_input_anfitriao);
        textInputSecretario = findViewById(R.id.text_input_secretario);
        textInputColaborador = findViewById(R.id.text_input_colaborador);
        textInputDia = findViewById(R.id.text_input_dia);
        textInputHora = findViewById(R.id.text_input_hora);

        textInputBaseCelula = findViewById(R.id.text_input_basecelula);
        textInputBaseCelula.getEditText ().setFocusable(true);
        textInputMembrosIEQ = findViewById(R.id.text_input_membrosieq);
        textInputMembrosIEQ.getEditText ().setFocusable(true);
        textInputConvidados = findViewById(R.id.text_input_convidados);
        textInputConvidados.getEditText ().setFocusable(true);
        textInputCriancas = findViewById(R.id.text_input_criancas);
        textInputCriancas.getEditText ().setFocusable(true);
        textInputTotal= findViewById(R.id.text_input_total);
        textInputTotal.getEditText ().setFocusable(true);

        listenerBase =  new View.OnFocusChangeListener ( ) {
            @Override
            public void onFocusChange ( View v , boolean hasFocus ) {
                if (!hasFocus) {
                    base = convertToint( textInputBaseCelula.getEditText().getText().toString());
                    total = base + membros + convidados + crianca;
                    textInputTotal.getEditText ( ).setText ( Integer.toString ( total ) );
                }
            }
        } ;
        listenerMembros =  new View.OnFocusChangeListener ( ) {
            @Override
            public void onFocusChange ( View v , boolean hasFocus ) {
                if (!hasFocus) {
                    membros = convertToint(textInputMembrosIEQ.getEditText().getText().toString());
                    total = base + membros + convidados + crianca;
                    textInputTotal.getEditText ( ).setText ( Integer.toString ( total ) );
                }
            }
        };

        listenerConvidados =  new View.OnFocusChangeListener ( ) {
            @Override
            public void onFocusChange ( View v , boolean hasFocus ) {
                if (!hasFocus) {
                    convidados = convertToint ( textInputConvidados.getEditText ( ).getText ( ).toString ( ) );
                    total = base + membros + convidados + crianca;
                    textInputTotal.getEditText ( ).setText ( Integer.toString ( total ) );
                }
            }
        };

        listenerCrianca = new View.OnFocusChangeListener ( ) {
            @Override
            public void onFocusChange ( View v , boolean hasFocus ) {
                if (!hasFocus) {
                    crianca = convertToint ( textInputCriancas.getEditText ( ).getText ( ).toString ( ) );
                    total = base + membros + convidados + crianca;
                    textInputTotal.getEditText ( ).setText ( Integer.toString ( total ) );
                }
            }
        } ;

        listenerTotal = new View.OnFocusChangeListener ( ) {
            @Override
            public void onFocusChange ( View v , boolean hasFocus ) {
            }
        };

        textInputBaseCelula.getEditText ().setOnFocusChangeListener(listenerBase );
        textInputMembrosIEQ.getEditText () .setOnFocusChangeListener(listenerMembros);
        textInputConvidados.getEditText ().setOnFocusChangeListener(listenerConvidados);
        textInputCriancas.getEditText ().setOnFocusChangeListener(listenerCrianca);
        textInputTotal.getEditText ().setOnFocusChangeListener(listenerTotal);

        textInputTotal.getEditText ( ).setText ( Integer.toString ( total ) );

        textInputDataInicio = findViewById( R.id.txt_dataInicio );

        /* Listeners dos radiobuttons */
        radioGroupEstudo = findViewById( R.id.rgEstudo );
        rbEstudoSim = findViewById(R.id.rbEstudoSim);
        rbEstudoNao = findViewById(R.id.rbEstudoNao);

        radioGroupQuebraGelo = findViewById(R.id.rgQuebragelo);
        rbQuebrageloSim = findViewById( R.id.rbQuebrageloSim );
        rbQuebrageloNao = findViewById( R.id.rbQuebrageloNao );
        radioGroupLanche = findViewById(R.id.rgLanche);
        rbLancheSim = findViewById( R.id.rbLancheSim);
        rbLancheNao = findViewById( R.id.rbLancheNao );
        radioGroupAceitacao = findViewById(R.id.rgAceitacao);
        rbAceitacaoSim = findViewById( R.id.rbAceitacaoSim );
        rbAceitacaoNao = findViewById( R.id.rbAceitacaoNao );
        radioGroupReconcilhacao = findViewById(R.id.rgReconciliacao);
        rbReconciliacaoSim = findViewById( R.id.rbReconciliacaoSim );
        rbReconciliacaoNao = findViewById( R.id.rbReconciliacaoNao );
        radioGroupTestemunho = findViewById(R.id.rgTestemunho);
        rbTestemunhoSim = findViewById( R.id.rbTestemunhoSim );
        rbTestemunhoNao = findViewById( R.id.rbTestemunhoNao );

        radioGroupEstudo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroupEstudo.getCheckedRadioButtonId() == rbEstudoSim.getId()) {
                    estudo = rbEstudoSim.getText().toString();
                } else if (radioGroupEstudo.getCheckedRadioButtonId() == rbEstudoNao.getId()) {
                    estudo = rbEstudoNao.getText().toString();
                }
            }
        } );

        radioGroupQuebraGelo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroupQuebraGelo.getCheckedRadioButtonId() == rbQuebrageloSim.getId()){
                    quebragelo = rbQuebrageloSim.getText().toString();
                }else if (radioGroupQuebraGelo.getCheckedRadioButtonId() == rbQuebrageloNao.getId()){
                    quebragelo = rbQuebrageloNao.getText().toString();
                }
            }
        } );

        radioGroupLanche.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroupLanche.getCheckedRadioButtonId() == rbLancheSim.getId()){
                    lanche = rbLancheSim.getText().toString();
                }else if (radioGroupLanche.getCheckedRadioButtonId() == rbLancheNao.getId()){
                    lanche = rbLancheNao.getText().toString();
                }
            }
        } );

        radioGroupAceitacao.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroupAceitacao.getCheckedRadioButtonId() == rbAceitacaoSim.getId()){
                    aceitacao = rbAceitacaoSim.getText().toString();
                }else if (radioGroupAceitacao.getCheckedRadioButtonId() == rbAceitacaoNao.getId()){
                    aceitacao = rbAceitacaoNao.getText().toString();
                }
            }
        } );

        radioGroupReconcilhacao.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroupReconcilhacao.getCheckedRadioButtonId() == rbReconciliacaoSim.getId()){
                    reconciliacao = rbReconciliacaoSim.getText().toString();
                }else if (radioGroupReconcilhacao.getCheckedRadioButtonId() == rbReconciliacaoNao.getId()){
                    reconciliacao = rbReconciliacaoNao.getText().toString();
                }
            }
        } );

        radioGroupTestemunho.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroupTestemunho.getCheckedRadioButtonId() == rbTestemunhoSim.getId()){
                    testemunho = rbTestemunhoSim.getText().toString();
                }else if (radioGroupTestemunho.getCheckedRadioButtonId() == rbTestemunhoNao.getId()){
                    testemunho = rbTestemunhoNao.getText().toString();
                }
            }
        } );
    }

    public int convertToint(String valor){
        if(valor.equals("")){
            int var = 0;
            return var;
        }else{
            return Integer.parseInt ( valor );
        }
    }

    /** Inicia Firebase   */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(AddRelatorioActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void pegandoConteudoCelula() {

        novaRef5 = databaseReference.child( "churchs/" + uidIgreja + "/cells/" + this.celulas_);
        novaRef5.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Celula c = ds.getValue( Celula.class );
                    String celula = Objects.requireNonNull( c ).getCelula();
                    String rede = c.getRede();
                    String supervisor = c.getSupervisor();
                    String lider = c.getLider();
                    String viceLider = c.getViceLider();
                    String anfitriao = c.getAnfitriao();
                    String secretario = c.getSecretario();
                    String colaborador = c.getColaborador();
                    String dia = c.getDia();
                    String hora = c.getHora();
                    //  String datainicio = c.getDatainicio();


                    Objects.requireNonNull( textInputCelula.getEditText() ).setText( celula );
                    Objects.requireNonNull( textInputRede.getEditText() ).setText(rede);
                    Objects.requireNonNull( textInputSupervisor.getEditText() ).setText(supervisor);
                    Objects.requireNonNull( textInputLider.getEditText() ).setText(lider);
                    Objects.requireNonNull( textInputViceLider.getEditText() ).setText(viceLider);
                    Objects.requireNonNull( textInputAnfitriao.getEditText() ).setText(anfitriao);
                    Objects.requireNonNull( textInputSecretario.getEditText() ).setText(secretario);
                    Objects.requireNonNull( textInputColaborador.getEditText() ).setText(colaborador);
                    Objects.requireNonNull( textInputDia.getEditText() ).setText(dia);
                    Objects.requireNonNull( textInputHora.getEditText() ).setText(hora);
                    //   Objects.requireNonNull( textInputDataInicio.getEditText() ).setText( datainicio );

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }


    public void addRelatorio(MenuItem menu) {
        addDataHora();
        validate=true;
        try {
            final String userId = UI.getUid ();
            relatorios = databaseReference.child( "churchs/" + uidIgreja + "/Reports/" );
            String celula = Objects.requireNonNull( textInputCelula.getEditText(), "" ).getText().toString().trim();
            String rede = Objects.requireNonNull( textInputRede.getEditText(), ""  ).getText().toString().trim();
            String supervisor = Objects.requireNonNull( textInputSupervisor.getEditText(), ""  ).getText().toString().trim();
            String lider = Objects.requireNonNull( textInputLider.getEditText(), ""  ).getText().toString().trim();
            String viceLider = Objects.requireNonNull( textInputViceLider.getEditText(), ""  ).getText().toString().trim();
            String anfitriao = Objects.requireNonNull( textInputAnfitriao.getEditText(), ""  ).getText().toString().trim();
            String secretario = Objects.requireNonNull( textInputSecretario.getEditText(), ""  ).getText().toString().trim();
            String colaborador = Objects.requireNonNull( textInputColaborador.getEditText(), ""  ).getText().toString().trim();
            String dia = Objects.requireNonNull( textInputDia.getEditText(), ""  ).getText().toString().trim();
            String hora = Objects.requireNonNull( textInputHora.getEditText(), ""  ).getText().toString().trim();

            String baseCelula = Objects.requireNonNull( textInputBaseCelula.getEditText(), "0" ).getText().toString().trim();
            if(baseCelula.equals ("")){
                validate = false;
                textInputBaseCelula.setError(mensagem1);
                textInputBaseCelula.setFocusable (true);
                textInputBaseCelula.requestFocus ();
            }else {
                textInputBaseCelula.setError(null);
                textInputBaseCelula.getEditText ().clearFocus ();
            }
            String membrosIEQ = Objects.requireNonNull( textInputMembrosIEQ.getEditText(), ""  ).getText().toString().trim();
            if(baseCelula.equals ("")){
                validate = false;
                textInputMembrosIEQ.setError(mensagem1);
                textInputMembrosIEQ.setFocusable (true);
                textInputMembrosIEQ.requestFocus ();
            }else {
                textInputMembrosIEQ.setError(null);
                textInputBaseCelula.getEditText ().clearFocus ();
            }
            String convidados = Objects.requireNonNull( textInputConvidados.getEditText(), ""  ).getText().toString().trim();
            if(convidados.equals ("")){
                validate = false;
                textInputConvidados.setError(mensagem1);
                textInputConvidados.setFocusable (true);
                textInputConvidados.requestFocus ();
            }else {
                textInputConvidados.setError(null);
                textInputConvidados.getEditText ().clearFocus ();
            }
            String criancas = Objects.requireNonNull( textInputCriancas.getEditText(), ""  ).getText().toString().trim();
            if(criancas .equals ("")){
                validate = false;
                textInputCriancas.setError(mensagem1);
                textInputCriancas.setFocusable (true);
                textInputCriancas.requestFocus ();
            }else {
                textInputCriancas.setError(null);
                textInputCriancas.getEditText ().clearFocus ();
            }
            String total =  Objects.requireNonNull( textInputTotal.getEditText(), ""  ).getText().toString().trim();
            if(total.equals ("")){
                validate = false;
                textInputTotal.setError(mensagem1);
                textInputTotal.setFocusable (true);
                textInputTotal.requestFocus ();
            }else {
                textInputTotal.setError(null);
                textInputTotal.getEditText ().clearFocus ();
            }

            String status = "1";
            if( validate ) {
                addDataHora ( );
                if ( !TextUtils.isEmpty ( celula ) && Logado == true && typeUserAdmin == true ) {
                    String uid = relatorios.push ( ).getKey ( );
                    Relatorio relatorio = new Relatorio ( uid , celula , rede , supervisor , lider , viceLider , anfitriao , secretario , colaborador , dia , hora , baseCelula , membrosIEQ , convidados , criancas , total , estudo , quebragelo , lanche , aceitacao , reconciliacao , testemunho , status , DataTime , userId );
                    if ( uid != null ) {
                        relatorios.child ( celula ).child ( uid ).setValue ( relatorio );

                        Toast.makeText ( this , mensagem2 , Toast.LENGTH_LONG ).show ( );
                        Intent intent = new Intent ( AddRelatorioActivity.this , RelatorioActivityView.class );
                        startActivity ( intent );
                        finish ( );
                    }
                } else {
                    Toast.makeText ( this , mensagem3, Toast.LENGTH_LONG ).show ( );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        AddRelatorioActivity.this.finish();
        Intent home = new Intent(AddRelatorioActivity.this,HomeActivity.class);
        startActivity(home);
        DrawerLayout drawer = findViewById( R.id.drawer_add_relatorio);
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save_cancel , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save){
            addRelatorio(item);
            return true;
        }
        if(id == R.id.action_Cancel){
            AddRelatorioActivity.this.finish();
            Intent intent = new Intent( AddRelatorioActivity.this,ReadRelatorioActivity.class );
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );

        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( AddRelatorioActivity.this, CelulasActivity.class );
            startActivity( celulas );

        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( AddRelatorioActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );

        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( AddRelatorioActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );

        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( AddRelatorioActivity.this, AgendaActivity.class );
            startActivity( agenda );

        } else if (id == R.id.nav_leader) {
            Intent agenda = new Intent( AddRelatorioActivity.this, LeaderActivity.class );
            startActivity( agenda );

        } else if (id == R.id.nav_realatorio) {
            Intent relatorio = new Intent( AddRelatorioActivity.this, RelatorioActivityView.class );
            startActivity( relatorio );

        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( AddRelatorioActivity.this, ContatoActivity.class );
            startActivity( contato );

        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( AddRelatorioActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );

        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( AddRelatorioActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_add_relatorio);
        drawer.closeDrawer( GravityCompat.START );
        return true;

    }

    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        DataTime = data + " "+ hora;
        DataT = data;
    }

}
