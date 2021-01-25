package Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.cellsgroup.R;

@SuppressWarnings( "ALL" )
public class ViewholderAgenda  extends RecyclerView.ViewHolder  {
    public TextView data;
    public TextView hora;
    public TextView evento;
    public TextView local;
    public TextView descricao;

    public ViewholderAgenda(@NonNull View itemView) {
        super( itemView );
        data = itemView.findViewById( R.id.tvDataAgenda );
        hora = itemView.findViewById(R.id.tvhoraAgenda);
        evento = itemView.findViewById( R.id.tveventoAgenda );
        local = itemView.findViewById( R.id.tvlocalAgenda );
        descricao = itemView.findViewById( R.id.tvdescricaoAgenda );
    }
}
