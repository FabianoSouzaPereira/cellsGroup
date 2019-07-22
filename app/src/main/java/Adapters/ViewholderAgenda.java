package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.ieqcelulas.R;

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
