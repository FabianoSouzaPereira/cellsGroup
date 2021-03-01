package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import br.com.cellsgroup.R;
import br.com.cellsgroup.models.agendas.Agenda;

public class AdapterListViewAgenda  extends RecyclerView.Adapter<AdapterListViewAgenda.ViewholderAgenda>{
    private final List<Agenda> agendas;

    public AdapterListViewAgenda(List<Agenda> agendas, Context context) {
        this.agendas = agendas;
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
        Spinner hora;
        Spinner minuto;
        TextView  evento;
        TextView  local;
        TextView  descricao;

        public ViewholderAgenda(View view){
            super ( view );
            data = view.findViewById ( R.id.tvDataAgenda);
            hora = view.findViewById(  R.id.spinnerhoraAg);
            minuto = view.findViewById ( R.id.spinnerminAg );
            evento = view.findViewById(  R.id.tveventoAgenda);
            local = view.findViewById(  R.id.tvlocalAgenda);
            descricao = view.findViewById( R.id.tvdescricaoAgenda);
        }

        public void bind(Agenda agenda){
            String hi = agenda.getHora ();
            String[] ho = hi.split (":");
            int h = Integer.parseInt ( ho[0] );
            int min = Integer.parseInt ( ho[1] );
            hora.setSelection (h);
            minuto.setSelection (min);

            data.setText( agenda.getData());

            evento.setText( agenda.getEvento() );
            local.setText( agenda.getLocal());
            descricao.setText( agenda.getDescricao() );
        }
    }
}
