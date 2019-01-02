package br.com.app5m.www.pokemonapi.Utils.Ws;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import br.com.app5m.www.pokemonapi.Utils.Dao.PokemonDao;
import br.com.app5m.www.pokemonapi.Utils.Dao.ResponseDao;
import br.com.app5m.www.pokemonapi.Utils.Utilidade;

public class PokemonsWS {

    private Context context;
    private Utilidade utilidade;
    private PokemonsCall pokemonsCall;

    public PokemonsWS(Context context, PokemonsCall pokemonsCall) {
        this.context = context;
        this.utilidade = new Utilidade(context);
        this.pokemonsCall = pokemonsCall;
    }

    public void tipos() {
        VolleyLog.DEBUG = true;
        final RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://pokeapi.co/api/v2/type/", null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ResponseDao teste = new Gson().fromJson(response.toString(), ResponseDao.class);
                pokemonsCall.getResult(teste, 0);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilidade.mostrarToast("Error: " + error.getMessage());


            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    public void pokemons(String URL) {
        utilidade.abrirDialog();
        VolleyLog.DEBUG = false;
        final RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL, null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                utilidade.fecharDialog();
                ResponseDao teste = new Gson().fromJson(response.toString(), ResponseDao.class);
                pokemonsCall.getResult(teste, 1);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilidade.fecharDialog();
                utilidade.mostrarToast("Error: " + error.getMessage());


            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    public void detalhes(String URL) {

        utilidade.abrirDialog();
        VolleyLog.DEBUG = false;
        final RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL, null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                utilidade.fecharDialog();
                PokemonDao teste = new Gson().fromJson(response.toString(), PokemonDao.class);
                pokemonsCall.getDetalhes(teste);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilidade.fecharDialog();
                utilidade.mostrarToast("Error: " + error.getMessage());


            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    public interface PokemonsCall {
        public void getResult(ResponseDao responseDao, int categoria);

        public void getDetalhes(PokemonDao pokemonDao);
    }

}
