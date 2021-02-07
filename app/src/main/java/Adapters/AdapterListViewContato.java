package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.cellsgroup.R;
import br.com.cellsgroup.models.pessoas.Leader;

@SuppressWarnings( "ALL" )
public class AdapterListViewContato extends RecyclerView.Adapter<AdapterListViewContato.ViewholderContato> {
    private List< Leader > leaders;
    private Context context;
    private OnContatoListener mOnContatoListener;

    public AdapterListViewContato(List< Leader > leaders,Context context, OnContatoListener onContatoListener) {
        this.leaders = leaders;
        this.context = context;
        this.mOnContatoListener = onContatoListener;

    }

    @NonNull
    @Override
    public ViewholderContato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_listview_contato, parent, false);

        return new ViewholderContato( view, mOnContatoListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewholderContato viewholder, final int position) {
        Leader leader = leaders.get( position );

        viewholder.celula.setText( leader.getCelula ());
        viewholder.nome.setText( leader.getNome());
        viewholder.email.setText(leader.getEmail ());
        viewholder.telefone.setText(leader.getTelefone());
    }

    @Override
    public int getItemCount() {
        return leaders.size();
    }


    /*  Classe interna Viewholder */
    public class ViewholderContato  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView celula;
        TextView nome;
        TextView email;
        TextView telefone;
        OnContatoListener onContatoListener;

        public ViewholderContato(@NonNull View view, OnContatoListener onContatoListener) {
            super( view );
            celula = (TextView) view.findViewById( R.id.txtNome_Celula);
            nome = (TextView) view.findViewById( R.id.txtNome_contato);
            email = (TextView) view.findViewById(R.id.txtEmail_contato);
            telefone  = (TextView) view.findViewById(R.id.txtTelefone_contato);

            this.onContatoListener = onContatoListener;

        }
        @Override
        public void onClick(View v) {

        }
    }
// Fim viewholder

    public interface OnContatoListener {
        void onContatoClick(int position, String key);
    }
}
