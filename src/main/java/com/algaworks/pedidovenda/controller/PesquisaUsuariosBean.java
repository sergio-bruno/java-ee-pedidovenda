package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.utils.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	private UsuarioFilter filtro;
	private List<Usuario> usuariosFiltrados;
	
	private Usuario usuarioSelecionado;
	
	public PesquisaUsuariosBean() {
		filtro = new UsuarioFilter();
		usuariosFiltrados = new ArrayList<>();
	}
	
	public void excluir() {
		System.out.print("cheguei...");
		
		usuarios.remover(usuarioSelecionado);
		usuariosFiltrados.remove(usuarioSelecionado);
		
		
		System.out.print("apaguei...");
		
		
		FacesUtil.addInfoMessage("Usuário " + usuarioSelecionado.getNome() + " excluído com sucesso.");
	}
	
	public void pesquisar() {
		usuariosFiltrados = usuarios.filtrados(filtro);
	}
	
	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
}