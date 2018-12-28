package br.com.app5m.www.pokemonapi.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.app5m.www.pokemonapi.R;
import br.com.app5m.www.pokemonapi.Utils.Dao.ResultsItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by willv on 26/10/2017.
 */

public class AdapTipo extends RecyclerView.Adapter<viewAdapTipo> {

    private List<ResultsItem> resultsItemList;
    private Utilidade utilidade;
    private AdapTiposCall tiposCall;
    private Context context;

    public AdapTipo(Context context, List<ResultsItem> resultsItemList, AdapTiposCall tiposCall) {
        this.context = context;
        this.resultsItemList = resultsItemList;
        this.utilidade = new Utilidade(context);
        this.tiposCall = tiposCall;
    }
    public interface AdapTiposCall{
        void abrirTipo(String url);
    }

    @Override
    public viewAdapTipo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type, parent, false);
        viewAdapTipo viewHolder = new viewAdapTipo(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final viewAdapTipo viewHolder, final int position) {
        final ResultsItem resultsItem = resultsItemList.get(position);
        viewHolder.texto.setText(utilidade.tratarNome(resultsItem.getName()));
        viewHolder.principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiposCall.abrirTipo(resultsItem.getUrl());
            }
        });



    }

    @Override
    public int getItemCount() {
        return resultsItemList.size();
    }
}

class viewAdapTipo extends ViewHolder {

    @BindView(R.id.principal)
    LinearLayout principal;
    @BindView(R.id.texto)
    TextView texto;

    viewAdapTipo(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}

