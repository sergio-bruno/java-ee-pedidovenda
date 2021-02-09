package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cargo;
import com.algaworks.pedidovenda.service.CadastroCargoService;
import com.algaworks.pedidovenda.utils.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCargoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroCargoService cadastroCargoService;
	
	private Cargo cargo;
	
	public CadastroCargoBean() {
		limpar();
	}
	
	public void inicializar() {
	}
	
	private void limpar() {
		cargo = new Cargo();
	}
	
	public void salvar() {
		this.cargo = cadastroCargoService.salvar(this.cargo);
		limpar();
		
		FacesUtil.addInfoMessage("Cargo salvo com sucesso!");
	}

	public Cargo getCargo() {
		return cargo;
	}
	
	//Para carregar os cargos quando for edição
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	// Para interagir com a página CadastroCargo.xhtml e saber se é edição ou inclusão
	public boolean isEditando() {
		return this.cargo.getId() != null;
	}

}