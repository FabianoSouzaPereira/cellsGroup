package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.cellsgroup.models.Intercessao;
import br.com.cellsgroup.R;
import br.com.cellsgroup.models.pessoas.User;


public class AdapterListViewUsuario extends RecyclerView.Adapter<AdapterListViewUsuario.ViewholderUsuario>
    implements  View.OnClickListener ,View.OnLongClickListener{
    private final List<User> usuarios;
    private Context context;
    private View.OnClickListener clicklistener;
    private View.OnLongClickListener longClickListener;

    public AdapterListViewUsuario(List<User> usuarios) {
        this.usuarios= usuarios;

    }

    @NonNull
    @Override
    public ViewholderUsuario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_listview_usuario,  parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewholderUsuario( view );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewholderUsuario viewholder, final int position) {
        User usuario = usuarios.get( position );

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
        return usuarios.size();
    }


    /*  Classe interna Viewholder */
    public class ViewholderUsuario  extends RecyclerView.ViewHolder{
        TextView uid;
        TextView nome;

        public ViewholderUsuario(@NonNull View view) {
            super( view );
            uid = (TextView) view.findViewById( R.id.uidUsuario);
            nome = (TextView) view.findViewById( R.id.nomeUsuario);

        }
    }
// Fim viewholder

}
