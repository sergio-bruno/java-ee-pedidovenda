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

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.utils.jpa.Transactional;

public class Clientes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cliente guardar(Cliente cliente) {
		return manager.merge(cliente);
	}

	@Transactional
	public void remover(Cliente cliente) {
		try {
			cliente = porId(cliente.getId());
			manager.remove(cliente);
			
			// Para tratar a exceção caso o cliente tenha sido usado em algum pedido a chave estrangeira não 
			// deixa excluir com o flush eu testo antes do commit
			manager.flush(); 
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído. Verifique se ele já foi usado em algum pedido.");
		}
	}
	
	public Cliente porDocumentoReceitaFederal(String documentoReceitaFederal) {
		try {
			return manager.createQuery("from Cliente where upper(doc_receita_federal) = :doc_receita_federal", Cliente.class)
				.setParameter("doc_receita_federal", documentoReceitaFederal.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked") // retorna uma lista prototipada
	public List<Cliente> filtrados(ClienteFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);
		
		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			criteria.add(Restrictions.eq("doc_receita_federal", filtro.getDocumentoReceitaFederal()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			// O Restrictions.ilike é case insensitive, não faz diferença se é maiúsculo ou minúsculo
			// O MatchMode.ANYWHERE coloca o % antes e depois da palava = "%joao%"
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Cliente porId(Long id) {
		return manager.find(Cliente.class, id);
	}	

	public List<Cliente> porNome(String nome) {
		return this.manager.createQuery("from Cliente " +
				"where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getResultList();
	}
	
}