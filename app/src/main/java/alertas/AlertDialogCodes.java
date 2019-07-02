package alertas;

import android.content.DialogInterface;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;

import android.widget.Toast;





public class AlertDialogCodes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AlertDialog alerta;


    public void exemplo_simples() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder( AlertDialogCodes.this);
        //define o titulo
        builder.setTitle("Titulo");
        //define a mensagem
        builder.setMessage("Qualifique este software");
        //define um botão como positivo
        builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(AlertDialogCodes.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(AlertDialogCodes.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

/*    public void exemplo_lista_single() {
        //Lista de itens
        ArrayList<String> itens = new ArrayList<String>();
        itens.add("Ruim");
        itens.add("Mediano");
        itens.add("Bom");
        itens.add("Ótimo");

        //adapter utilizando um layout customizado (TextView)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, itens);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Qualifique este software:");
        //define o diálogo como uma lista, passa o adapter.
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText( AlertDialogCodes.this, "posição selecionada=" + arg1, Toast.LENGTH_SHORT).show();
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }


    public void exemplo_lista_multi() {
        CharSequence[] charSequences = new CharSequence[]{"Filmes", "Dormir","Sair"};
        final boolean[] checados = new boolean[charSequences.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("O que você gosta?");
        builder.setMultiChoiceItems(charSequences, checados, new DialogInterface
                .OnMultiChoiceClickListener() {
            public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
                checados[arg1] = arg2;
            }
        });

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                StringBuilder texto = new StringBuilder("Checados: ");
                for (boolean ch : checados) {
                    texto.append(ch).append("; ");
                }
                Toast.makeText(AlertDialogCodes.this, texto.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        alerta = builder.create();
        alerta.show();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
