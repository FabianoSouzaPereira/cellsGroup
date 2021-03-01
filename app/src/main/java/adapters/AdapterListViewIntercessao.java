package adapters;

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

import br.com.cellsgroup.R;
import br.com.cellsgroup.models.intercessao.Intercessao;

@SuppressWarnings( "ALL" )
public class AdapterListViewIntercessao extends RecyclerView.Adapter<AdapterListViewIntercessao.ViewholderIntercessao>
    implements  View.OnClickListener ,View.OnLongClickListener{
    private List<Intercessao> intercessoes;
    private Context context;
    private View.OnClickListener clicklistener;
    private View.OnLongClickListener longClickListener;


    public AdapterListViewIntercessao(List<Intercessao> intercessoes) {
        this.intercessoes = intercessoes;
        this.context = context;

        setHasStableIds(false);

    }

    @NonNull
    @Override
    public ViewholderIntercessao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_listview_intercessao, parent, false);

        return new ViewholderIntercessao( view);
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


    /*  Classe interna Viewholder */
    public class ViewholderIntercessao  extends RecyclerView.ViewHolder {
        TextView uid;
        TextView nome;
        TextView motivo;
        TextView data;
        CardView cardView;
     //   OnIntercessaoListener onIntercessaoListener;

        public ViewholderIntercessao(@NonNull View view) {
            super( view );
            uid = (TextView) view.findViewById( R.id.tvUidIntercessao);
            nome = (TextView) view.findViewById( R.id.tvNomeIntercessao);
            motivo = (TextView) view.findViewById(R.id.etMotivo);
            data = (TextView) view.findViewById(R.id.tvdataIntercessao);
            cardView = view.findViewById( R.id.card_view_intercessao );
         //   this.onIntercessaoListener = onIntercessaoListener;
            //setado o mesmo listener para todos os elementos na view e a própria.
            view.setOnLongClickListener( longClickListener);
            uid.setOnLongClickListener( longClickListener );
            nome.setOnLongClickListener(longClickListener);
            motivo.setOnLongClickListener( longClickListener);
            data.setOnLongClickListener( longClickListener);
            cardView.setOnLongClickListener(longClickListener);
        }
//        @Override
//        public void onClick(View v) {
//            onIntercessaoListener.onIntercessaoClick( getAdapterPosition(), uid.getText().toString() );
//                    cardView.setCardBackgroundColor( R.color.colorPrimary );
//        }
    }
// Fim viewholder

//    public interface OnIntercessaoListener {
//        void onIntercessaoClick(int position, String key);
//    }
}
