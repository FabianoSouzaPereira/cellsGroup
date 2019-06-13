package login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.ieqcelulas.HomeActivity;
import br.com.ieqcelulas.R;

@SuppressWarnings("ALL")
public class WellcomeActivity extends AppCompatActivity {

    public static final String user="names";
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wellcome );

        txtUser = (TextView)findViewById(R.id.txtUser);
        String user = (getIntent().getStringExtra( "names"));
        txtUser.setText(getString( R.string.Bem_Vindo)+ user);

        Intent Home = new Intent( WellcomeActivity.this, HomeActivity.class );
        startActivity( Home );

    }

 }
