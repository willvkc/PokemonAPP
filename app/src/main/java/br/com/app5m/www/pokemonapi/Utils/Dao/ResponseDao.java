package br.com.app5m.www.pokemonapi.Utils.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDao {

	@SerializedName("next")
	private Object next;

	@SerializedName("previous")
	private Object previous;

	@SerializedName("count")
	private int count;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("pokemon")
	private List<PokemonItem> pokemon;

	public Object getNext(){
		return next;
	}

	public Object getPrevious(){
		return previous;
	}

	public int getCount(){
		return count;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public List<PokemonItem> getPokemon() {
		return pokemon;
	}
}