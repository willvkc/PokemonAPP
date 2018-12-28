package br.com.app5m.www.pokemonapi.Utils;

import br.com.app5m.www.pokemonapi.Utils.Dao.ResponseDao;
import retrofit2.Call;
import retrofit2.http.POST;


public interface Webservice {

    public static final String URL = "https://pokeapi.co/api/v2/";


    @POST("type")
    Call<ResponseDao> listar_tipos();

    @POST("")
    Call<ResponseDao> listar_pokemons();
}
