package adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.cellsgroup.R;
import br.com.cellsgroup.models.pessoas.Leader;



public class AdapterListViewLeader extends RecyclerView.Adapter< AdapterListViewLeader.ViewholderLeader>
    implements  View.OnClickListener ,View.OnLongClickListener{
    private final List<Leader> leaders;
    private View.OnClickListener clicklistener;
    private View.OnLongClickListener longClickListener;

    public AdapterListViewLeader (List<Leader> leaders) {
        this.leaders= leaders;

    }

    @NonNull
    @Override
    public ViewholderLeader onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_listview_leader ,  parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewholderLeader( view );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewholderLeader viewholder, final int position) {
        Leader usuario = leaders.get( position );

        viewholder.nome.setText( usuario.getNome());
        viewholder.uid.setText( usuario.getUid() );

    }

    public void setOnClickListener(View.OnClickListener clicklistener){
        this.clicklistener = clicklistener;
    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    @Override
    public void onClick(View v) {
        if(clicklistener != null){
            clicklistener.onClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(longClickListener != null){
            longClickListener.onLongClick(v);
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return leaders.size();
    }


    /*  Classe interna Viewholder */
    public class ViewholderLeader  extends RecyclerView.ViewHolder{
        TextView uid;
        TextView nome;

        public ViewholderLeader(@NonNull View view) {
            super( view );
            uid = view.findViewById( R.id.uidLeader);
            nome = view.findViewById( R.id.nomeLeader);

        }
    }
// Fim viewholder

}
