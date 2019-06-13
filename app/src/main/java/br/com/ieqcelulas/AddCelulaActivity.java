package br.com.ieqcelulas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import celulas.Celula;

import static br.com.ieqcelulas.HomeActivity.DataT;
import static br.com.ieqcelulas.HomeActivity.DataTime;
import static br.com.ieqcelulas.HomeActivity.igreja;
import static br.com.ieqcelulas.HomeActivity.status;

public class AddCelulaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference Celulas;
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
    private TextInputLayout textInputDataInicio;
    private String dia;
    private String hh;
    private String mm;
    private Spinner sp;
    private Spinner hr;
    private Spinner min;
    private String  hrs;

    private String[] semana = new String[] { "Dia da semana", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
    private String[] hora = new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00" };
    private String[] minuto = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "24",
            "25","26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
            "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_celula );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        inicializarComponentes();
        inicializarFirebase();

        Objects.requireNonNull( textInputDataInicio.getEditText() ).setText(DataT);

        ArrayAdapter<String> adapter = new ArrayAdapter<>( AddCelulaActivity.this, android.R.layout.simple_spinner_dropdown_item, semana );
        sp = findViewById( R.id.spinnerSemana );
        sp.setAdapter( adapter );
        sp.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dia = (String)sp.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dia = "";
            }
        } );


        ArrayAdapter<String> adapterhora = new ArrayAdapter<>( AddCelulaActivity.this, android.R.layout.simple_spinner_dropdown_item,hora );
        hr = findViewById( R.id.spinnerhora );
        hr.setAdapter( adapterhora );
        hr.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hh = (String)hr.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hh = "";
            }
        } );

        ArrayAdapter<String> adaptermin = new ArrayAdapter<>( AddCelulaActivity.this, android.R.layout.simple_spinner_dropdown_item,minuto );
        min = findViewById( R.id.spinnermin );
        min.setAdapter( adaptermin );
        min.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mm = (String)min.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mm = "";
            }
        } );

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }

    private void inicializarComponentes() {
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
        textInputDataInicio = findViewById(R.id.text_input_DataInicio);
    }


    /** Inicia Firebase   */
    private void inicializarFirebase() {
        Celulas = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.add_celula, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_Save){
            addCelulaClick(item);
            return true;
        }

        return super.onOptionsItemSelected( item );
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent(this,HomeActivity.class);
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( AddCelulaActivity.this,CelulasActivity.class);
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( AddCelulaActivity.this,ComunicadosActivity.class);
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( AddCelulaActivity.this,IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( AddCelulaActivity.this,AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( AddCelulaActivity.this,VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( AddCelulaActivity.this,ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( AddCelulaActivity.this,CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( AddCelulaActivity.this,EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    public void addCelulaClick(MenuItem item) {
        String celula = Objects.requireNonNull( textInputCelula.getEditText() ).getText().toString().trim();
        String rede = Objects.requireNonNull( textInputRede.getEditText() ).getText().toString().trim();
        String supervisor = Objects.requireNonNull( textInputSupervisor.getEditText() ).getText().toString().trim();
        String lider = Objects.requireNonNull( textInputLider.getEditText() ).getText().toString().trim();
        String viceLider = Objects.requireNonNull( textInputViceLider.getEditText() ).getText().toString().trim();
        String anfitriao = Objects.requireNonNull( textInputAnfitriao.getEditText() ).getText().toString().trim();
        String secretario = Objects.requireNonNull( textInputSecretario.getEditText() ).getText().toString().trim();
        String colaborador = Objects.requireNonNull( textInputColaborador.getEditText() ).getText().toString().trim();
       // String dia = Objects.requireNonNull( textInputDia.getEditText() ).getText().toString().trim();
        String hora = hh+":"+mm;
        String datainicio = Objects.requireNonNull( textInputDataInicio.getEditText() ).getText().toString().trim();
        String[] var = new String[]{rede, supervisor, lider, viceLider, anfitriao, secretario, colaborador, dia, hora, datainicio};
        if(!TextUtils.isEmpty( celula )){
            String uid = Celulas.push().getKey();
            Celula cel = new Celula(uid, celula, rede, supervisor, lider, viceLider, anfitriao, secretario, colaborador, dia, hora, datainicio, status, DataTime);
            if (uid == null) throw new AssertionError();
            Celulas.child(igreja + "/Celulas/").child( celula ).child( uid ).setValue( cel );

            Toast.makeText(this,"Criado célula com sucesso", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Erro ao tentar criar célula !", Toast.LENGTH_LONG).show();
        }

        Intent celulas = new Intent( AddCelulaActivity.this,CelulasActivity.class);
        startActivity( celulas );
        finish();
    }

/*    public boolean  testNullvariable(String[] var){
        for (String vart: var ) {
            if (vart == null){
              return false;
            }
            Toast.makeText(getApplicationContext(), "Preencha todas as informações !", Toast.LENGTH_SHORT).show();
        }
    }*/
}
