package br.com.app5m.www.pokemonapi.Utils.Dao;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("url")
	private String url;

	public String getName(){
		return name;
	}

	public String getUrl(){
		return url;
	}
}