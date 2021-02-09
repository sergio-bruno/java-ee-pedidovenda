package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.utils.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroClienteService cadastroClienteService;
	
	@Produces
	@ClienteEdicao
	private Cliente cliente;
	
	public CadastroClienteBean() {
		limpar();
	}
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			// Para carregar os endereços quando for uma edição
		}
	}
	
	public void clienteAlterado(@Observes ClienteAlteradoEvent event) {
		this.cliente = event.getCliente();
	}
	
	
	private void limpar() {
		cliente = new Cliente();
	}
	
	public void salvar() {
		this.cliente = cadastroClienteService.salvar(this.cliente);
		limpar();
		
		FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
	}

	public Cliente getCliente() {
		return cliente;
	}

	//Para carregar os clientes quando for edição
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	// Para interagir com a página CadastroCliente.xhtml e saber se é edição ou inclusão
	public boolean isEditando() {
		return this.cliente.getId() != null;
	}
}