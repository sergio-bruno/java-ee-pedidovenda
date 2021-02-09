package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Cargo;
import com.algaworks.pedidovenda.repository.filter.CargoFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.utils.jpa.Transactional;

public class Cargos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cargo guardar(Cargo cargo) {
		return manager.merge(cargo);
	}

	@Transactional
	public void remover(Cargo cargo) {
		try {
			cargo = porId(cargo.getId());
			manager.remove(cargo);
			
			// Para tratar a exceção caso o cargo tenha sido usado em algum pedido a chave estrangeira não 
			// deixa excluir com o flush eu testo antes do commit
			manager.flush(); 
		} catch (PersistenceException e) {
			throw new NegocioException("Cargo não pode ser excluído. Verifique se ele já foi usado em algum contato.");
		}
	}
	
	@SuppressWarnings("unchecked") // retorna uma lista prototipada
	public List<Cargo> filtrados(CargoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cargo.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			// O Restrictions.ilike é case insensitive, não faz diferença se é maiúsculo ou minúsculo
			// O MatchMode.ANYWHERE coloca o % antes e depois da palava = "%joao%"
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("descricao")).list();
	}

	public Cargo porDescricaoCargo(String descricao) {
		try {
			return manager.createQuery("from Cargo where upper(descricao) = :descricao", Cargo.class)
				.setParameter("descricao", descricao.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Cargo porId(Long id) {
		return manager.find(Cargo.class, id);
	}	
	
	public List<Cargo> porDescricao(String descricao) {
		return this.manager.createQuery("from Cargo " +
				"where upper(descricao) like :descricao", Cargo.class)
				.setParameter("descricao", "%" + descricao.toUpperCase() + "%")
				.getResultList();
	}
	
}