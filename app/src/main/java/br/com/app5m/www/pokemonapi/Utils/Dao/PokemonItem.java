package br.com.app5m.www.pokemonapi.Utils.Dao;

import com.google.gson.annotations.SerializedName;

public class PokemonItem {

    @SerializedName("pokemon")
    private PokemonDao pokemonDao;

    public PokemonDao getPokemonDao() {
        return pokemonDao;
    }

}
