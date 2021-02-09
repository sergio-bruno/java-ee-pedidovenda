package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.utils.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroEnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Endereco endereco;
	private Endereco enderecoSelecionado;
	
	@Inject
	@ClienteEdicao
	private Cliente cliente;
	
	public CadastroEnderecoBean() {
		endereco = new Endereco();
		this.enderecoSelecionado = new Endereco();
	}
	
	@Inject
	private Event<ClienteAlteradoEvent> clienteAlteradoEvent;
	
	public void adicionarEndereco(){
		System.out.print("cheguei.....");
		
		System.out.print("\nantes end. selecionado.....:"+enderecoSelecionado.getComplemento());
		System.out.print("\nantes end. .....:"+endereco.getComplemento());
		
		
		
		this.endereco.setCliente(cliente);
		if (endereco.getId() == null) {
			System.out.print("\nInclusão");
			this.cliente.getEnderecos().add(endereco);
		} else {
			System.out.print("alteração");
		}	
		this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(cliente));

		System.out.print("\ndepois end. selecionado.....:"+this.enderecoSelecionado.getComplemento());
		System.out.print("\ndepois end. .....:"+this.endereco.getComplemento());
		
		
		
		this.enderecoSelecionado = new Endereco();
	}	
	
	public Endereco getEndereco() {
		return endereco;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
		this.endereco = enderecoSelecionado;
	}
	
	public void excluir() {
		this.cliente.getEnderecos().remove(this.endereco);
		this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(cliente));
		FacesUtil.addInfoMessage("Edereço do cliente " + enderecoSelecionado.getLogradouro() + " excluído com sucesso.");
	}
	
}