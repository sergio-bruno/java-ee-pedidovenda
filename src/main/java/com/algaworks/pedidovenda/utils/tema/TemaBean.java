package com.algaworks.pedidovenda.utils.tema;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
//@SessionScoped
public class TemaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String temaSelecionado = "home";
	
	public List<String> getTemasDisponiveis() {
		return Arrays.asList( "south-street"  
							, "afterdark"
							, "afternoon"
							, "afterwork"
							, "aristo"
							, "black-tie"
							, "blitzer"
							, "bluesky"
							, "bootstrap"
							, "casablanca"
							, "cupertino"
							, "cruze"
							, "dark-hive"
							, "delta"
							, "dot-luv"
							, "eggplant"
							, "excite-bike"
							, "flick"
							, "glass-x"
							, "home"
							, "hot-sneaks"
							, "humanity"
							, "le-frog"
							, "midnight"
							, "mint-choc"
							, "overcast"
							, "pepper-grinder"
							, "redmond"
							, "rocket"
							, "sam"
							, "smoothness"
							, "south-street"
							, "start"
							, "sunny"
							, "swanky-purse"
							, "trontastic"
							, "ui-darkness"
							, "ui-lightness"
							, "vader");		
		
		
	}

	public String getTemaSelecionado() {
		return temaSelecionado;
	}

	public void setTemaSelecionado(String temaSelecionado) {
		this.temaSelecionado = temaSelecionado;
	}
	
}
