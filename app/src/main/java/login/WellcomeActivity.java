package login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import br.com.cellsgroup.HomeActivity;
import br.com.cellsgroup.R;

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
