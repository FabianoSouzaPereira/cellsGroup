package adapters;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.cellsgroup.R;

@SuppressWarnings( "ALL" )
public class ViewholderAgenda  extends RecyclerView.ViewHolder  {
    public TextView data;
    Spinner hora;
    Spinner minuto;
    public TextView evento;
    public TextView local;
    public TextView descricao;

    public ViewholderAgenda(@NonNull View itemView) {
        super( itemView );
        data = itemView.findViewById( R.id.tvDataAgenda );
        hora = itemView.findViewById(  R.id.spinnerhoraAg);
        minuto = itemView.findViewById ( R.id.spinnerminAg );
        evento = itemView.findViewById( R.id.tveventoAgenda );
        local = itemView.findViewById( R.id.tvlocalAgenda );
        descricao = itemView.findViewById( R.id.tvdescricaoAgenda );
    }
}
