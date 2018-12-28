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
import br.com.app5m.www.pokemonapi.Utils.Webservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        Webservice service = utilidade.getRetrofit(false).create(Webservice.class);
        Call<ResponseDao> param = service.listar_tipos();
        param.enqueue(new Callback<ResponseDao>() {
            @Override
            public void onResponse(Call<ResponseDao> call, final Response<ResponseDao> response) {
                pokemonsCall.getResult(response.body(), 0);
            }

            @Override
            public void onFailure(Call<ResponseDao> call, Throwable t) {
                utilidade.mostrarToast("Error: " + t.getMessage());

            }
        });
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
