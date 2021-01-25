package br.com.cellsgroup;

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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import celulas.Celula;

import static br.com.cellsgroup.HomeActivity.igreja;

@SuppressWarnings("ALL")
public class EditCelulaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference Celulas;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef1;
    private DatabaseReference novaRef2;
    private String celula_extra;
    String uid;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_celula );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Intent intent = getIntent();
        celula_extra = intent.getStringExtra( "Celula" );
        inicializarComponentes();
        inicializarFirebase();
        novaRef2 = databaseReference.child( "Igrejas/" + igreja + "/Celulas");
        pegandoConteudoCelula();


        ArrayAdapter<String> adapter = new ArrayAdapter<>( EditCelulaActivity.this, android.R.layout.simple_spinner_dropdown_item, semana );
        sp = (Spinner) findViewById( R.id.spinnerUpdateSemana );
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


        ArrayAdapter<String> adapterhora = new ArrayAdapter<>( EditCelulaActivity.this, android.R.layout.simple_spinner_dropdown_item,hora );
        hr = (Spinner)findViewById( R.id.spinnerUpdatehora );
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

        ArrayAdapter<String> adaptermin = new ArrayAdapter<>( EditCelulaActivity.this, android.R.layout.simple_spinner_dropdown_item,minuto );
        min = (Spinner)findViewById( R.id.spinnerUpdatemin );
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

    private void pegandoConteudoCelula() {

        novaRef1 = databaseReference.child( "Igrejas/" + igreja + "/Celulas/"+ this.celula_extra);
        novaRef1.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Celula c = ds.getValue( Celula.class );
                    uid = Objects.requireNonNull( c ).getUid();
                    String celula = Objects.requireNonNull( c ).getCelula();
                    String rede = c.getRede();
                    String supervisor = c.getSupervisor();
                    String lider = c.getLider();
                    String viceLider = c.getViceLider();
                    String anfitriao = c.getAnfitriao();
                    String secretario = c.getSecretario();
                    String colaborador = c.getColaborador();
                    dia = c.getDia();
                    hrs = c.getHora();
                    String datainicio = c.getDatainicio();

                    Objects.requireNonNull( textInputCelula.getEditText() ).setText( celula );
                    Objects.requireNonNull( textInputRede.getEditText() ).setText( rede );
                    Objects.requireNonNull( textInputSupervisor.getEditText() ).setText( supervisor );
                    Objects.requireNonNull( textInputLider.getEditText() ).setText( lider );
                    Objects.requireNonNull( textInputViceLider.getEditText() ).setText( viceLider );
                    Objects.requireNonNull( textInputAnfitriao.getEditText() ).setText( anfitriao );
                    Objects.requireNonNull( textInputSecretario.getEditText() ).setText( secretario );
                    Objects.requireNonNull( textInputColaborador.getEditText() ).setText( colaborador );

                    Objects.requireNonNull( textInputDataInicio.getEditText() ).setText( datainicio );

                    sp = (Spinner) findViewById( R.id.spinnerUpdateSemana );
                    sp.setSelection(getIndex(sp, dia));

                    hh = hrs.substring(0,2);
                    hr.setSelection(getIndex( hr, hh ));

                    mm = hrs.substring(3,5);
                    min.setSelection( getIndex( min, mm ) );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    //private method of your class
    private int getIndex(Spinner sp, String dia){
        for (int i=0;i<sp.getCount();i++){
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(dia)){
                return i;
            }
        }

        return 0;
    }

    public void editCelulaClick(MenuItem item) {
        String celula = Objects.requireNonNull( textInputCelula.getEditText() ).getText().toString().trim();
        String rede = Objects.requireNonNull( textInputRede.getEditText() ).getText().toString().trim();
        String supervisor = Objects.requireNonNull( textInputSupervisor.getEditText() ).getText().toString().trim();
        String lider = Objects.requireNonNull( textInputLider.getEditText() ).getText().toString().trim();
        String viceLider = Objects.requireNonNull( textInputViceLider.getEditText() ).getText().toString().trim();
        String anfitriao = Objects.requireNonNull( textInputAnfitriao.getEditText() ).getText().toString().trim();
        String secretario = Objects.requireNonNull( textInputSecretario.getEditText() ).getText().toString().trim();
        String colaborador = Objects.requireNonNull( textInputColaborador.getEditText() ).getText().toString().trim();
        String hora = hh + ":" + mm;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        novaRef2.child( celula ).child(  uid  );

        if(!TextUtils.isEmpty( celula )){
            Map<String, Object> celulaUpdates = new HashMap<>();
            celulaUpdates.put( celula + "/"  + uid + "/rede" , rede);
            celulaUpdates.put( celula + "/"  + uid + "/supervisor", supervisor );
            celulaUpdates.put( celula + "/"  + uid + "/lider", lider );
            celulaUpdates.put( celula + "/"  + uid + "/viceLider", viceLider );
            celulaUpdates.put( celula + "/"  + uid + "/anfitriao", anfitriao );
            celulaUpdates.put( celula + "/"  + uid + "/secretario", secretario );
            celulaUpdates.put( celula + "/"  + uid + "/colaborador", colaborador );
            celulaUpdates.put( celula + "/"  + uid + "/dia", dia );
            celulaUpdates.put( celula + "/"  + uid + "/hora", hora );
            celulaUpdates.put( celula + "/"  + uid + "/userId", userId );

            novaRef2.updateChildren( celulaUpdates );

            Toast.makeText(this,"Editado célula com sucesso", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Erro ao tentar Editar célula !", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent( EditCelulaActivity.this,CelulasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity( intent );
        finish();

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(EditCelulaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
        getMenuInflater().inflate( R.menu.edit_celula, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings_edit_celula) {
            return true;
        }
        if(id == R.id.action_Save_edit_celula){
            editCelulaClick(item);
            return true;
        }
        if (id ==R.id.action_cancel_edit_celula){

            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( EditCelulaActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( EditCelulaActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( EditCelulaActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( EditCelulaActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( EditCelulaActivity.this, VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( EditCelulaActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( EditCelulaActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( EditCelulaActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
