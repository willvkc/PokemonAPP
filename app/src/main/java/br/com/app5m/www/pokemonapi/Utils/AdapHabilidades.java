package br.com.app5m.www.pokemonapi.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.app5m.www.pokemonapi.R;
import br.com.app5m.www.pokemonapi.Utils.Dao.AbilitiesItem;
import br.com.app5m.www.pokemonapi.Utils.Dao.ResultsItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by willv on 26/10/2017.
 */

public class AdapHabilidades extends RecyclerView.Adapter<viewAdapHabilidades> {

    private List<AbilitiesItem> resultsItemList;
    private Utilidade utilidade;
    private Context context;

    public AdapHabilidades(Context context, List<AbilitiesItem> resultsItemList) {
        this.context = context;
        this.resultsItemList = resultsItemList;
        this.utilidade = new Utilidade(context);

    }

    @Override
    public viewAdapHabilidades onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habilidade, parent, false);
        viewAdapHabilidades viewHolder = new viewAdapHabilidades(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final viewAdapHabilidades viewHolder, final int position) {
        final ResultsItem resultsItem = resultsItemList.get(position).getAbility();
        viewHolder.texto.setText(utilidade.tratarNome(resultsItem.getName()));
    }

    @Override
    public int getItemCount() {
        return resultsItemList.size();
    }
}

class viewAdapHabilidades extends ViewHolder {

    @BindView(R.id.texto)
    TextView texto;

    viewAdapHabilidades(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}

