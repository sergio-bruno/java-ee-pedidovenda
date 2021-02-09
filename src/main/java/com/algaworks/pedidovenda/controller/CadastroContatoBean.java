package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cargo;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Contato;
import com.algaworks.pedidovenda.repository.Cargos;
import com.algaworks.pedidovenda.utils.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroContatoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Contato contato;
	private Contato contatoSelecionado;
	
	@Inject
	private Cargos cargos;
	
	@Inject
	@ClienteEdicao
	private Cliente cliente;
	
	
	public CadastroContatoBean() {
		contato = new Contato();
		this.contatoSelecionado = new Contato();
	}
	
	@Inject
	private Event<ClienteAlteradoEvent> clienteAlteradoEvent;
	
	public void adicionarContato(){
		this.contato.setCliente(cliente);
		if (contato.getId() == null) {
			System.out.print("Inclusão");
			this.cliente.getContatos().add(contato);
		} else {
			System.out.print("alteração");
		}	
		this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(cliente));
		
		this.contatoSelecionado = new Contato();
	}	

	public List<Cargo> completarCargos(String descricao) {
		return this.cargos.porDescricao(descricao);
	}
	
	public Contato getContato() {
		return contato;
	}

	public Contato getContatoSelecionado() {
		return contatoSelecionado;
	}

	public void setContatoSelecionado(Contato contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
		this.contato = contatoSelecionado;
	}
	
	public void excluir() {
		this.cliente.getContatos().remove(this.contato);
		this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(cliente));
		FacesUtil.addInfoMessage("Edereço do cliente " + contatoSelecionado.getNome() + " excluído com sucesso.");
	}

}