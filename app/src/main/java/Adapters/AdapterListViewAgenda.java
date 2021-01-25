package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;
import agendas.Agenda;
import br.com.cellsgroup.R;

public class AdapterListViewAgenda  extends RecyclerView.Adapter<AdapterListViewAgenda.ViewholderAgenda>{
    private final List<Agenda> agendas;
    private final Context context;

    public AdapterListViewAgenda(List<Agenda> agendas, Context context) {
        this.agendas = agendas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewholderAgenda onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.item_listview_agenda, viewGroup, false);

        return new ViewholderAgenda(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderAgenda viewholderAgenda, int position) {
        Agenda agenda = agendas.get( position );
        viewholderAgenda.bind (agenda);
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }

    public static class ViewholderAgenda extends  ViewHolder{
        TextView data;
        TextView hora;
        TextView  evento;
        TextView  local;
        TextView  descricao;

        public ViewholderAgenda(View view){
            super ( view );
            data = view.findViewById ( R.id.text_input_Data);
            hora = view.findViewById(  R.id.text_input_hora );
            evento = view.findViewById(  R.id.text_input_DataEvento );
            local = view.findViewById(  R.id.text_input_localAgenda);
            descricao = view.findViewById( R.id.text_input_descricaoAgenda);
        }

        public void bind(Agenda agenda){
            data.setText( agenda.getData());
            hora.setText( agenda.getHora() );
            evento.setText( agenda.getEvento() );
            local.setText( agenda.getLocal());
            descricao.setText( agenda.getDescricao() );
        }
    }
}
