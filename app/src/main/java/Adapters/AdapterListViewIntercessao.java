package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import br.com.ieqcelulas.Intercessao;
import br.com.ieqcelulas.R;

@SuppressWarnings( "ALL" )
public class AdapterListViewIntercessao extends RecyclerView.Adapter<ViewholderIntercessao> {
    private List<Intercessao> intercessoes;
    private Context context;

    private static int lastCheckedPos = 0;

    public AdapterListViewIntercessao(List<Intercessao> intercessoes,Context context) {
        this.intercessoes = intercessoes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewholderIntercessao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_intercessao, parent, false);

        ViewholderIntercessao holder = new ViewholderIntercessao(view);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewholderIntercessao viewholder, final int position) {
        Intercessao intercessao = intercessoes.get( position );

        viewholder.nome.setText( intercessao.getNome());
        viewholder.motivo.setText(intercessao.getMotivo());
        viewholder.data.setText(intercessao.getData());

    }

    @Override
    public int getItemCount() {
        return intercessoes.size();
    }

}
