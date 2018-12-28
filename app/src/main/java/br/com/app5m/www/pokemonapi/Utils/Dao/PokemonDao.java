package br.com.app5m.www.pokemonapi.Utils.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonDao {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("height")
    private int height;

    @SerializedName("weight")
    private int weight;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("abilities")
    private List<AbilitiesItem> abilities;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<AbilitiesItem> getAbilities() {
        return abilities;
    }
}
