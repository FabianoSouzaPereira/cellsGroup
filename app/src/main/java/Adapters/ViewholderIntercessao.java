package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.ieqcelulas.R;

@SuppressWarnings( "ALL" )
public class ViewholderIntercessao extends RecyclerView.ViewHolder {
    public TextView nome;
    public TextView motivo;
    public TextView data;
    public CardView cardView;


    public ViewholderIntercessao(@NonNull View view) {
        super( view );
        nome = (TextView) view.findViewById( R.id.tvNomeIntercessao);
        motivo = (TextView) view.findViewById(R.id.etMotivo);
        data = (TextView) view.findViewById(R.id.tvdataIntercessao);
        cardView = view.findViewById( R.id.card_view_intercessao );
    }
}
