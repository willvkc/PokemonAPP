package br.com.app5m.www.pokemonapi.Utils.Dao;

import com.google.gson.annotations.SerializedName;

public class Sprites {
    @SerializedName("front_default")
    private String front_default;

    public String getFront_default() {
        return front_default;
    }
}
