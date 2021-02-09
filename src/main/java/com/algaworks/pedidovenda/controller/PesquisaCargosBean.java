package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cargo;
import com.algaworks.pedidovenda.repository.Cargos;
import com.algaworks.pedidovenda.repository.filter.CargoFilter;
import com.algaworks.pedidovenda.utils.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaCargosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Cargos cargos;
	
	private CargoFilter filtro;
	private List<Cargo> cargosFiltrados;
	
	private Cargo cargoSelecionado;
	
	public PesquisaCargosBean() {
		filtro = new CargoFilter();
		cargosFiltrados = new ArrayList<>();
	}
	
	public void excluir() {
		cargos.remover(cargoSelecionado);
		cargosFiltrados.remove(cargoSelecionado);
		
		FacesUtil.addInfoMessage("Cargo " + cargoSelecionado.getDescricao() + " excluído com sucesso.");
	}
	
	public void pesquisar() {
		cargosFiltrados = cargos.filtrados(filtro);
	}
	
	public List<Cargo> getCargosFiltrados() {
		return cargosFiltrados;
	}

	public CargoFilter getFiltro() {
		return filtro;
	}

	public Cargo getCargoSelecionado() {
		return cargoSelecionado;
	}

	public void setCargoSelecionado(Cargo cargoSelecionado) {
		this.cargoSelecionado = cargoSelecionado;
	}
	
}