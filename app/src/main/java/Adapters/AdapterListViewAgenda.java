package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


import agendas.Agenda;
import br.com.cellsgroup.R;

@SuppressWarnings( "ALL" )
public class AdapterListViewAgenda  extends RecyclerView.Adapter<ViewholderAgenda>{
    private List<Agenda> agendas;
    private Context context;

    public AdapterListViewAgenda(List<Agenda> agendas, Context context) {
        this.agendas = agendas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewholderAgenda onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.item_listview_agenda, viewGroup, false);

        ViewholderAgenda holder = new ViewholderAgenda(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderAgenda viewholderAgenda, int position) {
        Agenda agenda = agendas.get( position );
        viewholderAgenda.data.setText( agenda.getData());
        viewholderAgenda.hora.setText( agenda.getHora() );
        viewholderAgenda.evento.setText( agenda.getEvento() );
        viewholderAgenda.local.setText( agenda.getLocal());
        viewholderAgenda.descricao.setText( agenda.getDescricao() );
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }
}
