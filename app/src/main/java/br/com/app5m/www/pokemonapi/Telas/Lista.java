package br.com.app5m.www.pokemonapi.Telas;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import br.com.app5m.www.pokemonapi.R;
import br.com.app5m.www.pokemonapi.Utils.AdapHabilidades;
import br.com.app5m.www.pokemonapi.Utils.AdapPokemon;
import br.com.app5m.www.pokemonapi.Utils.AdapTipo;
import br.com.app5m.www.pokemonapi.Utils.Dao.PokemonDao;
import br.com.app5m.www.pokemonapi.Utils.Dao.PokemonItem;
import br.com.app5m.www.pokemonapi.Utils.Dao.ResponseDao;
import br.com.app5m.www.pokemonapi.Utils.Utilidade;
import br.com.app5m.www.pokemonapi.Utils.Ws.PokemonsWS;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Lista extends AppCompatActivity implements PokemonsWS.PokemonsCall, AdapTipo.AdapTiposCall, AdapPokemon.AdapPokemonCall {

    @BindView(R.id.lista)
    RecyclerView lista;
    @BindView(R.id.txtMensagem)
    TextView txtMensagem;
    @BindView(R.id.btnFooter)
    Button btnFooter;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.linEsconder)
    LinearLayout linEsconder;

    private AlertDialog dialogFiltro, dialogDetalhes;
    private List<PokemonItem> pokemonItems;
    private ResponseDao responseDaoTipos;
    private TextView txtMensagemFiltro;
    private RecyclerView listaFiltro;
    private Button btnFooterFiltro;
    private PokemonsWS pokemonsWS;
    private Utilidade utilidade;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        ButterKnife.bind(this);
        context = this;
        utilidade = new Utilidade(context);
        pokemonsWS = new PokemonsWS(context, this);
        new getTipos().execute();

        ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView txtTitle = viewActionBar.findViewById(R.id.txtTitle);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(false);
        abar.setHomeButtonEnabled(false);
        txtTitle.setText("Lista de Pokémon's ");

        btnFooter.setVisibility(View.VISIBLE);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFiltro();
            }
        });

    }

    private void abrirFiltro() {
        if (responseDaoTipos == null){
            utilidade.mostrarToast("Tipos de Pokémon's ainda está carregando, aguarde...");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogFiltro = builder.create();
        dialogFiltro.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.dialog_lista, null);
        dialogFiltro.setView(dialogLayout);
        dialogFiltro.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFiltro.requestWindowFeature(Window.FEATURE_NO_TITLE);

        utilidade.abrirDialog(dialogFiltro);

        btnFooterFiltro = dialogLayout.findViewById(R.id.btnFooter);
        listaFiltro = dialogLayout.findViewById(R.id.lista);
        txtMensagemFiltro = dialogLayout.findViewById(R.id.txtMensagem);
        ImageButton btnClose = dialogLayout.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilidade.fecharDialog(dialogFiltro);
            }
        });

        btnFooterFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linEsconder.setVisibility(View.VISIBLE);
                lista.setVisibility(View.GONE);
                utilidade.fecharDialog(dialogFiltro);
            }
        });


        if (utilidade.tratarLista(txtMensagemFiltro, listaFiltro, responseDaoTipos.getResults())) {
            AdapTipo adapTipo = new AdapTipo(context, responseDaoTipos.getResults(), Lista.this);
            listaFiltro.setLayoutManager(new LinearLayoutManager(context));
            listaFiltro.setItemAnimator(new DefaultItemAnimator());
            listaFiltro.setAdapter(adapTipo);
        }

    }

    private void abrirDetalhes(@NonNull final PokemonDao pokemonDao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogDetalhes = builder.create();
        dialogDetalhes.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.dialog_pokemon, null);
        dialogDetalhes.setView(dialogLayout);
        dialogDetalhes.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogDetalhes.requestWindowFeature(Window.FEATURE_NO_TITLE);

        utilidade.abrirDialog(dialogDetalhes);

        Button btnFooter = dialogLayout.findViewById(R.id.btnFooter);
        RecyclerView habilidades = dialogLayout.findViewById(R.id.habilidades);
        LinearLayout linHabilidades = dialogLayout.findViewById(R.id.linHabilidades);
        TextView txtTitulo = dialogLayout.findViewById(R.id.txtTitulo);
        TextView altura = dialogLayout.findViewById(R.id.altura);
        TextView peso = dialogLayout.findViewById(R.id.peso);
        ImageButton btnClose = dialogLayout.findViewById(R.id.btnClose);
        ImageView img = dialogLayout.findViewById(R.id.img);

        DecimalFormat formato = new DecimalFormat("#.##");

        final String peso_str = formato.format(pokemonDao.getWeight() * 0.1) + " kilos";
        final String altura_Str = formato.format(pokemonDao.getHeight() * 0.1) + " metros";

        peso.setText(peso_str);
        altura.setText(altura_Str);
        txtTitulo.setText(utilidade.tratarNome(pokemonDao.getName()));
        Picasso.get().load(pokemonDao.getSprites().getFront_default()).into(img);

        AdapHabilidades adapTipo = new AdapHabilidades(context, pokemonDao.getAbilities());
        habilidades.setLayoutManager(new LinearLayoutManager(context));
        habilidades.setItemAnimator(new DefaultItemAnimator());
        habilidades.setAdapter(adapTipo);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilidade.fecharDialog(dialogDetalhes);
            }
        });

        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Nome: " + utilidade.tratarNome(pokemonDao.getName()) + "\n"
                + "Peso: " + peso_str + "\n" + "Altura: " + altura_Str);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Pokémon " + utilidade.tratarNome(pokemonDao.getName()));
                startActivity(Intent.createChooser(shareIntent, "Compartilhar Pokémon"));
            }
        });


    }

    @Override
    public void getResult(ResponseDao responseDao, int categoria) {
        switch (categoria) {
            case 0:
                //tipos
                this.responseDaoTipos = responseDao;
                break;

            case 1:
                //pokemons
                utilidade.fecharDialog(dialogFiltro);
                this.pokemonItems = responseDao.getPokemon();
                getPokemons();

                break;

        }
    }

    @Override
    public void getDetalhes(PokemonDao pokemonDao) {
        abrirDetalhes(pokemonDao);
    }


    private void getPokemons() {
        if (utilidade.tratarLista(linEsconder, lista, pokemonItems)) {
            AdapPokemon adapPokemon = new AdapPokemon(context, pokemonItems, this);
            lista.setLayoutManager(new LinearLayoutManager(context));
            lista.setItemAnimator(new DefaultItemAnimator());
            lista.setAdapter(adapPokemon);
        }
    }

    @Override
    public void abrirTipo(String url) {
        pokemonsWS.pokemons(url);
    }

    @Override
    public void abrirPokemon(String url) {
        pokemonsWS.detalhes(url);
    }

    private class getTipos extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            pokemonsWS.tipos();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }


}
