package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.utils.jsf.FacesUtil;
import com.algaworks.pedidovenda.repository.Grupos;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	private Usuario usuario;
	private Grupo grupoSelecionado;
	private List<Grupo> gruposUsuario;
	
	public CadastroUsuarioBean() {
		limpar();
	}
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			//Para carregar os grupos de acesso do usuário no combo
			gruposUsuario = grupos.listaGrupos();
			grupoSelecionado = new Grupo();
		}
	}
	
	private void limpar() {
		usuario = new Usuario();
		gruposUsuario = new ArrayList<Grupo>();
		grupoSelecionado = new Grupo();
	}
	
	public void excluirGrupo() {
		this.usuario.getGrupos().remove(grupoSelecionado);
		FacesUtil.addInfoMessage("Grupo de acesso do Usuário " + grupoSelecionado.getDescricao() + " excluído com sucesso.");
	}

	public void salvar() {
		this.usuario = cadastroUsuarioService.salvar(this.usuario);

		limpar();
		FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
	}

	public void adicionarGrupo(){
		this.usuario.getGrupos().add(grupoSelecionado);
	}	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getGruposUsuario() {
		return gruposUsuario;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	// Para interagir com a página CadastroProduto.xhtml e saber se é edição ou inclusão
	public boolean isEditando() {
		return this.usuario.getId() != null;
	}
	
}