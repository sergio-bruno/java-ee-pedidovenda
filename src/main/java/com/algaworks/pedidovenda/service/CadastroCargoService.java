package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Cargo;
import com.algaworks.pedidovenda.repository.Cargos;
import com.algaworks.pedidovenda.utils.jpa.Transactional;

public class CadastroCargoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Cargos cargos;
	
	@Transactional
	public Cargo salvar(Cargo cargo) {
		Cargo cargoExistente = cargos.porDescricaoCargo(cargo.getDescricao());
		
		if (cargoExistente != null && !cargoExistente.equals(cargo)) {
			throw new NegocioException("Já existe um cargo com a descrição informada.");
		}
		
		return cargos.guardar(cargo);
	}
	
}