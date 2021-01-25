package br.com.cellsgroup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import agendas.Agenda;

import static br.com.cellsgroup.HomeActivity.Logado;
import static br.com.cellsgroup.HomeActivity.igreja;
import static br.com.cellsgroup.HomeActivity.typeUserAdmin;

public class AddAgendaActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextInputLayout EditTextDataEvento;
    private TextInputLayout EditTexthora;
    private TextInputLayout EditTextevento;
    private TextInputLayout EditTextlocal;
    private TextInputLayout EditTextdescricao;
    public String DataTime;
    public String DataT;
    private String dia;
    private String hh;
    private String mm;
    private Spinner sp;
    private Spinner hr;
    private Spinner min;
    private String  hrs;

    private final String[] semana = new String[] { "Dia da semana", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
    private final String[] hora = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
    private final String[] minuto = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "24",
            "25","26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
            "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_agenda );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        inicializarFirebase();
        addDataHora();
        inicializarComponentes();

    }

    private void inicializarComponentes() {
        ArrayAdapter<String> adapterhora = new ArrayAdapter<>( AddAgendaActivity.this, android.R.layout.simple_spinner_dropdown_item,hora );
        hr = findViewById( R.id.spinnerhoraAgenda );
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

        ArrayAdapter<String> adaptermin = new ArrayAdapter<>( AddAgendaActivity.this, android.R.layout.simple_spinner_dropdown_item,minuto );
        min = findViewById( R.id.spinnerminAgenda );
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
        EditTextDataEvento = findViewById( R.id.text_input_DataEvento);
        Objects.requireNonNull( EditTextDataEvento.getEditText() ).setText(DataT);
        EditTextevento = findViewById( R.id.text_input_eventoAgenda);
        EditTextlocal = findViewById( R.id.text_input_localAgenda);
        EditTextdescricao = findViewById( R.id.text_input_descricaoAgenda);

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(AddAgendaActivity.this);  //inicializa  o SDK credenciais padrão do aplicativo do Google
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public void onBackPressed() {
        AddAgendaActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_save_addAgenda){
            addAgendaClick(item);
            return true;
        }
        if(id == R.id.action_cancel_addAgenda){
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void addAgendaClick(MenuItem item) {
        addDataHora();

        String hora = hh + ":" + mm;
        String data = EditTextDataEvento.getEditText().getText().toString().trim();
        String evento = EditTextevento.getEditText().getText().toString().trim();
        String local = EditTextlocal.getEditText().getText().toString().trim();
        String descricao = EditTextdescricao.getEditText().getText().toString().trim();
        String status = "1";
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (!TextUtils.isEmpty( evento ) && !TextUtils.isEmpty( local ) && Logado && typeUserAdmin) {
            String uid = databaseReference.push().getKey();
            Agenda agenda = new Agenda( uid, data, hora, evento, local, descricao, status, userId );
            databaseReference.child( "Igrejas/" + igreja + "/Agenda").child( uid ).setValue( agenda );
            Intent intent = new Intent( AddAgendaActivity.this,AgendaActivity.class );
            startActivity( intent );
            finish();
            Toast.makeText(this,"Criado agenda com sucesso", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText( this, "Erro ao tentar criar intercessão !", Toast.LENGTH_LONG ).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        DataTime = data + " "+ hora;
        DataT = data;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
