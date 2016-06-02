package br.com.deguste.model.bo;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.criterion.Restrictions;

import br.com.deguste.dao.GenericHibernateDAO;
import br.com.deguste.interfaces.ClienteBusiness;
import br.com.deguste.model.entity.Categoria;
import br.com.deguste.model.entity.Cliente;
import br.com.deguste.model.entity.Funcionario;

public class ClienteBO extends GenericHibernateDAO<Cliente> implements ClienteBusiness, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3600735910834492189L;
	
	public ClienteBO(){
		super(Cliente.class);
	}
	
	@Override
	public Cliente salvar(Cliente cliente) throws Exception {


		if(cliente.getId() == null){
			this.verificaCliente(cliente);
			super.create(cliente);
		}else		
			super.update(cliente);

		return cliente;
	}
	
	public void verificaCliente(Cliente cliente) throws Exception{
		try{
			Query queryClientes = em.createQuery("SELECT c FROM Cliente c WHERE "
							+ "LOWER(c.nome) = LOWER(:nome) AND c.cpf =:cpf AND c.ativo =:ativo");
			queryClientes.setParameter("ativo", true);
			queryClientes.setParameter("nome", cliente.getNome());
			queryClientes.setParameter("cpf", cliente.getCpf());
			Number Clientes = (Number) queryClientes.getSingleResult();
			
			if (Clientes.longValue() > 0  ) {
				throw new Exception();
			}
		} catch (NoResultException e) {
			return;
		} catch (Exception e) {
			throw new Exception(
					"Essa Cliente j� foi cadastrado");
		}
	}

	@Override
	public List<Cliente>consultaClienteAtivo(Cliente cliente) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder("SELECT c FROM Cliente c WHERE 1=1");
		
		if(cliente.getNome() != null && !cliente.getNome().isEmpty()){
			query.append(" AND LOWER(c.nome) LIKE LOWER(:nome)");
			parametros.put("nome", "%"+cliente.getNome()+"%");
		}
		
		if(cliente.getCpf() != null && !cliente.getCpf().isEmpty()){
			query.append(" AND c.cpf =:cpf");
			parametros.put("cpf", cliente.getCpf());
		}
		
		query.append(" AND c.ativo =:ativo");
		parametros.put("ativo", true);
		TypedQuery<Cliente> q = em.createQuery(query.toString(), Cliente.class);
		if(parametros != null && !parametros.isEmpty()){
			for(Entry<String, Object> entry : parametros.entrySet()){
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return q.getResultList();
	}
	
	public List<Cliente>getClientesAtivos() throws Exception {
		return em.createQuery("SELECT c FROM Cliente c WHERE c.ativo =:ativo ").setParameter("ativo", true).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> selectFornecedores() {
		return super.em.createQuery("SELECT c FROM Cliente c").getResultList();
	}

}
