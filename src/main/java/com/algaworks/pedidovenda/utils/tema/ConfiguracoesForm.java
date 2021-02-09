package com.algaworks.pedidovenda.utils.tema;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="configuracoesForm")
public class ConfiguracoesForm {

    private String meuTema;
    private Map<String, String> themes = new HashMap<String, String>();

    @PostConstruct
    public void init() {
        themes = new HashMap<String, String>();  
        themes.put("South Street", "south-street");  
		themes.put("Afterdark", "afterdark");
		themes.put("Afternoon", "afternoon");
		themes.put("Afterwork", "afterwork");
		themes.put("Aristo", "aristo");
		themes.put("Black-Tie", "black-tie");
		themes.put("Blitzer", "blitzer");
		themes.put("Bluesky", "bluesky");
		themes.put("Bootstrap", "bootstrap");
		themes.put("Casablanca", "casablanca");
		themes.put("Cupertino", "cupertino");
		themes.put("Cruze", "cruze");
		themes.put("Dark-Hive", "dark-hive");
		themes.put("Delta", "delta");
		themes.put("Dot-Luv", "dot-luv");
		themes.put("Eggplant", "eggplant");
		themes.put("Excite-Bike", "excite-bike");
		themes.put("Flick", "flick");
		themes.put("Glass-X", "glass-x");
		themes.put("Home", "home");
		themes.put("Hot-Sneaks", "hot-sneaks");
		themes.put("Humanity", "humanity");
		themes.put("Le-Frog", "le-frog");
		themes.put("Midnight", "midnight");
		themes.put("Mint-Choc", "mint-choc");
		themes.put("Overcast", "overcast");
		themes.put("Pepper-Grinder", "pepper-grinder");
		themes.put("Redmond", "redmond");
		themes.put("Rocket", "rocket");
		themes.put("Sam", "sam");
		themes.put("Smoothness", "smoothness");
		themes.put("South-Street", "south-street");
		themes.put("Start", "start");
		themes.put("Sunny", "sunny");
		themes.put("Swanky-Purse", "swanky-purse");
		themes.put("Trontastic", "trontastic");
		themes.put("UI-Darkness", "ui-darkness");
		themes.put("UI-Lightness", "ui-lightness");
		themes.put("Vader", "vader");

		//aqui voce pode retornar seu tema atual do banco
    }

    /*
	public void salvarTema() {  
		//aqui voce pode salvar seu tema no banco
		//this.meuTema = "redmond";
		System.out.print("Salvar this.Meutema: "+this.meuTema);
		System.out.print("Salvar Meutema: "+meuTema);
    }
    */

	public String getMeuTema() {
        if(meuTema == null) meuTema = "south-street";
		return meuTema;
    }

    public void setMeuTema(String meuTema) {
        this.meuTema = meuTema;
    }

	public Map<String, String> getThemes() {
        return themes;
    }

}
