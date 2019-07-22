package Adapters;

/*
*  Criado classe interna para o Viewholder
*  Criado interface para OnClikListener
*  Colocado no construtor viewholderIgreja mais uma variável (OnIgrejaListener onIgrejaListener)
*  pois ela não seria reconhecida na Activity. Daria erro de 'not an enclosing class'; pois não é
*  uma classe interna da Activity;
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import br.com.ieqcelulas.R;


public class AdapterListViewIgreja extends RecyclerView.Adapter<AdapterListViewIgreja.ViewholderIgreja> {
    private List<String> igrejas;
    private Context context;
    private OnIgrejaListener mOnIgrejaListener;

    public AdapterListViewIgreja(List<String> igrejas, Context context, OnIgrejaListener onIgrejaListener) {
        this.igrejas = igrejas;
        this.context = context;
        this.mOnIgrejaListener = onIgrejaListener;
    }

    @NonNull
    @Override
    public ViewholderIgreja onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_listview_igreja, parent, false);

      //  ViewholderIgreja holder = new ViewholderIgreja( view, mOnIgrejaListener );

        return new ViewholderIgreja( view, mOnIgrejaListener );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewholderIgreja viewholderIgreja, final int position) {
        String igreja = igrejas.get( position );
        viewholderIgreja.nome.setText( igreja );
    }

    @Override
    public int getItemCount() {
        return igrejas.size();
    }
    
    /* Classe interna Viewholder  */
    public class ViewholderIgreja  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nome;
        OnIgrejaListener onIgrejaListener;

        public ViewholderIgreja(@NonNull View view, OnIgrejaListener onIgrejaListener) {
            super( view );
            nome = view.findViewById( R.id.tvNomeIgreja);
            this.onIgrejaListener = onIgrejaListener;
            view.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            onIgrejaListener.onIgrejaClick( getAdapterPosition() );
        }
    }


    public interface OnIgrejaListener{
        void onIgrejaClick(int position);
    }
}
