package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.cellsgroup.Intercessao;
import br.com.cellsgroup.R;

@SuppressWarnings( "ALL" )
public class AdapterListViewIntercessao extends RecyclerView.Adapter<AdapterListViewIntercessao.ViewholderIntercessao> {
    private List<Intercessao> intercessoes;
    private Context context;
    private OnIntercessaoListener mOnIntercessaoListener;

    public AdapterListViewIntercessao(List<Intercessao> intercessoes,Context context, OnIntercessaoListener onIntercessaoListener) {
        this.intercessoes = intercessoes;
        this.context = context;
        this.mOnIntercessaoListener = onIntercessaoListener;

    }

    @NonNull
    @Override
    public ViewholderIntercessao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_listview_intercessao, parent, false);

        return new ViewholderIntercessao( view, mOnIntercessaoListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewholderIntercessao viewholder, final int position) {
        Intercessao intercessao = intercessoes.get( position );

        viewholder.nome.setText( intercessao.getNome());
        viewholder.motivo.setText(intercessao.getMotivo());
        viewholder.data.setText(intercessao.getData());
        viewholder.uid.setText( intercessao.getUid() );

    }

    @Override
    public int getItemCount() {
        return intercessoes.size();
    }


    /*  Classe interna Viewholder */
    public class ViewholderIntercessao  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView uid;
        TextView nome;
        TextView motivo;
        TextView data;
        CardView cardView;
        OnIntercessaoListener onIntercessaoListener;

        public ViewholderIntercessao(@NonNull View view, OnIntercessaoListener onIntercessaoListener) {
            super( view );
            uid = (TextView) view.findViewById( R.id.tvUidIntercessao);
            nome = (TextView) view.findViewById( R.id.tvNomeIntercessao);
            motivo = (TextView) view.findViewById(R.id.etMotivo);
            data = (TextView) view.findViewById(R.id.tvdataIntercessao);
            cardView = view.findViewById( R.id.card_view_intercessao );
            this.onIntercessaoListener = onIntercessaoListener;
            //setado o mesmo listener para todos os elementos na view e a própria.
            view.setOnClickListener( this );
            uid.setOnClickListener( this );
            nome.setOnClickListener( this );
            motivo.setOnClickListener( this );
            data.setOnClickListener( this );
            cardView.setOnClickListener( this );
        }
        @Override
        public void onClick(View v) {
            onIntercessaoListener.onIntercessaoClick( getAdapterPosition(), uid.getText().toString() );
                    cardView.setCardBackgroundColor( R.color.colorPrimary );
        }
    }
// Fim viewholder

    public interface OnIntercessaoListener {
        void onIntercessaoClick(int position, String key);
    }
}
