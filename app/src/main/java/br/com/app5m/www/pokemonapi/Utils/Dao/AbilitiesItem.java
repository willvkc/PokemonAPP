package br.com.app5m.www.pokemonapi.Utils.Dao;

import com.google.gson.annotations.SerializedName;

public class AbilitiesItem {

    @SerializedName("ability")
    ResultsItem ability;

    public ResultsItem getAbility() {
        return ability;
    }
}
