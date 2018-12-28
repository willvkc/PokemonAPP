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
import br.com.app5m.www.pokemonapi.Utils.Dao.PokemonDao;
import br.com.app5m.www.pokemonapi.Utils.Dao.PokemonItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by willv on 26/10/2017.
 */

public class AdapPokemon extends RecyclerView.Adapter<viewAdapPokemon> {

    private List<PokemonItem> pokemonItems;
    private Utilidade utilidade;
    private AdapPokemonCall adapPokemonCall;
    private Context context;

    public AdapPokemon(Context context, List<PokemonItem> pokemonItems, AdapPokemonCall adapPokemonCall) {
        this.context = context;
        this.pokemonItems = pokemonItems;
        this.utilidade = new Utilidade(context);
        this.adapPokemonCall = adapPokemonCall;
    }
    
    public interface AdapPokemonCall{
        void abrirPokemon(String url);
    }
    @Override
    public viewAdapPokemon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);
        viewAdapPokemon viewHolder = new viewAdapPokemon(view);
        return viewHolder; 
    }

    @Override
    public void onBindViewHolder(final viewAdapPokemon viewHolder, final int position) {
        final PokemonDao resultsItem = pokemonItems.get(position).getPokemonDao();
        viewHolder.texto.setText(utilidade.tratarNome(resultsItem.getName()));
        viewHolder.principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapPokemonCall.abrirPokemon(resultsItem.getUrl());
            }
        });


    }

    @Override
    public int getItemCount() {
        return pokemonItems.size();
    }
}

class viewAdapPokemon extends ViewHolder {

    @BindView(R.id.principal)
    LinearLayout principal;
    @BindView(R.id.texto)
    TextView texto;

    viewAdapPokemon(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}

